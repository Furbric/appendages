package agency.highlysuspect.appendages;

import agency.highlysuspect.appendages.parts.*;
import agency.highlysuspect.appendages.parts.color.AppendageColor;
import agency.highlysuspect.appendages.resource.Bestiary;
import agency.highlysuspect.appendages.resource.BestiaryReloadListener;
import agency.highlysuspect.appendages.util.AppendageColorSerde;
import agency.highlysuspect.appendages.util.MountPointSerde;
import agency.highlysuspect.appendages.util.RegistryTypeAdapter;
import agency.highlysuspect.appendages.util.Vec3dTypeAdapter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class Init implements ClientModInitializer {
	public static final String MODID = "appendages";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public static Bestiary bestiary = null;
	public static Wardrobe wardrobe = null;
	
	public static final Gson GSON;
	
	static {
		GSON = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.setPrettyPrinting()
			.serializeNulls()
			.registerTypeAdapter(Vec3d.class, new Vec3dTypeAdapter())
			.registerTypeAdapter(BodyPart.MountPoint.class, new MountPointSerde())
			.registerTypeAdapter(AppendageColor.class, new AppendageColorSerde())
			.registerTypeAdapter(AppendagePreset.class, RegistryTypeAdapter.createNullSafe(() -> bestiary.getPresets()))
			.registerTypeAdapter(AppendageModelType.class, RegistryTypeAdapter.createNullSafe(() -> bestiary.getModelTypes()))
			.registerTypeAdapter(AppendageTextureType.class, RegistryTypeAdapter.createNullSafe(() -> bestiary.getTextureTypes()))
			.create();
	}
	
	@Override
	public void onInitializeClient() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new BestiaryReloadListener());
		
		Path appendagesPath = MinecraftClient.getInstance().runDirectory.toPath().resolve("appendages");
		wardrobe = new Wardrobe(appendagesPath);
	}
}
