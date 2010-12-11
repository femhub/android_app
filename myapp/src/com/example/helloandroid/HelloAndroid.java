package com.example.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.SeekBar;
import android.view.View;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;

public class HelloAndroid extends Activity
    implements SeekBar.OnSeekBarChangeListener
{
    SeekBar seekbar;
    EditText num1;
    EditText num2;
    TextView output;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        this.seekbar = (SeekBar) this.findViewById(R.id.seekbar);
        this.seekbar.setOnSeekBarChangeListener(this);
        this.seekbar.setMax(100);
        this.num1 = (EditText) this.findViewById(R.id.num1);
        this.num2 = (EditText) this.findViewById(R.id.num2);
        this.output = (TextView) this.findViewById(R.id.output);

        final Button button = (Button) this.findViewById(R.id.run);
        final HelloAndroid self = this;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                self.run();
            }
        });
    }

    public void run() {
        final CharSequence t = "XX: num1 = " + num1.getText() + "; num2 = " +
            num2.getText() + ";";
        Toast.makeText(this.getApplicationContext(), t,
                Toast.LENGTH_SHORT).show();
        JSONRPCClient client = JSONRPCClient.create("http://lab.femhub.org/async");
        try
        {
          String string = client.callString("init", "some_uuid");
          double d = client.callDouble("pow", 4, 5);
          int i = client.callInt("add", 56, 25);
        }
        catch (JSONRPCException e)
        {
          //this.output.setText(e.getStackTrace());
          this.output.setText(e.toString());
        }
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
