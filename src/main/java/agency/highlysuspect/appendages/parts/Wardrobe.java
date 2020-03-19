package agency.highlysuspect.appendages.parts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Wardrobe {
	private final Map<String, Outfit> outfits = new HashMap<>();
	
	public Collection<Outfit> getOutfits() {
		return outfits.values();
	}
	
	public void addOutfit(Outfit outfit) {
		outfits.put(outfit.getName(), outfit);
	}
	
	public void removeOutfit(Outfit outfit) {
		removeOutfitByName(outfit.getName());
	}
	
	public Outfit getOutfitByName(String name) {
		return outfits.get(name);
	}
	
	public void removeOutfitByName(String name) {
		outfits.remove(name);
	}
	
	
}
