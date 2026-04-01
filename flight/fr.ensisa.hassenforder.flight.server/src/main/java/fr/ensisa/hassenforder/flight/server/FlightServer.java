package fr.ensisa.hassenforder.flight.server;

import fr.ensisa.hassenforder.flight.database.Model;
import fr.ensisa.hassenforder.flight.server.images.ImagesCache;
import fr.ensisa.hassenforder.io.FileHelper;

public class FlightServer {

	private Model model = null;
	private ImagesCache cache = null;
	private TCPServer tcp = null;

	public void start () {
		model = new Model();
		model.initialize();
		cache = new ImagesCache();
		tcp = new TCPServer(new Business(model, cache));
		tcp.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileHelper.guessSrcDir("fr.ensisa.hassenforder.flight.server");
		FlightServer m = new FlightServer ();
		m.start();
	}

}
