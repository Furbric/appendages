package agency.highlysuspect.appendages.util;

import com.google.gson.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;

public class ItemStackSerde implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
	@Override
	public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(Registry.ITEM.getId(src.getItem()).toString());
	}
	
	@Override
	public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return new ItemStack(Registry.ITEM.get(Identifier.tryParse(json.getAsString())));
	}
}
