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

public class PlayerFragmentItem extends Fragment {
    TextView[] topTextViews;
    ImageView[] itemImageViews;
    TextView[] bottomTextViews;
    PlayerControls playerControls;
    ConstraintLayout ItemDetailsLayout;
    boolean itemDetailOpen = false;
    boolean sortDirection = false;
    List<Item> items;
    public PlayerFragmentItem(PlayerControls playerControls) {
        this.playerControls = playerControls;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_equip_item, container, false);
        LinearLayout mainLayout = rootView.findViewById(R.id.mainLayout);
        int size = playerControls.player.getInventorySize();
        topTextViews = new TextView[size];
        bottomTextViews = new TextView[size];
        itemImageViews = new ImageView[size];
        setUpButtons(rootView);
        items = playerControls.inventoryControls.items;
        regenerateInventoryView(mainLayout, inflater, "Equipped");
        return rootView;
    }
    private void regenerateInventoryView(LinearLayout mainLayout, LayoutInflater inflater, String sortBy) {
        mainLayout.removeAllViews();
        sortDirection = !sortDirection;
        for (int i = 0; i < items.size(); i++) {
            View slotLayout = inflater.inflate(R.layout.inventory_slot, mainLayout, false);
            topTextViews[i] = slotLayout.findViewById(R.id.topTextView);
            itemImageViews[i] = slotLayout.findViewById(R.id.itemImageView);
            if (items.get(i).getIsEquipped()) {
                itemImageViews[i].setBackground(new ColorDrawable(getResources().getColor(R.color.black)));
            }
            int finalI = i;
            itemImageViews[i].setOnClickListener(v -> showItemDetailView(items.get(finalI)));
            bottomTextViews[i] = slotLayout.findViewById(R.id.bottomTextView);
            topTextViews[i].setText(items.get(i).getItemName());
            String text = "Amount: " + items.get(i).getItemValue();
            bottomTextViews[i].setText(text);
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
       LinearLayout sortButtonContainer = rootView.findViewById(R.id.SortButtonContainer);
       Button EquipSortButton = new Button(playerControls.context);
       EquipSortButton.setOnClickListener(v -> regenerateInventoryView(requireView().findViewById(R.id.mainLayout), LayoutInflater.from(requireContext()), "Equipped"));
       sortButtonContainer.addView(EquipSortButton);
       Button PriceSortButton = new Button(playerControls.context);
       PriceSortButton.setOnClickListener(v -> regenerateInventoryView(requireView().findViewById(R.id.mainLayout), LayoutInflater.from(requireContext()), "Price"));
       sortButtonContainer.addView(PriceSortButton);
       Button RaritySortButton = new Button(playerControls.context);
       RaritySortButton.setOnClickListener(v -> regenerateInventoryView(requireView().findViewById(R.id.mainLayout), LayoutInflater.from(requireContext()), "Rarity"));
       sortButtonContainer.addView(RaritySortButton);
    }
    public void showItemDetailView(Item item) {
        if (!itemDetailOpen) {
            ItemDetailsLayout.setVisibility(View.VISIBLE);
            TextView ItemNameView = ItemDetailsLayout.findViewById(R.id.ItemNameView);
            ItemNameView.setText(item.getItemName());
            Button trash = ItemDetailsLayout.findViewById(R.id.TrashButton);
            trash.setOnClickListener(v -> removeItemFromDetailView(item));
            Button equip = ItemDetailsLayout.findViewById(R.id.EquipButton);
            equip.setVisibility(View.INVISIBLE);
            if(item.getIsEquitable()) {
                equip.setVisibility(View.VISIBLE);
                equip.setOnClickListener(v -> equipItemFromDetailView(item));
                equip.setText(item.getIsEquipped() ? "Un-Equip" : "Equip");
            }
            itemDetailOpen = true;
        }
    }
    public void removeItemFromDetailView(Item item) {
       if(!item.getIsEquipped()) {
           ItemDetailsLayout.setVisibility(View.GONE);
           itemDetailOpen = false;
           playerControls.inventoryControls.removeItem(item);
           regenerateInventoryView(requireView().findViewById(R.id.mainLayout), LayoutInflater.from(requireContext()), "Equipped");
       }
       else {
           playerControls.DisplayMessage("Equipped item cannot be removed from inventory.");
       }
    }
    public void equipItemFromDetailView(Item item) {
        boolean isEquipped = item.getIsEquipped();
        if(isEquipped) {
            item.setIsEquipped(false);
        }
        else {
            String type = item.getItemType();
            for (Item checkItem : items) {
                if (checkItem.getItemType().equals(type) && checkItem.getIsEquipped()) {
                    checkItem.setIsEquipped(false);
                }
            }
            item.setIsEquipped(true);
        }
        playerControls.inventoryControls.updateItem(item);
        ItemDetailsLayout.setVisibility(View.GONE);
        itemDetailOpen = false;
        sortDirection = false;
        regenerateInventoryView(requireView().findViewById(R.id.mainLayout), LayoutInflater.from(requireContext()),"Equipped");
   }
}