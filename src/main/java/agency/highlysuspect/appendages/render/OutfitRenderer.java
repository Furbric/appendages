package agency.highlysuspect.appendages.render;

import agency.highlysuspect.appendages.Init;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

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
		return Init.OUTFIT_REGISTRY.get(new Identifier("appendages", "yes")).bake();
		/*
		Outfit outfit = new Outfit();
		
		AppendageModel breadModel = new AppendageModel.ItemStackModel().setStack(new ItemStack(Items.FIRE_CORAL));
		
		Appendage breadWing = new Appendage()
			.setModel(breadModel)
			.setMountPoint(BodyPart.TORSO.getMountPointByName("back"))
			.setPosition(new Vec3d(2.5, 7, -1))
			.setRotation(new Vec3d(-10, -85, -10))
			.setScale(new Vec3d(1, 1, 1.2))
			.setSymmetrical(TriState.TRUE);
		
		outfit.addAppendage(breadWing);
		
		return outfit.bake();*/
	}
}
