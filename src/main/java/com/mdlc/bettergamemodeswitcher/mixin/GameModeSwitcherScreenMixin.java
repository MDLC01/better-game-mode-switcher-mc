package com.mdlc.bettergamemodeswitcher.mixin;

import com.mdlc.bettergamemodeswitcher.GameModeConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixin {
    /**
     * Changes the default selected game mode to ignore Spectator.
     */
    @Inject(method = "getDefaultSelected", at = @At("HEAD"), cancellable = true)
    private void changeDefaultSelected(CallbackInfoReturnable<GameType> cir) {
        cir.setReturnValue(GameModeConfiguration.getSwitcherDefaultSelection(Minecraft.getInstance().gameMode));
    }
}
