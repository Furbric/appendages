package agency.highlysuspect.appendages.resource;

import agency.highlysuspect.appendages.Init;
import agency.highlysuspect.appendages.parts.AppendageModel;
import agency.highlysuspect.appendages.parts.AppendageModelType;
import agency.highlysuspect.appendages.parts.AppendagePreset;
import agency.highlysuspect.appendages.parts.BodyPart;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BestiaryReloadListener implements SimpleResourceReloadListener<Bestiary> {
	private static final Identifier ID = new Identifier(Init.MODID, "bestiary_reloader");
	@Override
	public Identifier getFabricId() {
		return ID;
	}
	
	@Override
	public CompletableFuture<Bestiary> load(ResourceManager manager, Profiler profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			profiler.push(Init.MODID + ":load_bestiary");
			
			Bestiary bestiary = new Bestiary();
			
			//TODO load them from JSON instead of hardcoding sample data, haha
			AppendageModelType breadModelType = new AppendageModelType(0);
			bestiary.registerModelType(new Identifier(Init.MODID, "bread_model"), breadModelType);
			
			AppendagePreset breadPreset = new AppendagePreset((app) -> {
				app.setModel(new AppendageModel(breadModelType).vibeCheck());
				app.setMountPoint(BodyPart.TORSO.getMountPointByName("back"));
			});
			bestiary.registerPreset(new Identifier(Init.MODID, "bread"), breadPreset);
			
			profiler.pop();
			return bestiary;
		}, executor);
	}
	
	@Override
	public CompletableFuture<Void> apply(Bestiary bestiary, ResourceManager manager, Profiler profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			profiler.push(Init.MODID + ":apply_bestiary");
			
			Init.bestiary = bestiary;
			
			profiler.pop();
			return null;
		}, executor);
	}
}
