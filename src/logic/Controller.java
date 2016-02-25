package logic;

import view.View;
import view.reader.ConsoleReader;
import view.writer.ConsoleWriter;

/**
 * Created by igladush on 25.02.16.
 */
public class Controller {
    private View view;
    private ConsoleReader consoleReader;
    private ConsoleWriter consoleWriter;
    private String userAns;

    private TfCalcer tfCalcer;
    public Controller() {
        view = new View();
        consoleReader = new ConsoleReader();
        consoleWriter = new ConsoleWriter();
        tfCalcer=new TfCalcer();
    }

    public void run(){
            while(true){
                view.showMainMenu();
                userAns=consoleReader.read();

                switch (userAns){
                    case("1"):
                        consoleWriter.write("Input you word in format Path  Word");
                        String inputString=consoleReader.read();
                        consoleWriter.writeUserAnswer(tfCalcer.getTfIdf(inputString));
                        break;
                    case("0"):
                        view.buy();
                        return;
                }
            }

    }
}
