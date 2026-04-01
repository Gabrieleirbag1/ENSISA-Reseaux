package fr.ensisa.hassenforder.brutes.server;

import java.io.File;

import fr.ensisa.hassenforder.brutes.server.model.Model;
import fr.ensisa.hassenforder.io.FileHelper;

public class Application {

	private Model model = null;
	private TCPServer tcp = null;

	public void start () {
		model = new Model();
		tcp = new TCPServer(model);
		tcp.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileHelper.guessSrcDir("fr.ensisa.hassenforder.brutes.server");
		Application m = new Application ();
		m.start();
	}

}
