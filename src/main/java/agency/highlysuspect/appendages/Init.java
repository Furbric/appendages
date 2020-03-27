package agency.highlysuspect.appendages;

import agency.highlysuspect.appendages.resource.AppendageTypesRegistry;
import agency.highlysuspect.appendages.resource.AppendagesResourceReloadListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Init implements ClientModInitializer {
	public static final String MODID = "appendages";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public static AppendageTypesRegistry appendageTypesRegistry = null;
	
	@Override
	public void onInitializeClient() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new AppendagesResourceReloadListener());
	}
	
	private static long lastLogTime = 0;
	public static void logSometimes(String asd) {
		long now = System.currentTimeMillis();
		if(now - lastLogTime > 500L) {
			lastLogTime = now;
			LOGGER.info(asd);
		}
	}
}
