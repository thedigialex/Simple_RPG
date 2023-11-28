package thedigialex.simplerpg;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HeaderControls {
    ImageView profileImage;
    TextView playerName;
    TextView playerLevel;
    TextView playerGold;
    ProgressBar levelProgressBarHeader;
    View headerView;
    Player player;

    public HeaderControls(View headerView, Player player) {
        this.headerView = headerView;
        this.player = player;
    }
    public void initViews() {
        profileImage = headerView.findViewById(R.id.profile_image);
        playerName = headerView.findViewById(R.id.player_name);
        playerGold = headerView.findViewById(R.id.player_money);
        playerLevel = headerView.findViewById(R.id.player_level);
        levelProgressBarHeader = headerView.findViewById(R.id.levelProgressBarHeader);
        setUpViews();
    }
    public void setUpViews() {
        String nameAndTitle = player.getTitle() + " " + player.getName();
        String raceAndClass = player.getRace() + " " + player.getJob();
        //profileImage.setImageResource(resourceID);
        playerName.setText(player.getName());
        updateHeader("Gold",player.getGold()+"");
        updateHeader("Exp",player.getLevel()+","+player.getExp()+","+raceAndClass);
    }
    public void updateHeader(String view, String value) {
        switch (view) {
            case "Gold":
                String Gold = "Gold: "+ value;
                playerGold.setText(Gold);
                break;
            case "Exp":
                String[] valueData = value.split(",");
                int level = Integer.parseInt(valueData[0]);
                int exp = Integer.parseInt(valueData[1]);
                int expRequired = level * 10;
                levelProgressBarHeader.setMax(expRequired);
                levelProgressBarHeader.setProgress(exp);
                String levelRC = valueData[2] + "\nLevel: " + level;
                playerLevel.setText(levelRC);
                break;
        }
    }
}