package agency.highlysuspect.appendages.render.model;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

public abstract class AppendageModelRenderer {
	public abstract void draw(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityModel<? extends PlayerEntity> playerModel);
}
