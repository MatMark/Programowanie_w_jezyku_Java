package lab08.automat;

import java.io.IOException;

public class AutomatService {

	public static void main(String[] args) throws IOException {
	    AutomatImpl automat = new AutomatImpl();
        automat.register();
        System.in.read();
        automat.unregister();
	}
}
