package view.reader;

import model.TfIdfFile;

import java.io.Closeable;
import java.util.List;

/**
 * Created by igladush on 25.02.16.
 */
public interface TfIdfReader extends  AutoCloseable,Closeable {
    List<TfIdfFile> read();
}
