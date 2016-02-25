package model;

import java.io.File;

/**
 * Created by igladush on 25.02.16.
 */
public class DataOfWord {

    private String word;
    private long count;

    public String getWord() {
        return word;
    }

    public long getCount() {
        return count;
    }
    public void incCount(){
        this.count++;
    }
    //for Serialization in the future
    public DataOfWord() {
    }

    public DataOfWord(String word){
        this.word=word;
        this.count=1;
    }

    public DataOfWord(String word, long count) {
        this.word = word;
        this.count = count;
    }

    @Override
    public String toString(){
        return word+" "+count+" ";
    }


}
