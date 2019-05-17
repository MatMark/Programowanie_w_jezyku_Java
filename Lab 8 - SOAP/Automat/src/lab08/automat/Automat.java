package lab08.automat;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface Automat {
	@WebMethod public int getId();
	@WebMethod public List<Drink> getItems();
}
