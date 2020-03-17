package agency.highlysuspect.appendages.ui.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;

public class UIRenderUtils {
	/**
	 * Version of DrawableHelper#fillGradient that doesn't round its input to integers.
	 * (Also it takes blitOffset as a parameter, for completeness - normally some instanced internal drawablehelper junk)
	 */
	public static void fillGradientButBetter(double left, double top, double right, double bottom, int color1, int color2, double blitOffset) {
		float f = (float)(color1 >> 24 & 255) / 255.0F;
		float g = (float)(color1 >> 16 & 255) / 255.0F;
		float h = (float)(color1 >> 8 & 255) / 255.0F;
		float i = (float)(color1 & 255) / 255.0F;
		float j = (float)(color2 >> 24 & 255) / 255.0F;
		float k = (float)(color2 >> 16 & 255) / 255.0F;
		float l = (float)(color2 >> 8 & 255) / 255.0F;
		float m = (float)(color2 & 255) / 255.0F;
		RenderSystem.disableTexture();
		RenderSystem.enableBlend();
		RenderSystem.disableAlphaTest();
		RenderSystem.defaultBlendFunc();
		RenderSystem.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
		bufferBuilder.vertex(right, top, blitOffset).color(g, h, i, f).next();
		bufferBuilder.vertex(left, top, blitOffset).color(g, h, i, f).next();
		bufferBuilder.vertex(left, bottom, blitOffset).color(k, l, m, j).next();
		bufferBuilder.vertex(right, bottom, blitOffset).color(k, l, m, j).next();
		tessellator.draw();
		RenderSystem.shadeModel(7424);
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.enableTexture();
	}
	
	/**
	 * Returns 0 if the number is at the bottom of the range, 1 if the number is at the top, and smoothly transitions in between.
	 * The inverse of the "lerp" operation (which takes a range and a 0..1, and produces a number that percentage of the way through the range)
	 */
	public static float unlerp(float between, float min, float max) {
		return (between - min) / (max - min);
	}
}
