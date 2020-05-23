package agency.highlysuspect.appendages.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.*;
import net.minecraft.util.JsonHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Type;

public class TaggedUnionSerde<T> implements JsonSerializer<T>, JsonDeserializer<T> {
	@SafeVarargs
	public TaggedUnionSerde(Class<? extends T>... classes) {
		typeLookup = HashBiMap.create();
		
		for(Class<? extends T> classs : classes) {
			typeLookup.put(classs, classs.getAnnotation(Name.class).value());
		}
	}
	
	private final BiMap<Type, String> typeLookup;
	
	@Override
	public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject obj = JsonHelper2.ensureType(context.serialize(src), JsonObject.class);
		obj.addProperty("type", typeLookup.get(src.getClass()));
		return obj;
	}
	
	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = JsonHelper2.ensureType(json, JsonObject.class);
		return context.deserialize(obj, typeLookup.inverse().get(JsonHelper.getString(obj, "type")));
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Name {
		String value();
	}
}
