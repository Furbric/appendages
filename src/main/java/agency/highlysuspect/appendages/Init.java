package agency.highlysuspect.appendages;

import agency.highlysuspect.appendages.parts.*;
import agency.highlysuspect.appendages.parts.color.AppendageColor;
import agency.highlysuspect.appendages.resource.PresetRegistry;
import agency.highlysuspect.appendages.resource.PresetRegistryReloadListener;
import agency.highlysuspect.appendages.util.*;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class Init implements ClientModInitializer {
	public static final String MODID = "appendages";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public static PresetRegistry presetRegistry = null;
	public static Wardrobe wardrobe = null;
	
	public static final Gson GSON;
	
	static {
		GSON = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.setPrettyPrinting()
			.setLenient()
			.serializeNulls()
			.registerTypeAdapter(Vec3d.class, new Vec3dTypeAdapter())
			.registerTypeAdapter(ModelIdentifier.class, new ModelIdentifierTypeAdapter().nullSafe())
			.registerTypeAdapter(BodyPart.MountPoint.class, new MountPointSerde())
			.registerTypeAdapter(AppendageColor.class, new AppendageColorSerde())
			.registerTypeAdapter(TypeToken.getParameterized(Preset.class, Appendage.class).getType(), RegistryTypeAdapter.createNullSafe(() -> presetRegistry.getAppendagePresets()))
			.registerTypeAdapter(TypeToken.getParameterized(Preset.class, AppendageModel.class).getType(), RegistryTypeAdapter.createNullSafe(() -> presetRegistry.getModelPresets()))
			.create();
	}
	
	@Override
	public void onInitializeClient() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new PresetRegistryReloadListener());
		
		Path appendagesPath = MinecraftClient.getInstance().runDirectory.toPath().resolve("appendages");
		wardrobe = new Wardrobe(appendagesPath);
	}
}
