package utils;
/**
 * Created by WZQ on 15-2-4.
 */

import java.io.InputStream;
import java.util.List;

import model.WordModel;

public interface WordParser {
    public abstract List<WordModel> parse(InputStream inputStream) throws Exception;

    public abstract String serialize(List<WordModel> wordModels) throws Exception;
}
