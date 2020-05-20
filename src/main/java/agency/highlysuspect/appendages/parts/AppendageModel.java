package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.util.Copyable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class AppendageModel implements Copyable<AppendageModel> {
	public AppendageModel() {}
	
	public AppendageModel(Preset<AppendageModel> preset) {
		this.preset = preset;
	}
	
	private Preset<AppendageModel> preset;
	
	//TODO a ModelIdentifier is not a unique way to define a model. Probably need to bring out subclasses
	private ModelIdentifier modelPath;
	private AppendageTexture[] textures = new AppendageTexture[0];
	
	public ModelIdentifier getModelPath() {
		return modelPath;
	}
	
	public AppendageModel setModelPath(ModelIdentifier modelPath) {
		this.modelPath = modelPath;
		return this;
	}
	
	public int getTextureCount() {
		return textures.length;
	}
	
	public AppendageModel setTextureCount(int textureCount) {
		//TODO maybe copy the old ones over, idk, probably doesn't matter
		this.textures = new AppendageTexture[textureCount];
		
		return this;
	}
	
	public AppendageTexture[] getTextures() {
		return textures;
	}
	
	public AppendageModel setTextures(AppendageTexture[] textures) {
		this.textures = textures;
		return this;
	}
	
	@Override
	public AppendageModel copy() {
		AppendageModel copy = new AppendageModel();
		
		copy.preset = preset;
		copy.modelPath = modelPath;
		copy.textures = copyArray(textures, AppendageTexture.class);
		
		return copy;
	}
	
	///// functionality
	
	public void draw(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityModel<? extends PlayerEntity> playerModel) {
		ItemStack stack = new ItemStack(Items.BREAD);
		MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, 0xFFFFFF, matrices, vertexConsumers);
	}
}
