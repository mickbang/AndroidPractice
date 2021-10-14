package com.lq.websocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hosopy.actioncable.ActionCable;
import com.hosopy.actioncable.ActionCableException;
import com.hosopy.actioncable.Channel;
import com.hosopy.actioncable.Connection;
import com.hosopy.actioncable.Consumer;
import com.hosopy.actioncable.Subscription;
import com.squareup.okhttp.OkHttpClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class JavaCableActivity extends AppCompatActivity {
    Button _startBtn;
    TextView _contentText;
    String _url = "wss://solarbrain.client.com.au/cable";

    public static void start(Context context){
        context.startActivity(new Intent(context,JavaCableActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_cable);
        _startBtn = findViewById(R.id.startBtn);
        _contentText = findViewById(R.id.contentText);

        _startBtn.setOnClickListener(v -> {
            _start();
        });
    }

    private void _start() {
        // 1. Setup
        URI uri = null;
        try {
            uri = new URI(_url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Consumer.Options options = new Consumer.Options();
//        options.hostnameVerifier = new RxUtils.TrustAllHostnameVerifier();
        options.reconnection = true;

        Map<String, String> headers = new HashMap();
        headers.put("X-API-EMAIL", "emily.zheng@client.com.au");
        headers.put("X-API-TOKEN", "oHvng7XbyXVzktZJ1htz");
        options.headers = headers;
        Consumer consumer = ActionCable.createConsumer(uri, options);

// 2. Create subscription
        Channel appearanceChannel = new Channel("SignInChannel");
        Subscription subscription = consumer.getSubscriptions().create(appearanceChannel);

        subscription
                .onConnected(new Subscription.ConnectedCallback() {
                    @Override
                    public void call() {
                        // Called when the subscription has been successfully completed
                        outPut("连接成功");
                    }
                }).onRejected(new Subscription.RejectedCallback() {
            @Override
            public void call() {
                // Called when the subscription is rejected by the server
                outPut("连接被拒绝");
            }
        }).onReceived(new Subscription.ReceivedCallback() {
            @Override
            public void call(JsonElement data) {
                // Called when the subscription receives data from the server
                outPut("onText:" + (data.toString()));
            }
        }).onDisconnected(new Subscription.DisconnectedCallback() {
            @Override
            public void call() {
                // Called when the subscription has been closed
                outPut("onClose:" );
            }
        }).onFailed(new Subscription.FailedCallback() {
            @Override
            public void call(ActionCableException e) {
                // Called when the subscription encounters any error
                outPut("onFailed:" + e.getMessage());

            }
        });

// 3. Establish connection
        consumer.connect();
// 4. Perform any action
//        subscription.perform("away");

// 5. Perform any action using JsonObject(GSON)
//        JsonObject params = new JsonObject();
//        params.addProperty("foo", "bar");
//        subscription.perform("appear", params);
    }


    private void outPut(String text) {
        runOnUiThread(() -> _contentText.setText(_contentText.getText() + "\n\n" + text));
    }
}