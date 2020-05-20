package agency.highlysuspect.appendages.parts.color;

public class ColorPalette {
	public ColorPalette(int size) {
		colors = new AppendageColor[size];
		for(int i = 0; i < size; i++) {
			colors[i] = new AppendageColor.Unset();
		}
	}
	
	public final AppendageColor[] colors;
	
	public AppendageColor get(int id) {
		return colors[id];
	}
	
	public void set(int id, AppendageColor color) {
		colors[id] = color;
	}
}
