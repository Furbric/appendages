package agency.highlysuspect.appendages.render.model;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class AppendageItemStackModelRenderer extends AppendageModelRenderer {
	public AppendageItemStackModelRenderer(ItemStack stack, int tint) {
		this.stack = stack;
		this.tint = tint;
	}
	
	private final ItemStack stack;
	private final int tint;
	
	@Override
	public void draw(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityModel<? extends PlayerEntity> playerModel) {
		MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, tint, matrices, vertexConsumers);
	}
}
