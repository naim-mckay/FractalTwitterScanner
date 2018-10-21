package com.fractallabs.assignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Utils {

    public static String calculatePercentChange(TwitterScanner.TSValue newTSValue, TwitterScanner.TSValue oldTSValue){

        String percentChangeStr;
        double percentage;
        double oldTSValueVal = oldTSValue.getVal();
        double newTSValueVal = newTSValue.getVal();

        if(newTSValueVal == oldTSValueVal){
            return "There's been no change since the last check";
        }else if (newTSValueVal== 0 && oldTSValueVal > 0){
            percentChangeStr = "There's been a decrease of ";
            percentage = oldTSValueVal * 100;
        }else if(oldTSValueVal == 0 && newTSValueVal > 0){
            percentChangeStr = "There's been a increase of ";
            percentage = newTSValueVal * 100;
        }else {
            double direction = newTSValueVal / oldTSValueVal;
            if (direction > 1) {
                percentage = (newTSValueVal - oldTSValueVal) / oldTSValueVal * 100;
                percentChangeStr = "There's been a increase of ";
            } else {
                percentage = (oldTSValueVal - newTSValueVal) / oldTSValueVal * 100;
                percentChangeStr = "There's been a decrease of ";
            }
        }
        StringBuilder builder = new StringBuilder(percentChangeStr);
        builder.append(String.format("%.0f", percentage)).append("% \n");
        return builder.toString();
    }




    public static void  showOutPut(String changeStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        BufferedWriter writer=null;
        File file = new File("./output.txt");
        try {
            writer = new BufferedWriter(new FileWriter(file.getName(), true));
            writer.append("\n").append(formatter.format(now)).append(":: ").append(changeStr);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer!= null) writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
