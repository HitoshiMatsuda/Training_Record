package jp.co.futureantiques.trainingrecord.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;

public class SelectMenuActivity extends AbsMainActivity {
    private final String LOG_TAG = "SelectMenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_select_menu);
        listView = findViewById(R.id.training_menu_select_list);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.app_actionbar);

        Intent intent = getIntent();
        join_key = intent.getStringExtra(JOIN_KEY);
        muscle_name = intent.getStringExtra(MUSCLE_KEY);
        mYear = intent.getStringExtra(YEAR_KEY);
        mMonth = intent.getStringExtra(MONTH_KEY);
        mDay = intent.getStringExtra(DAY_KEY);

        aBar();

        //ListViewへ表示
        mDBManager = new DBManager(SelectMenuActivity.this);
        cursor = mDBManager.selectTrainingMenu(muscle_name);
        cursor.moveToFirst();

        SimpleCursorAdapter sca = new SimpleCursorAdapter(
                this,
                R.layout.simple_muscle_list_layout,
                cursor,
                new String[]{"training_name"},
                new int[]{R.id.name_text},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(sca);

        //トレーニング内容を入力へ
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SelectMenuActivity.this,MenuDetailActivity.class);
                //値を引き渡す
                intent.putExtra("key", String.valueOf(id));
                intent.putExtra(JOIN_KEY,join_key);
                intent.putExtra(MUSCLE_KEY,muscle_name);
                intent.putExtra(YEAR_KEY,mYear);
                intent.putExtra(MONTH_KEY,mMonth);
                intent.putExtra(DAY_KEY,mDay);
                //Activityの起動
                startActivity(intent);
            }
        });

        //メニュー選択時の画面遷移機能
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_icon:
                        Log.i(LOG_TAG, "home_iconが選択されました。");
                        Intent intent = new Intent(SelectMenuActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.training_register:
                        Log.i(LOG_TAG, "トレーニング追加ボタンが選択されました。");
                        Intent intentT = new Intent(SelectMenuActivity.this, TodayTrainingMenuRegisterActivity.class);
                        startActivity(intentT);
                        break;
                    case R.id.weight_register:
                        Log.i(LOG_TAG, "体重追加ボタンが選択されました。");
                        Intent intentW = new Intent(SelectMenuActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        return true;
                    case R.id.menu_register:
                        Log.i(LOG_TAG, "メニュー追加ボタンが選択されました。");
                        Intent intentM = new Intent(SelectMenuActivity.this, AllMenuActivity.class);
                        startActivity(intentM);
                        return true;
                }
                return true;
            }
        });
    }
}