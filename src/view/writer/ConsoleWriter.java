package view.writer;

import model.UserAnswerFormat;

import java.io.IOException;
import java.util.List;

/**
 * Created by igladush on 25.02.16.
 */
public class ConsoleWriter implements Writer<String> {
    @Override
    public void write(String... s) {
        for(String temp: s){
            System.out.println(temp);
        }
    }

    @Override
    public void write(List<String> s) {
        for(String string: s){
            System.out.println(s);
        }
    }

    public void writeUserAnswer(UserAnswerFormat... usAnswer){
        for(UserAnswerFormat temp: usAnswer){
            System.out.println(temp);
        }
    }

    @Override
    public void close()  {

    }
}
