package jp.co.futureantiques.trainingrecord.Activity;

import androidx.annotation.NonNull;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;

public class SelectTrainingMenuActivity extends AbsMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_training_menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDBManager = new DBManager(SelectTrainingMenuActivity.this);
        Intent intent = getIntent();
        mId = intent.getStringExtra("mId");
        String editKey = intent.getStringExtra("editKey");
        join_key = intent.getStringExtra(JOIN_KEY);
        muscle_name = intent.getStringExtra(MUSCLE_KEY);
        mYear = intent.getStringExtra(YEAR_KEY);
        mMonth = intent.getStringExtra(MONTH_KEY);
        mDay = intent.getStringExtra(DAY_KEY);
        trainingName.setText(muscle_name);


        aBar();

        //ListViewへ表示
        cursor = mDBManager.selectTrainingMenu(muscle_name);
        cursor.moveToFirst();

        SimpleCursorAdapter sca = new SimpleCursorAdapter(
                this,
                R.layout.list_layout_register_training_select,
                cursor,
                new String[]{"training_name"},
                new int[]{R.id.name_text},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(sca);


        //ListViewのSwipe機能
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("info", "リフレッシュされました");

                cursor = mDBManager.selectTrainingMenu(muscle_name);
                cursor.moveToFirst();

                SimpleCursorAdapter sca = new SimpleCursorAdapter(
                        SelectTrainingMenuActivity.this,
                        R.layout.list_layout_register_training_select,
                        cursor,
                        new String[]{"training_name"},
                        new int[]{R.id.name_text},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                listView.setAdapter(sca);

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        //トレーニング内容を入力へ
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SelectTrainingMenuActivity.this, TrainingDetailRegisterActivity.class);
                //値を引き渡す
                intent.putExtra("key", String.valueOf(id));
                intent.putExtra("mId",mId);
                intent.putExtra("editKey",editKey);
                intent.putExtra(JOIN_KEY, join_key);
                intent.putExtra(MUSCLE_KEY, muscle_name);
                intent.putExtra(YEAR_KEY, mYear);
                intent.putExtra(MONTH_KEY, mMonth);
                intent.putExtra(DAY_KEY, mDay);
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
                        Log.i("info", "home_iconが選択されました。");
                        Intent intent = new Intent(SelectTrainingMenuActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.training_register:
                        Log.i("info", "トレーニング追加ボタンが選択されました。");
                        Intent intentT = new Intent(SelectTrainingMenuActivity.this, TrainingRegisterActivity.class);
                        startActivity(intentT);
                        break;
                    case R.id.weight_register:
                        Log.i("info", "体重追加ボタンが選択されました。");
                        Intent intentW = new Intent(SelectTrainingMenuActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        return true;
                    case R.id.menu_register:
                        Log.i("info", "メニュー追加ボタンが選択されました。");
                        Intent intentM = new Intent(SelectTrainingMenuActivity.this, TrainingNameRegisterActivity.class);
                        startActivity(intentM);
                        return true;
                }
                return true;
            }
        });
    }
}