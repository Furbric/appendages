package agency.highlysuspect.appendages.render;

import agency.highlysuspect.appendages.parts.Outfit;
import agency.highlysuspect.appendages.parts.OutfitManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

public class AppendagesFeatureRenderer<T extends PlayerEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {
	public AppendagesFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> context) {
		super(context);
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float customAngle, float headYaw, float headPitch) {
		if(entity == null || entity.isInvisible()) return;
		
		Outfit outfit = OutfitManager.getOutfitFor(entity);
		//noinspection ConstantConditions (it's a stubbed method, TODO remove this)
		if(outfit == null) return;
		
		PlayerEntityModel<? extends PlayerEntity> playerModel = getContextModel();
		outfit.getAppendages().forEach(appendage -> appendage.draw(matrices, vertexConsumers, light, playerModel));
	}
}
