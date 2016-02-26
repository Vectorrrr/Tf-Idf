package view.writer;

import model.TfIdfFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by igladush on 25.02.16.
 */
public class TfFileWriter {
    private final String ERROR_WRITER = "I have exception when i write in your file";
    public final static String SEPARATOR = "========";

    private ConsoleWriter consoleWriter;

    public TfFileWriter() {
        consoleWriter = new ConsoleWriter();
    }

    public void write(List<TfIdfFile> tfIdfFileList, String path) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            for (TfIdfFile temp : tfIdfFileList) {
                fileOutputStream.write(temp.toString().getBytes());
                fileOutputStream.write(System.getProperty("line.separator").getBytes());
                for (int i = 0; i < temp.countOfWord(); i++) {
                    fileOutputStream.write(temp.getWord(i).toString().getBytes());
                    fileOutputStream.write(System.getProperty("line.separator").getBytes());
                }
            }
            fileOutputStream.write(SEPARATOR.getBytes());
        } catch (IOException e) {
            consoleWriter.write(ERROR_WRITER);
        }
    }
}
