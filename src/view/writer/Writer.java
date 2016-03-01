package view.writer;

import java.io.Closeable;
import java.util.List;

/**
 * Created by igladush on 25.02.16.
 */
public interface Writer<T>  extends AutoCloseable,Closeable{
    void write(T... s);
    void write(List<T> s);
}
