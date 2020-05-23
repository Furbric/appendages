package agency.highlysuspect.appendages.resource;

import com.google.gson.Gson;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class JsonRegistryLoader<T> implements SimpleSynchronousResourceReloadListener {
	public JsonRegistryLoader(Type type, String folder, Identifier fabricId) {
		this.type = type;
		this.folder = folder;
		this.fabricId = fabricId;
	}
	
	private final Type type;
	private final String folder;
	private final Identifier fabricId;
	private final ArrayList<Identifier> fabricDependencies = new ArrayList<>();
	
	private Registry<T> registry;
	
	@Override
	public void apply(ResourceManager manager) {
		SimpleRegistry<T> newRegistry = new SimpleRegistry<>();
		JsonLoaderUtil.<T>loadJson(manager, type, folder, newRegistry::add);
		this.registry = newRegistry;
	}
	
	public T get(Identifier id) {
		return registry.get(id);
	}
	
	public Registry<T> getRegistry() {
		return registry;
	}
	
	@Override
	public Identifier getFabricId() {
		return fabricId;
	}
	
	@Override
	public Collection<Identifier> getFabricDependencies() {
		return fabricDependencies;
	}
	
	//convenience method tbh
	public JsonRegistryLoader<T> dependsOn(JsonRegistryLoader<?> other) {
		fabricDependencies.add(other.getFabricId());
		return this;
	}
}
