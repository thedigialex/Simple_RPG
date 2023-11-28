package thedigialex.simplerpg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.List;

public class QuestControls {
    List<Quest> quests;
    Context context;
    Player player;
    View itemView;
    ConstraintLayout PopUpMenu;
    LinearLayout PopUpLinearLayout;
    LayoutInflater inflater;
    public QuestControls(Context context, Player player) {
        this.context = context;
        this.player = player;
    }
    public void addQuestsSlot() {
        for (Quest quest : quests) {
            if(player.getLevel() >= quest.getLevelRequirement()) {
                itemView = inflater.inflate(R.layout.quest_slot, null);
                LinearLayout HiddenLayout = itemView.findViewById(R.id.HiddenLayout);
                HiddenLayout.setVisibility(View.GONE);
                itemView.setOnClickListener(new View.OnClickListener() {
                    boolean isHidden = true;
                    @Override
                    public void onClick(View v) {
                        HiddenLayout.setVisibility(isHidden ? View.VISIBLE : View.GONE);
                        isHidden = !isHidden;
                    }
                });
                TextView textView0 = itemView.findViewById(R.id.topQuestTextView);
                TextView textView1 = itemView.findViewById(R.id.bottomQuestTextView);
                TextView textView2 = itemView.findViewById(R.id.QuestRequirementTextView);
                textView0.setText(quest.getName());
                textView1.setText(quest.getDescription());
                textView2.setText(questTypeToString(quest.getQuestType()));
                Button QuestTurnInButton = itemView.findViewById(R.id.QuestTurnInButton);
                QuestTurnInButton.setVisibility(View.INVISIBLE);
                if (!quest.getIsCompleted()) {
                    QuestTurnInButton.setVisibility(View.VISIBLE);
                    QuestTurnInButton.setOnClickListener(v -> checkQuestCompletion(quest));
                }
                PopUpLinearLayout.addView(itemView);
            }
        }
    }
    public void checkQuestCompletion(Quest quest) {
        player.setGold(player.getGold() + 5);
        player.updatePlayer(player);
    }

    public String questTypeToString(int id) {
        String questType;
        switch (id) {
            default: questType = "Fetch";
        }
        return "Requirements: " + questType;
    }
}