package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.parts.color.AppendageColor;
import agency.highlysuspect.appendages.render.AppendageTexture;
import com.google.common.base.Preconditions;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class Appendage {
	private AppendageType type; //defines the model
	private AppendageTexture texture;
	private List<AppendageColor> colors = new ArrayList<>();
	
	//Maybe these can be merged into one transformation matrix? but this isn't the place to do it
	//just a note for later...
	private BodyPart.MountPoint mountPoint;
	private Vec3d positionOffset = Vec3d.ZERO;
	private Vec3d rotationOffset = Vec3d.ZERO;
	private Vec3d scale = new Vec3d(1, 1, 1);
	
	public AppendageType getType() {
		return type;
	}
	
	public void setType(AppendageType type) {
		this.type = type;
	}
	
	public AppendageTexture getTexture() {
		return texture;
	}
	
	public void setTexture(AppendageTexture texture) {
		this.texture = texture;
	}
	
	public BodyPart.MountPoint getMountPoint() {
		return mountPoint;
	}
	
	public void setMountPoint(BodyPart.MountPoint mountPoint) {
		this.mountPoint = mountPoint;
	}
	
	public Vec3d getPositionOffset() {
		return positionOffset;
	}
	
	public void setPositionOffset(Vec3d positionOffset) {
		this.positionOffset = positionOffset;
	}
	
	public Vec3d getRotationOffset() {
		return rotationOffset;
	}
	
	public void setRotationOffset(Vec3d rotationOffset) {
		this.rotationOffset = rotationOffset;
	}
	
	//more like scalie, am i right
	public Vec3d getScale() {
		return scale;
	}
	
	public void setScale(Vec3d scale) {
		this.scale = scale;
	}
	
	public Appendage copy() {
		Appendage copy = new Appendage();
		
		copy.mountPoint = mountPoint;
		copy.texture = texture;
		copy.colors = colors;
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
	
	public static class Builder {
		private final Appendage build = new Appendage();
		
		public Builder setType(AppendageType type) {
			build.setType(type);
			return this;
		}
		
		public Builder setTexture(AppendageTexture texture) {
			build.setTexture(texture);
			return this;
		}
		
		public Builder setMountPoint(BodyPart.MountPoint mountPoint) {
			build.setMountPoint(mountPoint);
			return this;
		}
		
		public Builder setPositionOffset(Vec3d position) {
			build.setPositionOffset(position);
			return this;
		}
		
		public Builder setRotationOffset(Vec3d rotation) {
			build.setRotationOffset(rotation);
			return this;
		}
		
		public Builder setScale(Vec3d scale) {
			build.setScale(scale);
			return this;
		}
		
		public Appendage build() {
			Preconditions.checkNotNull(build.type, "no type");
			//TODO uncomment Preconditions.checkNotNull(build.texture, "no texture");
			Preconditions.checkNotNull(build.mountPoint, "no mount point");
			return build;
		}
	}
}
