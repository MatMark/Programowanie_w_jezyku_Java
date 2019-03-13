package lab01.Classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lab01.Controllers.MonitorController;
import lab01.Interfaces.IMonitor;
import lab01.Interfaces.IRejestr;
import lab01.Interfaces.ISensor;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Monitor extends Application implements IMonitor {
    //--------------------------------------------------------------------------------ATRYBUTY
    private int monitorID;
    private String name;
    private ISensor sensor;
    private IRejestr rejestr;
    private IMonitor im;
    private MonitorController controller;
    //------------------------------------------------------------------------------//ATRYBUTY

    //-------------------------------------------------------------------------------GET / SET
    public IRejestr getRejestr() {
        return rejestr;
    }
    public void setRejestr(IRejestr rejestr) {
        this.rejestr = rejestr;
    }
    public int getMonitorID() {
        return monitorID;
    }
    public ISensor getSensor() {
        return sensor;
    }
    public void setController(MonitorController controller) { this.controller = controller; }
    public void setSensor(ISensor sensor) {
        this.sensor = sensor;
    }
    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void setReadings(String readings) {
        System.out.println(readings);
        controller.addText(readings);
    }
    @Override
    public void setInput(ISensor sen) throws RemoteException {
        if(sen != null)
        {
            setSensor(sen);
            controller.updateID();
        }
        else controller.resetValues();
    }

    @Override
    public int getId() {
        return getMonitorID();
    }
    //-----------------------------------------------------------------------------//GET / SET

    //-----------------------------------------------------------------------------KONSTRUKTORY
    public Monitor(int port) throws IOException, NotBoundException {
        this.monitorID = port;
        initMonitor(new Stage());
    }


    public Monitor(){}
    //---------------------------------------------------------------------------//KONSTRUKTORY

    //--------------------------------------------------------------------------------METODY
    @Override
    public void start(Stage primaryStage) throws Exception{ new Monitor(PortFactory.getMonitorPort()); }

    public void close() throws RemoteException, NotBoundException {
        System.out.println("Closing");
        if(getSensor() != null) getSensor().stop();
        rejestr.unregister(getMonitorID());
        UnicastRemoteObject.unexportObject(this, true);
    }

    public void initMonitor(Stage primaryStage) throws IOException, NotBoundException {
        im = (IMonitor) UnicastRemoteObject.exportObject(this, getMonitorID());
        Registry reg = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
        setName("Monitor " + this.getMonitorID());
        reg.rebind(name, im);
        this.setRejestr((IRejestr) reg.lookup("Rejestr"));
        this.getRejestr().register(im);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Forms/MonitorFXML.fxml"));
        Parent root = loader.load();
        MonitorController controller = loader.getController();
        controller.setActualMonitor(this);
        this.setController(controller);
        primaryStage.setTitle("Monitor " + this.getMonitorID());
        primaryStage.setScene(new Scene(root, 440, 325));
        primaryStage.setOnCloseRequest((WindowEvent event1) -> {
            try {
                close();
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        });
        primaryStage.show();
    }

    @Override
    public String toString() {
        return "Monitor " + monitorID;
    }

    //------------------------------------------------------------------------------//METODY

    public static void main(String[] args) { launch(args); }
}

