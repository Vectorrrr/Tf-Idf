package logic;

import model.UserAnswerFormat;
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
    private Index index;
    private TfCalcer tfCalcer;

    public Controller() {
        view = new View();
        consoleReader = new ConsoleReader();
        consoleWriter = new ConsoleWriter();
        tfCalcer = new TfCalcer();
        index=new Index();
    }

    public void run() {
        while (true) {
            view.showMainMenu();
            String userAns = consoleReader.read();

            switch (userAns) {
                case ("1"):
                    consoleWriter.write("Input you word in format Path  Word");
                    String inputString = consoleReader.read();
                    for (UserAnswerFormat usAns : tfCalcer.getTfIdf(inputString)) {
                        consoleWriter.write(usAns.toString());
                    }
                    break;
                case ("0"):
                    view.buy();
                    return;
            }
        }

    }
}
