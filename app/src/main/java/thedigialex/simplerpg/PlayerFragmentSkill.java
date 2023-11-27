package thedigialex.simplerpg;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.List;

public class PlayerFragmentSkill extends Fragment {
    TextView[] topTextViews = new TextView[3];
    ImageView[] itemImageViews = new ImageView[3];
    TextView[] bottomTextViews = new TextView[3];
    PlayerControls playerControls;
    ConstraintLayout ItemDetailsLayout;
    boolean itemDetailOpen = false;
    boolean sortDirection = false;
    List<Skill> skills;

    public PlayerFragmentSkill(PlayerControls playerControls) {
        this.playerControls = playerControls;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_equip_item, container, false);
        LinearLayout mainLayout = rootView.findViewById(R.id.mainLayout);
        setUpButtons(rootView);
        skills = playerControls.inventoryControls.skills;
        regenerateInventoryView(mainLayout, inflater, "Equipped");
        return rootView;
    }
    private void regenerateInventoryView(LinearLayout mainLayout, LayoutInflater inflater, String sortBy) {
        mainLayout.removeAllViews();
        sortDirection = !sortDirection;
        for (int i = 0; i < skills.size(); i++) {
            View slotLayout = inflater.inflate(R.layout.inventory_slot, mainLayout, false);
            topTextViews[i] = slotLayout.findViewById(R.id.topTextView);
            itemImageViews[i] = slotLayout.findViewById(R.id.itemImageView);
            if (skills.get(i).getIsEquipped()) {
                itemImageViews[i].setBackground(new ColorDrawable(getResources().getColor(R.color.black)));
            }
            int finalI = i;
            itemImageViews[i].setOnClickListener(v -> showItemDetailView(skills.get(finalI)));
            bottomTextViews[i] = slotLayout.findViewById(R.id.bottomTextView);
            topTextViews[i].setText(skills.get(i).getSkillName());
            mainLayout.addView(slotLayout);
        }
    }
    public void setUpButtons(View rootView) {
        ItemDetailsLayout = rootView.findViewById(R.id.ItemDetailsLayout);
        Button close = ItemDetailsLayout.findViewById(R.id.ButtonClose);
        close.setOnClickListener(v -> {
            ItemDetailsLayout.setVisibility(View.GONE);
            itemDetailOpen = false;
        });
        ItemDetailsLayout.findViewById(R.id.TrashButton).setVisibility(View.INVISIBLE);
    }
    public void showItemDetailView(Skill skill) {
        if (!itemDetailOpen) {
            ItemDetailsLayout.setVisibility(View.VISIBLE);
            TextView ItemNameView = ItemDetailsLayout.findViewById(R.id.ItemNameView);
            ItemNameView.setText(skill.getSkillName());
            Button equip = ItemDetailsLayout.findViewById(R.id.EquipButton);
            equip.setVisibility(View.VISIBLE);
            equip.setOnClickListener(v -> equipItemFromDetailView(skill));
            equip.setText(skill.getIsEquipped() ? "Un-Equip" : "Equip");
            itemDetailOpen = true;
        }
    }
    public void equipItemFromDetailView(Skill skill) {
        boolean isEquipped = skill.getIsEquipped();
        if(isEquipped) {
            skill.setIsEquipped(false);
        }
        else {
            //need to fix this for setting only 4 skills to equipped
            skill.setIsEquipped(true);
        }
        playerControls.inventoryControls.updateSkill(skill);
        ItemDetailsLayout.setVisibility(View.GONE);
        itemDetailOpen = false;
        sortDirection = false;
        regenerateInventoryView(requireView().findViewById(R.id.mainLayout), LayoutInflater.from(requireContext()),"Equipped");
    }
}