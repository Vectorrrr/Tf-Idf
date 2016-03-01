package view.reader;

import java.io.Closeable;

/**
 * Created by igladush on 25.02.16.
 */
public interface Reader<T>  extends AutoCloseable,Closeable{
    T read();
}
