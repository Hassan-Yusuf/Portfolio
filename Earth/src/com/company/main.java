package com.company;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws FileNotFoundException {
        String filename = "earth.xyz";
        System.out.println(("Processing map data, this may take a while..."));
        JFrame window = new JFrame("Map of Earth");
        plotMap pmap = new plotMap(filename);
        window.getContentPane().setPreferredSize(new Dimension(600,600));
        window.add(pmap);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        Scanner input = new Scanner(System.in);
        pmap.earth1.readDataMap(filename);
        while(true) {
            System.out.println("Please enter a longitude (0 - 360) on one line, followed by latitude (-90 - 90) on the next:");
            String longitude = input.nextLine();
            if("quit".equals(longitude)){
                System.out.println("Bye!");
                break;
            }
            if(pmap.earth1.isCorrect(longitude,360.0,0.0)==false){
                System.out.println("invalid longitude, please enter a longitude (0 - 360) or 'quit' to end program.");
                continue;
            }
            String latitude = input.nextLine();
            if("quit".equals(latitude)){
                System.out.println("Bye!");
                break;
            }
            if(pmap.earth1.isCorrect(latitude,90.0,-90.0)==false){
                System.out.println("invalid latitude, please enter a latitude (-90 - 90) or 'quit' to end program.");
                continue;
            }
            try {
                System.out.println("The altitude at longitude " + longitude + " and latitude " + latitude + " is " + pmap.earth1.getAltitude(Double.parseDouble(longitude), Double.parseDouble(latitude)) + " meters.");
            } catch(NullPointerException e) {
                System.out.println("please enter a valid altitude.");
            }

            System.out.println("Please enter 1 for percentage, or 2 for altitude, or 3 to shift altitude, or 4 to calculate distance to.");
            String option = input.nextLine();
            if (option.equals("1")) {
                System.out.println("Please enter an altitude to check above and below");
                String altitude = input.nextLine();
                if(pmap.earth1.isCorrect(altitude,100000.0,-100000.0)==false){
                    System.out.println("invalid response, restarting");
                    continue;
                }
                pmap.earth1.percentageAbove(Double.parseDouble(altitude));
                pmap.earth1.percentageBelow(Double.parseDouble(altitude));

            }
            else if (option.equals("2")) {

            }
            else if(option.equals("3")){
                System.out.println("enter a value to shift sea levels by (negative values included).");
                String altitude = input.next();
                if(pmap.earth1.isCorrect(altitude,100000.0,-100000.0)==false){
                    System.out.println("invalid response, restarting");
                    continue;
                }
                pmap.earth1.shiftSea(Double.parseDouble(altitude));
                plotMap pmap2 = new plotMap(pmap.earth1.arrayOfEarth);
                window.getContentPane().removeAll();
                window.repaint();
                window.add(pmap2);
                window.pack();
            }
            else if(option.equals("4")){
                System.out.println("Enter your first longitude:");
                Double x1 = Double.parseDouble(input.next());
                System.out.println("Enter your first latitude:");
                Double y1 = Double.parseDouble(input.next());
                System.out.println("Enter your first altitude:");
                Double z1 = Double.parseDouble(input.next());
                System.out.println("Enter your second longitude:");
                Double x2 = Double.parseDouble(input.next());
                System.out.println("Enter your second latitude:");
                Double y2 = Double.parseDouble(input.next());
                System.out.println("Enter your second altitude:");
                Double z2 = Double.parseDouble(input.next());
                MapCoordinate coord1 = new MapCoordinate(x1,y1,z1);
                MapCoordinate coord2 = new MapCoordinate(x2,y2,z2);
                System.out.println("Distance is:\t"+coord1.distanceTo(coord2));
                System.out.println("Are they equal?");
                if (coord1.compareTo(coord2)==0 && coord1.equals(coord2)){
                    System.out.println(coord1.toString()+" is equal to "+coord2.toString());
                }
                else{
                    System.out.println(coord1.toString()+" is not equal to "+coord2.toString());
                }
            }
            else if (option.equals("quit")){
                System.out.println("Bye!");
                break;
            }
            else{
                System.out.println("invalid response");
            }

        }
        input.close();
    }
}
