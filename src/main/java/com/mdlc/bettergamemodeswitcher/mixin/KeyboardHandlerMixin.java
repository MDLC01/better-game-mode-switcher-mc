package com.mdlc.bettergamemodeswitcher.mixin;

import com.mojang.brigadier.tree.RootCommandNode;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
    @Shadow @Final private Minecraft minecraft;

    /**
     * Redirects the {@link net.minecraft.world.entity.player.Player#hasPermissions(int) hasPermissions} call in F3+N
     * behavior to check whether the {@code gamemode} command is available instead.
     */
    @Redirect(method = "handleDebugKeys", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasPermissions(I)Z", ordinal = 1))
    private boolean redirectF3NHasPermission(LocalPlayer player, int level) {
        ClientPacketListener connection = this.minecraft.getConnection();
        if (connection == null) {
            return true;
        }
        RootCommandNode<ClientSuggestionProvider> root = connection.getCommands().getRoot();
        return root.getChild("gamemode") != null;
    }

    /**
     * Redirects the {@link net.minecraft.world.entity.player.Player#hasPermissions(int) hasPermissions} call in the
     * game mode switcher's behavior to check whether the {@code gamemode} command is available instead.
     */
    @Redirect(method = "handleDebugKeys", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasPermissions(I)Z", ordinal = 2))
    private boolean redirectF3F4HasPermission(LocalPlayer player, int level) {
        ClientPacketListener connection = this.minecraft.getConnection();
        if (connection == null) {
            return true;
        }
        RootCommandNode<ClientSuggestionProvider> root = connection.getCommands().getRoot();
        return root.getChild("gamemode") != null;
    }
}
