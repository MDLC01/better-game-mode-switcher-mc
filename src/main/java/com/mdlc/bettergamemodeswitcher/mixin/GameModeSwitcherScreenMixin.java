package com.mdlc.bettergamemodeswitcher.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mdlc.bettergamemodeswitcher.GameModeConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.server.permissions.PermissionCheck;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixin {
    /**
     * Redirects the {@link PermissionCheck#check(PermissionSet)} call in F3+N behavior to check whether the
     * {@code gamemode} command is available instead.
     */
    @Redirect(method = "switchToHoveredGameMode(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screens/debug/GameModeSwitcherScreen$GameModeIcon;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/permissions/PermissionCheck;check(Lnet/minecraft/server/permissions/PermissionSet;)Z", ordinal = 0))
    private static boolean redirectF3NHasPermission(PermissionCheck check, PermissionSet permissions, @Local(argsOnly = true) Minecraft minecraft) {
        return GameModeConfiguration.hasPermission(minecraft);
    }

    /**
     * Changes the default selected game mode to ignore Spectator.
     */
    @Inject(method = "getDefaultSelected", at = @At("HEAD"), cancellable = true)
    private void changeDefaultSelected(CallbackInfoReturnable<GameType> cir) {
        cir.setReturnValue(GameModeConfiguration.getSwitcherDefaultSelection(Minecraft.getInstance().gameMode));
    }
}
