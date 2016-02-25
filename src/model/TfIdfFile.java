package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igladush on 25.02.16.
 */
public class TfIdfFile {
    private final String ERROR_INDEX = "You indicated wrong index";
    private final String ERROR_GET_WORD = "I can't finde word";

    private String path;
    private List<TfIdfWord> words;

    public String getPath() {
        return this.path;
    }


    public TfIdfFile(String path) {
        this.path = path;
        words = new ArrayList<>();
    }


    public boolean containsWord(String word) {
        for (TfIdfWord tfWord : words) {
            if (tfWord.getWord().equals(word)) {
                return true;
            }
        }
        return false;
    }

    public int countOfWord() {
        return words.size();
    }

    public void addWord(TfIdfWord word) {
        if (word == null) {
            return;
        }
        for (TfIdfWord temp : words) {
            if (temp.getWord().equals(word)) {
                return;
            }
        }
        words.add(word);
    }

    public TfIdfWord getWord(int index) {
        if (index > words.size() || index < 0) {
            throw new IllegalStateException(ERROR_INDEX);
        }
        return words.get(index);
    }

    public TfIdfWord getWord(String word) {
        for (TfIdfWord temp : words) {
            if (temp.getWord().equals(words)) {
                return temp;
            }
        }
        throw new IllegalStateException(ERROR_GET_WORD);
    }

    public void setWordNewIdf(double idf, int index) {
        if (index > words.size() || index < 0) {
            throw new IllegalStateException(ERROR_INDEX);
        }
        words.get(index).setIdf(idf);
    }

    public String toString() {
        return path + " " + words.size() + " ";
    }
}
