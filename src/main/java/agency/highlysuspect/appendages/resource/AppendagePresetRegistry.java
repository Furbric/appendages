package agency.highlysuspect.appendages.resource;

import agency.highlysuspect.appendages.parts.Appendage;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;

public class AppendagePresetRegistry extends JsonRegistryLoader<Appendage> {
	public AppendagePresetRegistry(Type type, String folder, Identifier fabricId) {
		super(type, folder, fabricId);
	}
	
	@Override
	public void apply(ResourceManager manager) {
		super.apply(manager);
		
		for(Appendage app : getRegistry()) {
			app.completeDefaultValues();
		}
	}
}
