package com.company.clientserver;

import java.net.*;
import java.util.ArrayList;
import java.io.*;



public class Client {

    public static ArrayList<Object> ClientToClient(PrintWriter inputToServer, BufferedReader outputFromServer) throws IOException{
        int port = Integer.parseInt(outputFromServer.readLine());
        int portInUse = Integer.parseInt(outputFromServer.readLine());
        Socket connectToClient = null;
        ArrayList<Object> list = new ArrayList<Object>();
        if(portInUse == 0){
            ServerSocket a = new ServerSocket(port);
            connectToClient = a.accept();
            InputStreamReader in = new InputStreamReader(connectToClient.getInputStream());
            inputToServer = new PrintWriter(connectToClient.getOutputStream());
            outputFromServer = new BufferedReader(in);

            list.add(inputToServer);
            list.add(outputFromServer);
        }
        else if(portInUse ==1){
            connectToClient = new Socket("localhost", port);
            InputStreamReader in = new InputStreamReader(connectToClient.getInputStream());
            inputToServer = new PrintWriter(connectToClient.getOutputStream());
            outputFromServer = new BufferedReader(in);
            list.add(inputToServer);
            list.add(outputFromServer);
        }
        return list;
    }

}