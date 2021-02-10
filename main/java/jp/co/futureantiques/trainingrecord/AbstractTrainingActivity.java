package jp.co.futureantiques.trainingrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AbstractTrainingActivity extends AppCompatActivity {
    protected RadioGroup radioGroup;
    protected RadioButton radioButtonP;
    protected RadioButton radioButtonB;
    protected RadioButton radioButtonS;
    protected RadioButton radioButtonL;
    protected RadioButton radioButtonR;
    protected RadioButton radioButtonRest;
    protected int radioId;
    protected int numP;
    protected int numB;
    protected int numS;
    protected int numL;
    protected int numR;
    protected int numRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abstract_training);
    }
}