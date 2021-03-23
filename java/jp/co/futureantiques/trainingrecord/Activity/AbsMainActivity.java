package jp.co.futureantiques.trainingrecord.Activity;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;

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
    protected String muscle_name;
    protected Spinner spinner;

    //ListView表示用
    protected ListView listView;
    protected DBManager mDBManager;
    protected Cursor cursor;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    //登録関係
    protected Button registerButton;
    protected Context context;
    protected String mId;
    protected String oldTraining;
    protected String newTraining;
    protected String selectTraining;

    //トレーニング記録用
    protected int keyId;
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
        setContentView(R.layout.activity_abstract_training_record);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.app_actionbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        mNavigationView.bringToFront();

        aBar();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    public void aBar(){
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        mNavigationView.bringToFront();
    }

}