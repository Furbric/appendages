package agency.highlysuspect.appendages.render;

import agency.highlysuspect.appendages.mixin.ModelPartMixin;
import agency.highlysuspect.appendages.parts.BodyPart;
import agency.highlysuspect.appendages.parts.Outfit;
import agency.highlysuspect.appendages.parts.OutfitManager;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

public class AppendagesFeatureRenderer<T extends PlayerEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {
	public AppendagesFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> context) {
		super(context);
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float customAngle, float headYaw, float headPitch) {
		if(entity == null || entity.isInvisible()) return;
		
		PlayerEntityModel<? extends PlayerEntity> playerModel = getContextModel();
		
		Outfit outfit = OutfitManager.getOutfitFor(entity);
		//noinspection ConstantConditions (it's a stubbed method)
		if(outfit == null) return;
		
		outfit.forEachAppendage(appendage -> {
			matrices.push();
			
			/////Coordinate Setup
			
			BodyPart.MountPoint mountPoint = appendage.getMountPoint();
			BodyPart bodyPart = mountPoint.getBodyPart();
			ModelPart playerModelPart = bodyPart.associatedModelPart(playerModel);
			
			//first translate/rotate coordinate system to this body part
			playerModelPart.rotate(matrices); //now (i believe?) up is "along the part", e.g. on the head, up now points from neck to scalp
			
			//next translate/rotate to the mount point on this body part's cuboid
			ModelPart.Cuboid cuboid = ((ModelPartMixin) playerModelPart).getCuboids().get(0);
			mountPoint.applyTransform(cuboid, matrices); //now 0,0,0 is at the mount point and "up" is facing away from the body part
			//todo the orientation isn't correct see the other todo in bodypart
			
			//finally apply user-defined translate/rotate/scales
			Vec3d userTranslate = appendage.getPositionOffset();
			matrices.translate(userTranslate.x, userTranslate.y, userTranslate.z);
			
			//I DONT KNOW WHAT IM DOING
			Vec3d userRotate = appendage.getRotationOffset();
			//actually apply it lol (it's pitch/yaw/roll)
			
			Vec3d userScale = appendage.getScale();
			matrices.scale((float) userScale.x, (float) userScale.y, (float) userScale.z);
			
			/////Drawing
			
			//TODO actually draw a model lmao instead of just your own head !!!
			playerModel.head.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(((AbstractClientPlayerEntity)entity).getSkinTexture())), light, 0xFFFFFF);
			
			matrices.pop();
		});
	}
}
