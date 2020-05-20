package agency.highlysuspect.appendages.util;

import agency.highlysuspect.appendages.parts.color.AppendageColor;
import com.google.gson.*;
import net.minecraft.util.JsonHelper;

import java.lang.reflect.Type;

public class AppendageColorSerde implements JsonSerializer<AppendageColor>, JsonDeserializer<AppendageColor> {
	@Override
	public JsonElement serialize(AppendageColor src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject j = new JsonObject();
		
		if(src instanceof AppendageColor.Unset) {
			//Leave the object blank
		} else if(src instanceof AppendageColor.Fixed) {
			AppendageColor.Fixed fixed = (AppendageColor.Fixed) src;
			
			j.addProperty("type", "fixed");
			j.addProperty("color", Integer.toString(fixed.getColor(), 16));
		} else if(src instanceof AppendageColor.PaletteReference) {
			AppendageColor.PaletteReference ref = (AppendageColor.PaletteReference) src;
			
			j.addProperty("type", "palette_reference");
			j.addProperty("ref", ref.getReference());
		}
		
		return j;
	}
	
	@Override
	public AppendageColor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject j = JsonHelper2.ensureType(json, JsonObject.class);
		
		if(!j.has("type")) return new AppendageColor.Unset();
		
		String type = JsonHelper.getString(j, "type");
		switch(type) {
			case "unset": return new AppendageColor.Unset();
			case "fixed": return new AppendageColor.Fixed().setColor(JsonHelper2.parseInt(JsonHelper.getString(j, "color"), 16, 0x000000));
			case "palette_reference": return new AppendageColor.PaletteReference().setReference(JsonHelper.getInt(j, "ref"));
			default: throw new JsonParseException("unknown type " + type);
		}
	}
}
