package agency.highlysuspect.appendages.ui.widget;

import agency.highlysuspect.appendages.Init;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;

public class ColorPickerWidget extends BaseWidget {
	public ColorPickerWidget(int x, int y) {
		super(x, y, WIDTH, HEIGHT, I18n.translate("appendages.ui.colorPicker"));
	}
	
	private static final int WIDTH = 200;
	private static final int HEIGHT = 150;
	
	private Mode currentMode = Mode.HV_PANE;
	
	private static final Identifier MODE_CYCLE_ICON = new Identifier(Init.MODID, "color_picker_mode_cycle"); //TODO
	private IconButtonWidget modeCycleButton = new IconButtonWidget(
		5, 5, 10, 10, "appendages.ui.colorPicker.modeCycle", MODE_CYCLE_ICON, button -> cycleMode()
	);
	
	private void cycleMode() {
		currentMode = currentMode.next();
	}
	
	@Override
	public void render(int mouseX, int mouseY, float delta) {
		super.render(mouseX, mouseY, delta);
		
		
	}
	
	private enum Mode {
		PALETTE,
		HV_PANE,
		RGB_SLIDERS,
		HSV_SLIDERS,
		;
		
		private Mode next() {
			switch(this) {
				case PALETTE: return HV_PANE;
				case HV_PANE: return RGB_SLIDERS;
				case RGB_SLIDERS: return HSV_SLIDERS;
				case HSV_SLIDERS: return PALETTE;
				default: throw new IllegalStateException(this.name());
			}
		}
	}
}
