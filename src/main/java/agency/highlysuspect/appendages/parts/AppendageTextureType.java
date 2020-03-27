package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.resource.AppendageTypesRegistry;
import agency.highlysuspect.appendages.util.JsonHelper2;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.Identifier;

public class AppendageTextureType {
	public AppendageTextureType(int tintSlots) {
		this.tintSlots = tintSlots;
	}
	
	private final int tintSlots;
	
	public Identifier getId(AppendageTypesRegistry registry) {
		return registry.getTextureTypes().getId(this);
	}
	
	public int getTintSlots() {
		return tintSlots;
	}
	
	public JsonElement toJson(AppendageTypesRegistry registry) {
		return new JsonPrimitive(getId(registry).toString());
	}
	
	public static AppendageTextureType fromJson(JsonElement je, AppendageTypesRegistry registry) throws JsonParseException {
		return registry.getTextureType(new Identifier(JsonHelper2.ensureType(je, JsonPrimitive.class).getAsString()));
	}
}
