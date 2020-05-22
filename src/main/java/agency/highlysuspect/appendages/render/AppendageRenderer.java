package agency.highlysuspect.appendages.render;

import agency.highlysuspect.appendages.mixin.ModelPartMixin;
import agency.highlysuspect.appendages.parts.BodyPart;
import agency.highlysuspect.appendages.render.model.AppendageModelRenderer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class AppendageRenderer {
	public AppendageRenderer(AppendageModelRenderer appendageModelRenderer, BodyPart.MountPoint mountPoint, Vec3d positionOffset, Vec3d rotationOffset, Vec3d scale) {
		this.appendageModelRenderer = appendageModelRenderer;
		this.mountPoint = mountPoint;
		
		this.positionOffset = positionOffset;
		this.rotationOffset = rotationOffset;
		this.scale = scale;
	}
	
	private final AppendageModelRenderer appendageModelRenderer;
	private final BodyPart.MountPoint mountPoint;
	
	private final Vec3d positionOffset;
	private final Vec3d rotationOffset;
	private final Vec3d scale;
	
	public void draw(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityModel<? extends PlayerEntity> playerModel) {
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
		
		//Everything's set up, delegate to the model
		appendageModelRenderer.draw(matrices, vertexConsumers, light, playerModel);
		
		matrices.pop();
	}
}
