package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.parts.color.OutfitPalette;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Outfit {
	private final List<Appendage> appendages = new ArrayList<>();
	private final OutfitPalette colorPalette = new OutfitPalette();
	
	public void forEachAppendage(Consumer<Appendage> func) {
		appendages.forEach(func);
	}
	
	public List<Appendage> getAppendages() {
		return appendages;
	}
	
	public void addAppendage(Appendage app) {
		appendages.add(app);
	}
	
	public OutfitPalette getPalette() {
		return colorPalette;
	}
}
