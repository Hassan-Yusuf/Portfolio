import java.io.*;
import java.net.*;

public class PedestrianClient {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 8888);
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));
                String checkConnection = serverInput.readLine(); //just inital connection check
                System.out.println(checkConnection);
                if(checkConnection.equalsIgnoreCase("Connection successful!")){
                    while (true) {
                        System.out.print("Please choose from the following options: \n1. Press the button to trigger a crossing sequence\n" +
                                "2. Report the status of the Panel (i.e. \"Standby\", \"Waiting\", or \"Off\")\n" +
                                "3. Exit the client application\nEnter here: ");
                        String input = clientInput.readLine();
                        if (input.equalsIgnoreCase("3")) {
                            output.println("Exit");
                            break;
                        } else if (input.equalsIgnoreCase("2")) {
                            output.println("Panel check");
                            String response = serverInput.readLine();
                            System.out.println(response);
                        } else if (input.equalsIgnoreCase("1")) {
                            output.println("Button press");
                            String response = serverInput.readLine();
                            System.out.println(response);
                            Thread checkSafety = new Thread(() -> {
                                try{
                                    while(true){
                                        String safetyChecker = serverInput.readLine();
                                        if(safetyChecker!=null&&safetyChecker.equalsIgnoreCase("Safe")){
                                            System.out.println("\nServer now says it is safe to start crossing now!\nEnter here: ");
                                        }
                                    }
                                } catch(IOException e){
                                    e.printStackTrace();
                                }

                            });
                            checkSafety.start();
                            //System.out.println(in.readLine());
                            //String safetycheck = in.readLine();


                        }
                    }

                    clientSocket.close();
                }
        }
        catch (IOException e) {
            System.out.println("Server is not online!");
        }
    }
}
