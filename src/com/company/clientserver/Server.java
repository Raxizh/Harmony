package com.company.clientserver;

import java.io.*;
import java.net.*;
public class Server{
    static int currentPort  = 0;
    static int clientPortInUse = 0;

    //thread creator for the Server
    static class ServerThread extends Thread {
        @Override
        public void run() {
            try {
                ServerSocket a = new ServerSocket(currentPort);
                Socket b = a.accept();
                InputStreamReader input = new InputStreamReader(b.getInputStream());
                BufferedReader serverInput = new BufferedReader(input);
                PrintWriter serverOutput = new PrintWriter(b.getOutputStream());
                //LoginVeriification(serverInput, serverOutput);
                ServerClientToClientSetup(serverInput, serverOutput);
                a.close();
                b.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Send a port number to connect to if chat is chosen, 8500 is our dummy port for now assigned to a global variable
    public static void ServerClientToClientSetup(BufferedReader serverInput, PrintWriter serverOutput) throws IOException {
        System.out.println("Assigning port\n");
        serverOutput.println("8500");
        serverOutput.println(clientPortInUse);
        serverOutput.flush();

        if(clientPortInUse == 0){
            clientPortInUse = 1;
        }
    }

    public static void main(String[] args)throws IOException{
        int clientNum = 1;
        for(;;){
            ServerSocket a = new ServerSocket(4999);
            Socket b = a.accept();
            System.out.println("Client " + clientNum + " connected");
            clientNum++;

            PrintWriter initServerOutput = new PrintWriter(b.getOutputStream());

            //new port that the created thread will use, will be randomized later
            initServerOutput.println("7980");
            initServerOutput.flush();
            currentPort = 7980;       
                ServerThread test0 = new ServerThread();
                test0.start();
                a.close();
        }
    }
}