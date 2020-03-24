package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.util.JsonHelper2;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.math.Vec3d;

public class Appendage {
	private AppendageType type; //defines the model
	private AppendageTexture texture;
	
	//Maybe these can be merged into one transformation matrix? but this isn't the place to do it
	//just a note for later...
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
	
	public AppendageTexture getTexture() {
		return texture;
	}
	
	public Appendage setTexture(AppendageTexture texture) {
		this.texture = texture;
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
		//Preconditions.checkNotNull(texture, "null texture!"); //TODO uncomment when textures are here
		Preconditions.checkNotNull(mountPoint, "null mount point!");
		return this;
	}
	
	public Appendage copy() {
		Appendage copy = new Appendage();
		
		copy.type = type;
		copy.texture = texture;
		
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
	
	public JsonElement toJson() {
		JsonObject j = new JsonObject();
		
		j.add("type", type.toJson());
		//j.add("texture", texture.toJson()); //TODO uncomment when textures are here
		j.add("mount_point", mountPoint.toJson());
		
		j.add("position", JsonHelper2.vec3dToArray(positionOffset));
		j.add("rotation", JsonHelper2.vec3dToArray(rotationOffset));
		j.add("scale", JsonHelper2.vec3dToArray(scale));
		
		return j;
	}
	
	public static Appendage fromJson(JsonElement je) throws JsonParseException {
		JsonObject j = JsonHelper2.ensureType(je, JsonObject.class);
		Appendage a = new Appendage();
		
		a.setType(AppendageType.fromJson(j.get("type")));
		//a.setTexture(AppendageTexture.fromJson(j.get("texture"))); //TODO uncomment when textures are here
		a.setMountPoint(BodyPart.MountPoint.fromJson(j.get("mount_point")));
		
		a.setPositionOffset(JsonHelper2.getVec3d(j, "position"));
		a.setRotationOffset(JsonHelper2.getVec3d(j, "rotation"));
		a.setScale(JsonHelper2.getVec3d(j, "scale"));
		
		return a;
	}
}
