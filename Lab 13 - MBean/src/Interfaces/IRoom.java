package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRoom extends Remote {
    List<String> getMessages() throws RemoteException;
    List<IClient> getClients() throws RemoteException;
    String getName() throws RemoteException;
    void setMaxClients(int value) throws RemoteException;
    int getPort() throws RemoteException;
    void appendMessages(String msg) throws RemoteException;
    boolean join(IClient client) throws RemoteException;
    void quit(IClient client) throws RemoteException;
}
