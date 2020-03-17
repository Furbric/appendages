package agency.highlysuspect.appendages.mixin;

import agency.highlysuspect.appendages.Init;
import agency.highlysuspect.appendages.ui.AppendagesScreen;
import agency.highlysuspect.appendages.ui.Buttons;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
	public GameMenuScreenMixin() { super(null); }
	
	@Inject(
		method = "initWidgets",
		at = @At("TAIL")
	)
	public void onInitWidgets(CallbackInfo ci) {
		addButton(new Buttons.EnterSettings(
			5,
			height - 25,
			120,
			20,
			I18n.translate("appendages.ui.button.appendagesSettings"),
			(button -> {
				Init.LOGGER.info("owo");
				MinecraftClient.getInstance().openScreen(new AppendagesScreen(this));
			})
		));
	}
}
