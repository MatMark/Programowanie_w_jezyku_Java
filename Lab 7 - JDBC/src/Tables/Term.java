package Tables;

public class Term {
    private int id;
    private String date;
    private String time;
    private int clientId;
    private int serviceId;
    private String clientFullName;
    private String serviceName;

    public Term(int id, String date, String time, String clientFullName, String serviceName) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.clientFullName = clientFullName;
        this.serviceName = serviceName;
    }

    public Term(int id, String date, String time, int clientId, int serviceId, String clientFullName, String serviceName) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.clientId = clientId;
        this.serviceId = serviceId;
        this.clientFullName = clientFullName;
        this.serviceName = serviceName;
    }

    public Term(int id, String date, String time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public Term(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "\n\nId zlecenia: " + id +
                "\nData: " + date +
                "\nGodzina: " + time +
                "\nKlient: " + clientFullName +
                "\nUsługa: " + serviceName;
    }
}
