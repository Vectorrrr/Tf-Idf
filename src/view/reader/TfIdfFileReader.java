package view.reader;

import model.DataOfWord;
import model.TfIdfFile;
import model.TfIdfWord;
import view.writer.ConsoleWriter;
import view.writer.TfFileWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igladush on 25.02.16.
 */

public class TfIdfFileReader implements TfIdfReader {
    private final String EMPTY_STRING = "";
    private final String ERROR_READ = "";

    private ConsoleWriter consoleWriter;
    private BufferedReader bufferedReader;
    private String nextLine;

    public TfIdfFileReader(String path) throws IOException {
        consoleWriter = new ConsoleWriter();
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        nextLine = bufferedReader.readLine();
    }

    public TfIdfFileReader(File file) throws IOException {
        consoleWriter = new ConsoleWriter();
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        nextLine = bufferedReader.readLine();
    }

    @Override
    public List<TfIdfFile> read() {
        List<TfIdfFile> answer = new ArrayList<>();
        TfIdfFile temp = null;
        while (canRead()) {
            String[] input = nextLine.split(" ");
            if (input.length == 2) {
                temp = new TfIdfFile(input[1]);
                answer.add(temp);
            } else {
                String word = input[0];
                double tf = Double.parseDouble(input[1]);
                double idf = Double.parseDouble(input[2]);
                temp.addWord(new TfIdfWord(word, tf, idf));
            }
            getNextLine();
        }
        return answer;
    }

    public boolean canRead() {
        return !TfFileWriter.SEPARATOR.equals(nextLine);
    }

    private void getNextLine() {
        try {
            nextLine = bufferedReader.readLine();
        } catch (IOException e) {
            consoleWriter.write(ERROR_READ);
        }
    }


    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }

}