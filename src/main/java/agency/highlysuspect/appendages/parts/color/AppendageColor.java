package agency.highlysuspect.appendages.parts.color;

import agency.highlysuspect.appendages.parts.Outfit;
import com.google.common.base.Preconditions;

public abstract class AppendageColor {
	public abstract int getColor();
	public abstract void setColor(int newColor);
	
	public static class Standalone extends AppendageColor {
		private int color;
		
		@Override
		public int getColor() {
			return color;
		}
		
		@Override
		public void setColor(int newColor) {
			color = newColor;
		}
	}
	
	public static class PaletteReference extends AppendageColor {
		public PaletteReference(Outfit outfit, int paletteId) {
			Preconditions.checkArgument(paletteId < OutfitPalette.SIZE, "out of range palette id");
			
			this.outfit = outfit;
			this.paletteId = paletteId;
		}
		
		private Outfit outfit;
		private int paletteId;
		
		@Override
		public int getColor() {
			return outfit.getPalette().getColor(paletteId);
		}
		
		@Override
		public void setColor(int newColor) {
			outfit.getPalette().setColor(paletteId, newColor);
		}
	}
}
