package agency.highlysuspect.appendages.parts.color;

import agency.highlysuspect.appendages.parts.Outfit;

/**
 * Tagged union for the different things you might have on a color palette.
 * Remember to visit AppendageColorSerde if you add one.
 */
public abstract class AppendageColor {
	public abstract int getColorInContext(Outfit outfit);
	
	public static class Unset extends AppendageColor {
		@Override
		public int getColorInContext(Outfit outfit) {
			return 0;
		}
	}
	
	public static class Fixed extends AppendageColor {
		private int color;
		
		@Override
		public int getColorInContext(Outfit outfit) {
			return color;
		}
		
		public int getColor() {
			return color;
		}
		
		public Fixed setColor(int color) {
			this.color = color;
			return this;
		}
	}
	
	public static class PaletteReference extends AppendageColor {
		private int reference;
		
		boolean spook = false;
		@Override
		public int getColorInContext(Outfit outfit) {
			if(spook) return 0;
			
			//Prevents you from infinitely looping a palette into itself somehow
			spook = true;
			int color = outfit.getPalette().get(reference).getColorInContext(outfit);
			spook = false;
			
			return color;
		}
		
		public int getReference() {
			return reference;
		}
		
		public PaletteReference setReference(int reference) {
			this.reference = reference;
			return this;
		}
	}
	
	//TODO: add a color-cycle color, maybe other combinators
}
