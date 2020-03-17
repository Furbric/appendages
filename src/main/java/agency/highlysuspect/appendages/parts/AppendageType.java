package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.render.AppendageModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class AppendageType {
	public AppendageType(Identifier id, String artist) {
		this.id = id;
		this.artist = artist;
	}
	
	public final Identifier id;
	public final String artist;
	
	public Identifier getId() {
		return id;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public AppendageModel getModel() {
		//TODO Stub
		return null;
	}
	
	public void draw(MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
	
	}
	
	//Model? (probably by id)
}
