package model;

/**
 * Created by igladush on 25.02.16.
 */
public class TfIdfWord {

    private String word;
    private double tf;
    private double idf;

    public double getTf() {
        return tf;
    }

    public double getIdf() {
        return idf;
    }

    public void setIdf(double idf) {
        if (idf < 0) {
            return;
        }
        this.idf = idf;
    }

    public double getTfIdf() {
        return tf * idf;
    }

    public TfIdfWord(String word, double tf, double idf) {
        this.word = word;
        this.tf = tf;
        this.idf = idf;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return word + " " + tf + " " + idf + " ";
    }
}
