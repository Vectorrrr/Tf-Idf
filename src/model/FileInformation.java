package model;


import java.io.File;
import java.io.FileInputStream;
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
    public String getPath(){
        return this.path;
    }
    public FileInformation(String path) {
        this.path = path;
    }
    public FileInformation(File file){
        this.path=file.getAbsolutePath();
    }

    //return 0 if word exist else return count of word

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

    public int getCountWords() {
        return dataOfWords.size();
    }

    public double getTfWord(String word) {
        return containWord(word) / (dataOfWords.size()*1.0);
    }
}
