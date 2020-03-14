package agency.highlysuspect.appendages.mixin;

import agency.highlysuspect.appendages.render.AppendagesFeatureRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
	@Inject(
		method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V",
		at = @At("TAIL")
	)
	public void onConstruct(CallbackInfo ci) {
		//lol this is cursed sorry
		((LivingEntityRendererMixin)this).addFeatureButItsPublicAndNotTypesafe(new AppendagesFeatureRenderer<>((PlayerEntityRenderer) (Object) this));
	}
}
