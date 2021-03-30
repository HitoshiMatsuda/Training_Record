package jp.co.futureantiques.trainingrecord.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;
import jp.co.futureantiques.trainingrecord.TrainingData;

public class TrainingNameEditActivity extends AbsMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_training_name_edit);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        setContentView(R.layout.activity_training_name_edit);
        super.onResume();

        aBar();

        Intent intent = this.getIntent();
        mDBManager = new DBManager(TrainingNameEditActivity.this);
        cursor = mDBManager.selectMuscle();
        cursor.moveToFirst();

        //intentを取得し、更新データのkeyに設定する
        mId = intent.getStringExtra("key");


        //更新前のトレーニングメニュー
        TrainingData trainingData = mDBManager.selectMenuEdit(mId);
        oldTraining = trainingData.getMenu();
        trainingName.setText(oldTraining);


        //トレーニングメニュー更新処理
        Button updateB = findViewById(R.id.update_button);
        updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTraining = inputTrainingName.getText().toString();
                mDBManager.trainMenuUpDate(mId,newTraining);

                Intent intent = new Intent(TrainingNameEditActivity.this, TrainingNameRegisterActivity.class);
                startActivity(intent);
            }
        });


        //トレーニングメニュー削除処理
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBManager.trainMenuDelete(mId);

                Intent intent = new Intent(TrainingNameEditActivity.this, TrainingNameRegisterActivity.class);
                startActivity(intent);
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_icon:
                        Intent intent = new Intent(TrainingNameEditActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.training_register:
                        Intent intentT = new Intent(TrainingNameEditActivity.this, TrainingRegisterActivity.class);
                        startActivity(intentT);
                        break;
                    case R.id.weight_register:
                        Intent intentW = new Intent(TrainingNameEditActivity.this, WeightRegisterActivity.class);
                        startActivity(intentW);
                        return true;
                    case R.id.menu_register:
                        Intent intentM = new Intent(TrainingNameEditActivity.this, TrainingNameRegisterActivity.class);
                        startActivity(intentM);
                        return true;
                }
                return true;
            }
        });

    }
}