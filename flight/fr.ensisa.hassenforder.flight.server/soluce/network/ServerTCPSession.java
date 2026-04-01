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

	private void processGetImage(ServerTCPReader reader, ServerTCPWriter writer) {
		String imageName = reader.getName();
		byte [] image = business.getImageByName(imageName);
		if (image == null) {
			writer.createKO();
		} else {
			writer.createReplyImage(image);
		}
	}

	private void processCancelTicket(ServerTCPReader reader, ServerTCPWriter writer) {
		Authenticator authenticator = reader.getAuthenticator();
		if (! business.validateAuthenticator(authenticator.getId(), authenticator.getToken())) {
			writer.createNOWAY();
			return;
		}
		PaymentResult result = business.cancelTicket (reader.getTicketId());
		writer.createReplyPaymentResult(result);
	}
	
	private void processGetAllTickets(ServerTCPReader reader, ServerTCPWriter writer) {
		Authenticator authenticator = reader.getAuthenticator();
		if (! business.validateAuthenticator(authenticator.getId(), authenticator.getToken())) {
			writer.createNOWAY();
			return;
		}
		Collection<Ticket> tickets = business.getAllTickets (authenticator.getId());
		Collection<TicketReply> reply = dto.buildTicketReply(tickets, business.getModel());
		writer.createReplyTickets(reply);
	}

	private void processPayTicket(ServerTCPReader reader, ServerTCPWriter writer) {
		Authenticator authenticator = reader.getAuthenticator();
		if (! business.validateAuthenticator(authenticator.getId(), authenticator.getToken())) {
			writer.createNOWAY();
			return;
		}
		PaymentResult result = business.payTicket (reader.getTicketId());
		writer.createReplyPaymentResult(result);
	}

	private void processGetTicket(ServerTCPReader reader, ServerTCPWriter writer) {
		Authenticator authenticator = reader.getAuthenticator();
		if (! business.validateAuthenticator(authenticator.getId(), authenticator.getToken())) {
			writer.createNOWAY();
			return;
		}
		Ticket ticket = business.getTicket (reader.getTicketId());
		if (ticket == null) {
			writer.createKO();
		} else {
			TicketReply reply = dto.buildTicketReply(ticket, business.getModel());
			writer.createReplyTicket(reply);
		}
	}

	private void processBookTravel(ServerTCPReader reader, ServerTCPWriter writer) {
		Authenticator authenticator = reader.getAuthenticator();
		if (! business.validateAuthenticator(authenticator.getId(), authenticator.getToken())) {
			writer.createNOWAY();
			return;
		}
		TravelBooking booking = reader.getTicketBooking();
		Ticket ticket = business.bookTravel (authenticator.getId(), booking);
		if (ticket == null) {
			writer.createKO();
		} else {
			writer.createReplyBookedTravel(ticket);
		}
	}

	private void processGetFilteredTravels(ServerTCPReader reader, ServerTCPWriter writer) {
		TravelFilter filter = reader.getTravelFilter();
		Collection<Travel> travels = business.getFilteredTravels(filter.getFrom(), filter.getTo(), filter.getDay());
		Collection<TravelReply> reply = dto.buildTravelReply(travels, business.getModel());
		writer.createReplyTravels(reply);
	}

	private void processGetAllTravels(ServerTCPReader reader, ServerTCPWriter writer) {
		Collection<Travel> travels = business.getAllTravels();
		Collection<TravelReply> reply = dto.buildTravelReply(travels, business.getModel());
		writer.createReplyTravels(reply);
	}

	private void processUpdateAccount(ServerTCPReader reader, ServerTCPWriter writer) {
		Authenticator authenticator = reader.getAuthenticator();
		if (! business.validateAuthenticator(authenticator.getId(), authenticator.getToken())) {
			writer.createNOWAY();
			return;
		}
		User user = reader.getUser();
		boolean result = business.updateUser(user);
		if (! result) {
			writer.createKO();
		} else {
			writer.createOK();
		}
	}

	private void processCreateAccount(ServerTCPReader reader, ServerTCPWriter writer) {
		User user = reader.getUser();
		boolean result = business.createUser(user);
		if (! result) {
			writer.createKO();
		} else {
			writer.createOK();
		}
	}

	private void processGetAccount(ServerTCPReader reader, ServerTCPWriter writer) {
		Authenticator authenticator = reader.getAuthenticator();
		if (! business.validateAuthenticator(authenticator.getId(), authenticator.getToken())) {
			writer.createNOWAY();
			return;
		}
		User user = business.getUserById(authenticator.getId());
		if (user == null) {
			writer.createKO();
		} else {
			writer.createReplyAccount(user);
		}
	}

	private void processConnect(ServerTCPReader reader, ServerTCPWriter writer) {
		User user = reader.getUser();
		long userId = business.validateConnection(user);
		if (userId == 0) {
			writer.createKO();
		} else {
			business.connect(userId);
			User actual = business.getUserById(userId);
			String token = business.getToken(userId);
			writer.createReplyConnected(actual, token);
		}
	}

	private void processDisconnect(ServerTCPReader reader, ServerTCPWriter writer) {
		Authenticator authenticator = reader.getAuthenticator();
		if (! business.validateAuthenticator(authenticator.getId(), authenticator.getToken())) {
			writer.createNOWAY();
			return;
		}
		business.disconnect(authenticator.getId());
		writer.createOK();
	}

	public boolean operate() {
		try {
			boolean result = true;
			ServerTCPWriter writer = new ServerTCPWriter (connection.getOutputStream());
			ServerTCPReader reader = new ServerTCPReader (connection.getInputStream());
			reader.receive ();
			switch (reader.getType ()) {
			case 0 : result = false; break; // socket closed
			case Protocol.REQUEST_CONNECT:				processConnect (reader, writer); break;
			case Protocol.REQUEST_DISCONNECT:			processDisconnect (reader, writer); break;
			case Protocol.REQUEST_GET_ACCOUNT:			processGetAccount (reader, writer); break;
			case Protocol.REQUEST_CREATE_ACCOUNT:		processCreateAccount (reader, writer); break;
			case Protocol.REQUEST_UPDATE_ACCOUNT:		processUpdateAccount (reader, writer); break;
			case Protocol.REQUEST_GET_ALL_TRAVELS:		processGetAllTravels (reader, writer); break;
			case Protocol.REQUEST_GET_FILTERED_TRAVELS:	processGetFilteredTravels (reader, writer); break;
			case Protocol.REQUEST_BOOK_TRAVEL:			processBookTravel (reader, writer); break;
			case Protocol.REQUEST_GET_TICKET:			processGetTicket (reader, writer); break;
			case Protocol.REQUEST_PAY_TICKET:			processPayTicket (reader, writer); break;
			case Protocol.REQUEST_GET_ALL_TICKETS:		processGetAllTickets (reader, writer); break;
			case Protocol.REQUEST_CANCEL_TICKET:		processCancelTicket (reader, writer); break;
			case Protocol.REQUEST_GET_IMAGE:			processGetImage (reader, writer); break;
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
