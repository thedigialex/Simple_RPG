package thedigialex.simplerpg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TownActivity extends AppCompatActivity {
    PlayerControls playerControls;
    boolean menuOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town);
        playerControls = ((PlayerControllerManager) getApplication()).getPlayerControls();
        if(playerControls != null) {
            playerControls.UpdateViews(findViewById(R.id.header), findViewById(R.id.footer), "Town", this);
        }
        else {
            Intent intent = getIntent();
            int playerId = intent.getIntExtra("playerId", -1);
            playerControls = new PlayerControls(playerId, findViewById(R.id.header), findViewById(R.id.footer),this, getApplicationContext(), "Town");
            ((PlayerControllerManager) getApplication()).setPlayerControls(playerControls);
        }
    }
    public void showQuest(View view) {
        playerControls.createQuestControls(getLayoutInflater(), findViewById(R.id.PopUpLinearLayout), findViewById(R.id.PopUpMenu));
        if(!menuOpen){
            whichPopUpToShow(1);
            playerControls.questControls.addQuestsSlot(false);
        }
    }
    public void showBank(View view) {
        if(!menuOpen) {
            whichPopUpToShow(2);
        }

        //Add item example
        Item item = new Item(0,0, "Weapon", "Weapon", playerControls.player.getPlayerId(), false, true,1);
        item.stack =1;
        playerControls.inventoryControls.addItem(item);
        item = new Item(1,0, "Craft", "CraftMaterial", playerControls.player.getPlayerId(), false, false,10);
        item.stack =1;
        playerControls.inventoryControls.addItem(item);
        item = new Item(2,0, "Potion", "Potion", playerControls.player.getPlayerId(), false, false,10);
        item.stack =1;
        playerControls.inventoryControls.addItem(item);

        Skill skill = new Skill(0,"Placeholder", "Holding",  playerControls.player.getPlayerId(), false);
        playerControls.inventoryControls.addSkill(skill);
    }
    public void bankSubmission(View view) {
        EditText submissionAmount = findViewById(R.id.BankEdit);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch bankSwitch = findViewById(R.id.BankSwitch);
        String text = submissionAmount.getText().toString();
        if (!text.equals("")) {
            int amount = Integer.parseInt(text);
            if (bankSwitch.isChecked()) {
                amount = Math.min(amount, playerControls.player.getBankGold());
                playerControls.player.setGold(playerControls.player.getGold() + amount);
                playerControls.player.setBankGold(playerControls.player.getBankGold() - amount);
            }
            else {
                amount = Math.min(amount, playerControls.player.getGold());
                playerControls.player.setGold(playerControls.player.getGold() - amount);
                playerControls.player.setBankGold(playerControls.player.getBankGold() + amount);
            }
            playerControls.player.updatePlayer(playerControls.player);
        }
        playerControls.headerControls.setUpViews();
        setUpBankDisplay(null);
        submissionAmount.setText("");
    }
    public void setUpBankDisplay(View view) {
        TextView bankTitleTextView = findViewById(R.id.BankTitle);
        TextView bankHeaderTextView = findViewById(R.id.BankHeader);
        TextView bankSwitchTextTextView = findViewById(R.id.BankSwitchText);
        bankSwitchTextTextView.setText("Deposit");
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch bankSwitch = findViewById(R.id.BankSwitch);
        if(bankSwitch.isChecked()) {
            bankSwitchTextTextView.setText("Withdraw");
        }
        String text = "Bank: " + playerControls.player.getBankGold();
        bankTitleTextView.setText(text);
        text = "Inventory: " + playerControls.player.getGold();
        bankHeaderTextView.setText(text);
    }
    public void whichPopUpToShow(int id) {
        ConstraintLayout PopUpMenu = findViewById(R.id.PopUpMenu);
        PopUpMenu.setVisibility(View.VISIBLE);
        ConstraintLayout PopUp;
        menuOpen = true;
        switch (id) {
            case 1:
                PopUp = findViewById(R.id.QuestPopUp);
                PopUp.setVisibility(View.VISIBLE);
                LinearLayout PopUpLinearLayout = findViewById(R.id.PopUpLinearLayout);
                PopUpLinearLayout.removeAllViews();
                break;
            case 2:
                PopUp = findViewById(R.id.BankPopUp);
                PopUp.setVisibility(View.VISIBLE);
                setUpBankDisplay(null);
                break;
        }
    }
    public void showQuestButton(View view) {
        boolean completed = false;
        if(view.getId() ==  R.id.QuestButtonCompleted) {
            completed = true;
        }
        playerControls.questControls.addQuestsSlot(completed);
    }
    public void ClosePopUpMenu(View view) {
        menuOpen = false;
        int[] layoutIds = {R.id.PopUpMenu, R.id.QuestPopUp, R.id.BankPopUp};
        for (int layoutId : layoutIds) {
            ConstraintLayout layout = findViewById(layoutId);
            layout.setVisibility(View.GONE);
        }
    }
}