package agency.highlysuspect.appendages.util;

import agency.highlysuspect.appendages.parts.color.AppendageColor;
import com.google.gson.*;

import java.lang.reflect.Type;

public class AppendageColorSerde implements JsonSerializer<AppendageColor>, JsonDeserializer<AppendageColor> {
	@Override
	public JsonElement serialize(AppendageColor src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject j = new JsonObject();
		
		if(src instanceof AppendageColor.Unset) {
			return new JsonObject();
		} else if(src instanceof AppendageColor.Fixed) {
			AppendageColor.Fixed fixed = (AppendageColor.Fixed) src;
			return JsonHelper2.colorToRgbArray(fixed.getColor());
		} else if(src instanceof AppendageColor.PaletteReference) {
			AppendageColor.PaletteReference ref = (AppendageColor.PaletteReference) src;
			return new JsonPrimitive("#" + ref.getReference());
		}
		
		return j;
	}
	
	@Override
	public AppendageColor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if(json.isJsonObject()) {
			return new AppendageColor.Unset(); //represented by {}
		} else if(json.isJsonArray() && json.getAsJsonArray().size() == 3) {
			return new AppendageColor.Fixed().setColor(JsonHelper2.rgbArrayToColor(json.getAsJsonArray()));
		} else if(json.isJsonPrimitive()) {
			String s = json.getAsString();
			if(s.startsWith("#")) {
				return new AppendageColor.PaletteReference().setReference(Integer.parseInt(s.substring(1)));
			}
		}
		
		throw new JsonParseException("Not sure how to parse " + json.toString() + " as a color");
	}
}
