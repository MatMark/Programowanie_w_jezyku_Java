package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    String getName() throws RemoteException;
}
