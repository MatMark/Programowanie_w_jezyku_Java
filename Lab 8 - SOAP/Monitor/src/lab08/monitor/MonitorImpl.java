package lab08.monitor;

import lab08.automat.Automat;
import lab08.automat.AutomatImplService;
import lab08.automat.Drink;

import javax.jws.WebService;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@WebService(endpointInterface= "lab08.monitor.Monitor")
public class MonitorImpl implements Monitor {

    private static int freePort = 8080;
    public List<Automat> automats = new ArrayList<Automat>();

    @Override
    public int getFreePort() {
        freePort++;
        return freePort;
    }

    @Override
    public void registerAutomat(int port) {
        AutomatImplService service = null;
        String url = "http://localhost:"+port+"/WS/Automat?wsdl";
        try {
            service = new AutomatImplService(new URL(url));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Automat automat = service.getAutomatImplPort();
        automats.add(automat);
    }

    @Override
    public void unregisterAutomat(int port) {
        for (Automat automat: automats) {
            if(automat.getId() == port) {
                automats.remove(automat);
            }
            System.out.println(automats);
        }
    }

    @Override
    public void updateRequest(int port) {
        for (Automat automat : automats) {
            if(automat.getId() == port) {
                List<Drink> items = automat.getItems();
                System.out.println("\nAutomat nr " + automat.getId());
                for (Drink drink : items) {
                    System.out.println(drink.getName() + " : " + drink.getCount());
                }
            }
        }
    }
}
