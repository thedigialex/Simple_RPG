package thedigialex.simplerpg;

import android.app.Application;
public class PlayerControllerManager extends Application {
    private PlayerControls playerControls;
    public PlayerControls getPlayerControls() {
        return playerControls;
    }
    public void setPlayerControls(PlayerControls playerControls) {
        this.playerControls = playerControls;
    }
}