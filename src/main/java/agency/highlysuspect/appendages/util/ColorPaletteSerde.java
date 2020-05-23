package agency.highlysuspect.appendages.util;

import agency.highlysuspect.appendages.parts.color.AppendageColor;
import agency.highlysuspect.appendages.parts.color.ColorPalette;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ColorPaletteSerde implements JsonSerializer<ColorPalette>, JsonDeserializer<ColorPalette> {
	@Override
	public JsonElement serialize(ColorPalette src, Type typeOfSrc, JsonSerializationContext context) {
		JsonArray uwu = new JsonArray();
		for(int i = 0; i < src.size(); i++) {
			uwu.add(context.serialize(src.get(i), AppendageColor.class));
		}
		return uwu;
	}
	
	@Override
	public ColorPalette deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonArray uwu = JsonHelper2.ensureType(json, JsonArray.class);
		ColorPalette yea = new ColorPalette(uwu.size());
		for(int i = 0; i < uwu.size(); i++) {
			yea.set(i, context.deserialize(uwu.get(i), AppendageColor.class));
		}
		return yea;
	}
}
