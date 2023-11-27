package thedigialex.simplerpg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlayerFragmentStats extends Fragment {
    PlayerControls playerControls;
    public PlayerFragmentStats(PlayerControls playerControls) {
        this.playerControls = playerControls;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_stats, container, false);
        //topTextViews = new TextView[player.getPlayerStats().length];
       // bottomTextViews = new TextView[player.getPlayerStats().length];
        setUpViews(rootView);
        return rootView;
    }
    public void setUpViews(View rootView) {
        int[] viewIds = {
                R.id.PlayerGoldText, R.id.PlayerStepText, R.id.strText,
                R.id.dexText, R.id.agiText, R.id.intText
        };
        String[] textData = {
                "Gold: " + playerControls.player.getGold(),
                "Steps: " + playerControls.player.getSteps(),
                "STR: " + playerControls.player.getStrength(),
                "DEX: " + playerControls.player.getDexterity(),
                "INT: " + playerControls.player.getIntelligence(),
                "END: " + playerControls.player.getEndurance()
        };
        for (int i = 0; i < viewIds.length; i++) {
            TextView textView = rootView.findViewById(viewIds[i]);
            textView.setText(textData[i]);
        }
        AddStatTextView(rootView.findViewById(R.id.StatsContainerTop),0);
        AddStatTextView(rootView.findViewById(R.id.StatsContainerMiddle),3);
        AddStatTextView(rootView.findViewById(R.id.StatsContainerBottom),6);
    }
    private void AddStatTextView(LinearLayout mainLayout, int offSet) {
        mainLayout.removeAllViews();
        int[] stats = playerControls.getPlayerStats();
        String[] statsName = new String[]{"Attack", "Defense", "MagAtk", "MagDef", "Speed", "Hit", "HP", "MA", "ST"};
        int maxStatsToShow = Math.min(3, stats.length - offSet);
        for (int i = offSet; i < offSet + maxStatsToShow; i++) {
            TextView textView = new TextView(playerControls.context);
            String text = statsName[i] + "\n" + stats[i];
            textView.setText(text);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            layoutParams.weight = 1;
            textView.setLayoutParams(layoutParams);
            mainLayout.addView(textView);
        }
    }
}
