package com.company;

import com.company.Security.PasswordSecurity;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;

//public class User_Login extends JFrame implements ActionListener {

public class User_Login extends JDialog {
    private int id_number;
    private boolean login_Sucess = false;
    Controller controller = new Controller();
    CreationController creationController = new CreationController();
    private final Connection connection = DriverManager.getConnection("jdbc:sqlite:src/453_Database.db");
    private static final String INSERT_SQL =
            "INSERT INTO Users(Username,Password,Email) VALUES(?, ?, ?)";

    //Accesses the database and gathers all the usernames, hashed passwords, and emails as well as the user ID #
    public static ArrayList<ArrayList> Gather_Logins_From_DB() {
        String jdbcURL = "jdbc:sqlite:src/453_Database.db";

        ArrayList<ArrayList> user_object_list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(jdbcURL);
            String user_table = "SELECT * FROM Users";
            Statement statement = connection.createStatement();
            ResultSet user_table_result = statement.executeQuery(user_table);
            while (user_table_result.next()) {
                ArrayList user_object = new ArrayList();
                if (user_table_result.getString(1) != null) {
                    String DB_username = user_table_result.getString(1);
                    String DB_password = user_table_result.getString(2);
                    String DB_email = user_table_result.getString(3);
                    String DB_idNumber = user_table_result.getString(4);
                    user_object.add(DB_username);
                    user_object.add(DB_password);
                    user_object.add(DB_email);
                    user_object.add(DB_idNumber);

                    user_object_list.add(user_object);
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Unable to connect to the database");
            throwables.printStackTrace();
        }
        return user_object_list;
    }

    //creates the UI for a user to log in and checks the entered information against already existing users loaded from
    //the database
    public User_Login() throws SQLException {

    }
    public void User_Login_Action() throws SQLException {
        ArrayList<ArrayList> loaded_user_DB = Gather_Logins_From_DB();
        loaded_user_DB.remove(0);

        try {
            //passes the entered password into the password security class via authenticate() to compare against the hashed password
            if (authenticate(getUsername(), getPassword(), loaded_user_DB)) {
                login_Sucess = true;
            } else  if (!login_Sucess){
                JOptionPane.showMessageDialog(User_Login.this,
                        "Invalid username or password",
                        "Login",
                        JOptionPane.ERROR_MESSAGE);
                // reset username and password
                login_Sucess = false;

            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace();
        }
    }

    //creates a new user and adds them to the database
    /*
    Possible create paramaters for user creation and pass them through from CreationController
     */
    public User create_user() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        PasswordSecurity ps = new PasswordSecurity();
        User new_user = new User();
        new_user.setUsername(CreationController.newUserName);
        new_user.setPassword(CreationController.newPassword);
        new_user.setEmail(CreationController.newEmail);

        Add_To_DB(new_user);
        return new_user;
    }

    public Boolean create_user_test(String user, String password, String Email) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
            PasswordSecurity ps = new PasswordSecurity();
            User new_user = new User();
            if(user.length() > 5 && user.length() < 13 && password.length() > 5 && password.length() < 13) {
                new_user.setUsername(user);
                new_user.setPassword(password);
                new_user.setEmail(Email);
                Add_To_DB(new_user);
                return true;
            }
            else return false;
    }



    public int Add_To_DB(User new_user) throws SQLException {
        int numRowsInserted = 0;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1, new_user.getUsername());
            preparedStatement.setString(2, new_user.getPassword());
            preparedStatement.setString(3, new_user.getEmail());

            numRowsInserted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();

        }
        System.out.println(numRowsInserted + "Rows inserted, added to DB");
        return numRowsInserted;
    }


    public String getUsername() {
        System.out.println("Username in the UserLogin class: " + Controller.userName);
        return Controller.userName;
    }

    public String getPassword() {
        System.out.println("Password in the UserLogin class: " + Controller.passWord);
        return Controller.passWord;
    }

    public int getIdNumber() {
        return this.id_number;
    }

    public boolean Login_Successful() {
        return login_Sucess;
    }

    //creates a user object from the data obtained from the database and compares it to what the user input is.
    //creates instance of the passsword security class to verify that the password entered with the corresponding username
    // is correct in comparison with the hash.
    public boolean authenticate(String username, String password, ArrayList<ArrayList> loaded_user_DB) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PasswordSecurity passwordSecurity = new PasswordSecurity();
        ArrayList user_object;
        for (int i = 0; i < loaded_user_DB.size(); i++) {
            user_object = loaded_user_DB.get(i);
            String dbUsername = (String) user_object.get(0);
            String dbPassword = (String) user_object.get(1);
            boolean passwordCheck = passwordSecurity.checkPassword(password, dbPassword);
            if (username.equalsIgnoreCase(dbUsername) && passwordCheck) {
                this.id_number = Integer.parseInt((String) user_object.get(3));
                JOptionPane.showMessageDialog(null, "Login Successful");
                return true;
            }
            user_object.clear();
        }
        return false;

    }

}

