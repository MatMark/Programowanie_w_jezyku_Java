package Tables;

public class Service {
    private int id;
    private int price;
    private String name;

    public Service(int price, String name) {
        this.price = price;
        this.name = name;
    }

    public Service(int ID, int price, String name) {
        this.id = ID;
        this.price = price;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
