package logic;

import model.*;
import view.reader.FileReader;
import view.writer.ConsoleWriter;
import view.writer.IndexWriter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.Thread.sleep;
import static java.util.Collections.synchronizedCollection;

/**
 * Created by igladush on 29.02.16.
 */

/*
* class for indexed directory
* in the indexed take part only *.txt files
*
* */
public class Index {
    private final String ERROR_CREATE = "I can't create file information";
    private final String ERROR_NOTDIRECTORY = "You want index file, not directory";
    private final String ERROR_EXIST = "The directory dosen't exist";


    private ConsoleWriter consoleWriter;

    public Index() {
        consoleWriter = new ConsoleWriter();
    }

    //todo it's good practise create space line with logic group in method
    public void indexedDirectory(String path) {
        File directory = new File(path);
        checkCorrectFile(directory);

        IndexDirectory indexDirectory=new IndexDirectory(path);

        createPreindex(indexDirectory);

        //todo haw do join
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       calcIdf(indexDirectory);
        saveIndex(indexDirectory.getFiles(), directory.getAbsolutePath());
    }


    private void createPreindex(IndexDirectory indexDirectory) {
        for (File file : getFiles(indexDirectory.getFile())) {
            if (file.canRead()) {
                new Thread(new FileGetter(file,indexDirectory)).start();
            }
        }
    }

    private void checkCorrectFile(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException(ERROR_EXIST);
        }
        if (file.isFile()) {
            throw new IllegalArgumentException(ERROR_NOTDIRECTORY);
        }
    }

    private List<File> getFiles(File f) {
        FilenameFilter only = new FilenameFilter() {
            private String ext = ".txt";

            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(ext);
            }
        };
        List<File> answer = new ArrayList<>();
        for (File file : f.listFiles(only)) {
            if (file.canRead()) {
                answer.add(file);
            }
        }
        return answer;
    }

    private void calcIdf(IndexDirectory directory) {
        int countFiles = directory.getCountFiles();
        Collection<IndexFile> dir=directory.getFiles();

        for (IndexFile file :dir) {
            for (IndexWord word : file.getWords()) {
                double count = 0;
                for (IndexFile temp : dir) {
                    if (temp.containsWord(word.getWord())) {
                        count++;
                    }
                }
                word.setIdf(countFiles / count);
            }
        }


    }

    private void saveIndex(Collection<IndexFile> files, String path) {
        try (IndexWriter indexWriter = new IndexWriter()) {
            indexWriter.write(files, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class FileGetter implements Runnable {

        private File file;
        private IndexDirectory indexDirectory;

        public FileGetter(File file, IndexDirectory indexDirectory) {
            this.file = file;
            this.indexDirectory=indexDirectory;
        }

        //add a prepack TfIdfFile, this file doesn't contains Idf
        @Override
        public void run() {
            prepackFile(createFileInformation());
        }


        private FileInformation createFileInformation() {
            System.out.println("Read file....." + file.getAbsolutePath());

            FileInformation fileInformation = new FileInformation(file.getAbsolutePath());

            try (FileReader fileReader = new FileReader(file)) {
                while (fileReader.canRead()) {

                    String[] temp = fileReader.read().split("[,. /]");
                    for (String s : temp) {
                        if (s.length() > 0) {
                            System.out.println("Add word " + s);
                            fileInformation.addWord(s);
                        }
                    }
                }
            } catch (IOException e) {
                consoleWriter.write(ERROR_CREATE);
            }
            return fileInformation;
        }

        private void prepackFile(FileInformation fileInformation) {
            IndexFile file = new IndexFile(fileInformation.getPath());

            for (WordCount wordCount : fileInformation.getWords()) {
                double tf = (1.0 * wordCount.getCount()) / fileInformation.totalCount();
                IndexWord word = new IndexWord(wordCount.getWord(), tf);
                file.addWord(word);
            }
            indexDirectory.addFile(file);
        }
    }
}


