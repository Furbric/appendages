package agency.highlysuspect.appendages.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.math.Vec3d;

import java.io.IOException;

public class Vec3dTypeAdapter extends TypeAdapter<Vec3d> {
	@Override
	public void write(JsonWriter out, Vec3d value) throws IOException {
		out.beginArray();
		out.value(value.x);
		out.value(value.y);
		out.value(value.z);
		out.endArray();
	}
	
	@Override
	public Vec3d read(JsonReader in) throws IOException {
		in.beginArray();
		double x = in.nextDouble();
		double y = in.nextDouble();
		double z = in.nextDouble();
		in.endArray();
		
		return new Vec3d(x, y, z);
	}
}
