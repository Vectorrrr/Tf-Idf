package logic;

import model.FileInformation;
import model.IndexFile;
import model.IndexWord;
import model.WordCount;
import view.reader.FileReader;
import view.writer.ConsoleWriter;
import view.writer.IndexWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private final String ERROR_NOTDIRECTORY="You want index file, not directory";
    private final String ERROR_EXIST="The directory dosen't exist";
    //todo delete this field
    private Collection<IndexFile> indexDirectory = synchronizedCollection(new ArrayList<IndexFile>());


    private ConsoleWriter consoleWriter;

    public Index() {
        consoleWriter = new ConsoleWriter();
    }

    public void indexedDirectory(String path) {
        File directory=new File(path);
        checkCorrectFile(directory);

        

    }
    private void checkCorrectFile(File file){
        if(!file.exists()){
            throw new IllegalArgumentException(ERROR_EXIST);
        }
        if(file.isFile()){
            throw new IllegalArgumentException(ERROR_NOTDIRECTORY);
        }
    }
    private List<File> createListFiles(File f){

        for(File file)
    }

    private void calcIdf(Collection<IndexFile> files) {
        int countFiles = files.size();
        for (IndexFile file : files) {
            for (IndexWord word : file.getWords()) {
                double count = 0;
                for (IndexFile temp : files) {
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

        public FileGetter(File file) {
            this.file = file;
        }

        //add a prepack TfIdfFile, this file doesn't contains Idf
        @Override
        public void run() {
            .prepackFile(createFileInformation());
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
            indexDirectory.add(file);
        }
    }
}


