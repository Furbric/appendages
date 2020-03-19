package agency.highlysuspect.appendages.parts.color;

import agency.highlysuspect.appendages.parts.Outfit;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public abstract class AppendageColor {
	public abstract int getColor();
	public abstract void setColor(int newColor);
	
	public static AppendageColor fromJson(JsonObject json, Outfit outfit) {
		//Hacky, but it works
		String type = json.get("type").getAsString();
		switch(type) {
			case "standalone": {
				Standalone poot = new Standalone();
				poot.fromJsonInner(json.get("value"));
				return poot;
			}
			case "palette_reference": {
				PaletteReference poot = new PaletteReference();
				poot.outfit = outfit;
				poot.fromJsonInner(json.get("value"));
				return poot;
			}
		}
		return new Unset();
	}
	
	public interface JsonSerializable {
		default JsonObject toJson() {
			JsonObject j = new JsonObject();
			j.addProperty("type", key());
			j.add("value", toJsonInner());
			return j;
		}
		
		String key();
		JsonElement toJsonInner();
		void fromJsonInner(JsonElement e);
	}
	
	public static class Unset extends AppendageColor {
		@Override
		public int getColor() {
			return 0;
		}
		
		@Override
		public void setColor(int newColor) {
			//no-op
		}
	}
	
	public static class Standalone extends AppendageColor implements JsonSerializable {
		private Standalone() {
			this(0);
		}
		
		public Standalone(int color) {
			this.color = color;
		}
		
		private int color;
		
		@Override
		public int getColor() {
			return color;
		}
		
		@Override
		public void setColor(int newColor) {
			color = newColor;
		}
		
		@Override
		public String key() {
			return "standalone";
		}
		
		@Override
		public JsonElement toJsonInner() {
			return new JsonPrimitive(color);
		}
		
		@Override
		public void fromJsonInner(JsonElement e) {
			setColor(e.getAsInt());
		}
	}
	
	public static class PaletteReference extends AppendageColor implements JsonSerializable {
		private PaletteReference() {}
		
		public PaletteReference(Outfit outfit, int paletteId) {
			this.outfit = outfit;
			this.paletteId = paletteId;
		}
		
		private Outfit outfit;
		private int paletteId;
		
		@Override
		public int getColor() {
			AppendageColor pointerChase = outfit.getPalette().get(paletteId);
			if(pointerChase instanceof PaletteReference) return 0;
			else return pointerChase.getColor();
		}
		
		@SuppressWarnings("UnnecessaryReturnStatement")
		@Override
		public void setColor(int newColor) {
			AppendageColor pointerChase = outfit.getPalette().get(paletteId);
			if(pointerChase instanceof PaletteReference) return;
			else pointerChase.setColor(newColor);
		}
		
		@Override
		public String key() {
			return "palette_reference";
		}
		
		@Override
		public JsonElement toJsonInner() {
			return new JsonPrimitive(paletteId);
		}
		
		@Override
		public void fromJsonInner(JsonElement e) {
			paletteId = e.getAsInt();
		}
	}
}
