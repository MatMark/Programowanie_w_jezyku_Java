import Interfaces.IClient;
import Interfaces.IRoom;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Room implements IRoom {

    private int port;
    private String name = "Unnamed";
    private int maxClients = 3;
    private List<String> messages = new ArrayList<>();
    private List<IClient> clients = new ArrayList<>();


    public Room(String name, int port) {
        this.port = port;
        this.name = name;
    }

    public int getMaxClients() {
        return maxClients;
    }

    @Override
    public List<String> getMessages() throws RemoteException {
        return messages;
    }

    @Override
    public List<IClient> getClients() throws RemoteException {
        return clients;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public void setMaxClients(int value) throws RemoteException {
        this.maxClients = value;
    }

    @Override
    public int getPort() throws RemoteException {
        return port;
    }

    @Override
    public void appendMessages(String msg) throws RemoteException {
        messages.add(msg + "\n");
    }

    @Override
    public boolean join(IClient client) throws RemoteException {
        if (clients.size() <= maxClients){
            clients.add(client);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void quit(IClient client) throws RemoteException {
        clients.remove(client);
    }
}
