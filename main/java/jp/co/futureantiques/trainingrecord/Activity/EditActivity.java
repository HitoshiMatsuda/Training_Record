package jp.co.futureantiques.trainingrecord.Activity;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.Fragment.DatePick;
import jp.co.futureantiques.trainingrecord.Fragment.DatePickUpdate;
import jp.co.futureantiques.trainingrecord.R;
import jp.co.futureantiques.trainingrecord.TrainingData;

public class EditActivity extends FragmentActivity implements OnDateSetListener {
    //タグ
    private final String LOG_TAG = "EditActivity";

    //更新前
    protected TextView oldYearText;
    protected TextView oldMonthText;
    protected TextView oldDayText;
    protected TextView oldMenuText;
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
    protected int rId;
    private Button cancel;
    private Button update;

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
        //Layoutを実装
        setContentView(R.layout.activity_edit);

        //DBをnewする
        DBManager mDBManager = new DBManager(EditActivity.this);
        Intent intent = this.getIntent();

        //intentを取得し、更新データのkeyに設定する
        mId = intent.getStringExtra("key");

        //更新用のデータを引っ張りにいく
        TrainingData trainingData = mDBManager.selectEdit(mId);

        //Toolbarをセット
        Toolbar mToolbar = findViewById(R.id.actionbar);

        //カレンダー表示ボタンを紐づける
        Button calButton = findViewById(R.id.update_calender_button);

        //TextViewを紐づける(更新前)
        oldYearText = findViewById(R.id.old_date_year);
        oldMonthText = findViewById(R.id.old_date_month);
        oldDayText = findViewById(R.id.old_date_day);
        oldMenuText = findViewById(R.id.old_train_menu);

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
        oldMenuText.setText(oldMenu);

        //キャンセルボタンを紐づける
        cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "home_iconが選択されました。");
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //登録ボタンを紐づける
        update = findViewById(R.id.update_button);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("InsertButton", "登録ボタンが押されました");
                //TextViewに表示されている値を取得
                newYear = newYearText.getText().toString();
                newMonth = newMonthText.getText().toString();
                newDay = newDayText.getText().toString();

                //選択されたRadioButtonごとに値を指定して格納する
                //RadioGroupを紐づける
                RadioGroup radioGroup = findViewById(R.id.radio_group);
                rId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(rId);
                newMenu = radioButton.getText().toString();
                switch (newMenu) {
                    case "大胸筋":
                        newMenu = "CHEST DAY";
                        mDBManager.trainUpDate(mId, newMenu, newYear, newMonth, newDay);
                        break;
                    case "背中":
                        newMenu = "BACK DAY";
                        mDBManager.trainUpDate(mId, newMenu, newYear, newMonth, newDay);
                        break;
                    case "肩・腕":
                        newMenu = "SHOULDER DAY";
                        mDBManager.trainUpDate(mId, newMenu, newYear, newMonth, newDay);
                        break;
                    case "下半身":
                        newMenu = "LOWER DAY";
                        mDBManager.trainUpDate(mId, newMenu, newYear, newMonth, newDay);
                        break;
                    case "有酸素":
                        newMenu = "RUN DAY";
                        mDBManager.trainUpDate(mId, newMenu, newYear, newMonth, newDay);
                        break;
                    case "オフ":
                        newMenu = "OFF DAY";
                        mDBManager.trainUpDate(mId, newMenu, newYear, newMonth, newDay);
                        break;
                    default:
                        newMenu = "記録無し";
                        mDBManager.trainUpDate(mId, newMenu, newYear, newMonth, newDay);
                        break;
                }
            }
        });
    }
}