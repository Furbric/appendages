package agency.highlysuspect.appendages.parts;

import com.google.common.base.Preconditions;
import net.minecraft.util.math.Vec3d;

public class Appendage {
	public Appendage() {}
	
	private AppendagePreset preset;
	private AppendageModel model;
	private BodyPart.MountPoint mountPoint;
	
	private Vec3d positionOffset = Vec3d.ZERO;
	private Vec3d rotationOffset = Vec3d.ZERO;
	private Vec3d scale = new Vec3d(1, 1, 1);
	
	public AppendagePreset getPreset() {
		return preset;
	}
	
	public Appendage setPreset(AppendagePreset preset) {
		this.preset = preset;
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
		Preconditions.checkNotNull(model, "null model!");
		Preconditions.checkNotNull(mountPoint, "null mount point!");
		return this;
	}
	
	public Appendage copy() {
		Appendage copy = new Appendage();
		
		copy.preset = preset;
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
}
