package view.writer;

import model.UserAnswerFormat;

/**
 * Created by igladush on 25.02.16.
 */
public class ConsoleWriter implements Writer {
    @Override
    public void write(String... s) {
        for(String temp: s){
            System.out.println(temp);
        }
    }

    public void writeUserAnswer(UserAnswerFormat... usAnswer){
        for(UserAnswerFormat temp: usAnswer){
            System.out.println(temp);
        }
    }
}
