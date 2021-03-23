package jp.co.futureantiques.trainingrecord.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;
import jp.co.futureantiques.trainingrecord.TrainingData;

public class MenuDetailActivity extends AbsMainActivity {
    private final String LOG_TAG = "MenuDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_menu_detail);

        mDBManager = new DBManager(MenuDetailActivity.this);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.app_actionbar);

        //intentを取得し、更新データのkeyに設定する
        Intent intent = this.getIntent();
        mId = intent.getStringExtra("key");
        join_key = intent.getStringExtra(JOIN_KEY);
        muscle_name = intent.getStringExtra(MUSCLE_KEY);
        mYear = intent.getStringExtra(YEAR_KEY);
        mMonth = intent.getStringExtra(MONTH_KEY);
        mDay = intent.getStringExtra(DAY_KEY);

        aBar();

        TextView trainingName = findViewById(R.id.training_name);
        TrainingData trainingData = mDBManager.selectMenuEdit(mId);
        selectTraining = trainingData.getMenu();
        trainingName.setText(selectTraining);

        heavyInput = findViewById(R.id.training_weight);
        firstInput = findViewById(R.id.first_set_edit);
        secondInput = findViewById(R.id.second_set_edit);
        thirdInput = findViewById(R.id.third_set_edit);
        fourthInput = findViewById(R.id.fourth_set_edit);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyId = Integer.parseInt(mId);
                heavy = heavyInput.getText().toString();
                first = firstInput.getText().toString();
                second = secondInput.getText().toString();
                third = thirdInput.getText().toString();
                fourth = fourthInput.getText().toString();

                mDBManager.insertTodayTraining(join_key,selectTraining,heavy,first,second,third,fourth);
                Intent intent = new Intent(MenuDetailActivity.this, TodayTrainingMenuRegisterActivity.class);
                intent.putExtra(JOIN_KEY,join_key);
                intent.putExtra(YEAR_KEY,mYear);
                intent.putExtra(MONTH_KEY,mMonth);
                intent.putExtra(DAY_KEY,mDay);
                startActivity(intent);
            }
        });

        //登録キャンセル機能(Home画面への遷移機能)
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "home_iconが選択されました。");
                Intent intent = new Intent(MenuDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_icon:
                        Log.i(LOG_TAG, "home_iconが選択されました。");
                        Intent intent = new Intent(MenuDetailActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.training_register:
                        Log.i(LOG_TAG, "トレーニング追加ボタンが選択されました。");
                        Intent intentT = new Intent(MenuDetailActivity.this, TodayTrainingMenuRegisterActivity.class);
                        startActivity(intentT);
                        break;
                    case R.id.weight_register:
                        Log.i(LOG_TAG, "体重追加ボタンが選択されました。");
                        Intent intentW = new Intent(MenuDetailActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        return true;
                    case R.id.menu_register:
                        Log.i(LOG_TAG, "メニュー追加ボタンが選択されました。");
                        Intent intentM = new Intent(MenuDetailActivity.this, AllMenuActivity.class);
                        startActivity(intentM);
                        return true;
                }
                return true;
            }
        });

    }
}