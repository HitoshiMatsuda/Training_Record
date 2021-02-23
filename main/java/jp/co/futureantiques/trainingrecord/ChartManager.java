package jp.co.futureantiques.trainingrecord;

import android.graphics.Color;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class ChartManager {
    private LineDataSet weights;
    private LineDataSet fats;
    private PieDataSet pectorals;
    private PieDataSet back;
    private PieDataSet lower;
    private PieDataSet shoulder;

    public ChartManager() {}

    public LineData setData(int wBox[], int fBox[]) {
        //ラインのデータ設定等
        //表示データ
        int[] swBox = wBox;
        int[] sfBox = fBox;

        //体重用リスト
        ArrayList<Entry> wData = new ArrayList<>();
        for (int i = 0; i < swBox.length; i++) {
            wData.add(new Entry(i, wBox[i], null, null));
        }
        //体脂肪用リスト
        ArrayList<Entry> fData = new ArrayList<>();
        for (int i = 0; i < sfBox.length; i++) {
            fData.add(new Entry(i, fBox[i], null, null));
        }

        //体重ラインの設定
        //ラベル名
        weights = new LineDataSet(wData, "体重");
        //線の色
        weights.setColor(Color.RED);
        //座標の色
        weights.setCircleColor(Color.WHITE);
        //座標の大きさ
        weights.setCircleRadius(3f);
        //線の太さ
        weights.setLineWidth(5f);
        //線の下を塗りつぶすか
        weights.setDrawFilled(false);
        //塗り潰したフィールドの色
        //weights.setFillColor(Color.RED);

        //体脂肪ラインの設定
        //ラベル名
        fats = new LineDataSet(fData, "体脂肪");
        //線の色
        fats.setColor(Color.BLUE);
        //座標の色
        fats.setCircleColor(Color.WHITE);
        //座標の大きさ
        fats.setCircleRadius(3f);
        fats.setLineWidth(5f);
        fats.setDrawFilled(false);
        //fats.setFillColor(Color.BLUE);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(weights);
        dataSets.add(fats);
        LineData lineData = new LineData(dataSets);

        return lineData;
    }

    public PieData setData(){
        String[] pieLabels = {"胸筋","背中","下半身","肩・腕","有酸素","オフ"};


        PieData pieData = new PieData();
        return pieData;
    }
}
