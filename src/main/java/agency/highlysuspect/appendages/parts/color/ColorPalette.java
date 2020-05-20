package agency.highlysuspect.appendages.parts.color;

import agency.highlysuspect.appendages.util.Copyable;

public class ColorPalette implements Copyable<ColorPalette> {
	public ColorPalette() {
		this(0);
	}
	
	public ColorPalette(int size) {
		colors = new AppendageColor[size];
		for(int i = 0; i < size; i++) {
			colors[i] = new AppendageColor.Unset();
		}
	}
	
	private ColorPalette(AppendageColor[] colors) {
		this.colors = colors;
	}
	
	public final AppendageColor[] colors;
	
	public AppendageColor get(int id) {
		return colors[id];
	}
	
	public void set(int id, AppendageColor color) {
		colors[id] = color;
	}
	
	@Override
	public ColorPalette copy() {
		return new ColorPalette(copyArray(colors, AppendageColor.class));
	}
}
