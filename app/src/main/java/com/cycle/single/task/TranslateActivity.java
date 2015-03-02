package com.cycle.single.task;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import utils.HTTPClient;


public class TranslateActivity extends Activity {
    @InjectView(R.id.translate_search)
    SearchView searchView;
    @InjectView(R.id.translate_text)
    TextView textView;
    private String result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("finish translate");
        registerReceiver(receiver, intentFilter);
        ButterKnife.inject(this);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        translate(query);
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void translate(String string) {
        try {
            result = HTTPClient.AnalyzingOfJson("http://fanyi.youdao.com/openapi.do" + "?keyfrom=" + "HomeworkHuster" + "&key=" + "1377657333" + "&type=" + "data" + "&doctype="
                    + "json" + "&type=" + "data" + "&version=" + "1.1" + "&q=" + string.trim(), TranslateActivity.this);
            Intent intent = new Intent();
            intent.setAction("finish translate");
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("finish translate")) textView.setText(result);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}