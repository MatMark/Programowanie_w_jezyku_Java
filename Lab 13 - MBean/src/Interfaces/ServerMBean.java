package Interfaces;

import java.rmi.RemoteException;

public interface ServerMBean {
    public int getMaxClients();
    public void setMaxClients(int value) throws RemoteException;
}
