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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.Fragment.DatePickUpdate;
import jp.co.futureantiques.trainingrecord.R;
import jp.co.futureantiques.trainingrecord.TrainingData;

public class EditActivity extends AbsMainActivity {
    private final String LOG_TAG = "EditActivity";

    //更新前
    protected TextView oldYearText;
    protected TextView oldMonthText;
    protected TextView oldDayText;
    protected String mId;
    protected String oldYear;
    protected String oldMonth;
    protected String oldDay;
    protected String oldMenu;
    //更新後
    protected TextView newYearText;
    protected TextView newMonthText;
    protected TextView newDayText;
    protected String newYear;
    protected String newMonth;
    protected String newDay;
    protected String newMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    public void showDatePickerDialog(View v) {
        Log.i("EditActivity","新しい日付選択ボタンを押しました。");
        DialogFragment dialogFragment = new DatePickUpdate();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //TextViewへ表示する(更新後データ)
        newYearText.setText(String.valueOf(year));
        newMonthText.setText(String.valueOf(month + 1));
        newDayText.setText(String.valueOf(dayOfMonth));
    }

    @Override
    public void onResume() {
        super.onResume();
        DBManager mDBManager = new DBManager(EditActivity.this);
        Intent intent = this.getIntent();

        setContentView(R.layout.activity_edit);

        //intentを取得し、更新データのkeyに設定する
        mId = intent.getStringExtra("key");

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.app_actionbar);
        spinner = findViewById(R.id.training_spi);

        aBar();

        TrainingData trainingData = mDBManager.selectEdit(mId);

        //TextViewを紐づける(更新前)
        oldYearText = findViewById(R.id.old_year);
        oldMonthText = findViewById(R.id.old_month);
        oldDayText = findViewById(R.id.old_day);

        //TextViewを紐づける(更新後)
        newYearText = findViewById(R.id.new_year);
        newMonthText = findViewById(R.id.new_month);
        newDayText = findViewById(R.id.new_day);

        //TrainingDataから更新前のデータを取得
        oldYear = trainingData.getYear();
        oldMonth = trainingData.getMonth();
        oldDay = trainingData.getDay();
        oldMenu = trainingData.getMenu();

        //TextViewへ表示する(更新元データ)
        oldYearText.setText(oldYear);
        oldMonthText.setText(oldMonth);
        oldDayText.setText(oldDay);

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

        //キャンセルボタンを紐づける
        Button cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "home_iconが選択されました。");
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //登録ボタンを紐づける
        Button update = findViewById(R.id.register_button);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("InsertButton", "登録ボタンが押されました");
                //TextViewに表示されている値を取得
                newYear = newYearText.getText().toString();
                newMonth = newMonthText.getText().toString();
                newDay = newDayText.getText().toString();
                switch (train_menu) {
                    case "大胸筋":
                        newMenu = "CHEST DAY";
                        mDBManager.trainUpDate(mId, train_menu, newYear, newMonth, newDay);
                        break;
                    case "背中":
                        newMenu = "BACK DAY";
                        mDBManager.trainUpDate(mId, train_menu, newYear, newMonth, newDay);
                        break;
                    case "肩・腕":
                        newMenu = "SHOULDER DAY";
                        mDBManager.trainUpDate(mId, train_menu, newYear, newMonth, newDay);
                        break;
                    case "下半身":
                        newMenu = "LOWER DAY";
                        mDBManager.trainUpDate(mId, train_menu, newYear, newMonth, newDay);
                        break;
                    case "有酸素":
                        newMenu = "RUN DAY";
                        mDBManager.trainUpDate(mId, train_menu, newYear, newMonth, newDay);
                        break;
                    case "オフ":
                        newMenu = "OFF DAY";
                        mDBManager.trainUpDate(mId, train_menu, newYear, newMonth, newDay);
                        break;
                    default:
                        newMenu = "記録無し";
                        mDBManager.trainUpDate(mId, train_menu, newYear, newMonth, newDay);
                        break;
                }
            }
        });

        //メニュー選択時の画面遷移機能
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_icon:
                        Log.i(LOG_TAG, "home_iconが選択されました。");
                        Intent intent = new Intent(EditActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.training_register:
                        Log.i(LOG_TAG, "トレーニング追加ボタンが選択されました。");
                        Intent intentT = new Intent(EditActivity.this, TrainingRegisterActivity.class);
                        startActivity(intentT);
                        break;
                    case R.id.weight_register:
                        Log.i(LOG_TAG, "体重追加ボタンが選択されました。");
                        Intent intentW = new Intent(EditActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        return true;
                    case R.id.menu_register:
                        Log.i(LOG_TAG, "メニュー追加ボタンが選択されました。");
                        Intent intentM = new Intent(EditActivity.this, MenuActivity.class);
                        startActivity(intentM);
                        return true;
                }
                return true;
            }
        });
    }
}