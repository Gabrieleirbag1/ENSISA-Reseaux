package fr.ensisa.hassenforder.flight.server;

import java.net.ServerSocket;
import java.net.Socket;

import fr.ensisa.hassenforder.flight.network.Protocol;
import fr.ensisa.hassenforder.flight.server.network.ServerTCPSession;

public class TCPServer extends Thread {

	private ServerSocket server = null;
	private Business business;
	
	public TCPServer(Business business) {
		super();
		this.business = business;
	}

	public void run () {
		try {
			server = new ServerSocket (Protocol.TRAVEL_TCP_PORT);
			while (true) {
				Socket connection = server.accept();
				ServerTCPSession session = new ServerTCPSession (connection, business);
				session.start ();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
