package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class Earth <arrayOfEarth> extends JPanel {
    double[][] arrayOfEarth;
    TreeMap<Double, TreeMap<Double, Double >> mapOfEarth;

    public void readDataArray(String filename) throws FileNotFoundException {
        arrayOfEarth = new double[2336041][3];
        Scanner input = new Scanner(new File(filename));
        int i = 0;
        while (input.hasNextLine()) {
            Scanner arrays = new Scanner(input.nextLine());
            for (int z = 0; z < 3; z++) {
                arrayOfEarth[i][z] = arrays.nextDouble();
            }
            i++;
        }
        input.close();
    }

    public List<Double> coordinatesAbove(double altitude){
        List<Double> coordinatesAbove = new ArrayList();
        for (int i = 0; i < arrayOfEarth.length; i++) {
            if (arrayOfEarth[i][2] > altitude) {
                coordinatesAbove.add(arrayOfEarth[i][2]);
            }
        }
        return coordinatesAbove;
    }

    public List<Double> coordinatesBelow(double altitude){
        List<Double> coordinatesBelow = new ArrayList();
        for (int i = 0; i < arrayOfEarth.length; i++) {
            if (arrayOfEarth[i][2] < altitude) {
                coordinatesBelow.add(arrayOfEarth[i][2]);
            }
        }
        return coordinatesBelow;
    }

    public void percentageAbove(double altitude) {
        List<Double> list = coordinatesAbove(altitude);
        double size = list.size();
        double percentageAbove = (size/2336041)*100;
        System.out.println("Percentage of coordinates above " + altitude+ " are:  " + String.format("%.1f", percentageAbove)+ "%");

    }

    public void percentageBelow(double altitude){
        List<Double> list = coordinatesBelow(altitude);
        double size = list.size();
        double percentageBelow = (size/2336041)*100;
        System.out.println("Percentage of coordinates below " + altitude+ " are:  " + String.format("%.1f", percentageBelow)+ "%");
    }
    public void readDataMap(String filename) throws FileNotFoundException {
        mapOfEarth = new TreeMap<>();
        Scanner input = new Scanner(new File(filename));
        double longitude, latitude, altitude;
        int i = 0;
        while (input.hasNextLine()) {
            Scanner linereader = new Scanner(input.nextLine());
            longitude = linereader.nextDouble();
            latitude =  linereader.nextDouble();
            altitude = linereader.nextDouble();
            if(mapOfEarth.get(longitude) == null){
                mapOfEarth.put(longitude, new TreeMap<>());
                mapOfEarth.get(longitude).put(latitude,altitude);
            }
            else{
                mapOfEarth.get(longitude).put(latitude,altitude);
            }
        }
        input.close();
    }
    public void generateMap(double resolution){
        double longitude, latitude, altitude;
        mapOfEarth = new TreeMap<>();
        int n = (int) (64800 / resolution / resolution);
        for(int x = 0;x < n; x++){
            longitude = (Math.random() * (360 - 0)) + 0;
            latitude = (Math.random() * ( 90 - -90)) + -90;
            altitude = (Math.random() * ( 3000 - 0 )) + 0;
            if(mapOfEarth.get(longitude) == null){
                mapOfEarth.put(longitude, new TreeMap<>());
                mapOfEarth.get(longitude).put(latitude,altitude);
            }
            else{
                mapOfEarth.get(longitude).put(latitude,altitude);
            }
        }
    }
    public double getAltitude(double longitude, double latitude){
        double altitude = mapOfEarth.get(longitude).get(latitude);
        return altitude;
    }
    public boolean isCorrect(String string,Double max,Double min){
        double num;
        try{
            num = Double.parseDouble(string);
        } catch(NumberFormatException e) {
            return false;
        }
        if(num<min || num>max){
            return false;
        }
        return true;
    }
    public void shiftSea(double shift){
        for (int i = 0; i < arrayOfEarth.length; i++) {
            arrayOfEarth[i][2] +=shift;
            double longitude = arrayOfEarth[i][0];
            double latitude = arrayOfEarth[i][1];
            double altitude = arrayOfEarth[i][2];
            mapOfEarth.get(longitude).replace(latitude,altitude);

        }

    }

}


