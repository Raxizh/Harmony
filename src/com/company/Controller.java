package com.company;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import com.company.clientserver.Client;

public class Controller implements Initializable {
    public static String userName;
    public static String passWord;
    public static String p2pUserName;

    public static PrintWriter inputToServer;
    public static BufferedReader outputFromServer;
    public static ArrayList<Object> list;

    @FXML  TextField usernameInput;
    @FXML  PasswordField passwordInput;
    @FXML Button logButton;
    int ScreenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    int ScreenHeight = (int) Screen.getPrimary().getBounds().getHeight();

    /*
    Method for the login button
    Takes user input from the Username and Password text fields and creates a new User_Login object that passes the userName and passWord to login the user
    Displays option pane for choosing group or P2P chat options
     */
    @FXML
    private void handleButtonAction (ActionEvent event) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, SQLException, InterruptedException {
        Object[] Options = {"Retry", "Exit"};
        for(;;) {
            try {
                Socket initServerConnect = new Socket("localhost", 4999);
                InputStreamReader a = new InputStreamReader(initServerConnect.getInputStream());
                BufferedReader initServerOutput = new BufferedReader(a);
                int newPort = Integer.parseInt(initServerOutput.readLine());
                initServerConnect.close();
                Thread.sleep(20);
                Socket connectToServer = new Socket("localhost", newPort);
                inputToServer = new PrintWriter(connectToServer.getOutputStream());
                InputStreamReader b = new InputStreamReader(connectToServer.getInputStream());
                outputFromServer = new BufferedReader(b);
                list = Client.ClientToClient(inputToServer, outputFromServer);
                list.add(userName);
                System.out.println(list.size());

                break;
            } catch (ConnectException e) {
                int n = JOptionPane.showOptionDialog(null, "Unable to connect to server", "Server error", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null,Options,Options[1]);
                if (n == 0) {
                    Thread.sleep(3000);
                    continue;

                }
                else if(n == 1)
                    System.exit(1);
                e.printStackTrace();
            }
        }

        userName = usernameInput.getText();
        p2pUserName = userName;
        passWord = passwordInput.getText();

        boolean move_on = false;
        User_Login user_login = null;
        user_login = new User_Login();

        user_login.User_Login_Action();
        while (!move_on && userName != null) {
            if (user_login.Login_Successful()) {
                // Options for the Dialog popup box
                Object[] options = { "Random P2P Chat", "Group chat"};

                // Creation of the dialog box; with the variable n being the user's input.
                int n = JOptionPane.showOptionDialog(null, "Which chat would you like to enter?", "Chat Option", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                System.out.println("Option number: " + n);
                if (n == JOptionPane.YES_OPTION){
                    Parent p2pPageParent = FXMLLoader.load(Controller.class.getResource("p2pPage.fxml"));
                    Stage app_Stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    app_Stage2.setScene(new Scene(p2pPageParent,ScreenWidth,ScreenHeight-60));
                    app_Stage2.setMaximized(true);
                    app_Stage2.show();
                }
                else if (n == JOptionPane.NO_OPTION){
                    Parent mainPageParent = FXMLLoader.load(Controller.class.getResource("mainPage.fxml"));
                    Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    app_Stage.setScene(new Scene(mainPageParent, ScreenWidth, ScreenHeight-60));
                    app_Stage.setMaximized(true);
                    app_Stage.show();
                }
                move_on = true;
            }
            else{
                System.out.println("No username found");
                break;
            }
        }
        userName = null;
        passWord = null;


    }

    /*
    Method for logging out the user
    Resets userName and passWord variables to null and presents the login page once again.
     */

    @FXML
    private void handleLogoutButton (ActionEvent event) throws IOException {
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        Stage app_Stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage2.setScene(new Scene(loginPageParent, ScreenWidth, ScreenHeight-60));
        app_Stage2.setMaximized(true);
        app_Stage2.show();
        userName = null;
        passWord = null;
    }

    /*
    Method for the "Create Account" button on the login page
    Displays the account creation page handled by the CreationController.java class
     */

    @FXML
    private void creationAccountButton (ActionEvent event) throws IOException {
        Parent creationPageParent = FXMLLoader.load(getClass().getResource("accountCreation.fxml"));
        Stage app_Stage3 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage3.setScene(new Scene(creationPageParent, ScreenWidth, ScreenHeight-60));
        app_Stage3.setMaximized(true);
        app_Stage3.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}