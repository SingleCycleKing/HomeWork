package model;

/**
 * Created by WZQ on 15-2-4.
 */
public class WordModel {
    private String word;
    private String translation;
    private String phonetic;
    private String tags;

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

    public void setPhonetich(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }
}
