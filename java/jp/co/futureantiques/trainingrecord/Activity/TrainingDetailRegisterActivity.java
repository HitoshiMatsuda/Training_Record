package jp.co.futureantiques.trainingrecord.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;

public class TrainingDetailRegisterActivity extends AbsMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_training_detail_register);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        setContentView(R.layout.activity_training_detail_register);
        super.onResume();

        mDBManager = new DBManager(TrainingDetailRegisterActivity.this);

        //intentを取得し、更新データのkeyに設定する
        Intent intent = this.getIntent();
        String mKey = intent.getStringExtra("key");
        String editKey = intent.getStringExtra("editKey");
        mId = intent.getStringExtra("mId");
        join_key = intent.getStringExtra(JOIN_KEY);
        muscle_name = intent.getStringExtra(MUSCLE_KEY);
        mYear = intent.getStringExtra(YEAR_KEY);
        mMonth = intent.getStringExtra(MONTH_KEY);
        mDay = intent.getStringExtra(DAY_KEY);

        aBar();

        mTrainingData = mDBManager.selectMenuEdit(mKey);
        selectTraining = mTrainingData.getMenu();
        trainingName.setText(selectTraining);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                heavy = heavyInput.getText().toString();
                first = firstInput.getText().toString();
                second = secondInput.getText().toString();
                third = thirdInput.getText().toString();
                fourth = fourthInput.getText().toString();
                mDBManager.insertTodayTraining(join_key, selectTraining, heavy, first, second, third, fourth);

                if (editKey == null) {
                    Intent intent = new Intent(TrainingDetailRegisterActivity.this, TrainingRegisterActivity.class);
                    intent.putExtra("mId",mId);
                    intent.putExtra(JOIN_KEY, join_key);
                    intent.putExtra(YEAR_KEY, mYear);
                    intent.putExtra(MONTH_KEY, mMonth);
                    intent.putExtra(DAY_KEY, mDay);
                    startActivity(intent);
                } else {
                    Intent intentEdit = new Intent(TrainingDetailRegisterActivity.this, TrainingEditActivity.class);

                    intentEdit.putExtra("mId",mId);
                    intentEdit.putExtra("editKey",editKey);
                    intentEdit.putExtra(JOIN_KEY, join_key);
                    intentEdit.putExtra(YEAR_KEY, mYear);
                    intentEdit.putExtra(MONTH_KEY, mMonth);
                    intentEdit.putExtra(DAY_KEY, mDay);

                    startActivity(intentEdit);
                }
            }
        });

        //登録キャンセル機能(Home画面への遷移機能)
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainingDetailRegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_icon:
                        Intent intent = new Intent(TrainingDetailRegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.training_register:
                        Intent intentT = new Intent(TrainingDetailRegisterActivity.this, TrainingRegisterActivity.class);
                        startActivity(intentT);
                        break;
                    case R.id.weight_register:
                        Intent intentW = new Intent(TrainingDetailRegisterActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        return true;
                    case R.id.menu_register:
                        Intent intentM = new Intent(TrainingDetailRegisterActivity.this, TrainingNameRegisterActivity.class);
                        startActivity(intentM);
                        return true;
                }
                return true;
            }
        });

    }
}