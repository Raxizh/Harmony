package com.company;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public abstract class Main extends Application {
    //Verifies that the program has been run before on the computer. Allows for initial logins, first user account creation, etc.
    public static void initialLoginVerify() throws IOException {
        File f = new File("C:\\453-Project");
        boolean path_check = f.exists();
        if (!path_check) {
            f.mkdir();
        }
    }

    //Logs in the user
    public static synchronized void User_Login() throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Parent loginPageParent = null;
                try {
                    loginPageParent = FXMLLoader.load(Main.class.getResource("loginPage.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Harmony v0.11");
                int ScreenWidth = (int) Screen.getPrimary().getBounds().getWidth();
                int ScreenHeight = (int) Screen.getPrimary().getBounds().getHeight();
                primaryStage.setScene(new Scene(loginPageParent, ScreenWidth, ScreenHeight-60));
                primaryStage.setMaximized(true);
                primaryStage.show();
            }


        });




        //final JFrame frame = new JFrame("User Login");
        //JButton login_button = new JButton("Click to login");
        //frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //frame.setSize(300, 100);
        //frame.setLayout(new FlowLayout());
        //frame.getContentPane().add(login_button);
        //frame.setVisible(true);
        //login_button.addActionListener(e -> {
        //boolean move_on = false;
        //User_Login user_login = null;
        //try {
        //user_login = new User_Login(frame);
        //} catch (SQLException ex) {
        //ex.printStackTrace();
        //}
        //user_login.setVisible(true);

        //while (!move_on) {
        //if (user_login.Login_Successful()) {
        //int id_number = user_login.getIdNumber();
        //frame.dispose();
        //move_on = true;
        //}
        //}

        //});
    }


    //this is where the bulk of the program can start. It allows nothing else about the program to be accessed without
    //successfully logging in first
    public static void After_Login(){
        System.out.println("You logged in successfully :D");
    }

    public static void main(String[] args) throws IOException {
        //test username is john, test password is Doe
        User_Login();
    }
}
