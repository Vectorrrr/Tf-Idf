package model;

/**
 * Created by igladush on 25.02.16.
 */
public class WordCount {

    private String word;
    private long count;

    public String getWord() {
        return word;
    }

    public long getCount() {
        return count;
    }

    public void incCount() {
        this.count++;
    }

    public WordCount(String word) {
        this.word = word;
        this.count = 1;
    }

    @Override
    public String toString() {
        return word + " " + count + " ";
    }

    @Override
    public boolean equals(Object o){
        if(o==null)
            return false;

        if(!(o instanceof String)){
            return false;
        }
        String s=(String)o;

        return s.equals(word);
    }

}
