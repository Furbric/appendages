package agency.highlysuspect.appendages.parts;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Wardrobe {
	public Wardrobe(Path path) {
		wardrobePath = path.resolve("wardrobe");
	}
	
	private final Map<String, Outfit> outfits = new HashMap<>();
	private final Path wardrobePath;
	
	public void init() throws IOException {
		Files.createDirectories(wardrobePath);
	}
	
	public void clear() {
		outfits.clear();
	}
	
	public void read() throws IOException {
		Files.walk(wardrobePath, FileVisitOption.FOLLOW_LINKS)
			.filter(path -> Files.isRegularFile(path))
			.forEach(path -> {
				try {
					
				} catch(Exception e) {
					
				}
			});
	}
}
