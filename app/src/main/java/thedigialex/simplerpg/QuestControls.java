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
    HeaderControls headerControls;
    View itemView;
    ConstraintLayout PopUpMenu;
    LinearLayout PopUpLinearLayout;
    LayoutInflater inflater;
    AppDatabase appDatabase;
    public QuestControls(Context context, Player player, HeaderControls headerControls, AppDatabase appDatabase) {
        this.context = context;
        this.player = player;
        this.headerControls = headerControls;
        this.appDatabase = appDatabase;
    }
    public void addQuestsSlot(Boolean completed) {
        PopUpLinearLayout.removeAllViews();
        for (Quest quest : quests) {
            if(player.getLevel() >= quest.getLevelRequirement() && quest.getIsCompleted() == completed) {
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
        player.setGold(player.getGold() + quest.getRewardGold());
        player.updatePlayer(player);
        quest.setIsCompleted(true);
        new Thread(() -> appDatabase.questDao().update(quest)).start();
        headerControls.setUpViews();
    }
    public String questTypeToString(int id) {
        String questType;
        switch (id) {
            default: questType = "Fetch";
        }
        return "Requirements: " + questType;
    }
}