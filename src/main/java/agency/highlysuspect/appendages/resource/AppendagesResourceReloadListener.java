package agency.highlysuspect.appendages.resource;

import agency.highlysuspect.appendages.Init;
import agency.highlysuspect.appendages.parts.AppendageModel;
import agency.highlysuspect.appendages.parts.AppendageModelType;
import agency.highlysuspect.appendages.parts.AppendageType;
import agency.highlysuspect.appendages.parts.BodyPart;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class AppendagesResourceReloadListener implements SimpleResourceReloadListener<AppendageTypesRegistry> {
	private static final Identifier ID = new Identifier(Init.MODID, "resource_reloader");
	@Override
	public Identifier getFabricId() {
		return ID;
	}
	
	@Override
	public CompletableFuture<AppendageTypesRegistry> load(ResourceManager manager, Profiler profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			profiler.push(Init.MODID + ":load");
			
			AppendageTypesRegistry reg = new AppendageTypesRegistry();
			
			//TODO load them from JSON instead of hardcoding sample data, haha
			AppendageModelType breadModelType = new AppendageModelType(0);
			reg.registerModelType(new Identifier(Init.MODID, "bread_model"), breadModelType);
			
			AppendageType breadAppType = new AppendageType("quaternary", (app) -> {
				app.setModel(new AppendageModel(breadModelType).vibeCheck());
				app.setMountPoint(BodyPart.TORSO.getMountPointByName("back"));
			});
			reg.registerAppendageType(new Identifier(Init.MODID, "bread"), breadAppType);
			
			profiler.pop();
			return reg;
		}, executor);
	}
	
	@Override
	public CompletableFuture<Void> apply(AppendageTypesRegistry registry, ResourceManager manager, Profiler profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			profiler.push(Init.MODID + ":apply");
			
			Init.appendageTypesRegistry = registry;
			
			profiler.pop();
			return null;
		}, executor);
	}
}
