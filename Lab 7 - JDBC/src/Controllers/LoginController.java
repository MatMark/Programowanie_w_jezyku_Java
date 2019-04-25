package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    RadioButton radioButtonPL;

    @FXML
    RadioButton radioButtonENG;

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
    }

    @FXML
    void selectENG(){
        radioButtonPL.setSelected(false);
    }
}
