package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.parts.color.ColorPalette;
import agency.highlysuspect.appendages.util.JsonFile;
import agency.highlysuspect.appendages.util.JsonHelper2;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.JsonHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Outfit {
	
	private String name;
	private List<Appendage> appendages = new ArrayList<>();
	private ColorPalette palette = new ColorPalette(8);
	
	public String getName() {
		return name;
	}
	
	public Outfit setName(String name) {
		this.name = name;
		return this;
	}
	
	public List<Appendage> getAppendages() {
		return appendages;
	}
	
	public Outfit setAppendages(List<Appendage> appendages) {
		this.appendages = appendages;
		return this;
	}
	
	public Outfit addAppendage(Appendage appendage) {
		appendages.add(appendage);
		return this;
	}
	
	public ColorPalette getPalette() {
		return palette;
	}
	
	public Outfit setPalette(ColorPalette palette) {
		this.palette = palette;
		return this;
	}
	
	public JsonElement toJson() {
		JsonObject j = new JsonObject();
		
		j.addProperty("name", name);
		j.add("palette", palette.toJson());
		j.add("appendages", JsonHelper2.listToJsonArray(appendages, Appendage::toJson));
		
		return j;
	}
	
	public static Outfit fromJson(JsonElement je) throws JsonSyntaxException {
		JsonObject j = JsonHelper2.ensureType(je, JsonObject.class);
		
		return new Outfit()
			.setName(JsonHelper.getString(j, "name"))
			.setPalette(ColorPalette.fromJson(j.get("palette")))
			.setAppendages(JsonHelper2.getList(j, "appendages", Appendage::fromJson));
	}
	
	public JsonFile toJsonFile(JsonFile.Directory directory) throws IOException {
		JsonFile file = directory.newFileWithPreferredFilename(name);
		file.setJson(toJson());
		return file;
	}
	
	//NB: Doesn't call "read" on the json file.
	//That's why it doesn't throw IOException.
	public static Outfit fromJsonFile(JsonFile file) throws JsonSyntaxException {
		try {
			return fromJson(file.getJson());
		} catch(Exception e) {
			throw new JsonSyntaxException("Cannot read outfit in file " + file.getPath(), e);
		}
	}
}
