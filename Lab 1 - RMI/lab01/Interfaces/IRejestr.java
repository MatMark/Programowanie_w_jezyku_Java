package lab01.Interfaces;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRejestr extends Remote {
    int register(IMonitor o) throws RemoteException;
    boolean unregister(int id) throws RemoteException, NotBoundException;
    List<IMonitor> getMonitors() throws RemoteException;
}