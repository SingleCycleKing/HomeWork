package utils;
/**
 * Created by WZQ on 15-2-4.
 */

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import model.WordModel;

public class AnalyzeXml implements WordParser {

    @Override
    public List<WordModel> parse(InputStream inputStream) throws Exception {
        List<WordModel> mModels = null;
        WordModel mModel = null;
        XmlPullParser mParser = Xml.newPullParser();
        mParser.setInput(inputStream, "UTF-8");
        int eventType = mParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    mModels = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (mParser.getName().equals("item")) mModel = new WordModel();
                    else if (mParser.getName().equals("word")) {
                        eventType = mParser.next();
                        assert mModel != null;
                        mModel.setWord(mParser.getText());
                    } else if (mParser.getName().equals("trans")) {
                        eventType = mParser.next();
                        assert mModel != null;
                        mModel.setTranslation(mParser.getText());
                    } else if (mParser.getName().equals("phonetic")) {
                        eventType = mParser.next();
                        assert mModel != null;
                        mModel.setPhonetich(mParser.getText());
                    } else if (mParser.getName().equals("tags")) {
                        eventType = mParser.next();
                        assert mModel != null;
                        mModel.setTags(mParser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (mParser.getName().equals("item")) {
                        mModels.add(mModel);
                        mModel = null;
                    }
                    break;
            }
            eventType = mParser.next();
        }

        return mModels;
    }

    @Override
    public String serialize(List<WordModel> wordModels) throws Exception {
        return null;
    }
}
