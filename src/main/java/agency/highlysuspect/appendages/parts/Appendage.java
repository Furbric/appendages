package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.render.AppendageRenderer;
import agency.highlysuspect.appendages.render.model.AppendageModelRenderer;
import com.google.common.base.Preconditions;
import net.minecraft.util.math.Vec3d;

import java.util.stream.Stream;

public class Appendage {
	private AppendageModel model = null;
	private BodyPart.MountPoint mountPoint = null;
	
	private Vec3d position = Vec3d.ZERO;
	private Vec3d rotation = Vec3d.ZERO;
	private Vec3d scale = ONE;
	private static final Vec3d ONE = new Vec3d(1, 1, 1);
	
	private boolean symmetrical;
	
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
	
	public Vec3d getPosition() {
		return position;
	}
	
	public Appendage setPosition(Vec3d position) {
		this.position = position;
		return this;
	}
	
	public Vec3d getRotation() {
		return rotation;
	}
	
	public Appendage setRotation(Vec3d rotation) {
		this.rotation = rotation;
		return this;
	}
	
	public Vec3d getScale() {
		return scale;
	}
	
	public Appendage setScale(Vec3d scale) {
		this.scale = scale;
		return this;
	}
	
	public boolean isSymmetrical() {
		return symmetrical;
	}
	
	public Appendage setSymmetrical(boolean symmetrical) {
		this.symmetrical = symmetrical;
		return this;
	}
	
	public Appendage copy() {
		return new Appendage()
			.setModel(getModel())
			.setMountPoint(getMountPoint())
			.setPosition(getPosition())
			.setRotation(getRotation())
			.setScale(getScale())
			.setSymmetrical(isSymmetrical());
	}
	
	public Stream<AppendageRenderer> bake(Outfit outfit) {
		Preconditions.checkNotNull(model, "Can't bake, no model");
		Preconditions.checkNotNull(mountPoint, "Can't bake, no mount point");
		
		AppendageModelRenderer appendageModelRenderer = model.bake(outfit, this);
		
		AppendageRenderer renderer = new AppendageRenderer(
			appendageModelRenderer,
			mountPoint,
			position,
			rotation,
			scale
		);
		
		if(!isSymmetrical()) return Stream.of(renderer);
		else return Stream.of(renderer, new AppendageRenderer(
			appendageModelRenderer,
			mountPoint.getMirrored(),
			position.multiply(-1, 1, 1),
			rotation.multiply(1, -1, -1),
			scale.multiply(-1, 1, -1)
		));
	}
}
