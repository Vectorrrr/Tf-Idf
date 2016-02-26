package view;

import view.writer.ConsoleWriter;

/**
 * Created by igladush on 25.02.16.
 */
public class View {
    private ConsoleWriter consoleWriter;

    public View() {
        consoleWriter = new ConsoleWriter();
    }

    public void showMainMenu() {
        consoleWriter.write("If you want calc tf-idf type 1");
        consoleWriter.write("If you want exit type 0");

    }

    public void buy() {
        consoleWriter.write("Good luck, bro!)");
    }
}
