package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.parts.color.ColorPalette;
import agency.highlysuspect.appendages.util.JsonHelper2;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

public class AppendageTexture {
	private AppendageTextureType type;
	private ColorPalette palette = new ColorPalette(0);
	
	public AppendageTextureType getType() {
		return type;
	}
	
	public AppendageTexture setType(AppendageTextureType type) {
		this.type = type;
		return this;
	}
	
	public ColorPalette getPalette() {
		return palette;
	}
	
	public AppendageTexture setPalette(ColorPalette palette) {
		this.palette = palette;
		return this;
	}
	
	public JsonElement toJson() {
		JsonObject j = new JsonObject();
		
		j.add("type", type.toJson());
		j.add("palette", palette.toJson());
		
		return j;
	}
	
	public static AppendageTexture fromJson(JsonElement je) throws JsonParseException {
		JsonObject j = JsonHelper2.ensureType(je, JsonObject.class);
		
		return new AppendageTexture()
			.setType(AppendageTextureType.fromJson(j.get("type")))
			.setPalette(ColorPalette.fromJson(j.get("palette")));
	}
}
