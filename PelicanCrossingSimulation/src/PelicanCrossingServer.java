import java.io.*;
import java.net.*;

public class PelicanCrossingServer {
    private static int count;
    public static int getCount(){
        return count;
    }
    public static void incCount(int no){
        count+=no;
    }
    public static void main(String[] args) {
        try {
            int pedestrianNo=0;
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("Pelican crossing server is listening for incoming connections...");
            // Initializing the panel, traffic light and pedestrian signal
            Panel panel = new Panel();
            TrafficLight tLight = new TrafficLight();
            PedestrianSignal pedestrianSignal = new PedestrianSignal();
            System.out.println("Panel is on "+ panel.getState()+" mode and ready to be pressed!");
            while (true) {
                    Socket clientSocket = serverSocket.accept();
                    // Here we create a new thread for handling the client.
                    pedestrianNo++;
                    ClientHandler clientHandler = new ClientHandler(pedestrianNo, clientSocket, panel, tLight, pedestrianSignal);
                    Thread clientThread = new Thread(clientHandler);
                    clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}