package agency.highlysuspect.appendages.parts.color;

import agency.highlysuspect.appendages.resource.AppendageTypesRegistry;
import agency.highlysuspect.appendages.util.JsonHelper2;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ColorPalette {
	public ColorPalette(int size) {
		this.size = size;
		
		colors = new AppendageColor[size];
		for(int i = 0; i < size; i++) {
			colors[i] = new AppendageColor().unset();
		}
	}
	
	private final int size;
	private final AppendageColor[] colors;
	
	public AppendageColor get(int id) {
		return colors[id];
	}
	
	public void setColor(int id, AppendageColor color) {
		colors[id] = color;
	}
	
	public JsonElement toJson(AppendageTypesRegistry registry) {
		JsonArray array = new JsonArray();
		
		for (AppendageColor color : colors) {
			array.add(color.toJson());
		}
		
		return array;
	}
	
	public static ColorPalette fromJson(JsonElement je, AppendageTypesRegistry registry) throws JsonParseException {
		JsonArray array = JsonHelper2.ensureType(je, JsonArray.class);
		
		int size = array.size();
		ColorPalette palette = new ColorPalette(size);
		
		for (int i = 0; i < array.size(); i++) {
			palette.setColor(i, AppendageColor.fromJson(array.get(i), registry));
		}
		
		return palette;
	}
}
