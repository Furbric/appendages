package agency.highlysuspect.appendages.parts.color;

public class ColorPalette {
	public ColorPalette() {
		this(0);
	}
	
	public ColorPalette(int size) {
		colors = new AppendageColor[size];
		for(int i = 0; i < size; i++) {
			colors[i] = AppendageColor.Unset.INST;
		}
	}
	
	private ColorPalette(AppendageColor[] colors) {
		this.colors = colors;
	}
	
	public final AppendageColor[] colors;
	
	public AppendageColor get(int id) {
		if(id > colors.length) return AppendageColor.Unset.INST;
		return colors[id];
	}
	
	public void set(int id, AppendageColor color) {
		colors[id] = color;
	}
	
	public int size() {
		return colors.length;
	}
	
	public ColorPalette copy() {
		AppendageColor[] uwu = new AppendageColor[colors.length];
		for (int i = 0; i < colors.length; i++) {
			uwu[i] = colors[i].copy();
		}
		return new ColorPalette(uwu);
	}
}
