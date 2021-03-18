package jp.co.futureantiques.trainingrecord.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import jp.co.futureantiques.trainingrecord.Fragment.TrainingRecordFragment;
import jp.co.futureantiques.trainingrecord.Fragment.WeightRecordFragment;
import jp.co.futureantiques.trainingrecord.Fragment.MainFragmentStateAdapter;
import jp.co.futureantiques.trainingrecord.R;

public class MainActivity extends AbsMainActivity {
    private final String LOG_TAG = "MainActivity";

    private ViewPager2 mPager;
    private MainFragmentStateAdapter mMainFragmentStateAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.app_actionbar);

        aBar();

        Menu menu = mNavigationView.getMenu();
        menu.findItem(R.id.logout_icon).setVisible(false);
        menu.findItem(R.id.login_icon).setVisible(false);

        //ViewPager2
        mPager = findViewById(R.id.viewPager2);

        //TabLayout
        mTabLayout = findViewById(R.id.tabs);

        //Fragment
        mFragmentList.add(new TrainingRecordFragment());
        mFragmentList.add(new WeightRecordFragment());

        //TabLayoutとViewPager2を連動させる
        tabViewAdapter();

        //MainFragmentStateAdapterをnewする
        mMainFragmentStateAdapter = new MainFragmentStateAdapter(this, mFragmentList);
        mPager.setAdapter(mMainFragmentStateAdapter);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_icon:
                        Log.i(LOG_TAG, "home_iconが選択されました。");
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.training_register:
                        Log.i(LOG_TAG, "トレーニング追加ボタンが選択されました。");
                        Intent intentT = new Intent(MainActivity.this, TrainingRegisterActivity.class);
                        startActivity(intentT);
                        break;
                    case R.id.weight_register:
                        Log.i(LOG_TAG, "体重追加ボタンが選択されました。");
                        Intent intentW = new Intent(MainActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        return true;
                    case R.id.menu_register:
                        Log.i(LOG_TAG, "メニュー追加ボタンが選択されました。");
                        Intent intentM = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(intentM);
                        return true;
                }
                return true;
            }
        });
    }


    private void tabViewAdapter() {
        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                mTabLayout.setScrollPosition(position, positionOffset, true);
            }
        });

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // タブを選択
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}