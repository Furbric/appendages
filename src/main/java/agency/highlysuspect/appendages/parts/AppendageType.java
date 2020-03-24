package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.render.AppendageModel;
import agency.highlysuspect.appendages.util.JsonHelper2;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class AppendageType {
	private Identifier id;
	
	public Identifier getId() {
		return id;
	}
	
	public AppendageType setId(Identifier id) {
		this.id = id;
		return this;
	}
	
	public String getArtist() {
		//TODO look this up
		return "Nobody";
	}
	
	public AppendageModel getModel() {
		//TODO look this up
		return null;
	}
	
	public void draw(MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
		//TODO of course
	}
	
	public JsonElement toJson() {
		JsonObject j = new JsonObject();
		
		j.addProperty("id", id.toString());
		
		return j;
	}
	
	public static AppendageType fromJson(JsonElement je) throws JsonParseException {
		JsonObject j = JsonHelper2.ensureType(je, JsonObject.class);
		
		return new AppendageType()
			.setId(new Identifier(JsonHelper.getString(j, "id")));
	}
}
