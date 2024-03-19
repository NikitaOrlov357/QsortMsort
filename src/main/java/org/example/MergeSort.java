package org.example;

import java.io.*;
import java.util.*;

public class MergeSort {     //объединение временных файлов с сортировкой
    static Record minRecord = new Record(Long.MAX_VALUE,"");
    String pathToFinalFile;
    static HashMap<BufferedReader, Record> cache = new HashMap<>();

    MergeSort(String fpath) {
        this.pathToFinalFile = fpath;
    }

    public void merge(ArrayList<String> filePaths) throws IOException {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(pathToFinalFile + "ff.csv");
        } catch (IOException e) {
            System.out.println("error open/create final file");
            return;
        }

        try {
            for (String fPath : filePaths) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fPath));
                Record record = new Record();
                record.r(bufferedReader.readLine());
                cache.put(bufferedReader, record);
            }

            while (!cache.isEmpty()) {
                BufferedReader bufferedReader = getMinRecord(cache);
                Record wr = cache.get(bufferedReader);
                fileWriter.write(wr.getString() + System.lineSeparator());
                String line = bufferedReader.readLine();
                if (line == null) {
                    bufferedReader.close();
                    cache.remove(bufferedReader);
                } else {
                    Record record = new Record();
                    record.r(line);
                    cache.put(bufferedReader, record);
                }

            }
            fileWriter.close();
        } finally {
            for (BufferedReader bufferedReader : cache.keySet()) {
                bufferedReader.close();
            }
        }
    }

    public static BufferedReader getMinRecord(HashMap<BufferedReader, Record> cache) throws FileNotFoundException {
        BufferedReader result = null;
        for (BufferedReader br : cache.keySet()) {
            if (cache.get(br).id <= minRecord.id) {
                minRecord = cache.get(br);
                result = br;
            }
        }
        minRecord = new Record(Long.MAX_VALUE,"");
        return result;
    }
}
