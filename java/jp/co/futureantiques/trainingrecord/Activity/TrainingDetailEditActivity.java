package jp.co.futureantiques.trainingrecord.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;

public class TrainingDetailEditActivity extends AbsMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_detail_edit);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDBManager = new DBManager(TrainingDetailEditActivity.this);

        aBar();

        Intent intent = this.getIntent();
        mId = intent.getStringExtra("key");
        String editKey = intent.getStringExtra("editKey");
        mTrainingData = mDBManager.detailTrainingRecordSelect(mId);
        oldTraining = mTrainingData.getMenu();
        heavy = mTrainingData.getHeavyWeight();
        first = mTrainingData.getFirst();
        second = mTrainingData.getSecond();
        third = mTrainingData.getThird();
        fourth = mTrainingData.getFourth();

        trainingName.setText(oldTraining);
        heavyInput.setText(heavy);
        firstInput.setText(first);
        secondInput.setText(second);
        thirdInput.setText(third);
        fourthInput.setText(fourth);

        //トレーニングメニュー削除処理
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBManager.deleteTrainingDetailRecord(mId);

                Intent intent = new Intent(TrainingDetailEditActivity.this, TrainingEditActivity.class);
                intent.putExtra("mId",mId);
                intent.putExtra("editKey",editKey);
                startActivity(intent);
                Log.i("move","MainActivityへ遷移します");

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBManager.trainingDetailRecordUpdate(mId,heavy,first,second,third,fourth);

                Intent intent = new Intent(TrainingDetailEditActivity.this, TrainingEditActivity.class);
                intent.putExtra("mId",mId);
                intent.putExtra("editKey",editKey);
                startActivity(intent);
                Log.i("move","MainActivityへ遷移します");
            }
        });
    }
}