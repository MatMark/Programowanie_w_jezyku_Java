package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Cipher cipher;
    private final File start = new File("C:\\Users\\matma\\Documents\\TestForEncryption");

    @FXML
    TextField publicKeyTextField;

    @FXML
    TextField privateKeyTextField;

    @FXML
    TextField sourceFileTextField;

    @FXML
    TextField destinationFileTextField;

    @FXML
    void encryptFile() throws Exception {
        PrivateKey privateKey = this.getPrivate(privateKeyTextField.getText());

        if (new File(sourceFileTextField.getText()).exists()) {
            this.encryptFile(this.getFileInBytes(new File(sourceFileTextField.getText())),
                    new File(destinationFileTextField.getText()),privateKey);
        } else {
            System.out.println("Nie ma takiego pliku źródłowego");
        }
    }

    @FXML
    void decryptFile() throws Exception {
        PublicKey publicKey = this.getPublic(publicKeyTextField.getText());
        if (new File(sourceFileTextField.getText()).exists()) {
            this.decryptFile(this.getFileInBytes(new File(sourceFileTextField.getText())),
                    new File(destinationFileTextField.getText()), publicKey);
        } else {
            System.out.println("Nie ma takiego pliku źródłowego");
        }
    }

    @FXML
    void changePublicKey(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Klucz publiczny");
        chooser.setInitialDirectory(start);
        publicKeyTextField.setText(chooser.showOpenDialog(stage).getAbsoluteFile().getPath());
    }

    @FXML
    void changePrivateKey(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Klucz prywatny");
        chooser.setInitialDirectory(start);
        privateKeyTextField.setText(chooser.showOpenDialog(stage).getAbsoluteFile().getPath());
    }

    @FXML
    void changeSourceFile(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Plik źródłowy");
        chooser.setInitialDirectory(start);
        sourceFileTextField.setText(chooser.showOpenDialog(stage).getAbsoluteFile().getPath());
    }

    @FXML
    void changeDestinationFile(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Plik wynikowy");
        chooser.setInitialDirectory(start);
        destinationFileTextField.setText(chooser.showOpenDialog(stage).getAbsoluteFile().getPath());
    }

    @FXML
    void newKeys(){
        GenerateKeys gk;
        try {
            gk = new GenerateKeys(1024);
            gk.createKeys();
            TextInputDialog td = new TextInputDialog("Nazwa klucza");
            td.setHeaderText("Podaj nazwę klucza do wygenerowania");
            td.setContentText(null);
            td.setTitle("Nowy klucz");
            td.showAndWait();
            gk.writeToFile(start.getPath()+"/"+td.getResult()+"-public", gk.getPublicKey().getEncoded());
            gk.writeToFile(start.getPath()+"/"+td.getResult()+"-private", gk.getPrivateKey().getEncoded());
        } catch (NoSuchAlgorithmException | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private PrivateKey getPrivate(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private PublicKey getPublic(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    private void writeToFile(File output, byte[] toWrite) throws IOException {
        FileOutputStream fos = new FileOutputStream(output);
        fos.write(toWrite);
        fos.flush();
        fos.close();
    }

    private void encryptFile(byte[] input, File output, PrivateKey key) throws IOException, GeneralSecurityException {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        writeToFile(output, this.cipher.doFinal(input));
    }

    private void decryptFile(byte[] input, File output, PublicKey key) throws IOException, GeneralSecurityException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        writeToFile(output, this.cipher.doFinal(input));
    }

    private byte[] getFileInBytes(File f) throws IOException {
        FileInputStream fis = new FileInputStream(f);
        byte[] fbytes = new byte[(int) f.length()];
        fis.read(fbytes);
        fis.close();
        return fbytes;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }
}
