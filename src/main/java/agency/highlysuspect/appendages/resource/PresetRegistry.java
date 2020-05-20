package agency.highlysuspect.appendages.resource;

import agency.highlysuspect.appendages.parts.Appendage;
import agency.highlysuspect.appendages.parts.AppendageModel;
import agency.highlysuspect.appendages.parts.Preset;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.function.Consumer;

public class PresetRegistry {
	private final SimpleRegistry<Preset<Appendage>> appendagePresets = new SimpleRegistry<>();
	private final SimpleRegistry<Preset<AppendageModel>> modelPresets = new SimpleRegistry<>();
	
	public Registry<Preset<Appendage>> getAppendagePresets() {
		return appendagePresets;
	}
	
	public Preset<Appendage> getAppendagePreset(Identifier id) {
		return appendagePresets.get(id);
	}
	
	public Registry<Preset<AppendageModel>> getModelPresets() {
		return modelPresets;
	}
	
	public Preset<AppendageModel> getModelPreset(Identifier id) {
		return modelPresets.get(id);
	}
	
	Preset<Appendage> registerAppendagePreset(Identifier id, Consumer<Appendage> configurator) {
		return appendagePresets.add(id, new Preset<>(Appendage::new, configurator));
	}
	
	Preset<AppendageModel> registerModelPreset(Identifier id, Consumer<AppendageModel> configurator) {
		return modelPresets.add(id, new Preset<>(AppendageModel::new, configurator));
	}
}
