package agency.highlysuspect.appendages.resource;

import agency.highlysuspect.appendages.Init;
import agency.highlysuspect.appendages.parts.Appendage;
import agency.highlysuspect.appendages.parts.AppendageModel;
import agency.highlysuspect.appendages.parts.BodyPart;
import agency.highlysuspect.appendages.parts.Preset;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class PresetRegistryReloadListener implements SimpleResourceReloadListener<PresetRegistry> {
	private static final Identifier ID = new Identifier(Init.MODID, "preset_reloader");
	@Override
	public Identifier getFabricId() {
		return ID;
	}
	
	@Override
	public CompletableFuture<PresetRegistry> load(ResourceManager manager, Profiler profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			profiler.push(Init.MODID + ":load_presets");
			
			PresetRegistry presetRegistry = new PresetRegistry();
			
			//TODO load them from JSON instead of hardcoding sample data, haha
			Preset<AppendageModel> breakPreset = presetRegistry.registerModelPreset(new Identifier(Init.MODID, "bread_model"), (model) -> {
				model.setTextureCount(0);
			});
			
			Preset<Appendage> breadBack = presetRegistry.registerAppendagePreset(new Identifier(Init.MODID, "bread"), (app) -> {
				app.setModel(breakPreset.get());
				app.setMountPoint(BodyPart.TORSO.getMountPointByName("back"));
			});
			
			profiler.pop();
			return presetRegistry;
		}, executor);
	}
	
	@Override
	public CompletableFuture<Void> apply(PresetRegistry presetRegistry, ResourceManager manager, Profiler profiler, Executor executor) {
		return CompletableFuture.supplyAsync(() -> {
			profiler.push(Init.MODID + ":apply_presets");
			
			Init.presetRegistry = presetRegistry;
			
			profiler.pop();
			return null;
		}, executor);
	}
}
