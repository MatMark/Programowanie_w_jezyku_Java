package lab01.Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISensor extends Remote {
    void start() throws RemoteException;
    void stop() throws RemoteException;
    int getId() throws RemoteException;
    void setOutput(IMonitor o) throws RemoteException;
}