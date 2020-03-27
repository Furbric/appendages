package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.resource.AppendageTypesRegistry;
import agency.highlysuspect.appendages.util.JsonHelper2;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.Identifier;

public class AppendageModelType {
	public AppendageModelType(int textureCount) {
		this.textureCount = textureCount;
	}
	
	private final int textureCount;
	
	public Identifier getId(AppendageTypesRegistry registry) {
		return registry.getModelTypes().getId(this);
	}
	
	public int getTextureCount() {
		return textureCount;
	}
	
	public JsonElement toJson(AppendageTypesRegistry registry) {
		return new JsonPrimitive(getId(registry).toString());
	}
	
	public static AppendageModelType fromJson(JsonElement je, AppendageTypesRegistry registry) {
		return registry.getModelType(new Identifier(JsonHelper2.ensureType(je, JsonPrimitive.class).getAsString()));
	}
}
