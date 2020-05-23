package agency.highlysuspect.appendages.util;

import agency.highlysuspect.appendages.Init;
import agency.highlysuspect.appendages.parts.Appendage;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.util.function.Supplier;

public class RegistryTypeAdapter<T> extends TypeAdapter<T> {
	public RegistryTypeAdapter(Supplier<Registry<T>> registryLookup) {
		this.registryLookup = registryLookup;
	}
	
	private final Supplier<Registry<T>> registryLookup;
	
	@Override
	public void write(JsonWriter out, T value) throws IOException {
		Identifier id = registryLookup.get().getId(value);
		if(id == null) {
			out.nullValue();
			return;
		}
		
		out.value(id.toString());
	}
	
	@Override
	public T read(JsonReader in) throws IOException {
		if(in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		} else return registryLookup.get().get(Identifier.tryParse(in.nextString()));
	}
	
	//Need to refer to it in a .class annotation
	public static class App extends RegistryTypeAdapter<Appendage> {
		public App() {
			super(Init.APPENDAGE_PRESET_REGISTRY::getRegistry);
		}
	}
}
