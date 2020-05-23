package agency.highlysuspect.appendages.resource;

import agency.highlysuspect.appendages.Init;
import com.google.gson.Gson;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.function.BiConsumer;

public class JsonLoaderUtil {
	public static <T> void loadJson(ResourceManager manager, Type type, String folder, BiConsumer<Identifier, T> action) {
		for (Identifier rawId : manager.findResources(folder, s -> s.endsWith(".json"))) {
			//Chop off the ".json"
			Identifier trimmedId = new Identifier(
				rawId.getNamespace(),
				rawId.getPath().substring(folder.length(), rawId.getPath().length() - 5)
			);
			
			//Load the file
			try (
				Resource resource = manager.getResource(rawId);
				InputStream is = resource.getInputStream();
				InputStreamReader reader = new InputStreamReader(is);
			) {
				action.accept(trimmedId, Init.GSON.fromJson(reader, type));
			} catch (Exception e) {
				Init.LOGGER.error("Trouble loading json " + rawId.toString(), e);
			}
		}
	}
}
