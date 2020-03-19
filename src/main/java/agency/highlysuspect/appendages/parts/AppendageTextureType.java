package agency.highlysuspect.appendages.parts;

import net.minecraft.util.Identifier;

public class AppendageTextureType {
	public AppendageTextureType(Identifier id, int tintSlots) {
		this.id = id;
		this.tintSlots = tintSlots;
	}
	
	private final Identifier id;
	private final int tintSlots;
	
	public Identifier getId() {
		return id;
	}
	
	public int getTintSlots() {
		return tintSlots;
	}
}
