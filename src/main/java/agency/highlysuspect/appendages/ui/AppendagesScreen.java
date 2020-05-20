package agency.highlysuspect.appendages.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

public class AppendagesScreen extends Screen {
	public AppendagesScreen(Screen parentScreen) {
		super(new TranslatableText("appendages.ui.edit.title"));
		this.parentScreen = parentScreen;
	}
	
	private final Screen parentScreen;
	
	@Override
	public void render(int mouseX, int mouseY, float delta) {
		renderBackground();
		super.render(mouseX, mouseY, delta);
		drawString(MinecraftClient.getInstance().textRenderer, "TODO", 50, 50, 0xFFFFFF);
	}
	
	@Override
	public void onClose() {
		MinecraftClient.getInstance().openScreen(parentScreen);
	}
}
