package lab01.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import lab01.Classes.Menadzer;
import lab01.Interfaces.IMonitor;
import lab01.Interfaces.ISensor;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class MenadzerController implements Initializable {

    private Menadzer actualMenadzer;

    @FXML
    ListView<IMonitor> MonitorsList;

    @FXML
    ListView<ISensor> SensorsList;

    @FXML
    TextArea LogsTextArea;

    @FXML
    void Connect() throws RemoteException {
        SensorsList.getSelectionModel().getSelectedItem().setOutput(MonitorsList.getSelectionModel().getSelectedItem());
        addToLogs("Poł. " + MonitorsList.getSelectionModel().getSelectedItem().getName() + " z sensorem " +  SensorsList.getSelectionModel().getSelectedItem().getId());
    }

    @FXML
    void AddSensor() throws RemoteException {
        getActualMenadzer().addSensor();
        Refresh();
    }

    @FXML
    void DeleteSensor() throws RemoteException {
        getActualMenadzer().removeSensor(SensorsList.getSelectionModel().getSelectedItem());
        Refresh();
    }


    @FXML
    void Refresh() throws RemoteException {
        actualMenadzer.setMonitorList(actualMenadzer.getRejestr().getMonitors());
        MonitorsList.setItems(FXCollections.observableArrayList(getActualMenadzer().getMonitorList()));
        SensorsList.setItems(FXCollections.observableArrayList(getActualMenadzer().getSensorList()));
        addToLogs("Odświeżono");
    }

    public void addToLogs(String log)
    {
        LogsTextArea.appendText(log + "\n");
    }

    private Menadzer getActualMenadzer() {
        return actualMenadzer;
    }

    public void setActualMenadzer(Menadzer actualMenadzer) {
        this.actualMenadzer = actualMenadzer;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MonitorsList.setCellFactory(param -> new ListCell<IMonitor>() {
            @Override
            protected void updateItem(IMonitor item, boolean empty) {
                super.updateItem(item, empty);

                try {
                    if (empty || item == null || item.getName() == null) {
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
