package agency.highlysuspect.appendages.render;

import agency.highlysuspect.appendages.parts.Appendage;
import agency.highlysuspect.appendages.parts.AppendageModel;
import agency.highlysuspect.appendages.parts.BodyPart;
import agency.highlysuspect.appendages.parts.Outfit;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

import java.util.stream.Stream;

public class OutfitRenderer {
	public OutfitRenderer(Stream<AppendageRenderer> stream) {
		this.appendageRenderers = stream.toArray(AppendageRenderer[]::new);
	}
	
	private final AppendageRenderer[] appendageRenderers;
	
	public void draw(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityModel<? extends PlayerEntity> playerModel) {
		for(AppendageRenderer r : appendageRenderers) {
			r.draw(matrices, vertexConsumers, light, playerModel);
		}
	}
	
	public static OutfitRenderer getFor(PlayerEntity player) {
		Outfit outfit = new Outfit();
		
		AppendageModel breadModel = new AppendageModel.ItemStackModel().setStack(new ItemStack(Items.FIRE_CORAL));
		
		Appendage breadWing = new Appendage()
			.setModel(breadModel)
			.setMountPoint(BodyPart.TORSO.getMountPointByName("back"))
			.setPosition(new Vec3d(2.5, 7, -1))
			.setRotation(new Vec3d(-10, -85, -10))
			.setScale(new Vec3d(1, 1, 1.2))
			.setSymmetrical(true);
		
		outfit.addAppendage(breadWing);
		
		return outfit.bake();
	}
}
