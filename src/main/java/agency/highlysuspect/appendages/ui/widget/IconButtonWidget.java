package agency.highlysuspect.appendages.ui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class IconButtonWidget extends AbstractButtonWidget {
	public IconButtonWidget(int x, int y, int width, int height, String narratorDescription, Identifier icon, Consumer<IconButtonWidget> action) {
		super(x, y, width, height, narratorDescription);
		this.icon = icon;
		this.action = action;
	}
	
	private final Identifier icon;
	private final Consumer<IconButtonWidget> action;
	
	@Override
	public void onClick(double mouseX, double mouseY) {
		action.accept(this);
	}
	
	@Override
	protected void renderBg(MinecraftClient client, int mouseX, int mouseY) {
		//TODO
	}
	
	@Override
	public void drawCenteredString(TextRenderer textRenderer, String str, int centerX, int y, int color) {
		//No-op
		//hack to suppress default behavior of rendering the label without having to paste too much abstractbuttonwidget code
	}
}
