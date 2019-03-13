package lab01.Classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab01.Controllers.MenadzerController;
import lab01.Interfaces.IMonitor;
import lab01.Interfaces.IRejestr;
import lab01.Interfaces.ISensor;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class Menadzer extends Application{

    //--------------------------------------------------------------------------------ATRYBUTY
    private List<IMonitor> monitorList;
    private List<ISensor> sensorList = new ArrayList<>();
    private IRejestr rejestr;
    private MenadzerController controller;
    private int senID = 1;
    //------------------------------------------------------------------------------//ATRYBUTY

    //-------------------------------------------------------------------------------GET / SET
    public List<IMonitor> getMonitorList() {
        return monitorList;
    }
    public void setMonitorList(List<IMonitor> monitorList) {
        this.monitorList = monitorList;
    }
    public List<ISensor> getSensorList() {
        return sensorList;
    }
    public IRejestr getRejestr() {
        return rejestr;
    }
    public void setRejestr(IRejestr rejestr) {
        this.rejestr = rejestr;
    }
    public MenadzerController getController() {
        return controller;
    }
    public void setController(MenadzerController controller) {
        this.controller = controller;
    }
    //-----------------------------------------------------------------------------//GET / SET

    //-----------------------------------------------------------------------------KONSTRUKTORY
    public Menadzer(int numOfSensors) throws IOException, NotBoundException {
        initMenadzer(this, new Stage());
        for (int i = 0; i < numOfSensors; i++) {
            addSensor();
        }
    }

    public Menadzer() {}

    //---------------------------------------------------------------------------//KONSTRUKTORY

    //--------------------------------------------------------------------------------METODY
    @Override
    public void start(Stage primaryStage) throws Exception{
        new Menadzer(3);
    }

    private void initMenadzer(Menadzer menadzer, Stage primaryStage) throws IOException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
        menadzer.setRejestr((IRejestr) reg.lookup("Rejestr"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Forms/MenadzerFXML.fxml"));
        Parent root = loader.load();
        MenadzerController controller = loader.getController();
        controller.setActualMenadzer(menadzer);
        menadzer.setController(controller);
        primaryStage.setTitle("Menadzer");
        primaryStage.setScene(new Scene(root, 550, 275));
        primaryStage.show();
    }

    public void addSensor() throws RemoteException {
        getController().addToLogs("Dodano sensor " + senID);
        this.sensorList.add(new Sensor(senID++));
    }

    public void removeSensor(ISensor sensor) throws RemoteException {
        sensor.stop();
        sensor.setOutput(null);
        getController().addToLogs("Usunięto sensor " + sensor.getId());
        this.sensorList.remove(sensor);
    }
    //------------------------------------------------------------------------------//METODY

    public static void main(String[] args) {
        launch(args);
    }
}

