import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private int pedestrianNo;
    private Socket clientSocket;
    private Panel panel;
    private TrafficLight tLight;
    private PedestrianSignal pedestrianSignal;

    public ClientHandler(int pedestrianNo, Socket clientSocket, Panel panel, TrafficLight tLight, PedestrianSignal pedestrianSignal) {
        this.pedestrianNo=pedestrianNo;
        this.clientSocket = clientSocket;
        this.panel = panel;
        this.tLight = tLight;
        this.pedestrianSignal = pedestrianSignal;
    }

    @Override
    public void run() {
        try {
            BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            if (PelicanCrossingServer.getCount() >= 2) {
                output.println("Too many pedestrians connected on this server! Please try again later."); //limits pedestrians to 2.
                clientSocket.close();
                return;
            }
            else{
                output.println("Connection successful!");
                System.out.println("Pedestrian " + pedestrianNo + " has connected."); //if there isn't more than 2 pedestrians then continues
                PelicanCrossingServer.incCount(1);

            }
            while (true) {
                String request = clientInput.readLine();
                if (request == null || request.equalsIgnoreCase("exit")) { //exit command
                    output.println("Bye pedestrian "+pedestrianNo+"!");
                    break; // Exit the thread
                }

                else if (request.equalsIgnoreCase("Button press")) {
                    if (panel.getState() == Enums.PanelStates.STANDBY) {
                        output.println("Button has been pressed! Server will confirm crossing safety check shortly via. Traffic light and Pedestrian light check.");
                        panel.pressButton();
                        Thread crossingThread = new Thread(() -> {
                            try {
                                //waiting state
                                Thread.sleep(7000); // Simulate 7 seconds
                                tLight.updateState();
                                System.out.println("Traffic light is now "+ tLight.getState()+"!");
                                Thread.sleep(3000); // Simulate 3 seconds
                                //Traffic light going to red and pedestrian light to green
                                tLight.updateState();
                                System.out.println("Traffic light is now "+ tLight.getState()+"!");
                                pedestrianSignal.updateState();
                                System.out.println("Pedestrian signal is now on "+ pedestrianSignal.getState()+"!");
                                //Safety check
                                while(tLight.getState()!= Enums.TLStates.RED
                                        ||pedestrianSignal.getState()!= Enums.PedestrianSState.GREEN_PERSON_ILLUMINATED){
                                    Thread.sleep(100); //Keep waiting a tenth of a second until the traffic light is red and pedestrian light is green
                                }
                                //Off state
                                output.println("Safe");
                                panel.updateState();
                                System.out.println("Panel is now on "+ panel.getState()+" mode!");
                                Thread.sleep(10000);
                                                                            // timings sections as stated
                                //flashing state for both
                                tLight.updateState();
                                System.out.println("Traffic light is now "+ tLight.getState()+"!");
                                pedestrianSignal.updateState();
                                System.out.println("Pedestrian signal is now on "+ pedestrianSignal.getState()+"!");
                                Thread.sleep(5000);

                                //Back to standby state i.e. beginning cycle
                                panel.updateState();
                                System.out.println("Panel is now back on "+ panel.getState()+" mode!");
                                tLight.updateState();
                                System.out.println("Traffic light is now "+ tLight.getState()+"!");
                                System.out.println("No longer safe to cross!");
                                pedestrianSignal.updateState();
                                System.out.println("Pedestrian signal is now on "+ pedestrianSignal.getState()+"!");

                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        crossingThread.start();
                    }
                    else{
                        output.println("Button has been pressed, however this has no effect yet. Please wait!");
                    }
                }
                else if(request.equalsIgnoreCase("Panel check")) {
                    output.println("Panel state is at: "+panel.getState());
                }
            }
            System.out.println("Pedestrian "+pedestrianNo+" has disconnected due to 'exit menu'.");
            clientSocket.close();
        }catch (IOException e) {
            try {
                System.out.println("Pedestrian "+pedestrianNo+" has disconnected due to 'connection issues or forcefully exiting'.");
                clientSocket.close();
            } catch (IOException ex) {
                System.out.println("Error! client socket closed already!");
            }
        }
        PelicanCrossingServer.incCount(-1);

    }
}