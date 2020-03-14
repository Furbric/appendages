package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.parts.color.AppendageColor;
import agency.highlysuspect.appendages.render.AppendageTexture;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class Appendage {
	private AppendageType type; //defines the model
	private AppendageTexture texture;
	private BodyPart bodyPart;
	private List<AppendageColor> colors;
	
	//Maybe these can be merged into one transformation matrix? but this isn't the place to do it
	private Vec3d position;
	private Vec3d rotation;
	private Vec3d scale;
	
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
	
	public BodyPart getBodyPart() {
		return bodyPart;
	}
	
	public void setBodyPart(BodyPart bodyPart) {
		this.bodyPart = bodyPart;
	}
	
	public List<AppendageColor> getColors() {
		return colors;
	}
	
	public void setColors(List<AppendageColor> colors) {
		this.colors = colors;
	}
	
	public Vec3d getPosition() {
		return position;
	}
	
	public void setPosition(Vec3d position) {
		this.position = position;
	}
	
	public Vec3d getRotation() {
		return rotation;
	}
	
	public void setRotation(Vec3d rotation) {
		this.rotation = rotation;
	}
	
	public Vec3d getScale() {
		return scale;
	}
	
	public void setScale(Vec3d scale) {
		this.scale = scale;
	}
}
