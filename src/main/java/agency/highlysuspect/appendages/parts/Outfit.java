package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.parts.color.OutfitPalette;

import java.util.ArrayList;
import java.util.List;

public class Outfit {
	private final List<Appendage> appendages = new ArrayList<>();
	private final OutfitPalette colorPalette = new OutfitPalette();
	
	public List<Appendage> getAppendages() {
		return appendages;
	}
	
	public OutfitPalette getPalette() {
		return colorPalette;
	}
}
