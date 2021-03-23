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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.DialogFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.Fragment.DatePick;
import jp.co.futureantiques.trainingrecord.R;

public class TodayTrainingMenuRegisterActivity extends AbsMainActivity {
    private final String LOG_TAG = "WeightRegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_training_menu_register);
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

        listView = findViewById(R.id.today_training_menu_list);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.app_actionbar);
        spinner = findViewById(R.id.training_spi);
        mDBManager = new DBManager(TodayTrainingMenuRegisterActivity.this);

        //TextViewを紐づける
        yearText = findViewById(R.id.test_text_year);
        monthText = findViewById(R.id.test_text_month);
        dayText = findViewById(R.id.test_text_day);

        Intent intent = getIntent();
        join_key = intent.getStringExtra(JOIN_KEY);
        muscle_name = intent.getStringExtra(MUSCLE_KEY);
        mYear = intent.getStringExtra(YEAR_KEY);
        mMonth = intent.getStringExtra(MONTH_KEY);
        mDay = intent.getStringExtra(DAY_KEY);
        if (join_key != null){
            String year = mYear;
            String month = mMonth;
            String dayOfMonth = mDay;

            int numYear = Integer.parseInt(year);
            int numMonth = Integer.parseInt(month);
            int numDay = Integer.parseInt(dayOfMonth);

            yearText.setText(String.valueOf(numYear));
            monthText.setText(String.valueOf(numMonth + 1));
            dayText.setText(String.valueOf(numDay));
        }else {

        }

        mYear = yearText.getText().toString();
        mMonth = monthText.getText().toString();
        mDay = dayText.getText().toString();

        aBar();

        //筋群選択機能(Spinners)
        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this, R.array.training_menus, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner = (Spinner) parent;
                muscle_name = (String) spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Itemが選択されなかった場合
                muscle_name = "筋群が選択されていません";
            }
        });


        //ListViewのSwipe機能
        mSwipeRefreshLayout = findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(LOG_TAG, "リフレッシュされました");

                mYear = yearText.getText().toString();
                mMonth = monthText.getText().toString();
                mDay = dayText.getText().toString();
                join_key = mYear + mMonth + mDay;
                cursor = mDBManager.selectTodayMenu(join_key);
                cursor.moveToFirst();
                SimpleCursorAdapter sca = new SimpleCursorAdapter(
                        TodayTrainingMenuRegisterActivity.this,
                        R.layout.detail_muscle_list_layout,
                        cursor,
                        new String[]{"training_name", "heavy", "first_set"
                                , "second_set", "third_set", "fourth_set"},
                        new int[]{R.id.training_menu_name, R.id.training_weight_text, R.id.first_set
                                , R.id.second_set, R.id.third_set, R.id.fourth_set},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                listView.setAdapter(sca);

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        Button selectButton = findViewById(R.id.menu_register_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYear = yearText.getText().toString();
                mMonth = monthText.getText().toString();
                mDay = dayText.getText().toString();
                join_key = mYear + mMonth + mDay;
                if (mYear != "") {
                    Log.i(LOG_TAG, "日付が選択されている");

                    if (muscle_name.equals("筋群が選択されていません")) {
                        Log.i(LOG_TAG, "筋群が選択されていない");
                        context = getApplicationContext();
                        CharSequence text = "筋群が選択されていません。";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    } else {
                        Log.i(LOG_TAG, "日付と筋群が選択されている");
                        Intent intent = new Intent(TodayTrainingMenuRegisterActivity.this, SelectMenuActivity.class);
                        //日付をkeyにして送る
                        intent.putExtra(JOIN_KEY, join_key);
                        intent.putExtra(MUSCLE_KEY, muscle_name);
                        intent.putExtra(YEAR_KEY,mYear);
                        intent.putExtra(MONTH_KEY,mMonth);
                        intent.putExtra(DAY_KEY,mDay);
                        startActivity(intent);
                    }

                } else {
                    Log.i(LOG_TAG, "日付が選択されていない");
                    context = getApplicationContext();
                    CharSequence text = "日付が選択されていません。";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });


        //ListViewへ表示
        mDBManager = new DBManager(TodayTrainingMenuRegisterActivity.this);
        if (join_key == null){

        }else {
            cursor = mDBManager.selectTodayMenu(join_key);
            cursor.moveToFirst();

            SimpleCursorAdapter sca = new SimpleCursorAdapter(
                    this,
                    R.layout.detail_muscle_list_layout,
                    cursor,
                    new String[]{"training_name", "heavy", "first_set"
                            , "second_set", "third_set", "fourth_set"},
                    new int[]{R.id.training_menu_name, R.id.training_weight_text, R.id.first_set
                            , R.id.second_set, R.id.third_set, R.id.fourth_set},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

            listView.setAdapter(sca);
        }


        //登録機能
        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("InsertButton", "登録ボタンが押されました");
                //TextViewに表示されている値を取得
                mYear = yearText.getText().toString();
                mMonth = monthText.getText().toString();
                mDay = dayText.getText().toString();
                join_key = mYear + mMonth + mDay;
                switch (muscle_name) {
                    case "CHEST":
                        muscle_name = "CHEST";
                        mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                        break;
                    case "BACK":
                        muscle_name = "BACK";
                        mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                        break;
                    case "SHOULDER":
                        muscle_name = "SHOULDER";
                        mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                        break;
                    case "LOWER":
                        muscle_name = "LOWER";
                        mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                        break;
                    case "RUN":
                        muscle_name = "RUN";
                        mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                        break;
                    case "OFF":
                        muscle_name = "OFF";
                        mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                        break;
                    default:
                        muscle_name = "記録無し";
                        mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                        break;
                }
            }
        });

        //登録キャンセル機能(Home画面への遷移機能)
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "home_iconが選択されました。");
                Intent intent = new Intent(TodayTrainingMenuRegisterActivity.this, MainActivity.class);
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
                        Intent intent = new Intent(TodayTrainingMenuRegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.training_register:
                        Log.i(LOG_TAG, "トレーニング追加ボタンが選択されました。");
                        Intent intentT = new Intent(TodayTrainingMenuRegisterActivity.this, TodayTrainingMenuRegisterActivity.class);
                        startActivity(intentT);
                        break;
                    case R.id.weight_register:
                        Log.i(LOG_TAG, "体重追加ボタンが選択されました。");
                        Intent intentW = new Intent(TodayTrainingMenuRegisterActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        return true;
                    case R.id.menu_register:
                        Log.i(LOG_TAG, "メニュー追加ボタンが選択されました。");
                        Intent intentM = new Intent(TodayTrainingMenuRegisterActivity.this, AllMenuActivity.class);
                        startActivity(intentM);
                        return true;
                }
                return true;
            }
        });
    }
}