package agency.highlysuspect.appendages.parts;

import agency.highlysuspect.appendages.Init;
import agency.highlysuspect.appendages.parts.color.AppendageColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class OutfitManager {
	static boolean asdfghjkl = true;
	
	public static Outfit getOutfitFor(PlayerEntity player) {
		if(false) return null;
		
		Outfit test = new Outfit().setName("yeet");
		AppendagePreset bread = Init.bestiary.getPreset(new Identifier(Init.MODID, "bread"));
		
		Appendage breadWing = bread.get()
			.setPositionOffset(new Vec3d(2.5, 7, -1))
			.setRotationOffset(new Vec3d(-10, -85, -10))
			.setScale(new Vec3d(1, 1, 1.2))
			.vibeCheck();
		
		test.addAppendage(breadWing);
		test.addAppendage(breadWing.mirrored());
		
		Appendage breadArms = bread.get()
			.setMountPoint(BodyPart.LEFT_ARM.getMountPointByName("left"))
			.setPositionOffset(new Vec3d(0, 2, 0))
			.setRotationOffset(new Vec3d(0, 0, 45))
			.setScale(new Vec3d(0.5, 0.5, 0.5))
			.vibeCheck();
		
		test.addAppendage(breadArms);
		test.addAppendage(breadArms.mirrored());
		
		Appendage breadEars = bread.get()
			.setMountPoint(BodyPart.HEAD.getMountPointByName("top"))
			.setPositionOffset(new Vec3d(-2, 1, -3))
			.setRotationOffset(new Vec3d(0, -30, 150))
			.setScale(new Vec3d(0.33, 0.33, 0.33))
			.vibeCheck();
		
		test.addAppendage(breadEars);
		test.addAppendage(breadEars.mirrored());
		
		test.addAppendage(bread.get()
			.setMountPoint(BodyPart.TORSO.getMountPointByName("tail"))
			.setPositionOffset(new Vec3d(0, 10, -2))
			.setRotationOffset(new Vec3d(0, 90, -20))
			.setScale(new Vec3d(0.3, 1.5, 1))
			.onModel(model -> {
				model.getTextures().forEach(texture -> {
					
				});
			})
			.vibeCheck()
		);
		
		test.getPalette().set(0, new AppendageColor.Fixed().setColor(0x223344));
		test.getPalette().set(1, new AppendageColor.PaletteReference().setReference(0));
		
		//Test that it survives a few serialization and deserialization cycles lmao
		String toJson = Init.GSON.toJson(Init.GSON.fromJson(Init.GSON.toJson(test), Outfit.class));
		if(asdfghjkl) {
			Init.LOGGER.info(toJson);
			asdfghjkl = false;
		}
		
		return Init.GSON.fromJson(toJson, Outfit.class);
	}
}
