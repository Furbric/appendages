package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.parts.color.ColorPalette;
public class AppendageTexture {
	public AppendageTexture() {}
	
	public AppendageTexture(AppendageTextureType type) {
		this.type = type;
	}
	
	private AppendageTextureType type;
	private ColorPalette palette = new ColorPalette(0);
	
	public AppendageTextureType getType() {
		return type;
	}
	
	public AppendageTexture setType(AppendageTextureType type) {
		this.type = type;
		return this;
	}
	
	public ColorPalette getPalette() {
		return palette;
	}
	
	public AppendageTexture setPalette(ColorPalette palette) {
		this.palette = palette;
		return this;
	}
}
