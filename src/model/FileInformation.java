package model;


import java.io.File;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by igladush on 25.02.16.
 */
public class FileInformation {
    private final String ERROR_INDEX = "You indicated wrong index";
    private String path;
    private List<WordCount> wordsCount = new ArrayList<>();


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

    public int totalCount() {
        int answer = 0;
        for (WordCount wordCount : wordsCount) {
            answer += wordCount.getCount();
        }
        return answer;
    }
    public Collection<WordCount> getWords(){
        return Collections.unmodifiableCollection(wordsCount);
    }
    public int getDifferentWords() {
        return wordsCount.size();
    }

    public String getWord(int index) {
        if (index > wordsCount.size() || 0 > index) {
            throw new IllegalStateException(ERROR_INDEX);
        }
        return wordsCount.get(index).getWord();
    }

    public long containWord(String word) {
        for (WordCount dataOfWord : wordsCount) {
            if (dataOfWord.getWord().equals(word)) {
                return dataOfWord.getCount();
            }
        }
        return 0;
    }

    public void addWord(String word) {
        for (int i = 0; i < wordsCount.size(); ++i) {
            if (wordsCount.get(i).getWord().equals(word)) {
                wordsCount.get(i).incCount();
                return;
            }
        }
        wordsCount.add(new WordCount(word));
    }

    public double getTfWord(String word) {
        return containWord(word) / (totalCount() * 1.0);
    }
}
