package agency.highlysuspect.appendages.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.fabricmc.fabric.api.util.TriState;

import java.io.IOException;

public class TriStateTypeAdapter extends TypeAdapter<TriState> {
	@Override
	public void write(JsonWriter out, TriState value) throws IOException {
		if(value == TriState.DEFAULT) out.nullValue();
		else out.value(value.get());
	}
	
	@Override
	public TriState read(JsonReader in) throws IOException {
		if(in.peek() == JsonToken.NULL) {
			in.nextNull();
			return TriState.DEFAULT;
		} else return TriState.of(in.nextBoolean());
	}
}
