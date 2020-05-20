package agency.highlysuspect.appendages.resource;

import agency.highlysuspect.appendages.parts.AppendageModelType;
import agency.highlysuspect.appendages.parts.AppendagePreset;
import agency.highlysuspect.appendages.parts.AppendageTextureType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

/**
 * A collection of pieces-parts used to build outfits.
 */
public class Bestiary {
	private final SimpleRegistry<AppendagePreset> appendagePresets = new SimpleRegistry<>();
	private final SimpleRegistry<AppendageModelType> modelTypes = new SimpleRegistry<>();
	private final SimpleRegistry<AppendageTextureType> textureTypes = new SimpleRegistry<>();
	
	public Registry<AppendagePreset> getAppendagePresets() {
		return appendagePresets;
	}
	
	public AppendagePreset getAppendageType(Identifier id) {
		return appendagePresets.get(id);
	}
	
	public Registry<AppendageModelType> getModelTypes() {
		return modelTypes;
	}
	
	public AppendageModelType getModelType(Identifier id) {
		return modelTypes.get(id);
	}
	
	public Registry<AppendageTextureType> getTextureTypes() {
		return textureTypes;
	}
	
	public AppendageTextureType getTextureType(Identifier id) {
		return textureTypes.get(id);
	}
	
	void registerAppendagePreset(Identifier id, AppendagePreset type) {
		appendagePresets.add(id, type);
	}
	
	void registerModelType(Identifier id, AppendageModelType type) {
		modelTypes.add(id, type);
	}
	
	void registerTextureType(Identifier id, AppendageTextureType type) {
		textureTypes.add(id, type);
	}
}