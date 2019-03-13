package lab01.Classes;

import java.util.concurrent.atomic.AtomicInteger;

public class PortFactory {

    private static AtomicInteger monitorPort = new AtomicInteger(1000);
    private static AtomicInteger sensorPort = new AtomicInteger(3000);

    public static int getMonitorPort() {
        if(monitorPort.get() < 3000) return monitorPort.incrementAndGet();
        else {
            monitorPort = new AtomicInteger(1000);
            return monitorPort.incrementAndGet();
        }
    }

    public static int getSensorPort() {
        if(sensorPort.get() < 6000) return sensorPort.incrementAndGet();
        else {
            sensorPort = new AtomicInteger(3000);
            return sensorPort.incrementAndGet();
        }
    }

}
