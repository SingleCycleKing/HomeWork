package com.cycle.single.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.InputStream;
import java.util.ArrayList;

import adapter.WordsAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import model.WordModel;
import utils.AnalyzeXml;


public class MainActivity extends ActionBarActivity {
    private ArrayList<String> words = new ArrayList<>();
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<String> translations = new ArrayList<>();
    private ArrayList<String> phonetics = new ArrayList<>();
    private ArrayList<WordModel> wordModels;
    @InjectView(R.id.wordList)
    ListView wordListView;
    @InjectView(R.id.search_view)
    SearchView searchView;

    @OnClick(R.id.translate)
    void translate() {
        startActivity(new Intent(MainActivity.this, TranslateActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();
    }

    private void init() {
        translate();
        try {
            InputStream inputStream = this.getAssets().open("cet4.xml");
            AnalyzeXml analyzeXml = new AnalyzeXml();
            wordModels = (ArrayList<WordModel>) analyzeXml.parse(inputStream);
            for (WordModel mModel : wordModels) {
                words.add(mModel.getWord());
                tags.add(mModel.getTags());
                translations.add(mModel.getTranslation());
                phonetics.add(mModel.getPhonetic());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        WordsAdapter adapter = new WordsAdapter(this, words, tags);
        wordListView.setAdapter(adapter);
        wordListView.setTextFilterEnabled(true);

        wordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, WordActivity.class);
                intent.putExtra("name", words.get(position));
                intent.putExtra("tag", tags.get(position));
                intent.putExtra("phonetic", phonetics.get(position));
                intent.putExtra("translation", translations.get(position));
                startActivity(intent);
            }
        });

        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    words.clear();
                    tags.clear();
                    phonetics.clear();
                    translations.clear();
                    for (WordModel mModel : wordModels) {
                        if (mModel.getWord().equals(query)) {
                            words.add(mModel.getWord());
                            tags.add(mModel.getTags());
                            translations.add(mModel.getTranslation());
                            phonetics.add(mModel.getPhonetic());
                        }
                    }
                    wordListView.setAdapter(new WordsAdapter(MainActivity.this, words, tags));
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    words.clear();
                    tags.clear();
                    phonetics.clear();
                    translations.clear();
                    for (WordModel mModel : wordModels) {
                        words.add(mModel.getWord());
                        tags.add(mModel.getTags());
                        translations.add(mModel.getTranslation());
                        phonetics.add(mModel.getPhonetic());
                    }
                    wordListView.setAdapter(new WordsAdapter(MainActivity.this, words, tags));
                }
                return true;
            }
        });
    }
}
