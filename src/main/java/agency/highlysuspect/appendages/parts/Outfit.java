package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.parts.color.ColorPalette;
import agency.highlysuspect.appendages.util.JsonFile;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Outfit {
	public Outfit(String name) {
		this.name = name;
	}
	
	private String name;
	private final List<Appendage> appendages = new ArrayList<>();
	private final ColorPalette colorPalette = new ColorPalette(8);
	
	public void forEachAppendage(Consumer<Appendage> func) {
		appendages.forEach(func);
	}
	
	public List<Appendage> getAppendages() {
		return appendages;
	}
	
	public void addAppendage(Appendage app) {
		appendages.add(app);
	}
	
	public ColorPalette getPalette() {
		return colorPalette;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public JsonObject toJson() {
		JsonObject j = new JsonObject();
		j.addProperty("name", name);
		
		j.add("palette", colorPalette.toJson());
		
		JsonArray appArray = new JsonArray();
		forEachAppendage(app -> appArray.add(app.toJson()));
		j.add("appendages", appArray);
		
		return j;
	}
	
	public JsonFile toJsonFile(JsonFile.Directory directory) throws IOException {
		JsonFile file = directory.newFile(name);
		
		JsonObject j = toJson();
		
		file.setJson(j);
		return file;
	}
}
