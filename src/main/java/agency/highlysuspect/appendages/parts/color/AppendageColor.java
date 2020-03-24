package agency.highlysuspect.appendages.parts.color;

import agency.highlysuspect.appendages.parts.Outfit;
import agency.highlysuspect.appendages.util.JsonHelper2;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.JsonHelper;

public class AppendageColor {
	//Janky "tagged union", because doing it the actual way with subclasses made my heart weep (and my head hurt).
	//Would do just about anything for a Rust-like enum right now, tbh.
	//Fortunately, for both FIXED and PALETTE_REFERENCE specializations, I want to store an int, so I can use the same one.
	//And for UNSET I don't need to store anything, but I won't let the extra int bother me.
	
	public enum Type {
		UNSET,
		FIXED,
		PALETTE_REFERENCE,
		;
	}
	
	private Type type = Type.UNSET;
	private int value;
	
	public int getColor(Outfit outfit) {
		if(type == Type.UNSET) return 0x000000;
		if(type == Type.FIXED) return value;
		if(type == Type.PALETTE_REFERENCE) return outfit.getPalette().get(value).value;
		throw new IllegalStateException("unknown type " + type.toString());
	}
	
	public AppendageColor setColor(int color) {
		type = Type.FIXED;
		value = color;
		return this;
	}
	
	public AppendageColor setReference(int ref) {
		type = Type.PALETTE_REFERENCE;
		value = ref;
		return this;
	}
	
	public AppendageColor unset() {
		type = Type.UNSET;
		return this;
	}
	
	public JsonElement toJson() {
		JsonObject j = new JsonObject();
		
		j.addProperty("type", JsonHelper2.enumToName(type));
		if(type == Type.FIXED) {
			j.addProperty("color", Integer.toString(value, 16));
		} else if(type == Type.PALETTE_REFERENCE) {
			j.addProperty("ref", value);
		} else if(type == Type.UNSET) {
			//Do nothing lol
		} else {
			throw new IllegalStateException("unknown type " + type.toString());
		}
		
		return j;
	}
	
	public static AppendageColor fromJson(JsonElement je) throws JsonParseException {
		JsonObject j = JsonHelper2.ensureType(je, JsonObject.class);
		
		AppendageColor color = new AppendageColor();
		Type type = JsonHelper2.nameToEnum(JsonHelper.getString(j, "type"), Type.class);
		
		switch(type) {
			case UNSET: return color.unset();
			case FIXED: return color.setColor(JsonHelper2.parseInt(JsonHelper.getString(j, "color"), 16, 0x000000));
			case PALETTE_REFERENCE: return color.setReference(JsonHelper.getInt(j, "ref"));
		}
		
		throw new IllegalStateException("unknown type " + type.toString());
	}
}
