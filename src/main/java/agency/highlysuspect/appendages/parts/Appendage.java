package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.parts.color.ColorPalette;
import agency.highlysuspect.appendages.render.AppendageRenderer;
import agency.highlysuspect.appendages.render.model.AppendageModelRenderer;
import agency.highlysuspect.appendages.util.RegistryTypeAdapter;
import com.google.common.base.Preconditions;
import com.google.gson.annotations.JsonAdapter;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.util.math.Vec3d;

import java.util.stream.Stream;

public class Appendage {
	@JsonAdapter(RegistryTypeAdapter.App.class)
	private Appendage preset;
	
	private String name = null;
	private String artist = null;
	
	private AppendageModel model = null;
	private ColorPalette colorPalette = null;
	private BodyPart.MountPoint mountPoint = null;
	
	private Vec3d position = null;
	private Vec3d rotation = null;
	private Vec3d scale = null;
	
	private TriState symmetrical = TriState.DEFAULT;
	
	public Appendage getPreset() {
		return preset;
	}
	
	public Appendage setPreset(Appendage preset) {
		this.preset = preset;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public String resolveName() {
		if(name == null && preset != null) return preset.resolveName();
		else return name;
	}
	
	public Appendage setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String resolveArtist() {
		if(artist == null && preset != null) return preset.resolveArtist();
		else return artist;
	}
	
	public Appendage setArtist(String artist) {
		this.artist = artist;
		return this;
	}
	
	public AppendageModel getModel() {
		return model;
	}
	
	public AppendageModel resolveModel() {
		if(model == null && preset != null) return preset.resolveModel();
		else return model;
	}
	
	public Appendage setModel(AppendageModel model) {
		this.model = model;
		return this;
	}
	
	public ColorPalette getColorPalette() {
		return colorPalette;
	}
	
	public ColorPalette resolveColorPalette() {
		if(colorPalette == null && preset != null) return preset.resolveColorPalette();
		else return colorPalette;
	}
	
	public Appendage setColorPalette(ColorPalette colorPalette) {
		this.colorPalette = colorPalette;
		return this;
	}
	
	public BodyPart.MountPoint getMountPoint() {
		return mountPoint;
	}
	
	public BodyPart.MountPoint resolveMountPoint() {
		if(mountPoint == null && preset != null) return preset.resolveMountPoint();
		else return mountPoint;
	}
	
	public Appendage setMountPoint(BodyPart.MountPoint mountPoint) {
		this.mountPoint = mountPoint;
		return this;
	}
	
	public Vec3d getPosition() {
		return position;
	}
	
	public Vec3d resolvePosition() {
		if(position == null && preset != null) return preset.resolvePosition();
		else return position;
	}
	
	public Appendage setPosition(Vec3d position) {
		this.position = position;
		return this;
	}
	
	public Vec3d getRotation() {
		return rotation;
	}
	
	public Vec3d resolveRotation() {
		if(rotation == null && preset != null) return preset.resolveRotation();
		else return rotation;
	}
	
	public Appendage setRotation(Vec3d rotation) {
		this.rotation = rotation;
		return this;
	}
	
	//more like scalie, am i right
	public Vec3d getScale() {
		return scale;
	}
	
	public Vec3d resolveScale() {
		if(scale == null && preset != null) return preset.resolveScale();
		else return scale;
	}
	
	public Appendage setScale(Vec3d scale) {
		this.scale = scale;
		return this;
	}
	
	public TriState getSymmetrical() {
		return symmetrical;
	}
	
	public TriState resolveSymmetrical() {
		if(symmetrical == TriState.DEFAULT && preset != null) return preset.resolveSymmetrical();
		else return symmetrical;
	}
	
	public Appendage setSymmetrical(TriState symmetrical) {
		this.symmetrical = symmetrical;
		return this;
	}
	
	public Appendage copy() {
		return new Appendage()
			.setPreset(getPreset())
			.setName(getName() + " - Copy")
			.setArtist(getArtist())
			.setModel(getModel().copy())
			.setColorPalette(getColorPalette().copy())
			.setMountPoint(getMountPoint())
			.setPosition(getPosition())
			.setRotation(getRotation())
			.setScale(getScale())
			.setSymmetrical(getSymmetrical());
	}
	
	public Appendage flattened() {
		return new Appendage()
			.setPreset(getPreset())
			.setName(resolveName())
			.setArtist(resolveArtist())
			.setModel(resolveModel())
			.setColorPalette(resolveColorPalette())
			.setMountPoint(resolveMountPoint())
			.setPosition(resolvePosition())
			.setRotation(resolveRotation())
			.setScale(resolveScale())
			.setSymmetrical(resolveSymmetrical());
	}
	
	public void completeDefaultValues() {
		if(name == null) name = "Untitled";
		if(artist == null) artist = "Unknown Artist";
		//if(model == null) //models are required
		if(colorPalette == null) colorPalette = new ColorPalette(0);
		//if(mountPoint == null) //mount points are required
		if(position == null) position = Vec3d.ZERO;
		if(rotation == null) rotation = Vec3d.ZERO;
		if(scale == null) scale = new Vec3d(1, 1, 1);
		if(symmetrical == TriState.DEFAULT) symmetrical = TriState.FALSE;
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
		
		if(symmetrical == TriState.TRUE) {
			return Stream.of(renderer, new AppendageRenderer(
				appendageModelRenderer,
				mountPoint.getMirrored(),
				position.multiply(-1, 1, 1),
				rotation.multiply(1, -1, -1),
				scale.multiply(-1, 1, -1)
			));
		} else {
			return Stream.of(renderer);
		}
	}
}
