package agency.highlysuspect.appendages;

import agency.highlysuspect.appendages.parts.AppendageModel;
import agency.highlysuspect.appendages.parts.BodyPart;
import agency.highlysuspect.appendages.parts.color.AppendageColor;
import agency.highlysuspect.appendages.resource.AppendageModelPresetLoader;
import agency.highlysuspect.appendages.resource.AppendagePresetLoader;
import agency.highlysuspect.appendages.util.*;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Init implements ClientModInitializer {
	public static final String MODID = "appendages";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public static final Gson GSON;
	
	public static final AppendagePresetLoader appendagePresetLoader = new AppendagePresetLoader();
	public static final AppendageModelPresetLoader appendageModelPresetLoader = new AppendageModelPresetLoader();
	
	static {
		GSON = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.setPrettyPrinting()
			.setLenient()
			.serializeNulls()
			.registerTypeAdapter(Vec3d.class, new Vec3dTypeAdapter())
			.registerTypeAdapter(ItemStack.class, new ItemStackSerde())
			.registerTypeAdapter(ModelIdentifier.class, new ModelIdentifierTypeAdapter().nullSafe())
			.registerTypeAdapter(BodyPart.MountPoint.class, new MountPointSerde())
			.registerTypeAdapter(AppendageColor.class, new AppendageColorSerde())
			.registerTypeAdapter(AppendageModel.class, new TaggedUnionSerde<>(
				AppendageModel.ItemStackModel.class
			))
			.create();
	}
	
	@Override
	public void onInitializeClient() {
		ResourceManagerHelper clientResources = ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES);
		
		clientResources.registerReloadListener(appendagePresetLoader);
		//clientResources.registerReloadListener(appendageModelPresetLoader);
	}
}
