package com.company;
// todo: Create a method with an on-action handler for client exiting.
// todo: On client exit, pass to Server class to show that client disconnected.
// todo: On client exit, println to ScrollPane/textFlow to show that client disconnects.
import com.company.clientserver.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.*;

import javafx.event.ActionEvent;

import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalTime;
import javafx.scene.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import static com.company.Controller.userName; //Gets current username


public class p2pController implements Runnable {
    public static String text;
    @FXML
    ScrollPane textContainer;
    @FXML
    TextField p2pTextField;
    @FXML
    TextFlow textFlow;
    @FXML
    TextArea textReceived;

    static String exitCode = "";

    int ScreenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    int ScreenHeight = (int) Screen.getPrimary().getBounds().getHeight();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

    PrintWriter inputToServer = Controller.inputToServer;
    BufferedReader outputFromServer = Controller.outputFromServer;
    //BufferedReader incomingMessage = null;
    String login = Controller.userName;

    Font myFont = new Font("Trebuchet MS", 18);


    public static Scanner userInput = new Scanner(System.in);
    LocalTime now = LocalTime.now();
    String time = now.format(dtf);


    // From here down is new implementation
    final int portNo = 4999;
    PrintWriter out;
    BufferedReader in;
    {
        try {
            out = (PrintWriter) Controller.list.get(0);
            in = (BufferedReader) Controller.list.get(1);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        new Thread(this).start();

    }

    public p2pController() throws IOException {
    }


    public void SendText(ActionEvent actionEvent) throws IOException {
        now = LocalTime.now();
        time = now.format(dtf);
        out.println(login);
        String message = p2pTextField.getText();
        System.out.println(login + " sent: " + message);
        out.println(message);
        Text text0 = new Text("[" + time + "] " + "[" + login + "]: " + message + "\n");
        text0.setFont(myFont);
        text0.setFill(Color.BLUE);
        textFlow.getChildren().add(text0);
        out.flush();
        p2pTextField.clear();
        textContainer.setVvalue(1.0f);
        textFlow.layout();
    }


    @Override
    public void run() {
        String exitCode = "refdjejdkejk";
        String otherUser;
        System.out.println("hello");
        Runtime.getRuntime().addShutdownHook(new Thread()  {
            public void run() {
                System.out.println("In shutdown hook");
                out.println("refdjejdkejk");
                out.flush();
            }});

        try {
            while((otherUser=in.readLine()) != null) {
                    String message = in.readLine();
                    if(message.equals(exitCode)){
                        System.out.println("User has exited the chat");
                        System.exit(1);
                    }
                    String finalMessage = message;
                    //textReceived.setText(textReceived.getText() + "\n" +"[" + time + "] " + "[" + otherUser + "]: " + finalMessage + "\n");
                    //Platform.runLater(
                            //() -> textReceived.setText(textReceived.getText() + "\n" +"[" + time + "] " + "[" + otherUsr + "]: " + finalMessage + "\n")
                    //);
                String finalOtherUser = otherUser;
                Text text1 = new Text("[" + time + "] " + "[" + finalOtherUser + "]: " + finalMessage + "\n");
                text1.setFont(myFont);
                text1.setFill(Color.BLUE);
                Platform.runLater(
                        () -> textFlow.getChildren().add(text1)
                );
                }
            textFlow.layout();
            textContainer.setVvalue(1.0f);
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
    
    @FXML
    private void handleChatExitButton(ActionEvent event) throws IOException {
        Parent loginPageParent = null;
        try {
            loginPageParent = FXMLLoader.load(p2pController.class.getResource("loginPage.fxml"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Stage app_Stage3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage3.setScene(new Scene(loginPageParent, ScreenWidth, ScreenHeight - 60));
        app_Stage3.setMaximized(true);
        app_Stage3.show();
        //System.out.println("Username after clicking logout button: " + Controller.userName);
        //System.out.println("Password after clicking logout button: " + Controller.passWord);
        userName = null;
        Controller.passWord = null;

    }





}
