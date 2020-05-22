package agency.highlysuspect.appendages.resource;

import com.google.gson.Gson;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class JsonRegistryLoader<T> extends JsonLoader<T> implements SimpleResourceReloadListener<Registry<T>> {
	public JsonRegistryLoader(Type type, Gson gson, String folder, Identifier fabricId) {
		super(type, gson, folder, fabricId);
	}
	
	private Registry<T> registry;
	
	@Override
	public CompletableFuture<Registry<T>> load(ResourceManager manager, Profiler profiler, Executor executor) {
		return super.loadJson(manager, profiler, executor).thenApplyAsync(map -> {
			SimpleRegistry<T> registry = new SimpleRegistry<>();
			map.forEach(registry::add);
			return registry;
		}, executor);
	}
	
	@Override
	public CompletableFuture<Void> apply(Registry<T> data, ResourceManager manager, Profiler profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			this.registry = data;
			return null;
		});
	}
	
	public T get(Identifier id) {
		return registry.get(id);
	}
}
