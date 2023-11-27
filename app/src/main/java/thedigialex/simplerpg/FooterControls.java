package thedigialex.simplerpg;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class FooterControls {
    Button leftButton;
    Button middleButton;
    Button rightButton;
    int playerId;
    View footerView;
    Activity activity;
    public FooterControls(View footerView, int playerId, Activity activity, String currentLocation) {
        this.playerId = playerId;
        this.footerView = footerView;
        this.activity = activity;
        initViews(currentLocation);
    }
    public void initViews(String currentLocation) {
        leftButton = footerView.findViewById(R.id.button1);
        middleButton = footerView.findViewById(R.id.button2);
        rightButton = footerView.findViewById(R.id.button3);
        switch (currentLocation) {
            case "PlayerInfo":
                leftButton.setBackgroundResource(R.drawable.player_info_button_pressed);
                middleButton.setBackgroundResource(R.drawable.town_button);
                rightButton.setBackgroundResource(R.drawable.adventure_button);
                break;
            case "Adventure":
                leftButton.setBackgroundResource(R.drawable.player_info_button);
                middleButton.setBackgroundResource(R.drawable.town_button);
                rightButton.setBackgroundResource(R.drawable.adventure_button_pressed);
                break;
            default:
                leftButton.setBackgroundResource(R.drawable.player_info_button);
                middleButton.setBackgroundResource(R.drawable.town_button_pressed);
                rightButton.setBackgroundResource(R.drawable.adventure_button);
                break;
        }
        setupButtons(currentLocation);
    }
    public void setupButtons(String currentLocation) {
        leftButton.setOnClickListener(v -> {
            if(!currentLocation.equals("Player")) {
                Intent intent = new Intent(activity, PlayerInfoActivity.class);
                intent.putExtra("playerId", playerId);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        middleButton.setOnClickListener(v -> {
            if(!currentLocation.equals("Town")) {
                Intent intent = new Intent(activity, TownActivity.class);
                intent.putExtra("playerId", playerId);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        rightButton.setOnClickListener(v -> {
            if(!currentLocation.equals("Adventure")) {
                Intent intent = new Intent(activity, AdventureActivity.class);
                intent.putExtra("playerId", playerId);
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }
}