package agency.highlysuspect.appendages.parts;

public class AppendageModelType {
	public AppendageModelType(int textureCount) {
		this.textureCount = textureCount;
	}
	
	//TODO something about the model of course
	private final int textureCount;
	
	public int getTextureCount() {
		return textureCount;
	}
}
