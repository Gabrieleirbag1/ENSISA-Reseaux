package fr.ensisa.hassenforder.flight.server.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ensisa.hassenforder.flight.database.SeatComfort;
import fr.ensisa.hassenforder.flight.database.SeatCount;
import fr.ensisa.hassenforder.flight.database.User;
import fr.ensisa.hassenforder.flight.network.Protocol;
import fr.ensisa.hassenforder.flight.server.network.dto.Authenticator;
import fr.ensisa.hassenforder.flight.server.network.dto.TravelBooking;
import fr.ensisa.hassenforder.flight.server.network.dto.TravelFilter;
import fr.ensisa.hassenforder.network.BasicAbstractReader;

public class ServerTCPReader extends BasicAbstractReader {

	private User user;
	private Authenticator authenticator;
	private TravelFilter travelFilter;
	private TravelBooking travelBooking;
	private long ticketId;
	private String name;

	public ServerTCPReader(InputStream inputStream) {
		super (inputStream);
	}

	private void eraseFields() {
		user = null;
		authenticator = null;
		travelFilter = null;
		travelBooking = null;
		ticketId = 0;
		name = null;
	}

	private Authenticator readAuthenticator() {
		long id = readLong();
		String token = readString();
		return new Authenticator(id, token);
	}

	private User readUser() {
		String name = readString();
		String mail = readString();
		String passwd = readString();
		int cash = readInt();
		User user = new User(name, mail, passwd, cash);
		return user;
	}

	private void readConnect() {
		String mail = readString();
		String passwd = readString();
		user = new User("", mail, passwd, 0);
	}

	private void readDisconnect() {
		authenticator = readAuthenticator();
	}

	private void readGetAccount() {
		authenticator = readAuthenticator();
	}

	private void readCreateAccount() {
		user = readUser();
	}

	private void readUpdateAccount() {
		authenticator = readAuthenticator();
		user = readUser();
	}

	private void readGetAllTravels() {
	}

	private void readGetFilteredTravels() {
		int optional = readInt();
		String from = null;
		String to = null;
		Date day = null;
		if ((optional & Protocol.WITH_FROM) != 0) from = readString();
		if ((optional & Protocol.WITH_TO) != 0) to = readString();
		if ((optional & Protocol.WITH_DAY) != 0) day = new Date(readLong());
		travelFilter = new TravelFilter(from, to, day);
	}

	private SeatComfort readSeatComfort() {
		return SeatComfort.valueOf(readString());
	}

	private SeatCount readSeatCount() {
		SeatComfort comfort = readSeatComfort();
		int count = readInt();
		return new SeatCount(comfort, count);
	}

	private void readBookTravel() {
		authenticator = readAuthenticator();
		long travelId = readLong();
		int size = readInt();
		List<SeatCount> counts = new ArrayList<>(size);
		for (int i=0;i< size;++i) {
			counts.add(readSeatCount());
		}
		travelBooking = new TravelBooking(travelId, counts);
	}

	private void readGetTicket() {
		authenticator = readAuthenticator();
		ticketId = readLong();
	}

	private void readPayTicket() {
		authenticator = readAuthenticator();
		ticketId = readLong();
	}

	private void readGetAllTickets() {
		authenticator = readAuthenticator();
	}

	private void readCancelTicket() {
		authenticator = readAuthenticator();
		ticketId = readLong();
	}

	private void readGetImage() {
		name = readString();
	}

	public void receive() {
		type = readInt ();
		eraseFields ();
		switch (type) {
		case 0 : break;
		case Protocol.REQUEST_CONNECT:				readConnect (); break;
		case Protocol.REQUEST_DISCONNECT:			readDisconnect (); break;
		case Protocol.REQUEST_GET_ACCOUNT:			readGetAccount (); break;
		case Protocol.REQUEST_CREATE_ACCOUNT:		readCreateAccount (); break;
		case Protocol.REQUEST_UPDATE_ACCOUNT:		readUpdateAccount (); break;
		case Protocol.REQUEST_GET_ALL_TRAVELS:		readGetAllTravels (); break;
		case Protocol.REQUEST_GET_FILTERED_TRAVELS:	readGetFilteredTravels (); break;
		case Protocol.REQUEST_BOOK_TRAVEL:			readBookTravel (); break;
		case Protocol.REQUEST_GET_TICKET:			readGetTicket (); break;
		case Protocol.REQUEST_PAY_TICKET:			readPayTicket (); break;
		case Protocol.REQUEST_GET_ALL_TICKETS:		readGetAllTickets (); break;
		case Protocol.REQUEST_CANCEL_TICKET:		readCancelTicket (); break;
		case Protocol.REQUEST_GET_IMAGE:			readGetImage (); break;
        }
    }

	public User getUser() {
		return user;
	}

	public Authenticator getAuthenticator() {
		return authenticator;
	}

	public TravelFilter getTravelFilter() {
		return travelFilter;
	}

	public TravelBooking getTicketBooking() {
		return travelBooking;
	}

	public long getTicketId() {
		return ticketId;
	}

	public String getName() {
		return name;
	}

}
