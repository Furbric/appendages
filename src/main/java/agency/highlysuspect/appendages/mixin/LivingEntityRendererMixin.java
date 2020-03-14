package agency.highlysuspect.appendages.mixin;

import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings("UnusedReturnValue")
@Mixin(LivingEntityRenderer.class)
public interface LivingEntityRendererMixin {
	@Invoker("addFeature") boolean addFeatureButItsPublicAndNotTypesafe(FeatureRenderer<?, ?> feature);
}
