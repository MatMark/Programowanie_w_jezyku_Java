package lab01.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMonitor extends Remote {
    void setReadings(String readings) throws RemoteException;
    void setInput(ISensor o) throws RemoteException;
    int getId() throws RemoteException;
    String getName() throws RemoteException;
}