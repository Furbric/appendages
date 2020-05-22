package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.Init;
import agency.highlysuspect.appendages.parts.color.ColorPalette;
import agency.highlysuspect.appendages.render.OutfitRenderer;

import java.util.ArrayList;
import java.util.List;

public class Outfit {
	private List<Appendage> appendages = new ArrayList<>();
	private ColorPalette colorPalette = new ColorPalette();
	
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
		Init.LOGGER.info(Init.GSON.toJson(this));
		
		return new OutfitRenderer(appendages.stream().flatMap(a -> a.bake(this)));
	}
}
