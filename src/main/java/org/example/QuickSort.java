package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class QuickSort {    //сортировка врменных файлов
    Record tmp = new Record(-1, "src/main/tmp");
    String sortedPath;
    static int fcounter = 0;

    QuickSort(String spath) {
        this.sortedPath = spath;
    }

    public ArrayList<String> qSort(ArrayList<String> paths) {

        ArrayList<String> sortedTmpFiles = new ArrayList<>();
        for (String path : paths) {
            ArrayList<Record> records = new ArrayList<>();
            File tmpFile = new File(path);
            String tmpLine;
            try (BufferedReader br = new BufferedReader(new FileReader(tmpFile))) {
                // br.readLine();
                while ((tmpLine = br.readLine()) != null) {
                    Record record = new Record();
                    if (!record.r(tmpLine)) {
                        continue;
                    }
                    records.add(record);
                }
                try {
                    try {
                        records = sort(records);
                    } catch (Exception exception) {
                        System.out.println("ошибка в сорте Record-ов");
                    }


                    File file = new File(sortedPath + fcounter + "sortedTpmFile" + UUID.randomUUID() + ".csv");
                    sortedTmpFiles.add(file.getPath());
                    try (FileWriter fileWriter = new FileWriter(file)) {
                        for (Record record : records) {
                            String line = record.getString();
                            fileWriter.write(line + System.lineSeparator());
                        }
                        fileWriter.close();
                        fcounter++;
                    }
                } catch (Exception e) {
                    System.out.println("тут броук");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return sortedTmpFiles;
    }

    public ArrayList<Record> swap(ArrayList<Record> arr, int i, int j) {
        this.tmp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, tmp);
        return arr;
    }

    public ArrayList<Record> sort(ArrayList<Record> arr) {
        int max = arr.size() - 1;
        int min = 0;
        arr = setpiv(arr, min, max);
        return arr;
    }

    public ArrayList<Record> setpiv(ArrayList<Record> arr, int min, int max) {
        if (min == max) {
            return arr;
        }
        int piv = max;
        int i = min - 1;
        for (int k = min; k < max; k++) {
            if (arr.get(piv).id >= arr.get(k).id) {
                i++;
                swap(arr, i, k);
            }
        }
        swap(arr, i + 1, piv);
        piv = i + 1;
        if (piv > min) {
            arr = setpiv(arr, min, piv - 1);
        }
        if (piv < max) {
            arr = setpiv(arr, piv + 1, max);
        }
        return arr;
    }
}
