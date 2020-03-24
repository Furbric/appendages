package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.util.JsonHelper2;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class AppendageTextureType {
	private Identifier id;
	
	public Identifier getId() {
		return id;
	}
	
	public AppendageTextureType setId(Identifier id) {
		this.id = id;
		return this;
	}
	
	public int getTintSlots() {
		//TODO: look this up in some kind of registry; no need to store it here probably
		return 0;
	}
	
	public JsonElement toJson() {
		JsonObject j = new JsonObject();
		
		j.addProperty("id", id.toString());
		
		return j;
	}
	
	public static AppendageTextureType fromJson(JsonElement je) throws JsonParseException {
		JsonObject j = JsonHelper2.ensureType(je, JsonObject.class);
		
		Identifier id = new Identifier(JsonHelper.getString(j, "id"));
		
		return new AppendageTextureType().setId(id);
	}
}
