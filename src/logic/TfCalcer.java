package logic;


import model.FileInformation;
import model.TfIdfFile;
import model.TfIdfWord;
import model.UserAnswerFormat;
import view.reader.FileReader;
import view.reader.TfIdfFileReader;
import view.writer.ConsoleWriter;
import view.writer.TfFileWriter;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by igladush on 25.02.16.
 */

public class TfCalcer {
    private final String ERROR_CREATE = "I can't create file information";
    private final String ERROR_READ = "I can't read file";
    private final String ERROR_FORMAT_INPUT_DATA = "You input incorrect string";
    private final String EMPTY_STRING = "";
    private final String NAME_TF_IDF_FILE = "tf-idf.file";

    private List<FileInformation> listOfFiles;
    private ConsoleWriter consoleWriter;
    private List<TfIdfFile> tfIdfDirectory;
    private TfFileWriter tfFileWriter;
    private String path;
    private String word;

    public TfCalcer() {
        consoleWriter = new ConsoleWriter();

    }

    public List<UserAnswerFormat> getTfIdf(String s) {
        createPath(s);

        if (!checkPath(path)) {
            return new ArrayList<>();
        }

        File workDirectory = new File(path);
        clearList();

        if (!checkMainFile(workDirectory)) {
            createTfIdfForDirectory(workDirectory);
            tfFileWriter.write(tfIdfDirectory, workDirectory + "/" + NAME_TF_IDF_FILE);
        } else {
            try (TfIdfFileReader tfReader = new TfIdfFileReader(workDirectory + "/" + NAME_TF_IDF_FILE)) {
                tfIdfDirectory = tfReader.read();

            } catch (IOException e) {
                consoleWriter.write(ERROR_READ);
            }
            if (addNewFile(workDirectory)) {
                recalcIdf();
                tfFileWriter.write(tfIdfDirectory, workDirectory + "/" + NAME_TF_IDF_FILE);
            }

        }


        List<UserAnswerFormat> answer = new ArrayList<>();
        for (TfIdfFile tfFile : tfIdfDirectory) {
            if (tfFile.containsWord(word)) {
                answer.add(new UserAnswerFormat(tfFile.getPath(), tfFile.getWord(word)));
            }
        }
        Collections.sort(answer);
        return answer;

    }

    public void createPath(String s) {
        String[] splitInput = s.split(" ");
        if (splitInput.length != 2) {
            throw new IllegalArgumentException(ERROR_FORMAT_INPUT_DATA);
        }

        path = splitInput[0];
        word = splitInput[1];
    }

    private boolean addNewFile(File workDirectory) {
        boolean containsNewFile = false;
        for (File f : workDirectory.listFiles()) {
            if (f.isDirectory()
                    || f.getAbsolutePath().equals(workDirectory + "/" + NAME_TF_IDF_FILE)
                    || f.getAbsolutePath().endsWith("~")) {
                continue;
            }
            boolean contains = false;
            for (TfIdfFile tf : tfIdfDirectory) {
                if (tf.getPath().equals(f.getAbsolutePath())) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                FileInformation temp =  new FileGetter(f).createFileInformation();
                TfIdfFile tfFile = new TfIdfFile(temp.getPath());

                for (int i = 0; i < temp.getDifferentWords(); ++i) {
                    String word = temp.getWord(i);
                    double tf = temp.getTfWord(word);

                    //we don't calc idf because we'll do recalc
                    tfFile.addWord(new TfIdfWord(word, tf, 0));
                }
                tfIdfDirectory.add(tfFile);
                containsNewFile = true;

            }
        }
        return containsNewFile;
    }

    private boolean checkPath(String path) {
        return new File(path).exists();

    }

    private boolean checkMainFile(File workDirectory) {
        for (String s : workDirectory.list()) {
            if (s.equals(NAME_TF_IDF_FILE)) {
                return true;
            }
        }
        return false;
    }

    private void createTfIdfForDirectory(File workDirectory) {
        getFileInformations(workDirectory);

        for (FileInformation fileInformation : listOfFiles) {
            TfIdfFile temp = new TfIdfFile(fileInformation.getPath());
            for (int i = 0; i < fileInformation.getDifferentWords(); ++i) {
                String word = fileInformation.getWord(i);
                double tf = fileInformation.getTfWord(word);
                double idf = getIdfWord(word);
                temp.addWord(new TfIdfWord(word, tf, idf));
            }
            tfIdfDirectory.add(temp);
        }
    }

    private void getFileInformations(File workDirectory) {
        for (File s : workDirectory.listFiles()) {
            //todo use FileFilter
            if (s.isDirectory() ||
                    s.getAbsolutePath().endsWith("~") ||
                    s.getAbsolutePath().equals(workDirectory.getAbsolutePath() + "/" + NAME_TF_IDF_FILE)) {
                continue;
            }
            new Thread(new FileGetter(s)).start();
        }
    }



    private double getIdfWord(String word) {
        double count = 0;
        for (FileInformation fileInformation : listOfFiles) {
            if (fileInformation.containWord(word) != 0) {
                count++;
            }
        }

        return listOfFiles.size() / (count);
    }

    private double getNewIdfWord(String word) {
        double count = 0;
        for (TfIdfFile tfIdfFile : tfIdfDirectory) {
            if (tfIdfFile.containsWord(word)) {
                count++;
            }
        }
        return tfIdfDirectory.size() / count;
    }

    private void recalcIdf() {

        for (int i = 0; i < tfIdfDirectory.size(); i++) {
            for (int j = 0; j < tfIdfDirectory.get(i).countOfWord(); ++j) {
                double newIdf = getNewIdfWord(tfIdfDirectory.get(i).getWord(j).getWord());
                tfIdfDirectory.get(i).setWordNewIdf(newIdf, j);
            }

        }
    }

    private void clearList() {
        tfIdfDirectory = new ArrayList<>();
        tfFileWriter = new TfFileWriter();
        listOfFiles = new ArrayList<>();
    }

    private class FileGetter implements Runnable {
        private File file;

        public FileGetter(String path) {
            this.file = new File(path);
        }
        public FileGetter(File file){
            this.file=file;
        }

        @Override
        public void run() {
            listOfFiles.add(createFileInformation());
        }
        private FileInformation createFileInformation() {
            System.out.println("Read file....."+file.getAbsolutePath());

            FileInformation fileInformation = new FileInformation(file);

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
    }

}
