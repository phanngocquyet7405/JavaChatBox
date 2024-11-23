package com.client.login;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private ImageView shieldImageView;
    @FXML
    private Button btn_close;
    @FXML
    private Label registerMessagesLabel;
    @FXML
    private PasswordField passwordTextfield;
    @FXML
    private PasswordField confirmTextField;
    @FXML
    private Label ConfirmPasswordLabel;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private TextField usernametextfield;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Image shieldImage = new Image(getClass().getResource("/images/register.png").toExternalForm());
            shieldImageView.setImage(shieldImage);
        } catch (NullPointerException e) {
            System.err.println("Image file not found: images/register.png");
        }
    }

    public void registerButtonAction(ActionEvent event) {
        if (passwordTextfield.getText().equals(confirmTextField.getText())) {
            ConfirmPasswordLabel.setText("You are set");
        } else {
            registerMessagesLabel.setText("Registration failed!");
            ConfirmPasswordLabel.setText("Password does not match!");
        }
        registerUser();
    }

    public void closeButtonActionEvent(ActionEvent event) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }

    public void registerUser(){
        databaseConnection connectionNow = new databaseConnection();
        Connection connectionDB = connectionNow.getConnection();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernametextfield.getText();
        String password = passwordTextfield.getText();

        String insertFields = "insert into user_account(last_name, first_name, username, password) values ('";
        String insertValues = firstname + "','" +lastname + "','" + username + "','" +password + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(insertToRegister);
            registerMessagesLabel.setText("User has been registered successfully!");
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
