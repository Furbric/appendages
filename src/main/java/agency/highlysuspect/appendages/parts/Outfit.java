package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.parts.color.ColorPalette;
import agency.highlysuspect.appendages.resource.AppendageTypesRegistry;
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
	
	public JsonElement toJson(AppendageTypesRegistry registry) {
		JsonObject j = new JsonObject();
		
		j.addProperty("name", name);
		j.add("palette", palette.toJson(registry));
		j.add("appendages", JsonHelper2.listToJsonArray(appendages, app -> app.toJson(registry)));
		
		return j;
	}
	
	public static Outfit fromJson(JsonElement je, AppendageTypesRegistry registry) throws JsonSyntaxException {
		JsonObject j = JsonHelper2.ensureType(je, JsonObject.class);
		
		return new Outfit()
			.setName(JsonHelper.getString(j, "name"))
			.setPalette(ColorPalette.fromJson(j.get("palette"), registry))
			.setAppendages(JsonHelper2.getList(j, "appendages", a -> Appendage.fromJson(a, registry)));
	}
	
	public JsonFile toJsonFile(JsonFile.Directory directory, AppendageTypesRegistry registry) throws IOException {
		JsonFile file = directory.newFileWithPreferredFilename(name);
		file.setJson(toJson(registry));
		return file;
	}
	
	//NB: Doesn't call "read" on the json file.
	//That's why it doesn't throw IOException.
	public static Outfit fromJsonFile(JsonFile file, AppendageTypesRegistry registry) throws JsonSyntaxException {
		try {
			return fromJson(file.getJson(), registry);
		} catch(Exception e) {
			throw new JsonSyntaxException("Cannot read outfit in file " + file.getPath(), e);
		}
	}
}
