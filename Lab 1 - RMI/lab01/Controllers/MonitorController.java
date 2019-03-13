package lab01.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lab01.Classes.Monitor;

import java.rmi.RemoteException;


public class MonitorController {

private Monitor actualMonitor;

    @FXML
    Button StartButton;

    @FXML
    Button StopButton;

    @FXML
    TextField IdTextField;

    @FXML
    TextArea DataTextArea;

    @FXML
    void Start() throws RemoteException {
        getActualMonitor().getSensor().start();
        DataTextArea.appendText("Sensor " + actualMonitor.getSensor().getId() + " ON\n");
        StartButton.setDisable(true);
        StopButton.setDisable(false);
    }

    @FXML
    void Stop() throws RemoteException {
        getActualMonitor().getSensor().stop();
        DataTextArea.appendText("Sensor " + actualMonitor.getSensor().getId() + " OFF\n");
        StartButton.setDisable(false);
        StopButton.setDisable(true);
    }

    public void addText(String str)
    {
        if (DataTextArea.getText().length() > 100) DataTextArea.setText("Czyszczenie konsoli\n");
        DataTextArea.appendText(str + "\n");
    }

    public void updateID() throws RemoteException {
        DataTextArea.appendText("Poł. z sen.: " + actualMonitor.getSensor().getId() + "\n");
        IdTextField.setText("" + actualMonitor.getSensor().getId());
        StartButton.setDisable(false);
        if(actualMonitor.getSensor() == null) resetValues();
    }

    public void resetValues()
    {
        DataTextArea.setText("");
        IdTextField.setText("Brak");
        StartButton.setDisable(true);
        StopButton.setDisable(true);
    }

    private Monitor getActualMonitor() { return actualMonitor; }

    public void setActualMonitor(Monitor actualMonitor) { this.actualMonitor = actualMonitor; }
}