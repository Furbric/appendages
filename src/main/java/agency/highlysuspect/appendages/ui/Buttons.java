package agency.highlysuspect.appendages.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AbstractPressableButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;

import java.util.function.Consumer;

public class Buttons {
	public static class Back extends AbstractPressableButtonWidget {
		public Back(int x, int y, int size, Consumer<Back> onPress) {
			super(x, y, size, size, I18n.translate("appendages.ui.button.back"));
			action = onPress;
		}
		
		//reinventing the ButtonWidget a bit
		private final Consumer<Back> action;
		@Override
		public void onPress() {
			action.accept(this);
		}
		
		@Override
		protected void renderBg(MinecraftClient client, int mouseX, int mouseY) {
			//TODO draw leftwards-pointing arrow icon
		}
		
		@Override
		public void drawCenteredString(TextRenderer textRenderer, String str, int centerX, int y, int color) {
			//No-op (suppress default AbstractPressableButtonWidget behavior of rendering the label as text)
			//I still keep the label non-blank for Narrator reasons
		}
	}
	
	public static class EnterSettings extends ButtonWidget {
		public EnterSettings(int x, int y, int width, int height, String message, PressAction onPress) {
			super(x, y, width, height, message, onPress);
		}
		
		@Override
		public boolean isHovered() {
			//Lol this is jank ass, but it's just a convenient place to hook
			//Fight me
			boolean yes = super.isHovered();
			if(yes) setAlpha(1);
			else setAlpha(0.2f);
			return yes;
		}
	}
}
