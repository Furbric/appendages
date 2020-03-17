package agency.highlysuspect.appendages.ui;

import agency.highlysuspect.appendages.ui.util.RenderState;
import agency.highlysuspect.appendages.ui.util.UIRenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

public class AppendagesScreen extends Screen {
	public AppendagesScreen(Screen parentScreen) {
		super(new TranslatableText("appendages.ui.edit.title"));
		this.parentScreen = parentScreen;
	}
	
	private final Screen parentScreen;
	
	private final Buttons.Back backButton = new Buttons.Back(5, 5, TITLEBAR_HEIGHT - 10, (button) -> onClose());
	
	private final Easer sidePaneWidthEaser = new Easer(0, 200, 0.5f);
	private final RenderState renderState = new RenderState();
	
	private static final int TITLEBAR_HEIGHT = 30;
	
	@Override
	protected void init() {
		addButton(backButton);
	}
	
	@Override
	public void render(int mouseX, int mouseY, float delta) {
		//Not calling super here - all super does is render buttons, and I don't want to do that right now.
		renderBackground();
		
		//Draw the titlebar and back button
		drawTitlebar(mouseX, mouseY, delta);
		
		//Draw the side panel background
		float sidePaneWidth = sidePaneWidthEaser.easeAndGet(delta);
		UIRenderUtils.fillGradientButBetter(0, TITLEBAR_HEIGHT, sidePaneWidth, height, 0xFFFF0000, 0xFFFF00FF, 0);
		
		//Scissor inside the side panel background
		double scale = MinecraftClient.getInstance().getWindow().getScaleFactor();
		renderState.pushScissor(0, (int) Math.round(TITLEBAR_HEIGHT * scale), (int) Math.round(sidePaneWidth * scale), (int) ((height - TITLEBAR_HEIGHT) * scale));
		
		//Draw the side panel
		drawSidePanelUi(mouseX, mouseY, delta);
		
		//Pop scissor
		renderState.popScissor();
		
		super.render(mouseX, mouseY, delta);
	}
	
	private void drawTitlebar(int mouseX, int mouseY, float delta) {
		backButton.render(mouseX, mouseY, delta);
		
		//IntelliJ doesn't know that the back button is square, so the X position of the text really *is* dependent on the titlebar height.
		//The damn thing is too smart - it's complaining I pass a variable with "height" into a parameter named "x".
		//noinspection SuspiciousNameCombination
		drawString(MinecraftClient.getInstance().textRenderer, "pane title here", TITLEBAR_HEIGHT, (TITLEBAR_HEIGHT / 2) - 4, 0xFFFFFF);
	}
	
	private void drawSidePanelUi(int mouseX, int mouseY, float delta) {
		UIRenderUtils.fillGradientButBetter(0, TITLEBAR_HEIGHT, width, height / 3f, 0xFF00FF00, 0xFF00FFFF, 0);
	}
	
	@Override
	public void onClose() {
		//TODO once I get pane stacks in, make this roll back only one pane
		MinecraftClient.getInstance().openScreen(parentScreen);
	}
}
