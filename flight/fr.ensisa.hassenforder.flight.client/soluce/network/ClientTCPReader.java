package fr.ensisa.hassenforder.flight.client.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import fr.ensisa.hassenforder.flight.client.model.Account;
import fr.ensisa.hassenforder.flight.client.model.Authenticator;
import fr.ensisa.hassenforder.flight.client.model.Connected;
import fr.ensisa.hassenforder.flight.client.model.PaymentResult;
import fr.ensisa.hassenforder.flight.client.model.PaymentState;
import fr.ensisa.hassenforder.flight.client.model.Seat;
import fr.ensisa.hassenforder.flight.client.model.SeatComfort;
import fr.ensisa.hassenforder.flight.client.model.SeatCount;
import fr.ensisa.hassenforder.flight.client.model.SeatState;
import fr.ensisa.hassenforder.flight.client.model.Ticket;
import fr.ensisa.hassenforder.flight.client.model.TicketState;
import fr.ensisa.hassenforder.flight.client.model.Travel;
import fr.ensisa.hassenforder.flight.client.model.User;
import fr.ensisa.hassenforder.flight.network.Protocol;
import fr.ensisa.hassenforder.network.BasicAbstractReader;

public class ClientTCPReader extends BasicAbstractReader {

	private Connected connected;
	private Account account;
	private List<Travel> travels;
	private long ticketId;
	private Ticket ticket;
	private PaymentResult paymentResult;
	private List<Ticket> tickets;
	private byte [] image;

    public ClientTCPReader(InputStream inputStream) {
        super(inputStream);
    }

    private void eraseFields() {
    	connected = null;
    	account = null;
    	travels = null;
    	ticketId = 0;
    	ticket = null;
    	paymentResult = null;
    	tickets = null;
    	image = null;
    }

    private User readUser () {
    	long id = readLong();
    	String name = readString();
    	int cash = readInt();
    	return new User (id, name, cash);
    }

    private Authenticator readAuthenticator () {
    	long id = readLong();
    	String token = readString();
    	return new Authenticator (id, token);
    }

    private Account readAccount () {
    	long id = 0;
    	String name = readString();
    	String mail = readString();
    	String passwd = "";
    	int cash = readInt();
    	return new Account (id, name, mail, passwd, cash);
    }

    private Connected readConnected () {
    	User user = readUser();
    	Authenticator authenticator = readAuthenticator();
    	return new Connected(authenticator, user);
    }
    
    private Date readDate() {
    	return new Date (readLong());
    }

    private SeatCount readSeatCount () {
    	SeatComfort comfort = readSeatComfort();
    	int count = readInt();
    	return new SeatCount(comfort, count);
    }

    private List<SeatCount> readSeatCounts() {
		List<SeatCount> counts = new ArrayList<>();
		int size = readInt();
		for (int i=0; i< size; ++i) {
			counts.add(readSeatCount());
		}
		return counts;
	}

    private Travel readTravel() {
		long id = readLong();
		String from = readString();
		String to = readString();
		Date day = readDate();
		List<SeatCount> counts = readSeatCounts();
		return new Travel(id, from, to, day, counts);
	}

	private List<Travel> readTravels() {
		List<Travel> travels = new ArrayList<Travel>();
		int size = readInt();
		for (int i=0; i< size; ++i) {
			travels.add(readTravel());
		}
		return travels;
	}

	private long readBookedTravel() {
		long ticketId = readLong();
		return ticketId;
	}

	private PaymentState readPaymentState () {
		String state = readString();
		return PaymentState.valueOf(state);
	}

	private PaymentResult readPayment() {
		PaymentState state = readPaymentState ();
		int value = readInt();
		return new PaymentResult(state, value);
	}

	private TicketState readTicketState () {
		String state = readString();
		return TicketState.valueOf(state);
	}

	private SeatComfort readSeatComfort () {
		String state = readString();
		return SeatComfort.valueOf(state);
	}

	private SeatState readSeatState () {
		String state = readString();
		return SeatState.valueOf(state);
	}

	private Seat readSeat() {
		long id = readLong();
		int position = readInt();
		long travelId = readLong();
		long ticketId = readLong();
		SeatComfort comfort = readSeatComfort();
		SeatState state = readSeatState();
		int cost = readInt();
		return new Seat(id, travelId, ticketId, comfort, position, state, cost);
	}
	
	private List<Seat> readSeats() {
		List<Seat> seats = new ArrayList<Seat>();
		int size = readInt();
		for (int i=0; i< size; ++i) {
			seats.add(readSeat());
		}
		return seats;
	}

	private Ticket readTicket() {
		long id = readLong();
		long userId = readLong();
		long travelId = readLong();
		TicketState state = readTicketState();
		List<Seat> seats = readSeats();
		String plane = readString();
		String from = readString();
		String to = readString();
		Date day = readDate();
		return new Ticket(id, userId, travelId, state, seats, plane, from, to, day);
	}

	private List<Ticket> readTickets() {
		List<Ticket> tickets = new ArrayList<Ticket>();
		int size = readInt();
		for (int i=0; i< size; ++i) {
			tickets.add(readTicket());
		}
		return tickets;
	}

	private byte [] readImage() {
		int size = readInt();
		byte [] content = new byte[size];
		for (int i=0; i< size; ++i) {
			content[i] = readByte();
		}
		return content;
	}
	
    public void receive() {
        type = readInt();
        eraseFields();
        switch (type) {
        case Protocol.REPLY_NOWAY:			break;
        case Protocol.REPLY_KO:				break;
        case Protocol.REPLY_OK:				break;
        case Protocol.REPLY_USER:			connected = readConnected (); break;
        case Protocol.REPLY_ACCOUNT:		account = readAccount (); break;
        case Protocol.REPLY_TRAVELS:		travels = readTravels (); break;
        case Protocol.REPLY_BOOKED_TRAVEL:	ticketId = readBookedTravel (); break;
        case Protocol.REPLY_TICKET:			ticket = readTicket (); break;
        case Protocol.REPLY_PAYMENT:		paymentResult = readPayment (); break;
        case Protocol.REPLY_TICKETS:		tickets = readTickets (); break;
        case Protocol.REPLY_IMAGE:			image = readImage (); break;
        }
    }


	public Connected getConnected () {
		return connected;
	}

	public Account getAccount() {
		return account;
	}

	public Collection<Travel> getTravels() {
		return travels;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public long getTicketId() {
		return ticketId;
	}

	public PaymentResult getPaymentResult() {
		return paymentResult;
	}

	public Collection<Ticket> getTickets() {
		return tickets;
	}

	public byte[] getImage() {
		return image;
	}

}
