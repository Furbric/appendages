package agency.highlysuspect.appendages.util;

import agency.highlysuspect.appendages.parts.BodyPart;
import com.google.gson.*;
import net.minecraft.util.JsonHelper;

import java.lang.reflect.Type;

public class MountPointSerde implements JsonSerializer<BodyPart.MountPoint>, JsonDeserializer<BodyPart.MountPoint> {
	@Override
	public JsonElement serialize(BodyPart.MountPoint src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject j = new JsonObject();
		
		j.addProperty("body_part", JsonHelper2.enumToName(src.getBodyPart()));
		j.addProperty("mount_point", src.getName());
		
		return j;
	}
	
	@Override
	public BodyPart.MountPoint deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject j = JsonHelper2.ensureType(json, JsonObject.class);
		
		BodyPart bodyPart = JsonHelper2.nameToEnum(JsonHelper.getString(j, "body_part"), BodyPart.class);
		String name = JsonHelper.getString(j, "mount_point");
		
		BodyPart.MountPoint point = bodyPart.getMountPointByName(name);
		if (point == null) throw new JsonSyntaxException("No mount point named " + name + " on part " + bodyPart.name());
		
		return point;
	}
}
