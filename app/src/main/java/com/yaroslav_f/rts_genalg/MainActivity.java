package com.yaroslav_f.rts_genalg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private GenAlgNetwork netw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        netw = new GenAlgNetwork();
    }

    public void train(View view) {
        EditText editText_A = findViewById(R.id.eT_A);
        EditText editText_B = findViewById(R.id.eT_B);
        EditText editText_C = findViewById(R.id.eT_C);
        EditText editText_D = findViewById(R.id.eT_D);
        EditText editText_Y = findViewById(R.id.eT_Y);
        TextView textView_X1 = findViewById(R.id.tVx1);
        TextView textView_X2 = findViewById(R.id.tVx2);
        TextView textView_X3 = findViewById(R.id.tVx3);
        TextView textView_X4 = findViewById(R.id.tVx4);
        TextView textView_result = findViewById(R.id.tV_result);

        boolean res = netw.Train(
                Integer.parseInt(editText_A.getText().toString()),
                Integer.parseInt(editText_B.getText().toString()),
                Integer.parseInt(editText_C.getText().toString()),
                Integer.parseInt(editText_D.getText().toString()),
                Integer.parseInt(editText_Y.getText().toString())
        );

        textView_X1.setText(String.format("X1: %s", netw.solution.X1));
        textView_X2.setText(String.format("X2: %s", netw.solution.X2));
        textView_X3.setText(String.format("X3: %s", netw.solution.X3));
        textView_X4.setText(String.format("X4: %s", netw.solution.X4));
        if (res)
            textView_result.setText(R.string.res_success);
        else
            textView_result.setText(R.string.res_failure);
    }
}
