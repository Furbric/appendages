package agency.highlysuspect.appendages.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.client.util.ModelIdentifier;

import java.io.IOException;

//TODO delete when i get rid of the reference to model identifier in AppendageModel
public class ModelIdentifierTypeAdapter extends TypeAdapter<ModelIdentifier> {
	@Override
	public void write(JsonWriter out, ModelIdentifier value) throws IOException {
		out.value(value.toString());
	}
	
	@Override
	public ModelIdentifier read(JsonReader in) throws IOException {
		return new ModelIdentifier(in.nextString());
	}
}
