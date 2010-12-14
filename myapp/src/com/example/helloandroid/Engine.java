package com.example.helloandroid;

import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.UUID;
import org.json.JSONObject;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;

public class Engine extends IntentService {
    String uuid = null;
    private static final String TAG = "HelloAndroid";

    public Engine() {
        super("Engine");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        try {
            String code = (String) intent.getExtras().get("code");
            JSONRPCClient client = JSONRPCClient.create("http://lab.femhub.org/async");
            String string;
            if (this.uuid == null) {
                this.uuid = UUID.randomUUID().toString();
                //this.output.setText("Initializing the engine...");
                string = client.callString("RPC.Engine.init", this.uuid);
            }
            //this.output.setText("Calculating...");
            JSONObject result = client.callJSONObject("RPC.Engine.evaluate",
                    this.uuid, code);
            String out = result.getString("out");
            //this.output.setText("Source: " + code + "\nOutput: " + out);
            Log.i(this.TAG, "Output: " + out);
        } catch (Throwable e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            //this.output.setText("Error = " + sw.toString());
            Log.i(this.TAG, sw.toString());
        }
    }
}
