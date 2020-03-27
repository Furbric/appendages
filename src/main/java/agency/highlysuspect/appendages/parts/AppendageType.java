package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.resource.AppendageTypesRegistry;
import agency.highlysuspect.appendages.util.JsonHelper2;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class AppendageType {
	public AppendageType(String artist, Consumer<Appendage> modelConfigurator) {
		this.artist = artist;
		this.modelConfigurator = modelConfigurator;
	}
	
	private final String artist;
	
	private final Consumer<Appendage> modelConfigurator;
	
	public Identifier getId(AppendageTypesRegistry registry) {
		return registry.getAppendageTypes().getId(this);
	}
	
	public String getArtist() {
		return artist;
	}
	
	public void applyDefaultOptions(Appendage model) {
		modelConfigurator.accept(model);
	}
	
	public Appendage newAppendage() {
		Appendage wow = new Appendage(this);
		applyDefaultOptions(wow);
		return wow;
	}
	
	public JsonElement toJson(AppendageTypesRegistry registry) {
		return new JsonPrimitive(getId(registry).toString());
	}
	
	public static AppendageType fromJson(JsonElement je, AppendageTypesRegistry registry) throws JsonParseException {
		return registry.getAppendageType(new Identifier(JsonHelper2.ensureType(je, JsonPrimitive.class).getAsString()));
	}
}
