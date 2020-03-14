package agency.highlysuspect.appendages.parts.color;

public class OutfitPalette {
	public static final int SIZE = 8;
	
	private final int[] colors = new int[SIZE];
	
	public int getColor(int id) {
		return colors[id];
	}
	
	public void setColor(int id, int color) {
		colors[id] = color;
	}
}
