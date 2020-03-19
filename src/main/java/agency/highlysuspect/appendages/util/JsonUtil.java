package agency.highlysuspect.appendages.util;

import com.google.gson.JsonArray;
import net.minecraft.util.math.Vec3d;

public class JsonUtil {
	public static JsonArray vec3dToArray(Vec3d vec) {
		JsonArray bop = new JsonArray();
		bop.add(vec.getX());
		bop.add(vec.getY());
		bop.add(vec.getZ());
		return bop;
	}
	
	public static Vec3d arrayToVec3d(JsonArray bop) {
		return new Vec3d(bop.get(0).getAsDouble(), bop.get(1).getAsDouble(), bop.get(2).getAsDouble());
	}
	
	public static <T extends Enum<?>> T enumByName(String name, Class<T> enumClass, T defaultValue) {
		for(T candidate : enumClass.getEnumConstants()) {
			if(candidate.name().equals(name)) return candidate;
		}
		
		return defaultValue;
	}
}
