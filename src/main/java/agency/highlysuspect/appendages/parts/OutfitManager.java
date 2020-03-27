package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.Init;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class OutfitManager {
	static boolean asdfghjkl = true;
	
	public static Outfit getOutfitFor(PlayerEntity player) {
		
		Outfit test = new Outfit().setName("yeet");
		AppendageType bread = Init.appendageTypesRegistry.getAppendageType(new Identifier(Init.MODID, "bread"));
		
		Appendage breadWing = bread.newAppendage()
			.setPositionOffset(new Vec3d(2.5, 7, -1))
			.setRotationOffset(new Vec3d(-10, -85, -10))
			.setScale(new Vec3d(1, 1, 1.2))
			.vibeCheck();
		
		test.addAppendage(breadWing);
		test.addAppendage(breadWing.mirrored());
		
		Appendage breadArms = bread.newAppendage()
			.setMountPoint(BodyPart.LEFT_ARM.getMountPointByName("left"))
			.setPositionOffset(new Vec3d(0, 2, 0))
			.setRotationOffset(new Vec3d(0, 0, 45))
			.setScale(new Vec3d(0.5, 0.5, 0.5))
			.vibeCheck();
		
		test.addAppendage(breadArms);
		test.addAppendage(breadArms.mirrored());
		
		Appendage breadEars = bread.newAppendage()
			.setMountPoint(BodyPart.HEAD.getMountPointByName("top"))
			.setPositionOffset(new Vec3d(-2, 1, -3))
			.setRotationOffset(new Vec3d(0, -30, 150))
			.setScale(new Vec3d(0.33, 0.33, 0.33))
			.vibeCheck();
		
		test.addAppendage(breadEars);
		test.addAppendage(breadEars.mirrored());
		
		test.addAppendage(bread.newAppendage()
			.setMountPoint(BodyPart.TORSO.getMountPointByName("tail"))
			.setPositionOffset(new Vec3d(0, 10, -2))
			.setRotationOffset(new Vec3d(0, 90, -20))
			.setScale(new Vec3d(0.3, 1.5, 1))
			.vibeCheck()
		);
		
		if(asdfghjkl) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Init.LOGGER.info(gson.toJson(test.toJson(Init.appendageTypesRegistry)));
			asdfghjkl = false;
		}
		
		return test;
	}
}
