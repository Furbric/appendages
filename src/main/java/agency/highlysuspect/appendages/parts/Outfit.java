package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.parts.color.ColorPalette;

import java.util.ArrayList;
import java.util.List;

public class Outfit {
	private String name;
	private List<Appendage> appendages = new ArrayList<>();
	private ColorPalette palette = new ColorPalette(8);
	
	public String getName() {
		return name;
	}
	
	public Outfit setName(String name) {
		this.name = name;
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
		appendages.add(appendage);
		return this;
	}
	
	public ColorPalette getPalette() {
		return palette;
	}
	
	public Outfit setPalette(ColorPalette palette) {
		this.palette = palette;
		return this;
	}
}
