package jp.co.futureantiques.trainingrecord.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;

public class TrainingNameRegisterActivity extends AbsMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_name_register);
    }

    @Override
    protected void onResume() {
        setContentView(R.layout.activity_training_name_register);
        super.onResume();

        mDBManager = new DBManager(TrainingNameRegisterActivity.this);

        aBar();

        //筋群選択機能(Spinners)
        ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(this, R.array.training_menus
                , android.R.layout.simple_spinner_item);
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
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("info", "リフレッシュされました");

                cursor = mDBManager.selectAllTrainingMenu();
                cursor.moveToFirst();

                SimpleCursorAdapter sca = new SimpleCursorAdapter(
                        TrainingNameRegisterActivity.this,
                        R.layout.list_layout_all_training_menu,
                        cursor,
                        new String[]{"training_name"},
                        new int[]{R.id.name_text},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                listView.setAdapter(sca);

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        //ListViewへ表示
        cursor = mDBManager.selectAllTrainingMenu();
        cursor.moveToFirst();

        SimpleCursorAdapter sca = new SimpleCursorAdapter(
                this,
                R.layout.list_layout_all_training_menu,
                cursor,
                new String[]{"training_name"},
                new int[]{R.id.name_text},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(sca);

        //トレーニングメニュー更新画面への遷移機能
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TrainingNameRegisterActivity.this, TrainingNameEditActivity.class);
                //値を引き渡す
                intent.putExtra("key", String.valueOf(id));
                //Activityの起動
                startActivity(intent);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("InsertButton", "登録ボタンが押されました");

                if (muscle_name.equals("筋群が選択されていません")) {
                    Log.i("info", "筋群が選択されていない");
                    context = getApplicationContext();
                    CharSequence text = "筋群が選択されていません。";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                } else {
                    Log.i("info", "筋群が選択されている");
                    Log.i("info", "トレーニングメニュー登録");
                    context = getApplicationContext();
                    CharSequence text = "トレーニングメニューを追加しました";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    switch (muscle_name) {
                        case "CHEST":
                            muscle_name = "CHEST";
                            selectTraining = inputTrainingName.getText().toString();
                            mDBManager.insertMenu(muscle_name, selectTraining);
                            toast.show();
                            break;
                        case "BACK":
                            muscle_name = "BACK";
                            selectTraining = inputTrainingName.getText().toString();
                            mDBManager.insertMenu(muscle_name, selectTraining);
                            toast.show();
                            break;
                        case "SHOULDER":
                            muscle_name= "SHOULDER";
                            selectTraining = inputTrainingName.getText().toString();
                            mDBManager.insertMenu(muscle_name, selectTraining);
                            toast.show();
                            break;
                        case "LOWER":
                            muscle_name = "LOWER";
                            selectTraining = inputTrainingName.getText().toString();
                            mDBManager.insertMenu(muscle_name, selectTraining);
                            toast.show();
                            break;
                        case "RUN":
                            muscle_name = "RUN";
                            selectTraining = inputTrainingName.getText().toString();
                            mDBManager.insertMenu(muscle_name, selectTraining);
                            toast.show();
                            break;
                        case "OFF":
                            muscle_name = "OFF";
                            selectTraining = inputTrainingName.getText().toString();
                            mDBManager.insertMenu(muscle_name, selectTraining);
                            toast.show();
                            break;
                        default:
                            muscle_name = "未分類";
                            selectTraining = inputTrainingName.getText().toString();
                            mDBManager.insertMenu(muscle_name, selectTraining);
                            toast.show();
                            break;
                    }
                }
            }
        });


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_icon:
                        Intent intent = new Intent(TrainingNameRegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.training_register:
                        Intent intentT = new Intent(TrainingNameRegisterActivity.this, TrainingRegisterActivity.class);
                        startActivity(intentT);
                        break;
                    case R.id.weight_register:
                        Intent intentW = new Intent(TrainingNameRegisterActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        return true;
                    case R.id.menu_register:
                        Intent intentM = new Intent(TrainingNameRegisterActivity.this, TrainingNameRegisterActivity.class);
                        startActivity(intentM);
                        return true;
                }
                return true;
            }
        });
    }
}