package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.Init;
import agency.highlysuspect.appendages.parts.color.ColorPalette;
import agency.highlysuspect.appendages.render.OutfitRenderer;

import java.util.ArrayList;
import java.util.List;

public class Outfit {
	private String name;
	private String artist;
	private List<Appendage> appendages = new ArrayList<>();
	private ColorPalette colorPalette = new ColorPalette();
	
	public String getName() {
		return name;
	}
	
	public Outfit setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public Outfit setArtist(String artist) {
		this.artist = artist;
		return this;
	}
	
	public List<Appendage> getAppendages() {
		return appendages;
	}
	
	public Outfit setAppendages(List<Appendage> appendages) {
		this.appendages = appendages;
		return this;
	}
	
	public Outfit addAppendage(Appendage appendage) {
		this.appendages.add(appendage);
		return this;
	}
	
	public ColorPalette getColorPalette() {
		return colorPalette;
	}
	
	public Outfit setColorPalette(ColorPalette colorPalette) {
		this.colorPalette = colorPalette;
		return this;
	}
	
	public OutfitRenderer bake() {
		Outfit hahaYes = Init.GSON.fromJson(Init.GSON.toJson(Init.GSON.fromJson(Init.GSON.toJson(this), Outfit.class)), Outfit.class);
		Init.LOGGER.info(Init.GSON.toJson(hahaYes));
		
		return new OutfitRenderer(hahaYes.appendages.stream().flatMap(a -> a.flattened().bake(hahaYes)));
	}
}
