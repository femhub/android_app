package com.example.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.SeekBar;
import android.view.View;

public class HelloAndroid extends Activity
    implements SeekBar.OnSeekBarChangeListener
{
    SeekBar seekbar;
    EditText num1;
    EditText num2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        this.seekbar = (SeekBar) this.findViewById(R.id.seekbar);
        this.seekbar.setOnSeekBarChangeListener(this);
        this.seekbar.setMax(100);
        this.num1 = (EditText) this.findViewById(R.id.num1);
        this.num2 = (EditText) this.findViewById(R.id.num2);

        final Button button = (Button) this.findViewById(R.id.run);
        final HelloAndroid self = this;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                self.run();
            }
        });
    }

    public void run() {
        final CharSequence t = "num1 = " + num1.getText() + "; num2 = " +
            num2.getText() + ";";
        Toast.makeText(this.getApplicationContext(), t,
                Toast.LENGTH_SHORT).show();
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        this.num2.setText(progress + " " + fromTouch);
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        this.num1.setText("start");
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        this.num1.setText("stop");
    }
}
