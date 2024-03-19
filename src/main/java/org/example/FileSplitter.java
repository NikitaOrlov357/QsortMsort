package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class FileSplitter {     //разделение input файла
    String tmpDir;
    FileSplitter(String tmpDir) {
//       FIXME Проверка на то, что это директория
        this.tmpDir = tmpDir;
    }

    public ArrayList<String> fileSplit(File inPutFile) {
        ArrayList<String> tmpFilesPaths = new ArrayList<>();
        int filePartCounter = 0;
        long fileSize = 0;
        long memoryLimit = 50 * 1024 * 1024;// Available memory in bytes;
//        long memoryLimit = 70 * 1024;// Available memory in bytes;



        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inPutFile))) {
            String columnsName = bufferedReader.readLine();
            File file;
            FileWriter fileWriter = null;
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                file = new File(tmpDir + filePartCounter + "tpmFile" + UUID.randomUUID() + ".csv");
                if ((fileSize + line.length() + System.lineSeparator().length() / 2) < memoryLimit) {
                    if (fileWriter == null) {
                        tmpFilesPaths.add(file.getPath());
                        fileWriter = new FileWriter(file);

                    }
                    fileSize += line.length() + System.lineSeparator().length();
                    fileWriter.write(line);
                    fileWriter.write(System.lineSeparator());
                } else {
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                    filePartCounter++;
                    fileSize = line.length() + System.lineSeparator().length();
                    file = new File(tmpDir + filePartCounter + "tpmFile" + UUID.randomUUID() + ".csv");
                    tmpFilesPaths.add(file.getPath());
                    fileWriter = new FileWriter(file);

                    fileWriter.write(line);
                    fileWriter.write(System.lineSeparator());
                }
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tmpFilesPaths;
    }
}