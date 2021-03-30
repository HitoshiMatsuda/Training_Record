package jp.co.futureantiques.trainingrecord.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private ViewPager2 mPager;
    private MainFragmentStateAdapter mMainFragmentStateAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Log.i("info", "onCreate");
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onResume() {
        setContentView(R.layout.activity_main);
        super.onResume();

        aBar();

        mPager = findViewById(R.id.viewPager2);
        mTabLayout = findViewById(R.id.tabs);

        mFragmentList.add(new TrainingRecordFragment());
        mFragmentList.add(new WeightRecordFragment());

        tabViewAdapter();

        //MainFragmentStateAdapterをnewする
        mMainFragmentStateAdapter = new MainFragmentStateAdapter(this, mFragmentList);
        mPager.setAdapter(mMainFragmentStateAdapter);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_icon:
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        Log.i("move","MainActivityに遷移");
                        startActivity(intent);
                        break;
                    case R.id.training_register:
                        Intent intentT = new Intent(MainActivity.this, TrainingRegisterActivity.class);
                        Log.i("move","TrainingRegisterActivityに遷移");
                        startActivity(intentT);
                        break;
                    case R.id.weight_register:
                        Intent intentW = new Intent(MainActivity.this, WeightRegisterActivity.class);
                        Log.i("move","WeightRegisterActivityに遷移");
                        startActivity(intentW);
                        return true;
                    case R.id.menu_register:
                        Intent intentM = new Intent(MainActivity.this, TrainingNameRegisterActivity.class);
                        Log.i("move","TrainingNameRegisterActivityに遷移");
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