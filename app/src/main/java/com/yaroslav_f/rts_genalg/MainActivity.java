package com.yaroslav_f.rts_genalg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

        boolean res = false;
        int lowest_iter = Integer.MAX_VALUE;
        int optimal_mut_rate = 0;
        int avr_iter;
        Individual solution_from_optimal_mr = null;
        for (int mutation_growth = 0; mutation_growth < 20; mutation_growth++) {
            avr_iter = 0;
            for (int i = 0; i < 3; i++) {
                res = netw.Train(
                        Integer.parseInt(editText_A.getText().toString()),
                        Integer.parseInt(editText_B.getText().toString()),
                        Integer.parseInt(editText_C.getText().toString()),
                        Integer.parseInt(editText_D.getText().toString()),
                        Integer.parseInt(editText_Y.getText().toString()),
                        mutation_growth
                );
                avr_iter += netw.iter;
            }
            if (lowest_iter > avr_iter/3) {
                lowest_iter = avr_iter;
                optimal_mut_rate = mutation_growth;
                solution_from_optimal_mr = netw.solution;
            }
        }

        textView_X1.setText(String.format("X1: %s", solution_from_optimal_mr.X1));
        textView_X2.setText(String.format("X2: %s", solution_from_optimal_mr.X2));
        textView_X3.setText(String.format("X3: %s", solution_from_optimal_mr.X3));
        textView_X4.setText(String.format("X4: %s", solution_from_optimal_mr.X4));
        if (res) {
            textView_result.setText(R.string.res_success);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Finished successfully")
                    .setMessage(String.format(
                            "Optimal mutation_rate: %s\nLowest number of iterations: %s",
                            optimal_mut_rate+10,
                            lowest_iter))
                    .setCancelable(true)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
        } else {
            textView_result.setText(R.string.res_failure);
        }
    }
}
