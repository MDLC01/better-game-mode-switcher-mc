package com.mdlc.bettergamemodeswitcher.mixin;

import com.mdlc.bettergamemodeswitcher.GameModeConfiguration;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {
    /**
     * Update game mode configuration on change.
     */
    @Inject(method = "setLocalMode(Lnet/minecraft/world/level/GameType;Lnet/minecraft/world/level/GameType;)V", at = @At("HEAD"))
    private void onSetLocalMode(GameType gameMode, GameType previousGameMode, CallbackInfo ci) {
        GameModeConfiguration.handleGameModeChange(gameMode, previousGameMode);
    }

    /**
     * Update game mode configuration on change.
     */
    @Inject(method = "setLocalMode(Lnet/minecraft/world/level/GameType;)V", at = @At("HEAD"))
    private void onSetLocalMode(GameType gameMode, CallbackInfo ci) {
        GameModeConfiguration.handleGameModeChange(gameMode);
    }
}
