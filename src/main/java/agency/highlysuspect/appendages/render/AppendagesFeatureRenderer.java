package agency.highlysuspect.appendages.render;

import agency.highlysuspect.appendages.Init;
import agency.highlysuspect.appendages.mixin.ModelPartMixin;
import agency.highlysuspect.appendages.parts.Appendage;
import agency.highlysuspect.appendages.parts.Outfit;
import agency.highlysuspect.appendages.parts.OutfitManager;
import net.minecraft.client.model.ModelPart;
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
		
		PlayerEntityModel<? extends PlayerEntity> playerModel = getContextModel();
		
		Outfit outfit = OutfitManager.getOutfitFor(entity);
		if(outfit == null) return;
		
		for(Appendage appendage : outfit.getAppendages()) {
			matrices.push();
			
			ModelPart basePart = appendage.getBodyPart().associatedModelPart(playerModel);
			transformToModelPartCenter(matrices, basePart);
			
			//TODO actually draw it lmfao
			
			matrices.pop();
		}
		
		Init.LOGGER.info("LOGSPAM!!!!!!!!!!!");
	}
	
	private static void transformToModelPartCenter(MatrixStack matrices, ModelPart part) {
		part.rotate(matrices);
		ModelPart.Cuboid cuboid = ((ModelPartMixin) part).getCuboids().get(0);
		matrices.translate((cuboid.minX + cuboid.maxX) / 32f, (cuboid.minY + cuboid.maxY) / 32f, (cuboid.minZ + cuboid.maxZ) / 32f);
	}
}
