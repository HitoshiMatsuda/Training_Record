package jp.co.futureantiques.trainingrecord.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;
import jp.co.futureantiques.trainingrecord.TrainingData;

public class MenuEditActivity extends AbsMainActivity {
    private final String LOG_TAG = "MenuEditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_edit);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_menu_edit);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.app_actionbar);


        aBar();

        Menu menu = mNavigationView.getMenu();
        menu.findItem(R.id.logout_icon).setVisible(false);
        menu.findItem(R.id.login_icon).setVisible(false);

        Intent intent = this.getIntent();
        mDBManager = new DBManager(MenuEditActivity.this);
        cursor = mDBManager.selectMuscle();
        cursor.moveToFirst();

        //intentを取得し、更新データのkeyに設定する
        mId = intent.getStringExtra("key");


        //更新前のトレーニングメニュー
        TrainingData trainingData = mDBManager.selectMenuEdit(mId);
        oldTraining = trainingData.getMenu();
        TextView oldTrainingText = findViewById(R.id.old_training_name);
        oldTrainingText.setText(oldTraining);


        //トレーニングメニュー更新処理
        Button updateB = findViewById(R.id.update_button);
        updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newTrainingText = findViewById(R.id.new_training_name);
                newTraining = newTrainingText.getText().toString();
                mDBManager.trainMenuUpDate(mId,newTraining);

                Intent intent = new Intent(MenuEditActivity.this, AllMenuActivity.class);
                startActivity(intent);
            }
        });


        //トレーニングメニュー削除処理
        Button deleteB = findViewById(R.id.delete_button);
        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBManager.trainMenuDelete(mId);

                Intent intent = new Intent(MenuEditActivity.this, AllMenuActivity.class);
                startActivity(intent);
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_icon:
                        Log.i(LOG_TAG, "home_iconが選択されました。");
                        Intent intent = new Intent(MenuEditActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.training_register:
                        Log.i(LOG_TAG, "トレーニングボタンが選択されました。");
                        Intent intentT = new Intent(MenuEditActivity.this, TodayTrainingMenuRegisterActivity.class);
                        startActivity(intentT);
                        break;
                    case R.id.weight_register:
                        Log.i(LOG_TAG, "体重ボタンが選択されました。");
                        Intent intentW = new Intent(MenuEditActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        return true;
                    case R.id.menu_register:
                        Log.i(LOG_TAG, "メニューボタンが選択されました。");
                        Intent intentM = new Intent(MenuEditActivity.this, AllMenuActivity.class);
                        startActivity(intentM);
                        return true;
                }
                return true;
            }
        });

    }
}