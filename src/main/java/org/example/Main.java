package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Boolean.TRUE;


public class Main {
    //    FIXME Каждая переменная должна быть с комментарием
    static String fpath = "C:\\Users\\mywke\\IdeaProjects\\DZzz\\maminPrynik\\src\\main\\resources\\ex.csv";//путь до input файла
    static String dpath = "src/main/resources/";//путь куда сделаться output файл
    static String tmpd = "src/main/tmp/";//папка с временными файлами

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024 / 1024 + "gb");


        File file = new File(fpath);


        FileSplitter fileSplitter = new FileSplitter(tmpd);
        ArrayList<String> paths = fileSplitter.fileSplit(file);

        QuickSort quickSort = new QuickSort(tmpd);
        paths = quickSort.qSort(paths);


        MergeSort mergeSort = new MergeSort(dpath);
        mergeSort.merge(paths);


//        CsvWriter.dd(fpath);


    }

    //класс для создания тестового csv файла
    public class CsvWriter {    //класс для создания тестового csv файла


        //              private static final long MAX_LENGTH = 1024 * 100;
        private static final long MAX_LENGTH = 1024 * 1024 * 1024;
        private static final Random RANDOM = new Random();

        public static void dd(String path) {
            int totalLength = 0;
            int rowNum = 0;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                String header = "id,name";
                totalLength += header.length();
                writer.write(header);
                writer.newLine();

                while (totalLength < MAX_LENGTH) {
//                while (totalLength < MAX_LENGTH && rowNum < MAX_ROWS) {
                    rowNum++;
                    String row = ThreadLocalRandom.current().nextLong(rowNum) + "," + generateRandomName();
                    totalLength += row.length();
                    if (totalLength > MAX_LENGTH) {
                        break;
                    }
                    writer.write(row);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing to CSV file: " + e.getMessage());
            }

            System.out.println("Total length of written rows: " + totalLength);
            System.out.println("Number of written rows: " + rowNum);
        }

        private static String generateRandomName() {
            int length = RANDOM.nextInt(10) + 1;
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < length; i++) {
                char c = (char) ('a' + RANDOM.nextInt(26));
                name.append(c);
            }
            return name.toString();
        }
    }


}