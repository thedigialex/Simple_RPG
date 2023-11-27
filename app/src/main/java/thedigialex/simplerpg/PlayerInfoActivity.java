package thedigialex.simplerpg;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PlayerInfoActivity extends AppCompatActivity {
    PlayerControls playerControls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);
        Intent intent = getIntent();
        int playerId = intent.getIntExtra("playerId", -1);
        playerControls = new PlayerControls(playerId, findViewById(R.id.header), findViewById(R.id.footer),this, getApplicationContext(), "PlayerInfo");
        SetUpActivityData();
    }
    public void SetUpActivityData() {
        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);
        PlayerPagerAdapter playerPagerAdapter = new PlayerPagerAdapter(getSupportFragmentManager(), getLifecycle(), playerControls);
        viewPager2.setAdapter(playerPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        String[] tabNames = {"Stats", "Equipment", "Inventory", "Skills"};
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(tabNames[position])).attach();
    }
}
