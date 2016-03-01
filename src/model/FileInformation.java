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

    public void addWord(String word) {
        for (int i = 0; i < wordsCount.size(); ++i) {
            if (wordsCount.get(i).getWord().equals(word)) {
                wordsCount.get(i).incCount();
                return;
            }
        }
        wordsCount.add(new WordCount(word));
    }
}
