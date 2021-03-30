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

public class TrainingRegisterActivity extends AbsMainActivity {

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

        mDBManager = new DBManager(TrainingRegisterActivity.this);

        Intent intent = getIntent();
        join_key = intent.getStringExtra(JOIN_KEY);
        mYear = intent.getStringExtra(YEAR_KEY);
        mMonth = intent.getStringExtra(MONTH_KEY);
        mDay = intent.getStringExtra(DAY_KEY);

        if (join_key != null) {
            Log.i("moved","トレーニングメニューを追加しました");
            yearText.setText(mYear);
            monthText.setText(mMonth);
            dayText.setText(mDay);
        } else {
            Log.i("info","HOME画面より遷移");
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


        //ListViewへ表示
        //トレーニングメニューごとの記録を取得し表示する
        mDBManager = new DBManager(TrainingRegisterActivity.this);
        if (join_key == null) {
            Log.i("info","トレーニング記録は登録されていませんでした");
        } else {
            cursor = mDBManager.selectTodayMenu(join_key);
            cursor.moveToFirst();

            SimpleCursorAdapter sca = new SimpleCursorAdapter(
                    this,
                    R.layout.list_layout_training_detail,
                    cursor,
                    new String[]{"training_name", "heavy", "first_set"
                            , "second_set", "third_set", "fourth_set"},
                    new int[]{R.id.training_menu_name, R.id.training_weight_text, R.id.first_set
                            , R.id.second_set, R.id.third_set, R.id.fourth_set},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

            listView.setAdapter(sca);
        }


        //ListViewのSwipe機能
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("info", "リフレッシュされました");

                mYear = yearText.getText().toString();
                mMonth = monthText.getText().toString();
                mDay = dayText.getText().toString();
                join_key = mYear + mMonth + mDay;
                cursor = mDBManager.selectTodayMenu(join_key);
                cursor.moveToFirst();
                SimpleCursorAdapter sca = new SimpleCursorAdapter(
                        TrainingRegisterActivity.this,
                        R.layout.list_layout_training_detail,
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


        //「トレーニングを追加」ボタンの押下処理
        //日付と筋群が指定されている場合のみ画面遷移可能
        Button selectButton = findViewById(R.id.menu_register_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYear = yearText.getText().toString();
                mMonth = monthText.getText().toString();
                mDay = dayText.getText().toString();
                join_key = mYear + mMonth + mDay;
                if (mYear != "") {
                    Log.i("info", "日付が選択されている");

                    if (muscle_name.equals("筋群が選択されていません")) {
                        Log.i("info", "筋群が選択されていない");
                        context = getApplicationContext();
                        CharSequence text = "筋群が選択されていません。";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    } else {
                        Log.i("info", "日付と筋群が選択されている");
                        Intent intent = new Intent(TrainingRegisterActivity.this, SelectTrainingMenuActivity.class);
                        //日付をkeyにして送る
                        intent.putExtra(JOIN_KEY, join_key);
                        intent.putExtra(MUSCLE_KEY, muscle_name);
                        intent.putExtra(YEAR_KEY,mYear);
                        intent.putExtra(MONTH_KEY,mMonth);
                        intent.putExtra(DAY_KEY,mDay);
                        startActivity(intent);
                    }

                } else {
                    Log.i("info", "日付が選択されていない");
                    context = getApplicationContext();
                    CharSequence text = "日付が選択されていません。";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });


        //トレーニング記録を登録します
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
                if (muscle_name.equals("筋群が選択されていません")) {
                    Log.i("info", "筋群が選択されていない");
                    context = getApplicationContext();
                    CharSequence text = "筋群が選択されていません。";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                } else {
                    Intent intent = new Intent(TrainingRegisterActivity.this, MainActivity.class);
                    switch (muscle_name) {
                        case "CHEST":
                            muscle_name = "CHEST";
                            mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                            startActivity(intent);
                            break;
                        case "BACK":
                            muscle_name = "BACK";
                            mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                            startActivity(intent);
                            break;
                        case "SHOULDER":
                            muscle_name = "SHOULDER";
                            mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                            startActivity(intent);
                            break;
                        case "LOWER":
                            muscle_name = "LOWER";
                            mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                            startActivity(intent);
                            break;
                        case "RUN":
                            muscle_name = "RUN";
                            mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                            startActivity(intent);
                            break;
                        case "OFF":
                            muscle_name = "OFF";
                            mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                            startActivity(intent);
                            break;
                        default:
                            muscle_name = "筋群指定なし";
                            mDBManager.insertTodayTraining(muscle_name, mYear, mMonth, mDay, join_key);
                            startActivity(intent);
                            break;
                    }
                }
            }
        });


        //トレーニングの記録をキャンセルし、HOME画面へ遷移
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingRegisterActivity.this, MainActivity.class);
                startActivity(intent);
                Log.i("move", "ホーム画面へ");
            }
        });


        //メニュー選択時の画面遷移機能
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_icon:
                        Intent intent = new Intent(TrainingRegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        Log.i("move", "ホーム画面へ");
                        break;
                    case R.id.training_register:
                        Intent intentT = new Intent(TrainingRegisterActivity.this, TrainingRegisterActivity.class);
                        startActivity(intentT);
                        Log.i("move", "トレーニング記録画面へ");
                        break;
                    case R.id.weight_register:
                        Intent intentW = new Intent(TrainingRegisterActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        Log.i("move", "体重記録画面へ");
                        return true;
                    case R.id.menu_register:
                        Intent intentM = new Intent(TrainingRegisterActivity.this, TrainingNameRegisterActivity.class);
                        startActivity(intentM);
                        Log.i("move", "トレーニング管理画面へ");
                        return true;
                }
                return true;
            }
        });
    }
}