package fr.ensisa.hassenforder.flight.server.network;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

import fr.ensisa.hassenforder.flight.database.Ticket;
import fr.ensisa.hassenforder.flight.database.Travel;
import fr.ensisa.hassenforder.flight.database.User;
import fr.ensisa.hassenforder.flight.network.Protocol;
import fr.ensisa.hassenforder.flight.server.Business;
import fr.ensisa.hassenforder.flight.server.network.dto.Authenticator;
import fr.ensisa.hassenforder.flight.server.network.dto.PaymentResult;
import fr.ensisa.hassenforder.flight.server.network.dto.TicketReply;
import fr.ensisa.hassenforder.flight.server.network.dto.TravelBooking;
import fr.ensisa.hassenforder.flight.server.network.dto.TravelFilter;
import fr.ensisa.hassenforder.flight.server.network.dto.TravelReply;
import fr.ensisa.hassenforder.flight.server.network.dto.dto;

public class ServerTCPSession extends Thread {

	private Socket connection;
	private Business business;
	
	public ServerTCPSession(Socket connection, Business business) {
		this.connection = connection;
		this.business = business;
	}

	public boolean operate() {
		try {
			boolean result = true;
			ServerTCPWriter writer = new ServerTCPWriter (connection.getOutputStream());
			ServerTCPReader reader = new ServerTCPReader (connection.getInputStream());
			reader.receive ();
			switch (reader.getType ()) {
			case 0 : result = false; break; // socket closed
			default: result = false; // connection jammed
			}
			if (result) writer.send ();
			return result;
		} catch (IOException e) {
			return false;
		}
	}

	public void run() {
		while (true) {
			if (! operate())
				break;
		}
		try {
			if (connection != null) connection.close();
		} catch (IOException e) {
		}
	}

}
