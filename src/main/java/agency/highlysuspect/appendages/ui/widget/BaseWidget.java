package agency.highlysuspect.appendages.ui.widget;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;

//Essentially, this is the useful stuff from AbstractButtonWidget, extracted to not-buttons.
//Few notes:
// Hovering and KB focus are merged.
public abstract class BaseWidget extends DrawableHelper implements Drawable, Element {
	public BaseWidget(int x, int y, int width, int height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	public int x, y, width, height;
	public String text;
	
	public boolean isFocused, isMouseFocused, isKeyboardFocused;
	public boolean isVisible;
	
	public long lastNarration = Long.MAX_VALUE;
	
	@Override
	public void render(int mouseX, int mouseY, float delta) {
		if(isVisible) {
			updateMouseFocusFlag(mouseX, mouseY);
		}
	}
	
	public void updateMouseFocusFlag(double mouseX, double mouseY) {
		isMouseFocused = isVisible && isMouseOver(mouseX, mouseY);
	}
	
	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		updateMouseFocusFlag(mouseX, mouseY);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return false;
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return false;
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		return false;
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		return false;
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return false;
	}
	
	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		return false;
	}
	
	@Override
	public boolean charTyped(char chr, int keyCode) {
		return false;
	}
	
	@Override
	public boolean changeFocus(boolean lookForwards) {
		return false;
	}
	
	@Override
	public boolean isMouseOver(double mouseX, double mouseY) {
		return mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height;
	}
}
