package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.render.AppendageModel;
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
	
	//Model? (probably by id)
}
