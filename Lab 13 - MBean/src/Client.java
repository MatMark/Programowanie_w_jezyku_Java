import Interfaces.IClient;
import Interfaces.IRoom;
import Interfaces.IServer;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Random;

public class Client implements IClient, Serializable {

    private StartPanel startPanel;

    private String name = "Unnamed";
    private List<IRoom> roomList;
    public IRoom room;
    private IServer server;

    public static void main(String[] args) throws IOException, NotBoundException {
        Client client = new Client("Klient " + new Random().nextInt(9999));
        client.init();
    }

    public Client(String name) throws RemoteException {
        this.name = name;
    }

    private void init() throws IOException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
        this.server = ((IServer) reg.lookup("Server"));
        startPanel = new StartPanel(this);
        refreshStart();
    }

    private void checkRooms() throws RemoteException {
        roomList = server.getRooms();
    }

    public void joinToRoom(IRoom r) throws RemoteException {
        boolean resp = r.join(this);
        if (resp){
            room = r;
        }else{
            System.out.println("Zbyt dużo osób w pokoju");
        }
    }

    public void refreshStart() throws RemoteException {
        checkRooms();
        startPanel.setList();
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IRoom> getRoomList() {
        return roomList;
    }

    public IServer getServer() {
        return server;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }
}
