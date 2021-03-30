package jp.co.futureantiques.trainingrecord.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;

public class WeightRegisterActivity extends AppCompatActivity {

    protected EditText mWeight;
    protected EditText mFat;
    protected String weight;
    protected String fat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_register);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Layoutを実装
        setContentView(R.layout.activity_weight_register);

        //DBをnewする
        DBManager mDBManager = new DBManager(WeightRegisterActivity.this);

        //Toolbarをセット
        Toolbar mToolbar = findViewById(R.id.actionbar);
        setSupportActionBar(mToolbar);

        //EditText紐付け
        //体重と体脂肪の入力値を受け取る
        mWeight = findViewById(R.id.weight_text);
        mFat = findViewById(R.id.fat_text);

        //体重と体脂肪を登録
        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("InsertButton", "登録ボタンが押されました");
                //EditTextに入力された値を変数へ格納する
                weight = mWeight.getText().toString();
                fat = mFat.getText().toString();
                mDBManager.insert(weight, fat);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //タグ
        String LOG_TAG = "WeightRegisterActivity";
        switch (item.getItemId()) {
            case R.id.home_icon:
                Log.i(LOG_TAG, "home_iconが選択されました。");
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_register:
                Log.i(LOG_TAG, "メニュー追加ボタンが選択されました。");
                Intent intentM = new Intent(this, TrainingRegisterActivity.class);
                startActivity(intentM);
                return true;
            case R.id.weight_register:
                Log.i(LOG_TAG, "体重追加ボタンが選択されました。");
                Intent intentW = new Intent(this, WeightRegisterActivity.class);
                startActivity(intentW);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}