package agency.highlysuspect.appendages.resource;

import agency.highlysuspect.appendages.parts.AppendageModelType;
import agency.highlysuspect.appendages.parts.AppendageTextureType;
import agency.highlysuspect.appendages.parts.AppendageType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.Optional;

public class AppendageTypesRegistry {
	private final SimpleRegistry<AppendageType> appendageTypes = new SimpleRegistry<>();
	private final SimpleRegistry<AppendageModelType> modelTypes = new SimpleRegistry<>();
	private final SimpleRegistry<AppendageTextureType> textureTypes = new SimpleRegistry<>();
	
	public Registry<AppendageType> getAppendageTypes() {
		return appendageTypes;
	}
	
	public AppendageType getAppendageType(Identifier id) {
		return appendageTypes.get(id);
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
	
	void registerAppendageType(Identifier id, AppendageType type) {
		appendageTypes.add(id, type);
	}
	
	void registerModelType(Identifier id, AppendageModelType type) {
		modelTypes.add(id, type);
	}
	
	void registerTextureType(Identifier id, AppendageTextureType type) {
		textureTypes.add(id, type);
	}
}
