package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.resource.AppendageTypesRegistry;
import agency.highlysuspect.appendages.util.JsonHelper2;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.JsonHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public enum BodyPart {
	HEAD, TORSO, RIGHT_ARM, LEFT_ARM, RIGHT_LEG, LEFT_LEG,
	;
	
	public ModelPart associatedModelPart(PlayerEntityModel<?> model) {
		switch(this) {
			case HEAD: return model.head;
			case TORSO: return model.torso;
			case RIGHT_ARM: return model.rightArm;
			case LEFT_ARM: return model.leftArm;
			case RIGHT_LEG: return model.rightLeg;
			case LEFT_LEG: return model.leftLeg;
			default: throw new IllegalStateException("BodyPart with no model part assigned");
		}
	}
	
	public BodyPart mirroredPart() {
		switch(this) {
			case LEFT_ARM: return RIGHT_ARM;
			case RIGHT_ARM: return LEFT_ARM;
			case RIGHT_LEG: return LEFT_LEG;
			case LEFT_LEG: return RIGHT_LEG;
			default: return this;
		}
	}
	
	private static final Map<BodyPart, Map<String, MountPoint>> mountsByPart = new HashMap<>();
	
	public Collection<MountPoint> getAvailableMountPoints() {
		return mountsByPart.get(this).values();
	}
	
	public MountPoint getMountPointByName(String name) {
		return mountsByPart.get(this).get(name);
	}
	
	static {
		//N.B. Cuboid coordinates are in "minecraft pixels" (so, the head is 8 cuboid units tall)
		//When rendering the actual cuboid, they are divided by 16.
		//So when I translate along the cuboid's rendered position, I also need to divide by 16.
		for(BodyPart part : values()) {
			Map<String, MountPoint> points = new HashMap<>();
			
			points.put("origin", new MountPoint(part, "origin", (cuboid, stack) -> {
				//average the x, y, and z coordinates to go to the center of the cuboid
				stack.translate((cuboid.minX + cuboid.maxX) / 32f, (cuboid.minY + cuboid.maxY) / 32f, (cuboid.minZ + cuboid.maxZ) / 32f);
			}));
			points.put("top", new MountPoint(part, "top", (cuboid, stack) -> {
				//average the x and z coordinates, but use the top y coordinate
				stack.translate((cuboid.minX + cuboid.maxX) / 32f, cuboid.minY / 16f, (cuboid.minZ + cuboid.maxZ) / 32f);
				stack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180));
			}));
			points.put("bottom", new MountPoint(part, "bottom", (cuboid, stack) -> {
				//average the x and z coordinates, but use the bottom y coordinate
				stack.translate((cuboid.minX + cuboid.maxX) / 32f, cuboid.maxY / 16f, (cuboid.minZ + cuboid.maxZ) / 32f);
			}));
			points.put("left", new MountPoint(part, "left", (cuboid, stack) -> {
				//average the y and z coordinates, but use the left x coordinate
				stack.translate(cuboid.maxX / 16f, (cuboid.minY + cuboid.maxY) / 32f, (cuboid.minZ + cuboid.maxZ) / 32f);
				stack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-90));
			}));
			points.put("right", new MountPoint(part, "right", (cuboid, stack) -> {
				//average the y and z coordinates, but use the right x coordinate
				stack.translate(cuboid.minX / 16f, (cuboid.minY + cuboid.maxY) / 32f, (cuboid.minZ + cuboid.maxZ) / 32f);
				stack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90));
			}));
			points.put("front", new MountPoint(part, "front", (cuboid, stack) -> {
				//average the x and y coordinates, but use the front z coordinate
				stack.translate((cuboid.minX + cuboid.maxX) / 32f, (cuboid.minY + cuboid.maxY) / 32f, cuboid.minZ / 16f);
				stack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90));
			}));
			points.put("back", new MountPoint(part, "back", (cuboid, stack) -> {
				//average the x and y coordinates, but use the back z coordinate
				stack.translate((cuboid.minX + cuboid.maxX) / 32f, (cuboid.minY + cuboid.maxY) / 32f, cuboid.maxZ / 16f);
				stack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
			}));
			
			mountsByPart.put(part, points);
		}
		
		mountsByPart.get(TORSO).put("tail", new MountPoint(TORSO, "tail", (cuboid, stack) -> {
			//"back", but also has the transformation from "bottom" applied
			stack.translate((cuboid.minX + cuboid.maxX) / 32f, cuboid.maxY / 16f, cuboid.maxZ / 16f);
			stack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
		}));
	}
	
	//I hesitate to make this an enum, because not all body parts have the same set of mount points
	//E.g. tail mount point
	public static class MountPoint {
		private MountPoint(BodyPart bodyPart, String name, BiConsumer<ModelPart.Cuboid, MatrixStack> setupFunc) {
			this.bodyPart = bodyPart;
			this.name = name;
			this.setupFunc = setupFunc;
		}
		
		private final BodyPart bodyPart;
		private final String name;
		private final BiConsumer<ModelPart.Cuboid, MatrixStack> setupFunc;
		
		public BodyPart getBodyPart() {
			return bodyPart;
		}
		
		public String getName() {
			return name;
		}
		
		public void applyTransform(ModelPart.Cuboid cuboid, MatrixStack stack) {
			setupFunc.accept(cuboid, stack);
		}
		
		public MountPoint getMirrored() {
			//TODO this is HOT GARBAGE please clean this up
			BodyPart mirroredPart = bodyPart.mirroredPart();
			if(name.equals("left")) {
				return mirroredPart.getMountPointByName("right");
			} else if(name.equals("right")) {
				return mirroredPart.getMountPointByName("left");
			} else return mirroredPart.getMountPointByName(name);
		}
		
		public JsonElement toJson(AppendageTypesRegistry registry) {
			JsonObject j = new JsonObject();
			
			j.addProperty("body_part", JsonHelper2.enumToName(bodyPart));
			j.addProperty("mount_point", getName());
			
			return j;
		}
		
		public static MountPoint fromJson(JsonElement je, AppendageTypesRegistry registry) throws JsonParseException {
			JsonObject j = JsonHelper2.ensureType(je, JsonObject.class);
			
			BodyPart bodyPart = JsonHelper2.nameToEnum(JsonHelper.getString(j, "body_part"), BodyPart.class);
			String name = JsonHelper.getString(j, "mount_point");
			
			MountPoint point = bodyPart.getMountPointByName(name);
			if (point == null) throw new JsonSyntaxException("No mount point named " + name + " on part " + bodyPart.name());
			
			return point;
		}
	}
}
