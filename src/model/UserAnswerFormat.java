package model;

/**
 * Created by igladush on 25.02.16.
 */
public class UserAnswerFormat implements Comparable<UserAnswerFormat>{
    private String path;
    private TfIdfWord word;

    public String getPath() {
        return path;
    }

    public TfIdfWord getWord(){
        return this.word;
    }

    public UserAnswerFormat(String path, TfIdfWord word) {
        this.path = path;
        this.word = word;
    }

    @Override
    public String toString() {
        return path + " " + word.getTfIdf();
    }

    public double getTfIdf() {
        return word.getTfIdf();
    }

    @Override
    public int compareTo(UserAnswerFormat o) {
        if(o==null || o.getTfIdf()<this.getTfIdf()){
            return 1;
        }
        if(o.getTfIdf()<this.getTfIdf()){
            return 0;
        }
        return -1;
    }
}
