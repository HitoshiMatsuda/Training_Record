package jp.co.futureantiques.trainingrecord.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.Fragment.DatePick;
import jp.co.futureantiques.trainingrecord.R;

public class TrainingRegisterActivity extends AbsMainActivity {
    private final String LOG_TAG = "WeightRegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_register);
    }


    public void showDatePickerDialog(View v) {
        DialogFragment dialogFragment = new DatePick();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearText.setText(String.valueOf(year));
        monthText.setText(String.valueOf(month + 1));
        dayText.setText(String.valueOf(dayOfMonth));
    }


    @Override
    public void onResume() {
        super.onResume();

        //layout紐付け
        setContentView(R.layout.activity_training_register);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.app_actionbar);
        spinner = findViewById(R.id.training_spi);

        aBar();

        //筋群選択機能
        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this,R.array.training_menus,android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner = (Spinner)parent;
                train_menu = (String) spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Itemが選択されなかった場合
                train_menu = "記録無し";
            }
        });


        //TextViewを紐づける
        yearText = findViewById(R.id.test_text_year);
        monthText = findViewById(R.id.test_text_month);
        dayText = findViewById(R.id.test_text_day);


        //登録機能
        Button registerButton = findViewById(R.id.register_button);
        DBManager mDBManager = new DBManager(TrainingRegisterActivity.this);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("InsertButton", "登録ボタンが押されました");
                //TextViewに表示されている値を取得
                mYear = yearText.getText().toString();
                mMonth = monthText.getText().toString();
                mDay = dayText.getText().toString();

                switch (train_menu) {
                    case "大胸筋":
                        train_menu = "CHEST DAY";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                    case "背中":
                        train_menu = "BACK DAY";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                    case "肩・腕":
                        train_menu = "SHOULDER DAY";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                    case "下半身":
                        train_menu = "LOWER DAY";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                    case "有酸素":
                        train_menu = "RUN DAY";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                    case "オフ":
                        train_menu = "OFF DAY";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                    default:
                        train_menu = "記録無し";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                }
            }
        });

        //登録キャンセル機能
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "home_iconが選択されました。");
                Intent intent = new Intent(TrainingRegisterActivity.this, MainActivity.class);
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
                        Intent intent = new Intent(TrainingRegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.training_register:
                        Log.i(LOG_TAG, "トレーニング追加ボタンが選択されました。");
                        Intent intentT = new Intent(TrainingRegisterActivity.this, TrainingRegisterActivity.class);
                        startActivity(intentT);
                        break;
                    case R.id.weight_register:
                        Log.i(LOG_TAG, "体重追加ボタンが選択されました。");
                        Intent intentW = new Intent(TrainingRegisterActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        return true;
                    case R.id.menu_register:
                        Log.i(LOG_TAG, "メニュー追加ボタンが選択されました。");
                        Intent intentM = new Intent(TrainingRegisterActivity.this, MenuActivity.class);
                        startActivity(intentM);
                        return true;
                }
                return true;
            }
        });
    }
}