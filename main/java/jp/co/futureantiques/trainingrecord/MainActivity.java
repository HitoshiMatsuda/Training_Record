package jp.co.futureantiques.trainingrecord;

import android.os.Bundle;

public class MainActivity extends AbstractTrainingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*//RadioButtonから選択値を取得する
        //RadioGroupを紐付ける
        radioGroup = findViewById(R.id.radio_group);
        radioId = radioGroup.getCheckedRadioButtonId();
        //Checkが入ったRadioButtonを識別する
        switch (radioId) {
            case R.id.pectorals_radio:
                Log.i("pectorals_radio_checked", "胸筋が選択されました");
                numP = 1;
            case R.id.back_radio:
                Log.i("pectorals_radio_checked", "背筋が選択されました");
                numB = 1;
            case R.id.lower_radio:
                Log.i("pectorals_radio_checked", "下半身が選択されました");
                numL = 1;
            case R.id.shoulder_radio:
                Log.i("pectorals_radio_checked", "肩が選択されました");
                numS = 1;
            case R.id.run_radio:
                Log.i("pectorals_radio_checked", "有酸素が選択されました");
                numR = 1;
            case R.id.rest_radio:
                Log.i("pectorals_radio_checked", "オフ日が選択されました");
                numRest = 1;
            default:
                //RadioButtonをどれも選択しなかった場合
                Log.i("switch_default","どれも選択されませんでした");
                break;
        }*/
    }
}