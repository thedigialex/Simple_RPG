package thedigialex.simplerpg;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PlayerPagerAdapter extends FragmentStateAdapter {

    PlayerControls playerControls;

    public PlayerPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, PlayerControls playerControls) {
        super(fragmentManager, lifecycle);
        this.playerControls = playerControls;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new PlayerFragmentEquip(playerControls);
            case 2:
                return new PlayerFragmentItem(playerControls);
            case 3:
                return new PlayerFragmentSkill(playerControls);
            default:
                return new PlayerFragmentStats(playerControls);
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Number of tabs
    }
}




