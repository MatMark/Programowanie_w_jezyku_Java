import Interfaces.IRoom;
import Interfaces.IServer;
import Interfaces.ServerMBean;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Server implements IServer, ServerMBean {

    private Registry reg;
    private int nextPort = 8081;
    private int maxClients = 3;

    private List<IRoom> rooms = new ArrayList<>();

    private void init() throws RemoteException {
        IServer server = (IServer) UnicastRemoteObject.exportObject(this, Registry.REGISTRY_PORT);
        this.reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        this.reg.rebind("Server", server);
        System.out.println("Serwer utworzony na porcie " + Registry.REGISTRY_PORT);
        newRoom("Pokój testowy 1");
        newRoom("Pokój testowy 2");
        newRoom("Pokój testowy 3");
    }

    public static void main(String[] args) throws RemoteException, MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
//        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
//        ObjectName objName = new ObjectName("lab13:server=XXX");
        Server server = new Server();
//        mbs.registerMBean(server, objName);
        server.init();
    }

    @Override
    public IRoom newRoom(String name) throws RemoteException {
        Room room = new Room(name, nextPort);
        room.setMaxClients(maxClients);

        IRoom ir = (IRoom) UnicastRemoteObject.exportObject(room, nextPort++);
        Registry reg = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
        reg.rebind(name, ir);
        ir.appendMessages("Utworzono nowy pokój: " + name);
        rooms.add(ir);

        return ir;
    }

    @Override
    public List<IRoom> getRooms() throws RemoteException {
        return rooms;
    }

    @Override
    public int getMaxClients() {
        return maxClients;
    }

    @Override
    public void setMaxClients(int value) throws RemoteException {
        this.maxClients = value;
        for (IRoom room: rooms) {
            room.setMaxClients(value);
        }
    }
}
