package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.parts.color.ColorPalette;
import agency.highlysuspect.appendages.render.model.AppendageItemStackModelRenderer;
import agency.highlysuspect.appendages.render.model.AppendageModelRenderer;
import agency.highlysuspect.appendages.util.TaggedUnionSerde;
import net.minecraft.item.ItemStack;

//TODO write a JSON deserializer for this tagged union
public abstract class AppendageModel {
	public abstract AppendageModelRenderer bake(Outfit outfit, Appendage appendage);
	
	private ColorPalette colorPalette = new ColorPalette();
	
	public ColorPalette getColorPalette() {
		return colorPalette;
	}
	
	public AppendageModel setColorPalette(ColorPalette colorPalette) {
		this.colorPalette = colorPalette;
		return this;
	}
	
	@TaggedUnionSerde.Name("item_stack")
	public static class ItemStackModel extends AppendageModel {
		private ItemStack stack;
		
		public ItemStack getStack() {
			return stack;
		}
		
		public ItemStackModel setStack(ItemStack stack) {
			this.stack = stack;
			return this;
		}
		
		@Override
		public AppendageModelRenderer bake(Outfit outfit, Appendage appendage) {
			return new AppendageItemStackModelRenderer(stack);
		}
	}
}
