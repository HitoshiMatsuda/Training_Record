package jp.co.futureantiques.trainingrecord.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;

public class AllMenuActivity extends AbsMainActivity {
    private final String LOG_TAG = "AllMenuActivity";

    private String menuName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setContentView(R.layout.activity_all_menu);

        listView = findViewById(R.id.training_menu_list);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.app_actionbar);
        spinner = findViewById(R.id.training_spi);
        mDBManager = new DBManager(AllMenuActivity.this);

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
        mSwipeRefreshLayout = findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(LOG_TAG, "リフレッシュされました");

                cursor = mDBManager.selectAllTrainingMenu();
                cursor.moveToFirst();

                SimpleCursorAdapter sca = new SimpleCursorAdapter(
                        AllMenuActivity.this,
                        R.layout.simple_training_menu_list_layout,
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
                R.layout.simple_training_menu_list_layout,
                cursor,
                new String[]{"training_name"},
                new int[]{R.id.name_text},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(sca);

        //トレーニングメニュー更新画面への遷移機能
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllMenuActivity.this, MenuEditActivity.class);
                //値を引き渡す
                intent.putExtra("key", String.valueOf(id));
                //Activityの起動
                startActivity(intent);
            }
        });

        EditText editText = findViewById(R.id.menu_name);
        registerButton = findViewById(R.id.menu_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("InsertButton", "登録ボタンが押されました");

                if (muscle_name.equals("筋群が選択されていません")) {
                    Log.i(LOG_TAG, "筋群が選択されていない");
                    context = getApplicationContext();
                    CharSequence text = "筋群が選択されていません。";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                } else {
                    Log.i(LOG_TAG, "筋群が選択されている");
                    switch (muscle_name) {
                        case "CHEST":
                            muscle_name = "CHEST";
                            menuName = editText.getText().toString();
                            mDBManager.insertMenu(muscle_name, menuName);
                            break;
                        case "BACK":
                            muscle_name = "BACK";
                            menuName = editText.getText().toString();
                            mDBManager.insertMenu(muscle_name, menuName);
                            break;
                        case "SHOULDER":
                            muscle_name= "SHOULDER";
                            menuName = editText.getText().toString();
                            mDBManager.insertMenu(muscle_name, menuName);
                            break;
                        case "LOWER":
                            muscle_name = "LOWER";
                            menuName = editText.getText().toString();
                            mDBManager.insertMenu(muscle_name, menuName);
                            break;
                        case "RUN":
                            muscle_name = "RUN";
                            menuName = editText.getText().toString();
                            mDBManager.insertMenu(muscle_name, menuName);
                            break;
                        case "OFF":
                            muscle_name = "OFF";
                            menuName = editText.getText().toString();
                            mDBManager.insertMenu(muscle_name, menuName);
                            break;
                        default:
                            muscle_name = "未分類";
                            menuName = editText.getText().toString();
                            mDBManager.insertMenu(muscle_name, menuName);
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
                        Log.i(LOG_TAG, "home_iconが選択されました。");
                        Intent intent = new Intent(AllMenuActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.training_register:
                        Log.i(LOG_TAG, "トレーニングボタンが選択されました。");
                        Intent intentT = new Intent(AllMenuActivity.this, TodayTrainingMenuRegisterActivity.class);
                        startActivity(intentT);
                        break;
                    case R.id.weight_register:
                        Log.i(LOG_TAG, "体重ボタンが選択されました。");
                        Intent intentW = new Intent(AllMenuActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        return true;
                    case R.id.menu_register:
                        Log.i(LOG_TAG, "メニューボタンが選択されました。");
                        Intent intentM = new Intent(AllMenuActivity.this, AllMenuActivity.class);
                        startActivity(intentM);
                        return true;
                }
                return true;
            }
        });
    }
}