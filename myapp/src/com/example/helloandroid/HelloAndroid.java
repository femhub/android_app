package com.example.helloandroid;

import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.UUID;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.RadioButton;
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
    CheckBox live;
    RadioButton rb1, rb2, rb3;
    String uuid;
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
        this.live = (CheckBox) this.findViewById(R.id.live);
        this.rb1 = (RadioButton) this.findViewById(R.id.rb1);
        this.rb2 = (RadioButton) this.findViewById(R.id.rb2);
        this.rb3 = (RadioButton) this.findViewById(R.id.rb3);
        this.rb2.toggle();
        this.uuid = null;

        final Button button = (Button) this.findViewById(R.id.run);
        final HelloAndroid self = this;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                self.run();
            }
        });
    }

    public void run() {
        // FIXME: the gui updates will not show until run() finishes, as those
        // have to be done from a different thread, or use events somehow, just
        // like in javascript the browser
        final String op;
        if (this.rb1.isChecked())
            op = " + ";
        else if (this.rb2.isChecked())
            op = " - ";
        else
            op = " * ";
        final String code = "from sympy import var, factor\nvar('x')\nfactor(x**" + num1.getText() + op + num2.getText() + ")";
        /*
        Toast.makeText(this.getApplicationContext(), code,
                Toast.LENGTH_SHORT).show();
        */
        try {
            JSONRPCClient client = JSONRPCClient.create("http://lab.femhub.org/async");
            String string;
            if (this.uuid == null) {
                this.uuid = UUID.randomUUID().toString();
                this.output.setText("Initializing the engine...");
                string = client.callString("RPC.Engine.init", this.uuid);
            }
            this.output.setText("Calculating...");
            JSONObject result = client.callJSONObject("RPC.Engine.evaluate",
                    this.uuid, code);
            String out = result.getString("out");
            this.output.setText("Source: " + code + "\nOutput: " + out);
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
        this.num1.setText(text);
        if (this.live.isChecked()) {
            this.run();
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
