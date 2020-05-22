package agency.highlysuspect.appendages.resource;

import agency.highlysuspect.appendages.Init;
import com.google.gson.Gson;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Version of Minecraft's JsonDataLoader that:
 * <ul>
 *   <li>plugs into Fabric's resource reloading machinery</li>
 *   <li>uses Gson's reflection API to deserialize json, instead of just handing you JsonObjects</li>
 * </ul>
 */
public abstract class JsonLoader<T> implements IdentifiableResourceReloadListener {
	public JsonLoader(Type type, Gson gson, String folder, Identifier fabricId) {
		this.type = type;
		this.gson = gson;
		this.folder = folder;
		this.fabricId = fabricId;
	}
	
	private final Type type;
	private final Gson gson;
	private final String folder;
	private final Identifier fabricId;
	
	private static final Logger LOGGER = Init.LOGGER;
	
	@Override
	public Identifier getFabricId() {
		return fabricId;
	}
	
	public CompletableFuture<Map<Identifier, T>> loadJson(ResourceManager manager, Profiler profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			profiler.startTick();
			
			Map<Identifier, T> map = new HashMap<>();
			
			for(Identifier rawId : manager.findResources(folder, s -> s.endsWith(".json"))) {
				//Chop off the ".json"
				Identifier trimmedId = new Identifier(
					rawId.getNamespace(),
					rawId.getPath().substring(0, rawId.getPath().length() - 5)
				);
				
				//Load the file
				try(
					Resource resource = manager.getResource(rawId);
					InputStream is = resource.getInputStream();
					InputStreamReader reader = new InputStreamReader(is);
				) {
					map.put(trimmedId, gson.fromJson(reader, type));
				} catch(Exception e) {
					LOGGER.error("Trouble loading json " + rawId.toString(), e);
				}
			}
			
			profiler.endTick();
			return map;
		}, executor);
	}
}
