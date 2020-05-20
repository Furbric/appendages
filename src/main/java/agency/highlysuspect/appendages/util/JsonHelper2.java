package agency.highlysuspect.appendages.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

//More json methods that I find handy. Extension of Minecraft's JsonHelper.
//*Cries in kotlin extension classes*
public class JsonHelper2 {
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
	
	public static Vec3d getVec3d(JsonObject j, String key) {
		return arrayToVec3d(JsonHelper.getArray(j, key));
	}
	
	public static <T extends Enum<?>> String enumToName(T enoom) {
		return enoom.name().toLowerCase(Locale.ROOT);
	}
	
	public static <T> List<T> getList(JsonObject j, String key, Function<JsonElement, ? extends T> elementGetter) {
		List<T> list = new ArrayList<>();
		
		JsonElement maybeArray = j.get(key);
		if(maybeArray == null) return list;
		
		JsonArray array = maybeArray.getAsJsonArray();
		
		for (JsonElement elem : array) {
			list.add(elementGetter.apply(elem));
		}
		
		return list;
	}
	
	public static <T> JsonArray listToJsonArray(Iterable<? extends T> things, Function<? super T, ? extends JsonElement> elementWriter) {
		JsonArray yeet = new JsonArray();
		for (T thing : things) {
			yeet.add(elementWriter.apply(thing));
		}
		return yeet;
	}
	
	public static <T extends Enum<?>> T nameToEnum(String name, Class<T> enumClass) {
		for(T candidate : enumClass.getEnumConstants()) {
			if(candidate.name().toLowerCase(Locale.ROOT).equals(name)) return candidate;
		}
		
		throw new JsonSyntaxException("No enum element in " + enumClass.getSimpleName() + " named " + name);
	}
	
	public static int parseInt(String x, int radix, int defaultIfError) {
		try {
			return Integer.parseInt(x, radix);
		} catch(NumberFormatException e) {
			return defaultIfError;
		}
	}
	
	public static <T extends JsonElement> T ensureType(JsonElement thing, Class<T> target) {
		if(target.isAssignableFrom(thing.getClass())) {
			//noinspection unchecked
			return (T) thing;
		} else {
			throw new JsonSyntaxException("expected a " + target.getSimpleName() + ", found a " + thing.getClass().getSimpleName());
		}
	}
	
}
