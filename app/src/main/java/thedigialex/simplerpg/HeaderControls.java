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

    public HeaderControls(View headerView) {
        this.headerView = headerView;
        initViews();
    }
    public void initViews() {
        profileImage = headerView.findViewById(R.id.profile_image);
        playerName = headerView.findViewById(R.id.player_name);
        playerGold = headerView.findViewById(R.id.player_money);
        playerLevel = headerView.findViewById(R.id.player_level);
        levelProgressBarHeader = headerView.findViewById(R.id.levelProgressBarHeader);
    }
    public void setUpViews(String name, int gold, int resourceID, int level, int exp, String raceAndClass) {
        //profileImage.setImageResource(resourceID);
        playerName.setText(name);
        updateHeader("Gold",gold+"");
        updateHeader("Exp",level+","+exp+","+raceAndClass);
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