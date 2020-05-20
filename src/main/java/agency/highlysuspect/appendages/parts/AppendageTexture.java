package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.parts.color.ColorPalette;
import agency.highlysuspect.appendages.util.Copyable;
import net.minecraft.util.Identifier;

public class AppendageTexture implements Copyable<AppendageTexture>  {
	private Identifier texture; //TODO something something sprite atlases
	private ColorPalette palette = new ColorPalette(0); //TODO palette size based on the texture.
	
	public Identifier getTexture() {
		return texture;
	}
	
	public AppendageTexture setTexture(Identifier texture) {
		this.texture = texture;
		return this;
	}
	
	public ColorPalette getPalette() {
		return palette;
	}
	
	public AppendageTexture setPalette(ColorPalette palette) {
		this.palette = palette;
		return this;
	}
	
	@Override
	public AppendageTexture copy() {
		AppendageTexture copy = new AppendageTexture();
		
		copy.texture = texture;
		copy.palette = palette.copy();
		
		return copy;
	}
}
