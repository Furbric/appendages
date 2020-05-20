package agency.highlysuspect.appendages.parts;

public class AppendageTextureType {
	public AppendageTextureType(int tintSlots) {
		this.tintSlots = tintSlots;
	}
	
	//TODO some reference to the texture
	private final int tintSlots;
	
	public int getTintSlots() {
		return tintSlots;
	}
}
