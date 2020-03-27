package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.resource.AppendageTypesRegistry;
import agency.highlysuspect.appendages.util.JsonHelper2;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

public class AppendageModel {
	public AppendageModel() {}
	
	public AppendageModel(AppendageModelType type) {
		this.type = type;
		this.textures = new ArrayList<>(type.getTextureCount()); //TODO fixed-size lists? are those a thing
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
		for(int i = 0; i < type.getTextureCount(); i++) {
			Preconditions.checkNotNull(textures.get(i), "null texture at " + i);
		}
		return this;
	}
	
	public JsonElement toJson(AppendageTypesRegistry registry) {
		JsonObject j = new JsonObject();
		
		j.add("type", type.toJson(registry));
		
		if(!textures.isEmpty()) {
			j.add("textures", JsonHelper2.listToJsonArray(textures, tex -> tex.toJson(registry)));
		}
		
		return j;
	}
	
	public static AppendageModel fromJson(JsonElement je, AppendageTypesRegistry registry) {
		JsonObject j = JsonHelper2.ensureType(je, JsonObject.class);
		
		return new AppendageModel()
			.setType(AppendageModelType.fromJson(JsonHelper.getObject(j, "type"), registry))
			.setTextures(JsonHelper2.getList(j, "textures", jj -> AppendageTexture.fromJson(jj, registry)));
	}
	
	///// functionality
	
	public void draw(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityModel<? extends PlayerEntity> playerModel) {
		ItemStack stack = new ItemStack(Items.BREAD);
		MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, 0xFFFFFF, matrices, vertexConsumers);
	}
}
