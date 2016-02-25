package view.reader;

import java.util.Scanner;

/**
 * Created by igladush on 25.02.16.
 */
public class ConsoleReader implements Reader {
    private Scanner scanner;

    public ConsoleReader() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
