package jp.co.futureantiques.trainingrecord.Activity;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.Fragment.DatePick;
import jp.co.futureantiques.trainingrecord.R;
import jp.co.futureantiques.trainingrecord.TrainingData;

public class AbsMainActivity extends FragmentActivity implements OnDateSetListener {
    protected final String YEAR_KEY = "year_key";
    protected final String MONTH_KEY = "month_key";
    protected final String DAY_KEY = "day_key";
    protected final String MUSCLE_KEY = "muscle_key";
    protected final String JOIN_KEY = "join_key";
    protected String join_key;

    //DrawerLayout
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected Toolbar mToolbar;

    //トレーニング登録日変数
    protected String mYear;
    protected String mMonth;
    protected String mDay;

    //トレーニング日確認用TextView
    protected TextView yearText;
    protected TextView monthText;
    protected TextView dayText;

    //トレーニング部位(大分類)
    protected TrainingData mTrainingData;
    protected String muscle_name;
    protected Spinner spinner;

    //トレーニングメニュー名記録関係
    protected TextView trainingNameUpdate;

    //ListView表示用
    protected ListView listView;
    protected DBManager mDBManager;
    protected Cursor cursor;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    //登録関係
    protected Button registerButton;
    protected Button deleteButton;
    protected Button updateButton;
    protected Button homeButton;
    protected Context context;
    protected String mId;
    protected String oldTraining;
    protected String newTraining;
    protected String selectTraining;

    //トレーニング記録用
    protected int keyId;
    protected TextView trainingName;
    protected EditText inputTrainingName;
    protected EditText heavyInput;
    protected EditText firstInput;
    protected EditText secondInput;
    protected EditText thirdInput;
    protected EditText fourthInput;
    protected String heavy;
    protected String first;
    protected String second;
    protected String third;
    protected String fourth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abs_main);
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
    protected void onResume() {
        super.onResume();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.app_actionbar);
        spinner = findViewById(R.id.training_spi);
        yearText = findViewById(R.id.test_text_year);
        monthText = findViewById(R.id.test_text_month);
        dayText = findViewById(R.id.test_text_day);
        mSwipeRefreshLayout = findViewById(R.id.swipe_layout);
        trainingName = findViewById(R.id.training_name);
        trainingNameUpdate = findViewById(R.id.training_name_update);
        inputTrainingName = findViewById(R.id.menu_name);
        listView = findViewById(R.id.training_list);
        yearText = findViewById(R.id.test_text_year);
        monthText = findViewById(R.id.test_text_month);
        dayText = findViewById(R.id.test_text_day);

        //Button
        registerButton = findViewById(R.id.register_button);
        deleteButton = findViewById(R.id.delete_button);
        updateButton = findViewById(R.id.update_button);
        homeButton = findViewById(R.id.home_button);

        //トレーニングのメニューごとの記録入力欄
        trainingName = findViewById(R.id.training_name);
        heavyInput = findViewById(R.id.training_weight);
        firstInput = findViewById(R.id.first_set_edit);
        secondInput = findViewById(R.id.second_set_edit);
        thirdInput = findViewById(R.id.third_set_edit);
        fourthInput = findViewById(R.id.fourth_set_edit);



        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        mNavigationView.bringToFront();

        aBar();


    }


    public void aBar(){
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        mNavigationView.bringToFront();
    }

}