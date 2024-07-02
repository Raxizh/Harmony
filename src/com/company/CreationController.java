package com.company;

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

import javafx.event.ActionEvent;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreationController implements Initializable {
    public static String newUserName;
    public static String newPassword;
    public static String newEmail;
    @FXML TextField newAccountUser;
    @FXML PasswordField newAccountPassword;
    @FXML TextField newAccountEmail;
    @FXML Button createAccountButton;
    int ScreenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    int ScreenHeight = (int) Screen.getPrimary().getBounds().getHeight();

    /*
    Button handler for "Create Account" button.
    Sends user/pass/email to create_user() function in User_Login class
     */
    @FXML
    private void handleAccountCreationButton(ActionEvent event) throws IOException, NoSuchAlgorithmException, SQLException, InvalidKeySpecException {
        User_Login user_login = null;
        user_login = new User_Login();

        if (newUserName.length() > 5 && newPassword.length() > 5 && newUserName.length() < 13 && newPassword.length() < 13) {
            user_login.create_user(); //Creates a user with user data entered.
            Parent loginPageParent = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
            Stage app_Stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            JOptionPane.showMessageDialog(null, "Account Creation Successful");
            app_Stage2.setScene(new Scene(loginPageParent, ScreenWidth, ScreenHeight - 60));
            app_Stage2.setMaximized(true);
            app_Stage2.show();
        }
        else if (newUserName.length() < 6 || newPassword.length() < 6){
            JOptionPane.showMessageDialog(null, "Username and Password must be at least 6 characters.");
        }
        else if (newUserName.length() > 13 || newPassword.length() > 13) {
            JOptionPane.showMessageDialog(null, "Username and Password must be less than 13 characters.");

        }
    }

    @FXML
    private void backToLoginButtonHandler(ActionEvent event) throws IOException {
        Parent loginPageParent = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        Stage app_Stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage2.setScene(new Scene(loginPageParent, ScreenWidth, ScreenHeight - 60));
        app_Stage2.setMaximized(true);
        app_Stage2.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User_Login test_login = null;
        try {
            test_login = new User_Login();
            Boolean x = true;
            x = test_login.create_user_test("ThirteenCharacters", "six", "testemail@gmail.com");
            if (x=true)
                System.out.println("Test passed :)");
            else if (x=false)
                System.out.println("Test failed :(");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }


    }

}
