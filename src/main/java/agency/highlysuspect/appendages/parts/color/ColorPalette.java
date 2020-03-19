package agency.highlysuspect.appendages.parts.color;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ColorPalette {
	public ColorPalette(int size) {
		this.size = size;
		
		colors = new AppendageColor[size];
		for(int i = 0; i < size; i++) {
			colors[i] = new AppendageColor.Unset();
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
	
	public JsonElement toJson() {
		JsonArray array = new JsonArray();
		
		for (int id = 0; id < size; id++) {
			AppendageColor c = colors[id];
			if(c instanceof AppendageColor.JsonSerializable) {
				JsonObject entry = new JsonObject();
				
				entry.addProperty("id", id);
				entry.add("color", ((AppendageColor.JsonSerializable)c).toJson());
				
				array.add(entry);
			}
		}
		
		return array;
	}
}
