package agency.highlysuspect.appendages.parts;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.math.Vec3d;

import java.util.Collection;

public enum BodyPart {
	HEAD, TORSO, RIGHT_ARM, LEFT_ARM, RIGHT_LEG, LEFT_LEG,
	;
	
	public ModelPart associatedModelPart(PlayerEntityModel<?> model) {
		switch(this) {
			case HEAD: return model.head;
			case TORSO: return model.torso;
			case RIGHT_ARM: return model.rightArm;
			case LEFT_ARM: return model.leftArm;
			case RIGHT_LEG: return model.rightLeg;
			case LEFT_LEG: return model.leftLeg;
			default: throw new IllegalStateException("BodyPart with no model part assigned");
		}
	}
	
	private static final Multimap<BodyPart, MountPoint> mountsByPart = HashMultimap.create();
	
	static {
		//TODO position and rotate ALLLLLL of these
		//needs to know about the dimensions of each box, in order to automatically do it (check the player model sizes)
		//maybe more points if they are needed
		for(BodyPart part : values()) {
			mountsByPart.put(part, new MountPoint(part, "origin", new Vec3d(0, 0, 0), new Vec3d(0, 0, 0)));
			mountsByPart.put(part, new MountPoint(part, "top", null, null));
			mountsByPart.put(part, new MountPoint(part, "bottom", null, null));
			mountsByPart.put(part, new MountPoint(part, "left", null, null));
			mountsByPart.put(part, new MountPoint(part, "right", null, null));
			mountsByPart.put(part, new MountPoint(part, "front", null, null));
			mountsByPart.put(part, new MountPoint(part, "back", null, null));
		}
	}
	
	public Collection<MountPoint> getAvailableMountPoints() {
		return mountsByPart.get(this);
	}
	
	public static class MountPoint {
		private MountPoint(BodyPart part, String name, Vec3d positionOffset, Vec3d rotationOffset) {
			this.part = part;
			this.name = name;
			this.positionOffset = positionOffset;
			this.rotationOffset = rotationOffset;
		}
		
		public final BodyPart part;
		public final String name;
		public final Vec3d positionOffset;
		public final Vec3d rotationOffset;
	}
}
