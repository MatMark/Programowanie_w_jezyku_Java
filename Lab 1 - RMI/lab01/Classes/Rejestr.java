package lab01.Classes;

import lab01.Interfaces.IMonitor;
import lab01.Interfaces.IRejestr;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Rejestr implements IRejestr {

    //--------------------------------------------------------------------------------ATRYBUTY
    private Registry reg;
    private List<IMonitor> monitorList = new ArrayList<>();
    //------------------------------------------------------------------------------//ATRYBUTY

    //-------------------------------------------------------------------------------GET / SET

    //-----------------------------------------------------------------------------//GET / SET

    //-----------------------------------------------------------------------------KONSTRUKTORY

    public Rejestr() throws RemoteException {
        initRegister();
    }

    //---------------------------------------------------------------------------//KONSTRUKTORY

    //--------------------------------------------------------------------------------METODY
    @Override
    public int register(IMonitor o) throws RemoteException {
        System.out.println("Zarejestrowano '" + o.getName() + "'");
        monitorList.add(o);
        return 0;
    }

    @Override
    public boolean unregister(int id) throws RemoteException, NotBoundException {

        for (IMonitor monitor : monitorList) {
            if(monitor.getId() == id){
                reg.unbind(monitor.getName());
                monitorList.remove(monitor);
                System.out.println("Wyrejestrowano '" + monitor.getName() + "'");
                return true;
            }
        }
        System.out.println("Nie udało się wyrejestrować 'Monitor " + id + "'");
        return false;
    }

    private void initRegister() throws RemoteException {
        IRejestr ir = (IRejestr) UnicastRemoteObject.exportObject(this, Registry.REGISTRY_PORT);
        this.reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        this.reg.rebind("Rejestr", ir);

        Scanner input = new Scanner(System.in);
        while (true) {
            switch (input.next()) {
                case "show":
                    this.showMonitors();
                    break;

                case "shutdown":
                    UnicastRemoteObject.unexportObject(this, true);
                    System.exit(0);
                    break;

                default:
                    System.out.println("Prawidłowe komendy to:\nshow\nshutdown\n");
                    break;
            }
        }
    }

    @Override
    public List<IMonitor> getMonitors() {
        return monitorList;
    }

    private void showMonitors() throws RemoteException {
        System.out.println(Arrays.toString(reg.list()));
    }

    //------------------------------------------------------------------------------//METODY

    public static void main(String[] args) throws RemoteException {
        new Rejestr();
    }
}