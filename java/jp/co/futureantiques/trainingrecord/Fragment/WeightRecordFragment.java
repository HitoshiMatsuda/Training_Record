package jp.co.futureantiques.trainingrecord.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;

import jp.co.futureantiques.trainingrecord.ChartManager;
import jp.co.futureantiques.trainingrecord.DataBase.DBManager;
import jp.co.futureantiques.trainingrecord.R;

public class WeightRecordFragment extends Fragment {
    protected LineChart mChart;
    protected ChartManager mChartManager;
    private DBManager mDBManager;
    protected Context context;
    protected int[] wBox;
    protected int[] fBox;
    protected LineData lineData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //MainActivityのContextを取得
        context = getActivity();
        mDBManager = new DBManager(context);
        mChartManager = new ChartManager();

        //DBより数値取得
        wBox = mDBManager.wSelect();
        fBox = mDBManager.fSelect();

        //LineSetへ格納
        lineData = mChartManager.setData(wBox, fBox);

        //折れ線グラフを紐付けする
        //fragmentの紐付け調べる
        mChart = view.findViewById(R.id.lineChartExample1);

        //グラフの背景色
        mChart.setDrawGridBackground(true);

        //グラフの説明テキスト表示を許可
        mChart.getDescription().setEnabled(true);

        //グラフの説明テキスト（アプリ名など）
        mChart.getDescription().setText(getResources().getString(R.string.app_name));


        //X軸の設定
        XAxis xAxis = mChart.getXAxis();
        //X軸のラベルの傾き指定
        xAxis.setLabelRotationAngle(45f);
        //X軸のMAX,min
        //X軸に同時に表示できるデータの数
        //10個まで同時に表示可能と設定する
        xAxis.setAxisMaximum(30f);
        xAxis.setAxisMinimum(0f);
        //DBへ登録した年月日をX軸へ追加
        //同じ日に複数登録した場合は？
        //xAxis.setValueFormatter(new IndexAxisValueFormatter(getDate()));
        //X軸を破線にする
        xAxis.enableAxisLineDashedLine(5f, 5f, 1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        //Y軸の設定
        YAxis yAxis = mChart.getAxisLeft();
        //Y軸のMAX,min
        yAxis.setAxisMaximum(100f);
        yAxis.setAxisMinimum(0f);
        //Y軸を破線にする
        yAxis.enableAxisLineDashedLine(5f, 5f, 1f);
        yAxis.setDrawZeroLine(true);

        //グラフの右側に目盛りが不要であれば"false"
        mChart.getAxisRight().setEnabled(false);

        //mChart.animateX(1500);
        //グラフをスクロール可能にする
        mChart.setVisibleXRangeMaximum(10f);
        mChart.setData(lineData);

        //グラフの表示
        mChart.invalidate();
        Log.i("Chart", "グラフが表示されます。");
    }
}