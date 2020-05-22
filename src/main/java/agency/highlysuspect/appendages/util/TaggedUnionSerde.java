package agency.highlysuspect.appendages.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.*;

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
		JsonElement elem = context.serialize(src);
		Preconditions.checkArgument(elem instanceof JsonObject, "TaggedUnionSerde can't be used on things that don't serialize to json objects");
		((JsonObject)elem).addProperty("type", typeLookup.get(src.getClass()));
		return elem;
	}
	
	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		throw new IllegalStateException("NYI"); //TODO
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Name {
		String value();
	}
}
