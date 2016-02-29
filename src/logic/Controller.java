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
                    consoleWriter.write("Input path to directory");
                    String inputString = consoleReader.read();
                    index.indexedDirectory(inputString);
                    break;
                case ("2"):
                    consoleWriter.write("Input path to directory that you want recalc");
                    inputString = consoleReader.read();
                    index.indexedDirectory(inputString);
                    break;
                case ("0"):
                    view.buy();
                    return;
            }
        }

    }
}
