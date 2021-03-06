package model;

import logic.Index;

/**
 * Created by igladush on 25.02.16.
 */
public class IndexWord {


    private String word;
    private double tf;
    private double idf;

    public void setIdf(double idf) {
        if (idf < 0) {
            return;
        }
        this.idf = idf;
    }

    public double getTfIdf() {
        return tf * idf;
    }


    public IndexWord(String word, double tf) {
        this.word = word;
        this.tf = tf;
    }
    public IndexWord(String word, double tf, double idf) {
        this(word,tf);
        this.idf = idf;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return word + " " + tf + " " + idf + " ";
    }

    @Override
    public boolean equals(Object o){
        if(o==null)
            return false;
        if(!(o instanceof IndexWord)){
            return false;
        }
        IndexWord s=(IndexWord) o;
        return s.getWord().equals(word);
    }
}
