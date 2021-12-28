package agh.ics.oop;

import agh.ics.oop.gui.CSVWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVWriterTest {


    CSVWriter csvWriter = new CSVWriter("csvTest");

    @Test
    void convertToCSVTest() {
        String[] data = {"abc","def","ghi"};
        String str = "abc,def,ghi";
        assertEquals(csvWriter.convertToCSV(data), str);

        String[] data1 = {"a1","a2","a3"};
        String str1 = "a1,a2,a3";
        assertEquals(csvWriter.convertToCSV(data1), str1);

    }



}
