package agency.highlysuspect.appendages;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Init implements ClientModInitializer {
	public static final String MODID = "appendages";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	@Override
	public void onInitializeClient() {
		//brap
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
