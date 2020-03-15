package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.Init;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class OutfitManager {
	public static Outfit getOutfitFor(PlayerEntity player) {
		//return null; //TODO Stub
		
		AppendageType testType = new AppendageType(new Identifier(Init.MODID, "test_type"), "quaternary");
		
		Outfit meme = new Outfit();
		
		meme.addAppendage(new Appendage.Builder()
			.setType(testType)
			.setMountPoint(BodyPart.LEFT_ARM.getMountPointByName("left"))
			.build()
		);
		
		meme.addAppendage(new Appendage.Builder()
			.setType(testType)
			.setMountPoint(BodyPart.LEFT_ARM.getMountPointByName("bottom"))
			.build()
		);
		
		return meme;
	}
}
