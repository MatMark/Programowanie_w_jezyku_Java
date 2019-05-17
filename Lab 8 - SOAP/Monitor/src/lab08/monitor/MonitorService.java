package lab08.monitor;

import javax.xml.ws.Endpoint;

public class MonitorService {

    private MonitorImpl monitor;

    public MonitorService() {
        monitor = new MonitorImpl();
        Endpoint.publish("http://localhost:8080/WS/Monitor", monitor);
    }

    public static void main(String[] args) {
        MonitorService service = new MonitorService();
        System.out.println("service up");
    }
}