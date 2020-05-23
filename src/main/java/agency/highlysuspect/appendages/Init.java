package agency.highlysuspect.appendages;

import agency.highlysuspect.appendages.parts.Appendage;
import agency.highlysuspect.appendages.parts.AppendageModel;
import agency.highlysuspect.appendages.parts.BodyPart;
import agency.highlysuspect.appendages.parts.Outfit;
import agency.highlysuspect.appendages.parts.color.AppendageColor;
import agency.highlysuspect.appendages.parts.color.ColorPalette;
import agency.highlysuspect.appendages.resource.AppendagePresetRegistry;
import agency.highlysuspect.appendages.resource.JsonRegistryLoader;
import agency.highlysuspect.appendages.util.*;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Init implements ClientModInitializer {
	public static final String MODID = "appendages";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public static final Gson GSON;
	
	static {
		GSON = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.setPrettyPrinting()
			.setLenient()
			.registerTypeAdapter(Vec3d.class, new Vec3dTypeAdapter().nullSafe())
			.registerTypeAdapter(TriState.class, new TriStateTypeAdapter())
			.registerTypeAdapter(ItemStack.class, new ItemStackSerde())
			.registerTypeAdapter(BodyPart.MountPoint.class, new MountPointSerde())
			.registerTypeAdapter(ColorPalette.class, new ColorPaletteSerde())
			.registerTypeAdapter(AppendageColor.class, new AppendageColorSerde())
			.registerTypeAdapter(AppendageModel.class, new TaggedUnionSerde<>(
				AppendageModel.ItemStackModel.class
			))
			.create();
	}
	
	public static final JsonRegistryLoader<Appendage> APPENDAGE_PRESET_REGISTRY = new AppendagePresetRegistry(Appendage.class, "appendages/appendage_presets/", new Identifier(MODID, "preset_registry"));
	
	public static final JsonRegistryLoader<Outfit> OUTFIT_REGISTRY = new JsonRegistryLoader<Outfit>(Outfit.class, "appendages/outfits/", new Identifier(MODID, "outfit_registry")).dependsOn(APPENDAGE_PRESET_REGISTRY);
	
	@Override
	public void onInitializeClient() {
		ResourceManagerHelper clientResources = ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES);
		
		clientResources.registerReloadListener(APPENDAGE_PRESET_REGISTRY);
		clientResources.registerReloadListener(OUTFIT_REGISTRY);
	}
}
