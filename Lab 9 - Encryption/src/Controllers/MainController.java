package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.security.PrivateKey;
import java.security.PublicKey;

public class MainController {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @FXML
    TextField publicKeyTextField;

    @FXML
    TextField privateKeyTextField;

    @FXML
    TextField sourceFileTextField;

    @FXML
    TextField destinationFileTextField;

    @FXML
    void encryptFile(){

    }

    @FXML
    void decryptFile(){

    }

    @FXML
    void changePublicKey(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser chooser = new FileChooser();
        publicKeyTextField.setText(chooser.showOpenDialog(stage).getAbsoluteFile().getPath());
    }

    @FXML
    void changePrivateKey(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser chooser = new FileChooser();
        privateKeyTextField.setText(chooser.showOpenDialog(stage).getAbsoluteFile().getPath());
    }

    @FXML
    void changeSourceFile(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser chooser = new FileChooser();
        sourceFileTextField.setText(chooser.showOpenDialog(stage).getAbsoluteFile().getPath());
    }

    @FXML
    void changeDestinationFile(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser chooser = new FileChooser();
        destinationFileTextField.setText(chooser.showOpenDialog(stage).getAbsoluteFile().getPath());
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
