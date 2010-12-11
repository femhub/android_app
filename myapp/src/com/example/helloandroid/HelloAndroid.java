package com.example.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.view.View;

public class HelloAndroid extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
        final Button button = (Button) this.findViewById(R.id.run);
        final HelloAndroid self = this;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                self.run();
            }
        });
    }

    public void run()
    {
        final EditText num1 = (EditText) this.findViewById(R.id.num1);
        final EditText num2 = (EditText) this.findViewById(R.id.num2);
        final CharSequence t = "num1 = " + num1.getText() + "; num2 = " +
            num2.getText() + ";";
        Toast.makeText(this.getApplicationContext(), t,
                Toast.LENGTH_SHORT).show();
    }
}
