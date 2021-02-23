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

import java.util.Locale;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.Fragment.DatePick;
import jp.co.futureantiques.trainingrecord.R;

public class TrainingRegisterActivity extends FragmentActivity implements OnDateSetListener {
    //タグ
    private final String LOG_TAG = "WeightRegisterActivity";

    private DBManager mDBManager;
    private Toolbar mToolbar;
    private TextView textView;
    private Button calButton;
    protected int mYear;
    protected int mMonth;
    protected int mDay;
    private RadioGroup radioGroup;
    private int id;
    private RadioButton radioButton;
    private Button button;
    private Button cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_register);
    }

    @Override
    public void onResume() {
        super.onResume();
        //layout紐付け
        setContentView(R.layout.activity_training_register);


        //Toolbarの設置
        mToolbar = findViewById(R.id.actionbar);

        //TextViewを紐づける
        textView = findViewById(R.id.test_text);

        //カレンダー表示ボタンを紐づける
        calButton = findViewById(R.id.calender_button);

        //RadioGroupを紐づける
        radioGroup = findViewById(R.id.radio_group);
        id = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(id);

        //登録ボタンを紐づける
        button = findViewById(R.id.register_button);
        mDBManager = new DBManager(TrainingRegisterActivity.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("InsertButton", "登録ボタンが押されました");

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

    public void showDatePickerDialog(View v) {
        DialogFragment dialogFragment = new DatePick();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String str = String.format(Locale.US, "%d/%d/%d", year, month + 1, dayOfMonth);
        textView.setText(str);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
    }*/

    /*//TextView(Test用)
    TextView textView = findViewById(R.id.test_text);

        //クリックイベントを実装
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calendarインスタンスを取得
                final Calendar date = Calendar.getInstance();

                //DatePickerDialogインスタンスを取得
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TrainingRegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //setした日付を取得して表示
                                editText.setText(String.format("%d / %02d / %02d", year, month + 1, dayOfMonth));
                            }
                        },
                        date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.DATE)
                );
                //dialogを表示
                datePickerDialog.show();
            }
        });*/
}