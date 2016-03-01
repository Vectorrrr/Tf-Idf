package model;

import logic.Index;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by van on 01.03.16.
 */
public class IndexDirectory {
    private Collection<IndexFile> indexFiles = new ArrayList<>();
    private String path;

    public IndexDirectory(String path) {
        this.path = path;
    }

    public File getFile() {
        return new File(path);
    }

    public int getCountFiles() {
        return indexFiles.size();
    }

    public synchronized Collection<IndexFile> getFiles() {
        return Collections.unmodifiableCollection(indexFiles);
    }

    public synchronized void addFile(IndexFile indexFile) {
        this.indexFiles.add(indexFile);
    }
}
