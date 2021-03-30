package jp.co.futureantiques.trainingrecord.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;

public class TrainingEditActivity extends AbsMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_training_edit);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        setContentView(R.layout.activity_training_edit);
        super.onResume();

        aBar();

        mDBManager = new DBManager(TrainingEditActivity.this);
        Intent intent = this.getIntent();
        mId = intent.getStringExtra("mId");
        String editKey = intent.getStringExtra("editKey");
        mTrainingData = mDBManager.selectEdit(mId);
        muscle_name = mTrainingData.getMenu();
        mYear = mTrainingData.getYear();
        mMonth = mTrainingData.getMonth();
        mDay = mTrainingData.getDay();
        join_key = mYear + mMonth + mDay;


        yearText.setText(mYear);
        monthText.setText(mMonth);
        dayText.setText(mDay);
        trainingName.setText(muscle_name);


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


        //TrainingDetailEditActivityへの遷移機能
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Move", "TrainingMenuEditActivityへ遷移します");
                Intent intent = new Intent(TrainingEditActivity.this, TrainingDetailEditActivity.class);
                intent.putExtra("key", String.valueOf(id));
                intent.putExtra("mId",mId);
                intent.putExtra("editKey", editKey);
                startActivity(intent);
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

                Intent intent = new Intent(TrainingEditActivity.this, SelectTrainingMenuActivity.class);
                intent.putExtra("mId",mId);
                intent.putExtra(JOIN_KEY, join_key);
                intent.putExtra(MUSCLE_KEY, muscle_name);
                intent.putExtra(YEAR_KEY, mYear);
                intent.putExtra(MONTH_KEY, mMonth);
                intent.putExtra(DAY_KEY, mDay);
                intent.putExtra("editKey", editKey);
                startActivity(intent);
            }
        });


        //トレーニング記録更新処理
        Button updateB = findViewById(R.id.update_button);
        updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingEditActivity.this, MainActivity.class);
                startActivity(intent);
                Log.i("move", "MainActivityへ遷移します");
            }
        });


        //トレーニングメニュー削除処理
        Button deleteB = findViewById(R.id.delete_button);
        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYear = yearText.getText().toString();
                mMonth = monthText.getText().toString();
                mDay = dayText.getText().toString();
                join_key = mYear + mMonth + mDay;
                mDBManager.trainingDelete(mId);
                mDBManager.trainingRecordDelete(join_key);

                Intent intent = new Intent(TrainingEditActivity.this, MainActivity.class);
                startActivity(intent);
                Log.i("move", "MainActivityへ遷移します");

            }
        });

        //メニュー選択時の画面遷移機能
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_icon:
                        Intent intent = new Intent(TrainingEditActivity.this, MainActivity.class);
                        startActivity(intent);
                        Log.i("move", "MainActivityへ遷移します");
                        break;
                    case R.id.training_register:
                        Intent intentT = new Intent(TrainingEditActivity.this, TrainingRegisterActivity.class);
                        startActivity(intentT);
                        Log.i("move", "TrainingRecordRegisterActivityへ遷移します");
                        break;
                    case R.id.weight_register:
                        Intent intentW = new Intent(TrainingEditActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        Log.i("move", "WeightRegisterActivityへ遷移します");
                        return true;
                    case R.id.menu_register:
                        Intent intentM = new Intent(TrainingEditActivity.this, TrainingNameRegisterActivity.class);
                        startActivity(intentM);
                        Log.i("move", "TrainingNameRegisterActivityへ遷移します");
                        return true;
                }
                return true;
            }
        });
    }
}