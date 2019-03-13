package lab01.Classes;

import lab01.Interfaces.IMonitor;
import lab01.Interfaces.ISensor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Sensor implements ISensor, Runnable {

    //--------------------------------------------------------------------------------ATRYBUTY
    private int sensorID;
    private IMonitor output;
    private ISensor is;
    private int data;
    private volatile boolean running = false;
    //------------------------------------------------------------------------------//ATRYBUTY

    //-------------------------------------------------------------------------------GET / SET
    private int getSensorID() {
        return sensorID;
    }
    public IMonitor getOutput() {
        return output;
    }
    private int getData() {
        return data;
    }
    private void setData(int data) {
        this.data = data;
    }
    private boolean isRunning() { return running; }
    private void setRunning(boolean running) { this.running = running; }
    @Override
    public int getId() {
        return getSensorID();
    }
    @Override
    public void setOutput(IMonitor out) throws RemoteException {
        if (out == null) output.setInput(null);
        this.output = out;
        if (out != null) output.setInput(is);
    }
    //-----------------------------------------------------------------------------//GET / SET

    //-----------------------------------------------------------------------------KONSTRUKTORY

    public Sensor(int sensorID) throws RemoteException {
        this.sensorID = sensorID;
        is = (ISensor) UnicastRemoteObject.exportObject(this, PortFactory.getSensorPort());
        Thread thread = new Thread(this);
        thread.start();
    }

    //---------------------------------------------------------------------------//KONSTRUKTORY

    //--------------------------------------------------------------------------------METODY
    @Override
    public void start() {
        System.out.println("Sensor " + getSensorID() + ": ON");
        this.setRunning(true);
    }

    @Override
    public void stop() {
        System.out.println("Sensor " + getSensorID() + ": OFF");
        this.setRunning(false);
    }

    @Override
    public void run() {

        int sample = 0;
        while (true) {
            if(isRunning()) {
                setData(new Random().nextInt(100));
                System.out.println("Sensor " + getSensorID() + " próbka nr " + sample++ + ": " + getData());
                if(output != null) {
                    try {
                        output.setReadings(Integer.toString(getData()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Sensor " + sensorID;
    }

    //------------------------------------------------------------------------------//METODY
}
