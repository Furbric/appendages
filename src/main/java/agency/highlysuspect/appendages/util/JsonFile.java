package agency.highlysuspect.appendages.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.FileNameUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;

public class JsonFile {
	public JsonFile(Path path) {
		this.path = path;
	}
	
	private Path path;
	private JsonObject json;
	
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	
	public Path getPath() {
		return path;
	}
	
	public void setPath(Path path) {
		this.path = path;
	}
	
	public JsonObject getJson() {
		return json;
	}
	
	public void setJson(JsonObject json) {
		this.json = json;
	}
	
	public void write() throws IOException {
		Files.write(path, GSON.toJson(json).getBytes(StandardCharsets.UTF_8));
	}
	
	public void read() throws IOException {
		json = new JsonParser().parse(Files.newBufferedReader(path)).getAsJsonObject();
	}
	
	public static class Directory {
		public Directory(Path base) {
			this.base = base;
		}
		
		private final Path base;
		
		public JsonFile newFile(String name) throws IOException {
			return new JsonFile(Paths.get(sanitizeFilename(name)));
		}
		
		private String sanitizeFilename(String name) throws IOException {
			return FileNameUtil.getNextUniqueName(base, name, ".json");
		}
		
		public Collection<JsonFile> getAllJsonFiles() throws IOException {
			return Files.list(base)
				.filter(path -> path.endsWith(".json"))
				.map(JsonFile::new)
				.collect(Collectors.toList());
		}
	}
}
