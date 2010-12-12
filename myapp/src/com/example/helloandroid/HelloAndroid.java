package com.example.helloandroid;

import java.io.StringWriter;
import java.io.PrintWriter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.SeekBar;
import android.view.View;
import android.util.Log;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;

public class HelloAndroid extends Activity
    implements SeekBar.OnSeekBarChangeListener
{
    SeekBar seekbar;
    EditText num1;
    EditText num2;
    TextView output;
    private static final String TAG = "HelloAndroid";

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
        final CharSequence code = num1.getText() + " + " + num2.getText();
        /*
        Toast.makeText(this.getApplicationContext(), code,
                Toast.LENGTH_SHORT).show();
        */
        try {
            JSONRPCClient client = JSONRPCClient.create("http://lab.femhub.org/async");
            String string = client.callString("RPC.Engine.init", "some_uuid");
            //this.output.setText("Output = " + string);
            string = client.callString("RPC.Engine.evaluate",
                    "some_uuid", code);
            this.output.setText("Output = " + string);
            /*
            double d = client.callDouble("pow", 4, 5);
            int i = client.callInt("add", 56, 25);
            */
        } catch (Throwable e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            this.output.setText("Error = " + sw.toString());
            Log.i(this.TAG, sw.toString());
        }
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        String text = "" + progress;
        this.num2.setText(text);
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
