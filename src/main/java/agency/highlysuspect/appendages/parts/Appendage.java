package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.util.Copyable;
import net.minecraft.util.math.Vec3d;

import java.util.function.Consumer;

public class Appendage implements Copyable<Appendage> {
	public Appendage() {}
	
	public Appendage(Preset<Appendage> preset) {
		this.preset = preset;
	}
	
	private Preset<Appendage> preset;
	
	private AppendageModel model;
	private BodyPart.MountPoint mountPoint;
	
	private Vec3d positionOffset = Vec3d.ZERO;
	private Vec3d rotationOffset = Vec3d.ZERO;
	private Vec3d scale = new Vec3d(1, 1, 1);
	
	public AppendageModel getModel() {
		return model;
	}
	
	public Appendage setModel(AppendageModel model) {
		this.model = model;
		return this;
	}
	
	public Appendage onModel(Consumer<AppendageModel> action) {
		action.accept(model);
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
	
	@Override
	public Appendage copy() {
		Appendage copy = new Appendage();
		
		copy.preset = preset;
		
		copy.model = model.copy();
		
		copy.mountPoint = mountPoint;
		
		copy.positionOffset = positionOffset;
		copy.rotationOffset = rotationOffset;
		copy.scale = scale;
		
		return copy;
	}
	
	public Appendage mirrored() {
		//TODO have some kind of relation between the original and the mirrored version
		Appendage mirror = copy();
		
		mirror.positionOffset = mirror.positionOffset.multiply(-1, 1, 1);
		mirror.rotationOffset = mirror.rotationOffset.multiply(1, -1, -1);
		mirror.scale = mirror.scale.multiply(-1, 1, -1);
		mirror.mountPoint = mirror.mountPoint.getMirrored();
		
		return mirror;
	}
}
