package thedigialex.simplerpg;

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

public class PlayerFragmentEquip extends Fragment {
    TextView[] leftTextViews = new TextView[3];
    ImageView[] leftImageViews = new ImageView[3];
    TextView[] middleTextViews = new TextView[3];
    ImageView[] middleImageViews = new ImageView[3];
    TextView[] rightTextViews = new TextView[3];
    ImageView[] rightImageViews = new ImageView[3];
    PlayerControls playerControls;
    ConstraintLayout ItemDetailsLayout;
    LinearLayout mainLayout;
    boolean itemDetailOpen = false;
    View slotLayout;
    public PlayerFragmentEquip(PlayerControls playerControls) {
        this.playerControls = playerControls;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_equip_item, container, false);
        mainLayout = rootView.findViewById(R.id.mainLayout);
        rootView.findViewById(R.id.SortButtonContainer).setVisibility(View.GONE);
        setUpButtons(rootView);
        slotLayout = inflater.inflate(R.layout.equipment_inventory_slot, mainLayout, false);
        updateEquippedItemsView();
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        updateEquippedItemsView();
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
    public void showItemDetailView(Item item) {
        if(!itemDetailOpen) {
            ItemDetailsLayout.setVisibility(View.VISIBLE);
            TextView ItemNameView = ItemDetailsLayout.findViewById(R.id.ItemNameView);
            ItemNameView.setText(item.getItemName());
            itemDetailOpen = true;
        }
    }
    public void updateEquippedItemsView() {
        mainLayout.removeAllViews();
        List<Item> equippedItems = playerControls.inventoryControls.getEquippedItems();
        for (int i = 0; i < 3; i++) {
            View slotLayout = LayoutInflater.from(requireContext()).inflate(R.layout.equipment_inventory_slot, mainLayout, false);
            leftTextViews[i] = slotLayout.findViewById(R.id.leftTextView);
            leftImageViews[i] = slotLayout.findViewById(R.id.leftmageView);
            middleTextViews[i] = slotLayout.findViewById(R.id.middleTextView);
            middleImageViews[i] = slotLayout.findViewById(R.id.middleImageView);
            rightTextViews[i] = slotLayout.findViewById(R.id.rightTextView);
            rightImageViews[i] = slotLayout.findViewById(R.id.rightImageView);
            String[] equipNames;
            switch (i) {
                case 0:
                    equipNames = new String[]{"Necklace", "Helmet", "Pet"};
                    for (Item equippedItem : equippedItems) {
                        if (equippedItem.getItemType().equals(equipNames[0])) {
                            equipNames[0] += "\n" + equippedItem.getItemName();
                        }
                        if (equippedItem.getItemType().equals(equipNames[1])) {
                            equipNames[1] += "\n" + equippedItem.getItemName();
                        }
                        if (equippedItem.getItemType().equals(equipNames[2])) {
                            equipNames[2] += "\n" + equippedItem.getItemName();
                        }
                    }
                    leftTextViews[i].setText(equipNames[0]);
                    middleTextViews[i].setText(equipNames[1]);
                    rightTextViews[i].setText(equipNames[2]);
                    break;
                case 1:
                    equipNames = new String[]{"Gloves","Armour","Ring"};
                    for (Item equippedItem : equippedItems) {
                        if (equippedItem.getItemType().equals(equipNames[0])) {
                            equipNames[0] += "\n" + equippedItem.getItemName();
                        }
                        if (equippedItem.getItemType().equals(equipNames[1])) {
                            equipNames[1] += "\n" + equippedItem.getItemName();
                        }
                        if (equippedItem.getItemType().equals(equipNames[2])) {
                            equipNames[2] += "\n" + equippedItem.getItemName();
                        }
                    }
                    leftTextViews[i].setText(equipNames[0]);
                    middleTextViews[i].setText(equipNames[1]);
                    rightTextViews[i].setText(equipNames[2]);
                    break;
                case 2:
                    equipNames = new String[]{"Shield","Boots","Weapon"};
                    for (Item equippedItem : equippedItems) {
                        if (equippedItem.getItemType().equals(equipNames[0])) {
                            equipNames[0] += "\n" + equippedItem.getItemName();
                        }
                        if (equippedItem.getItemType().equals(equipNames[1])) {
                            equipNames[1] += "\n" + equippedItem.getItemName();
                        }
                        if (equippedItem.getItemType().equals(equipNames[2])) {
                            equipNames[2] += "\n" + equippedItem.getItemName();
                            rightImageViews[i].setOnClickListener(v -> showItemDetailView(equippedItem));
                        }
                    }
                    leftTextViews[i].setText(equipNames[0]);
                    middleTextViews[i].setText(equipNames[1]);
                    rightTextViews[i].setText(equipNames[2]);
                    break;
            }
            mainLayout.addView(slotLayout);
        }
    }
}