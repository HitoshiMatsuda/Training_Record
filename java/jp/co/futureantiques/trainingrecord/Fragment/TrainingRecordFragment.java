package jp.co.futureantiques.trainingrecord.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.futureantiques.trainingrecord.Activity.EditActivity;
import jp.co.futureantiques.trainingrecord.Activity.MainActivity;
import jp.co.futureantiques.trainingrecord.ChartManager;
import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;

public class TrainingRecordFragment extends Fragment {
    private static final String TRAIN_MENU = "train_menu";
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";

    private DBManager mDBManager;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training_record, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //MainActivityのContextを取得
        context = getActivity();
        mDBManager = new DBManager(context);

        Cursor cursor = mDBManager.selectAll();
        cursor.moveToFirst();

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                context,
                R.layout.list_layout_main,
                cursor,
                new String[]{TRAIN_MENU, YEAR, MONTH, DAY},
                new int[]{R.id.menu_name, R.id.date_year, R.id.date_month, R.id.date_day},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );
        ListView listView = view.findViewById(R.id.training_list);
        listView.setAdapter(simpleCursorAdapter);

        //リスト項目選択処理(更新処理)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //インテントの作成
                Intent intent = new Intent(context, EditActivity.class);

                //値を引き渡す
                intent.putExtra("key", String.valueOf(id));
                //Activityの起動
                startActivity(intent);
            }
        });
    }
}