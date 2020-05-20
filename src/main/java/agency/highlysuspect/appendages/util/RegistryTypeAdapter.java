package agency.highlysuspect.appendages.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.util.function.Supplier;

public class RegistryTypeAdapter<T> extends TypeAdapter<T> {
	private RegistryTypeAdapter(Supplier<Registry<T>> registryLookup) {
		this.registryLookup = registryLookup;
	}
	
	public static <T> TypeAdapter<T> createNullSafe(Supplier<Registry<T>> registryLookup) {
		return new RegistryTypeAdapter<>(registryLookup).nullSafe();
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
		//No need to null-check, both Registry#get and Identifier.tryParse pass nulls along
		return registryLookup.get().get(Identifier.tryParse(in.nextString()));
	}
}
