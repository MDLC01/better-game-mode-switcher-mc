package com.mdlc.bettergamemodeswitcher;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.level.GameType;


public final class GameModeConfiguration {
    private GameModeConfiguration() {
    }

    /**
     * The player's last (or current) non-Spectator game mode.
     */
    private static GameType primaryGameMode = GameType.CREATIVE;
    /**
     * The player's second-to-last non-Spectator game mode.
     */
    private static GameType secondaryGameMode = GameType.SURVIVAL;

    /**
     * Updates the primary and secondary game modes.
     */
    public static void handleGameModeChange(GameType gameMode) {
        if (gameMode != null && gameMode != GameType.SPECTATOR && gameMode != primaryGameMode) {
            secondaryGameMode = primaryGameMode;
            primaryGameMode = gameMode;
        }
    }

    /**
     * Updates the primary and secondary game modes, taking into account the previous game mode.
     */
    public static void handleGameModeChange(GameType gameMode, GameType previousGameMode) {
        handleGameModeChange(previousGameMode);
        handleGameModeChange(gameMode);
    }

    /**
     * Returns the game mode that should be preselected given the current game mode.
     */
    public static GameType getSwitcherDefaultSelection(MultiPlayerGameMode currentGameMode) {
        if (currentGameMode != null && currentGameMode.getPlayerMode() == primaryGameMode) {
            return secondaryGameMode;
        } else {
            return primaryGameMode;
        }
    }
}
