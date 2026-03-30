package fr.ensisa.hassenforder.brutes.server;

import java.net.ServerSocket;
import java.net.Socket;

import fr.ensisa.hassenforder.brutes.network.Protocol;
import fr.ensisa.hassenforder.brutes.server.model.Model;
import fr.ensisa.hassenforder.brutes.server.network.TCPSession;

public class TCPServer extends Thread {

	private ServerSocket server = null;
	private Model model = null;

	public TCPServer(Model model) {
		super();
		this.model = model;
	}

	public void run () {
		try {
			server = new ServerSocket (Protocol.BRUTES_TCP_PORT);
			while (true) {
				Socket connection = server.accept();
				TCPSession session = new TCPSession (connection, model);
				session.start ();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
