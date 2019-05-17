package lab08.monitor;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface Monitor {

    @WebMethod
    public int getFreePort();

    @WebMethod
    public void registerAutomat(int port);

    @WebMethod
    public void unregisterAutomat(int port);

    @WebMethod
    public void updateRequest(int port);
}
