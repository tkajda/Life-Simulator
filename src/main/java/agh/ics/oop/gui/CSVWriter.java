package agh.ics.oop.gui;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVWriter {
    String fileName;
    File newFile;

    private final double numOfLivingAnimals= 0;
    private final double avgLifeLen= 0;
    private final double avgEnergy= 0;
    private final double numOfGrass= 0;
    private final double avgNumOfChildren= 0;
    private int allLines = 0;

    private final double[] array = {numOfLivingAnimals,avgLifeLen,avgEnergy,numOfGrass,avgNumOfChildren};

    public CSVWriter(String name) {
        this.fileName=name;
        String newFileName = fileName + ".csv";
        this.newFile = new File(newFileName);

    }



    public String toString() {
        return newFile.getPath();
    }

    public void addDataToFile(String[] data) throws IOException {
        deleteLastLane();
        allLines++;
        updateValues(data);
        FileWriter writer = new FileWriter(newFile,true);
        writer.write("\n");
        writer.write(convertToCSV(data));
        writer.write("\n");
        String[] avg = {String.valueOf(array[0]/allLines),
                        String.valueOf(array[1]/allLines),
                        String.valueOf(array[2]/allLines),
                        String.valueOf(array[3]/allLines),
                        String.valueOf(array[4]/allLines)};


        writer.write(convertToCSV(avg));

        writer.close();
    }

    public void deleteLastLane() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(newFile, "rw");
        byte b;
        long length = randomAccessFile.length() ;
        if (length != 0) {
            do {
                length -= 1;
                randomAccessFile.seek(length);
                b = randomAccessFile.readByte();
            } while (b != 10 && length > 0);
            randomAccessFile.setLength(length);
            randomAccessFile.close();
        }
    }

    public void updateValues(String[] data) {
        int i = 0;
        for(String number: data) {
            array[i]+=Double.parseDouble(number);
            i++;
        }

    }


    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }


}
