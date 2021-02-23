package jp.co.futureantiques.trainingrecord.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import jp.co.futureantiques.trainingrecord.Fragment.TrainingRecordFragment;
import jp.co.futureantiques.trainingrecord.Fragment.WeightRecordFragment;
import jp.co.futureantiques.trainingrecord.Fragment.MainFragmentStateAdapter;
import jp.co.futureantiques.trainingrecord.R;

public class MainActivity extends AppCompatActivity {
    //タグ
    private final String LOG_TAG = "MainActivity";

    private Toolbar mToolbar;
    private ViewPager2 mPager;
    private MainFragmentStateAdapter mMainFragmentStateAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //layout紐付け
        setContentView(R.layout.activity_main);

        //Toolbar
        mToolbar = findViewById(R.id.actionbar);
        setSupportActionBar(mToolbar);

        //ViewPager2
        mPager = findViewById(R.id.viewPager2);

        //TabLayout
        mTabLayout = findViewById(R.id.tabs);

        //Fragment
        mFragmentList.add(new TrainingRecordFragment());
        mFragmentList.add(new WeightRecordFragment());

        //TabLayoutとViewPager2を連動させる
        TabViewAdapter();

        //MainFragmentStateAdapterをnewする
        mMainFragmentStateAdapter = new MainFragmentStateAdapter(this, mFragmentList);
        mPager.setAdapter(mMainFragmentStateAdapter);

    }

    //TabLayoutとViewPagerを連動させるメソッド
    private void TabViewAdapter() {
        //ViewPagerのフリック処理
        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                // Tabのポジションを変更
                mTabLayout.setScrollPosition(position, positionOffset, true);
            }
        });

        //TabItem選択処理
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_icon:
                Log.i(LOG_TAG,"home_iconが選択されました。");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_register:
                Log.i(LOG_TAG,"メニュー追加ボタンが選択されました。");
                Intent intentM = new Intent(this, TrainingRegisterActivity.class);
                startActivity(intentM);
                return true;
            case R.id.weight_register:
                Log.i(LOG_TAG,"体重追加ボタンが選択されました。");
                Intent intentW = new Intent(this, WeightRegisterActivity.class);
                startActivity(intentW);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}