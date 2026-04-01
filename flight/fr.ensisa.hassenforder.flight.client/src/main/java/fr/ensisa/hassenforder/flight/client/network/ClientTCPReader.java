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
    private Collection<Travel> travels;
    private Long ticketId;
    private Ticket ticket;
    private PaymentResult paymentResult;
    private Collection<Ticket> tickets;
    private byte[] image;

    public ClientTCPReader(InputStream inputStream) {
        super(inputStream);
    }

    private void eraseFields() {
        connected = null;
        account = null;
        travels = null;
        ticketId = null;
        ticket = null;
        paymentResult = null;
        tickets = null;
        image = null;
    }

    private Authenticator readAuthenticator() {
        long id = readLong();
        String token = readString();
        return new Authenticator(id, token);
    }

    private User readUser() {
        long id = readLong();
        String name = readString();
        int cash = readInt();
        return new User(id, name, cash);
    }

    private void readConnected() {
        User user = readUser();
        Authenticator authenticator = readAuthenticator();
        connected = new Connected(authenticator, user);
    }

    private void readAccount() {
        String name = readString();
        String mail = readString();
        int cash = readInt();
        account = new Account(0, name, mail, "", cash);
    }

    private Date readDate() {
        return new Date(readLong());
    }

    private SeatComfort readSeatComfort() {
        return SeatComfort.valueOf(readString());
    }

    private SeatCount readSeatCount() {
        SeatComfort comfort = readSeatComfort();
        int count = readInt();
        return new SeatCount(comfort, count);
    }

    private SeatCount[] readSeatCounts() {
        int size = readInt();
        SeatCount[] counts = new SeatCount[size];
        for (int i = 0; i < size; i++) {
            counts[i] = readSeatCount();
        }
        return counts;
    }

    private Travel readTravel() {
        long id = readLong();
        String from = readString();
        String to = readString();
        Date day = readDate();
        SeatCount[] counts = readSeatCounts();
        return new Travel(id, from, to, day, counts);
    }

    private void readTravels() {
        int size = readInt();
        travels = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            travels.add(readTravel());
        }
    }

    private void readBookedTravel() {
        ticketId = readLong();
    }

    private SeatState readSeatState() {
        return SeatState.valueOf(readString());
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
        int size = readInt();
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            seats.add(readSeat());
        }
        return seats;
    }

    private TicketState readTicketState() {
        return TicketState.valueOf(readString());
    }

    private void readTicket() {
        long id = readLong();
        long userId = readLong();
        long travelId = readLong();
        TicketState state = readTicketState();
        List<Seat> seats = readSeats();
        String planeName = readString();
        String from = readString();
        String to = readString();
        Date day = readDate();
        ticket = new Ticket(id, userId, travelId, state, seats, planeName, from, to, day);
    }

    private PaymentState readPaymentState() {
        return PaymentState.valueOf(readString());
    }

    private void readPaymentResult() {
        PaymentState state = readPaymentState();
        int value = readInt();
        paymentResult = new PaymentResult(state, value);
    }

    private void readTickets() {
        int size = readInt();
        tickets = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            long id = readLong();
            long userId = readLong();
            long travelId = readLong();
            TicketState state = readTicketState();
            List<Seat> seats = readSeats();
            String planeName = readString();
            String from = readString();
            String to = readString();
            Date day = readDate();
            tickets.add(new Ticket(id, userId, travelId, state, seats, planeName, from, to, day));
        }
    }

    private void readImage() {
        int size = readInt();
        image = new byte[size];
        for (int i = 0; i < size; i++) {
            image[i] = readByte();
        }
    }

    public Connected getConnected() {
    	return connected;
    }

    public Account getAccount() {
        return account;
    }

    public Collection<Travel> getTravels() {
        return travels;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public Ticket getTicket() {
        return ticket;
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

    public void receive() {
        type = readInt();
        eraseFields();
        switch (type) {
        case Protocol.REPLY_NOWAY:          break;
        case Protocol.REPLY_KO:             break;
        case Protocol.REPLY_OK:             break;
        case Protocol.REPLY_USER:           readConnected(); break;
        case Protocol.REPLY_ACCOUNT:        readAccount(); break;
        case Protocol.REPLY_TRAVELS:        readTravels(); break;
        case Protocol.REPLY_BOOKED_TRAVEL:  readBookedTravel(); break;
        case Protocol.REPLY_TICKET:         readTicket(); break;
        case Protocol.REPLY_PAYMENT:        readPaymentResult(); break;
        case Protocol.REPLY_TICKETS:        readTickets(); break;
        case Protocol.REPLY_IMAGE:          readImage(); break;
        }
    }

}
