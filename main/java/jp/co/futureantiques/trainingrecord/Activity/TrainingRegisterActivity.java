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
import jp.co.futureantiques.trainingrecord.R;

public class TrainingRegisterActivity extends FragmentActivity implements OnDateSetListener {
    //タグ
    private final String LOG_TAG = "WeightRegisterActivity";

    protected String mYear;
    protected String mMonth;
    protected String mDay;
    private TextView yearText;
    private TextView monthText;
    private TextView dayText;
    private String train_menu;
    private int id;
    private RadioGroup radioGroup;
    private Button button;
    private Button cancel;

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
        //layout紐付け
        setContentView(R.layout.activity_training_register);

        //Toolbarの設置
        Toolbar mToolbar = findViewById(R.id.actionbar);

        //TextViewを紐づける
        yearText = findViewById(R.id.test_text_year);
        monthText = findViewById(R.id.test_text_month);
        dayText = findViewById(R.id.test_text_day);

        //カレンダー表示ボタンを紐づける
        Button calButton = findViewById(R.id.calender_button);

        //登録ボタンを紐づける
        button = findViewById(R.id.register_button);
        DBManager mDBManager = new DBManager(TrainingRegisterActivity.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("InsertButton", "登録ボタンが押されました");
                //TextViewに表示されている値を取得
                mYear = yearText.getText().toString();
                mMonth = monthText.getText().toString();
                mDay = dayText.getText().toString();

                //選択されたRadioButtonごとに値を指定して格納する
                //RadioGroupを紐づける
                radioGroup = findViewById(R.id.radio_group);
                id = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(id);
                train_menu = radioButton.getText().toString();
                switch (train_menu) {
                    case "大胸筋":
                        train_menu = "CHEST DAY";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                    case "背中":
                        train_menu = "BACK DAY";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                    case "肩・腕":
                        train_menu = "SHOULDER DAY";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                    case "下半身":
                        train_menu = "LOWER DAY";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                    case "有酸素":
                        train_menu = "RUN DAY";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                    case "オフ":
                        train_menu = "OFF DAY";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                    default:
                        train_menu = "記録無し";
                        mDBManager.insertTrain(train_menu, mYear, mMonth, mDay);
                        break;
                }
            }
        });

        //キャンセルボタンを紐づける
        cancel = findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "home_iconが選択されました。");
                Intent intent = new Intent(TrainingRegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}