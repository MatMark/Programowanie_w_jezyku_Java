package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {

    private Locale plLocale = new Locale("pl", "PL");
    private Locale usLocale = new Locale("en", "US");

    private ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", plLocale);

    @FXML
    RadioButton radioButtonPL;

    @FXML
    RadioButton radioButtonENG;

    @FXML
    Button loginButton;

    @FXML
    CheckBox adminCheckBox;

    @FXML
    TextField nameTextField;

    @FXML
    TextField surnameTextField;

    @FXML
    void login(ActionEvent event) throws IOException {
        if(adminCheckBox.isSelected()){
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../Panels/AdminPanel.fxml"));
            stage.setTitle("Admin");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        }
        else{
            UserConnection.activeUserName = nameTextField.getText();
            UserConnection.activeUserSurname = surnameTextField.getText();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../Panels/UserPanel.fxml"));
            stage.setTitle("User");
            stage.setScene(new Scene(root, 600, 300));
            stage.show();
        }
    }

    @FXML
    void selectPL(){
        radioButtonENG.setSelected(false);
        messages = ResourceBundle.getBundle("MessagesBundle", plLocale);
        UserConnection.messages = ResourceBundle.getBundle("MessagesBundle", plLocale);
        changeLanguage();
    }

    @FXML
    void selectENG(){
        radioButtonPL.setSelected(false);
        messages = ResourceBundle.getBundle("MessagesBundle", usLocale);
        UserConnection.messages = ResourceBundle.getBundle("MessagesBundle", usLocale);
        changeLanguage();
    }

    void changeLanguage() {
        nameTextField.setPromptText(messages.getString("name"));
        surnameTextField.setPromptText(messages.getString("surname"));
        loginButton.setText(messages.getString("login"));
        adminCheckBox.setText(messages.getString("admin"));
    }
}
