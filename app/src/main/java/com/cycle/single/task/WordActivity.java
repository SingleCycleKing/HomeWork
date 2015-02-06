package com.cycle.single.task;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class WordActivity extends ActionBarActivity {
    @InjectView(R.id.word)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        ButterKnife.inject(this);


        textView.setText(
                "Translation : " + getIntent().getStringExtra("translation") + "\n\n"
                        + "Phonetic : " + getIntent().getStringExtra("phonetic") + "\n\n"
                        + "Tag : " + getIntent().getStringExtra("tag") + "\n\n");
    }


}
