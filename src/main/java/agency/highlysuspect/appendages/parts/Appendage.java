package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.mixin.ModelPartMixin;
import agency.highlysuspect.appendages.resource.AppendageTypesRegistry;
import agency.highlysuspect.appendages.util.JsonHelper2;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class Appendage {
	public Appendage() {}
	
	public Appendage(AppendageType type) {
		this.type = type;
	}
	
	private AppendageType type;
	private AppendageModel model;
	private BodyPart.MountPoint mountPoint;
	
	private Vec3d positionOffset = Vec3d.ZERO;
	private Vec3d rotationOffset = Vec3d.ZERO;
	private Vec3d scale = new Vec3d(1, 1, 1);
	
	public AppendageType getType() {
		return type;
	}
	
	public Appendage setType(AppendageType type) {
		this.type = type;
		return this;
	}
	
	public AppendageModel getModel() {
		return model;
	}
	
	public Appendage setModel(AppendageModel model) {
		this.model = model;
		return this;
	}
	
	public BodyPart.MountPoint getMountPoint() {
		return mountPoint;
	}
	
	public Appendage setMountPoint(BodyPart.MountPoint mountPoint) {
		this.mountPoint = mountPoint;
		return this;
	}
	
	public Vec3d getPositionOffset() {
		return positionOffset;
	}
	
	public Appendage setPositionOffset(Vec3d positionOffset) {
		this.positionOffset = positionOffset;
		return this;
	}
	
	public Vec3d getRotationOffset() {
		return rotationOffset;
	}
	
	public Appendage setRotationOffset(Vec3d rotationOffset) {
		this.rotationOffset = rotationOffset;
		return this;
	}
	
	//more like scalie, am i right
	public Vec3d getScale() {
		return scale;
	}
	
	public Appendage setScale(Vec3d scale) {
		this.scale = scale;
		return this;
	}
	
	public Appendage vibeCheck() {
		Preconditions.checkNotNull(type, "null type!");
		Preconditions.checkNotNull(model, "null model!");
		Preconditions.checkNotNull(mountPoint, "null mount point!");
		return this;
	}
	
	public Appendage copy() {
		Appendage copy = new Appendage();
		
		copy.type = type;
		copy.model = model;
		copy.mountPoint = mountPoint;
		
		copy.positionOffset = positionOffset;
		copy.rotationOffset = rotationOffset;
		copy.scale = scale;
		
		return copy;
	}
	
	public Appendage mirrored() {
		Appendage mirror = copy();
		
		mirror.positionOffset = mirror.positionOffset.multiply(-1, 1, 1);
		mirror.rotationOffset = mirror.rotationOffset.multiply(1, -1, -1);
		mirror.scale = mirror.scale.multiply(-1, 1, -1);
		mirror.mountPoint = mirror.mountPoint.getMirrored();
		
		return mirror;
	}
	
	public JsonElement toJson(AppendageTypesRegistry registry) {
		JsonObject j = new JsonObject();
		
		j.add("type", type.toJson(registry));
		j.add("model", model.toJson(registry));
		j.add("mount_point", mountPoint.toJson(registry));
		
		j.add("position", JsonHelper2.vec3dToArray(positionOffset));
		j.add("rotation", JsonHelper2.vec3dToArray(rotationOffset));
		j.add("scale", JsonHelper2.vec3dToArray(scale));
		
		return j;
	}
	
	public static Appendage fromJson(JsonElement je, AppendageTypesRegistry registry) throws JsonParseException {
		JsonObject j = JsonHelper2.ensureType(je, JsonObject.class);
		Appendage a = new Appendage();
		
		a.setType(AppendageType.fromJson(j.get("type"), registry));
		a.setModel(AppendageModel.fromJson(j.get("model"), registry));
		a.setMountPoint(BodyPart.MountPoint.fromJson(j.get("mount_point"), registry));
		
		a.setPositionOffset(JsonHelper2.getVec3d(j, "position"));
		a.setRotationOffset(JsonHelper2.getVec3d(j, "rotation"));
		a.setScale(JsonHelper2.getVec3d(j, "scale"));
		
		return a;
	}
	
	///// functionality
	
	public void draw(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityModel<? extends PlayerEntity> playerModel) {
		matrices.push();
		
		/////Coordinate Setup
		
		BodyPart bodyPart = mountPoint.getBodyPart();
		ModelPart playerModelPart = bodyPart.associatedModelPart(playerModel);
		
		//first translate/rotate coordinate system to this body part
		playerModelPart.rotate(matrices); //now (i believe?) up is "along the part", e.g. on the head, up now points from neck to scalp
		
		//next translate/rotate to the mount point on this body part's cuboid
		ModelPart.Cuboid cuboid = ((ModelPartMixin) playerModelPart).getCuboids().get(0);
		mountPoint.applyTransform(cuboid, matrices); //now 0, 0, 0 is at the mount point, and "up" is facing away from the body part
		
		//finally apply user-defined translate/rotate/scales
		matrices.translate(positionOffset.x / 16f, positionOffset.y / 16f, positionOffset.z / 16f);
		
		//Yaw first, then pitch, then roll - no particular reason, this just feels the most intuitive for rotating parts
		matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float) rotationOffset.y));
		matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((float) rotationOffset.x));
		matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float) rotationOffset.z));
		
		matrices.scale((float) scale.x, (float) scale.y, (float) scale.z);
		
		model.draw(matrices, vertexConsumers, light, playerModel);
		
		matrices.pop();
	}
}
