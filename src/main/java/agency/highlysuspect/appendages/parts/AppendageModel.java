package agency.highlysuspect.appendages.parts;

import com.google.common.base.Preconditions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class AppendageModel {
	public AppendageModel() {}
	
	public AppendageModel(AppendageModelType type) {
		this.type = type;
		this.textures = new ArrayList<>(type.getTextureCount()); //TODO ideally, an int -> texture map, not a list
	}
	
	private AppendageModelType type;
	private List<AppendageTexture> textures;
	
	public AppendageModelType getType() {
		return type;
	}
	
	public AppendageModel setType(AppendageModelType type) {
		this.type = type;
		return this;
	}
	
	public List<AppendageTexture> getTextures() {
		return textures;
	}
	
	public AppendageModel setTextures(List<AppendageTexture> textures) {
		this.textures = textures;
		return this;
	}
	
	public AppendageModel vibeCheck() {
		Preconditions.checkNotNull(type, "null type!");
		Preconditions.checkArgument(type.getTextureCount() == textures.size(), "differing texture count, expected " + type.getTextureCount() + " found " + textures.size());
		for(int i = 0; i < type.getTextureCount(); i++) {
			Preconditions.checkNotNull(textures.get(i), "null texture at " + i);
		}
		return this;
	}
	
	///// functionality
	
	public void draw(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityModel<? extends PlayerEntity> playerModel) {
		ItemStack stack = new ItemStack(Items.BREAD);
		MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, 0xFFFFFF, matrices, vertexConsumers);
	}
}
