package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.render.model.AppendageItemStackModelRenderer;
import agency.highlysuspect.appendages.render.model.AppendageModelRenderer;
import agency.highlysuspect.appendages.render.model.builtin.BuiltinType;
import agency.highlysuspect.appendages.util.TaggedUnionSerde;
import com.google.gson.annotations.SerializedName;
import net.minecraft.item.ItemStack;

public abstract class AppendageModel {
	public abstract AppendageModelRenderer bake(Outfit outfit, Appendage appendage);
	public abstract AppendageModel copy();
	
	@TaggedUnionSerde.Name("item_stack")
	public static class ItemStackModel extends AppendageModel {
		@SerializedName("item")
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
			int color = appendage.resolveColorPalette().get(0).getColorInContext(outfit).orElse(0xFFFFFF);
			
			return new AppendageItemStackModelRenderer(stack, color);
		}
		
		@Override
		public AppendageModel copy() {
			return new ItemStackModel().setStack(getStack().copy());
		}
	}
	
	@TaggedUnionSerde.Name("builtin")
	public static class BuiltinModel extends AppendageModel {
		private BuiltinType type;
		
		public BuiltinType getType() {
			return type;
		}
		
		public BuiltinModel setType(BuiltinType type) {
			this.type = type;
			return this;
		}
		
		@Override
		public AppendageModelRenderer bake(Outfit outfit, Appendage appendage) {
			return null;
		}
		
		@Override
		public AppendageModel copy() {
			return new BuiltinModel().setType(getType());
		}
	}
}
