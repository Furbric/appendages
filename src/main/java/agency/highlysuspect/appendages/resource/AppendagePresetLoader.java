package agency.highlysuspect.appendages.resource;

import agency.highlysuspect.appendages.Init;
import agency.highlysuspect.appendages.parts.Appendage;
import net.minecraft.util.Identifier;

public class AppendagePresetLoader extends JsonRegistryLoader<Appendage> {
	public AppendagePresetLoader() {
		super(Appendage.class, Init.GSON, "appendages/appendage", ID);
	}
	
	private static final Identifier ID = new Identifier(Init.MODID, "appendage_presets");
}
