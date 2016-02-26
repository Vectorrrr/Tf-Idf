package model;


import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igladush on 25.02.16.
 */
public class FileInformation {
    private final String ERROR_INDEX = "You indicated wrong index";
    private String path;
    private List<DataOfWord> dataOfWords;

    {
        dataOfWords = new ArrayList<>();
    }

    public String getPath() {
        return this.path;
    }

    public FileInformation(String path) {
        this.path = path;
    }

    public FileInformation(File file) {
        this.path = file.getAbsolutePath();
    }

    //return 0 if word exist else return count of word

    public int getCountWords() {
        int answer = 0;
        for (DataOfWord dataOfWord : dataOfWords) {
            answer += dataOfWord.getCount();
        }
        return answer;
    }

    public int getDifferentWords() {
        return dataOfWords.size();
    }

    public String getWord(int index) {
        if (index > dataOfWords.size() || 0 > index) {
            throw new IllegalStateException(ERROR_INDEX);
        }
        return dataOfWords.get(index).getWord();
    }

    public long containWord(String word) {
        for (DataOfWord dataOfWord : dataOfWords) {
            if (dataOfWord.getWord().equals(word)) {
                return dataOfWord.getCount();
            }
        }
        return 0;
    }

    public void addWord(String word) {
        for (int i = 0; i < dataOfWords.size(); ++i) {
            if (dataOfWords.get(i).getWord().equals(word)) {
                dataOfWords.get(i).incCount();
                return;
            }
        }
        dataOfWords.add(new DataOfWord(word));
    }

    public double getTfWord(String word) {
        return containWord(word) / (getCountWords() * 1.0);
    }
}
