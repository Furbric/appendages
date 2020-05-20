package agency.highlysuspect.appendages.render;

import agency.highlysuspect.appendages.mixin.ModelPartMixin;
import agency.highlysuspect.appendages.parts.AppendageModel;
import agency.highlysuspect.appendages.parts.BodyPart;
import agency.highlysuspect.appendages.parts.Outfit;
import agency.highlysuspect.appendages.parts.OutfitManager;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class AppendagesFeatureRenderer<T extends PlayerEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {
	public AppendagesFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> context) {
		super(context);
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float customAngle, float headYaw, float headPitch) {
		if(entity == null || entity.isInvisible()) return;
		
		Outfit outfit = OutfitManager.getOutfitFor(entity);
		if(outfit == null) return;
		
		PlayerEntityModel<? extends PlayerEntity> playerModel = getContextModel();
		outfit.getAppendages().forEach(appendage -> {
			//TODO should i move this back into the appendage code, and not a separate thing that reads from it
			//Encapsulation is hard all my homies hate encapsulation
			
			BodyPart.MountPoint mountPoint = appendage.getMountPoint();
			Vec3d positionOffset = appendage.getPositionOffset();
			Vec3d rotationOffset = appendage.getRotationOffset();
			Vec3d scale = appendage.getScale();
			AppendageModel model = appendage.getModel();
			
			matrices.push();
			
			/////Coordinate Setup
			
			BodyPart bodyPart = mountPoint.getBodyPart();
			ModelPart playerModelPart = bodyPart.associatedModelPart(playerModel);
			
			//first translate/rotate coordinate system to this body part
			playerModelPart.rotate(matrices); //now (i believe?) up is "along the part", e.g. on the head, up now points from neck to scalp
			
			//next translate/rotate to the mount point on this body part's cuboid
			ModelPart.Cuboid cuboid = ((ModelPartMixin) playerModelPart).getCuboids().get(0);
			mountPoint.applyTransform(cuboid, matrices); //now 0, 0, 0 is at the mount point, and "up" is facing away from the body part
			
			//finally apply user-defined translate/rotate/scales
			matrices.translate(positionOffset.x / 16f, positionOffset.y / 16f, positionOffset.z / 16f);
			
			//Yaw first, then pitch, then roll - no particular reason, this just feels the most intuitive for rotating parts
			matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float) rotationOffset.y));
			matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion((float) rotationOffset.x));
			matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float) rotationOffset.z));
			
			matrices.scale((float) scale.x, (float) scale.y, (float) scale.z);
			
			model.draw(matrices, vertexConsumers, light, playerModel);
			
			matrices.pop();
		});
	}
}
