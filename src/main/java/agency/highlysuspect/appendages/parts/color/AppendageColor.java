package agency.highlysuspect.appendages.parts.color;

import agency.highlysuspect.appendages.parts.Outfit;

import java.util.Optional;

/**
 * Tagged union for the different things you might have on a color palette.
 * Remember to visit AppendageColorSerde if you add one.
 */
//TODO it might be a good idea to not bake these to ints just yet when baking an outfit
public abstract class AppendageColor {
	public abstract Optional<Integer> getColorInContext(Outfit outfit);
	public abstract AppendageColor copy();
	
	public static class Unset extends AppendageColor {
		public static final Unset INST = new Unset();
		
		@Override
		public Optional<Integer> getColorInContext(Outfit outfit) {
			return Optional.empty();
		}
		
		@Override
		public AppendageColor copy() {
			return new Unset();
		}
	}
	
	public static class Fixed extends AppendageColor {
		private int color;
		
		@Override
		public Optional<Integer> getColorInContext(Outfit outfit) {
			return Optional.of(color);
		}
		
		public int getColor() {
			return color;
		}
		
		public Fixed setColor(int color) {
			this.color = color;
			return this;
		}
		
		@Override
		public AppendageColor copy() {
			return new Fixed().setColor(getColor());
		}
	}
	
	public static class PaletteReference extends AppendageColor {
		private int reference;
		
		boolean spook = false;
		@Override
		public Optional<Integer> getColorInContext(Outfit outfit) {
			if(spook) return Optional.empty();
			
			//Prevents you from infinitely looping a palette into itself somehow
			spook = true;
			Optional<Integer> uwu = outfit.getColorPalette().get(reference).getColorInContext(outfit);
			spook = false;
			
			return uwu;
		}
		
		public int getReference() {
			return reference;
		}
		
		public PaletteReference setReference(int reference) {
			this.reference = reference;
			return this;
		}
		
		@Override
		public AppendageColor copy() {
			return new PaletteReference().setReference(getReference());
		}
	}
}
