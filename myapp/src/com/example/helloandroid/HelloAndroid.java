package com.example.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
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
        Toast.makeText(this.getApplicationContext(),
            "Run clicked!", Toast.LENGTH_SHORT).show();
    }
}
