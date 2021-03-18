package jp.co.futureantiques.trainingrecord.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import jp.co.futureantiques.trainingrecord.R;

public class MenuActivity extends AbsMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.app_actionbar);

        aBar();

        Menu menu = mNavigationView.getMenu();
        menu.findItem(R.id.logout_icon).setVisible(false);
        menu.findItem(R.id.login_icon).setVisible(false);
    }
}