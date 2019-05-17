package lab08.automat;

import lab08.monitor.Monitor;
import lab08.monitor.MonitorImplService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import static java.lang.Thread.sleep;

@WebService(endpointInterface= "lab08.automat.Automat")
public class AutomatImpl implements Automat, Runnable {

	int ID = 1;
	List<Drink> drinks = new ArrayList<Drink>();
	Monitor monitor;

	public AutomatImpl() {
		drinks.add(new Drink(0,"Coca-cola"));
		drinks.add(new Drink(0,"Sprite"));
		drinks.add(new Drink(0,"Pepsi"));
		drinks.add(new Drink(0,"Ice tea"));

		Thread thread = new Thread(this);
		thread.start();

		MonitorImplService service = null;
		try {
			service = new MonitorImplService(new URL("http://localhost:8080/WS/Monitor?wsdl"));
			monitor = service.getMonitorImplPort();
			ID = monitor.getFreePort();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void register(){
		System.out.println("Uruchomiono automat na porcie: " + ID);
		Endpoint.publish("http://localhost:"+ID+"/WS/Automat", this);
		monitor.registerAutomat(ID);
	}

	public void unregister(){
		System.out.println("Wyrejestrowano");
		monitor.unregisterAutomat(ID);
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public void setDrinks(List<Drink> drinks) {
		this.drinks = drinks;
	}

	private void changeValues() throws InterruptedException {
		for (Drink drink: drinks) {
			drink.setCount(new Random().nextInt(100));
		}
		sleep(10000);
		monitor.updateRequest(ID);
	}

	@Override
	public int getId() {
		return ID;
	}

	@Override
	public List<Drink> getItems() {
		return drinks;
	}

	@Override
	public void run() {
		try {
			while (true) {
				changeValues();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
