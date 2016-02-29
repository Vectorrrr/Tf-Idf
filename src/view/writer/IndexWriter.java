package view.writer;

import model.IndexFile;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by igladush on 25.02.16.
 */
//todo rewrite implements writer
public class IndexWriter implements AutoCloseable,Closeable{
    private final String ERROR_WRITER = "I have exception when i write in your file";
    private final String ERROR_READ_PROPERTY="I can't read property";
    public final static String SEPARATOR = "========";

    private ConsoleWriter consoleWriter;

    public IndexWriter() {
        consoleWriter = new ConsoleWriter();
    }

    public void write(Collection<IndexFile> tfIdfFileList, String path) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            for (IndexFile temp : tfIdfFileList) {
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


    @Override
    public void close() throws IOException {

    }
}
