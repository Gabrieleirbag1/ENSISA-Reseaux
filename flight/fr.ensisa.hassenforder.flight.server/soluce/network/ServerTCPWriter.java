package fr.ensisa.hassenforder.flight.server.network;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import fr.ensisa.hassenforder.flight.database.Seat;
import fr.ensisa.hassenforder.flight.database.SeatComfort;
import fr.ensisa.hassenforder.flight.database.SeatCount;
import fr.ensisa.hassenforder.flight.database.SeatState;
import fr.ensisa.hassenforder.flight.database.Ticket;
import fr.ensisa.hassenforder.flight.database.TicketState;
import fr.ensisa.hassenforder.flight.database.User;
import fr.ensisa.hassenforder.flight.network.Protocol;
import fr.ensisa.hassenforder.flight.server.network.dto.PaymentResult;
import fr.ensisa.hassenforder.flight.server.network.dto.PaymentState;
import fr.ensisa.hassenforder.flight.server.network.dto.TicketReply;
import fr.ensisa.hassenforder.flight.server.network.dto.TravelReply;
import fr.ensisa.hassenforder.network.BasicAbstractWriter;

public class ServerTCPWriter extends BasicAbstractWriter {

    public ServerTCPWriter(OutputStream outputStream) {
        super (outputStream);
    }

    public void createOK() {
        writeInt(Protocol.REPLY_OK);
    }

    public void createKO() {
        writeInt(Protocol.REPLY_KO);
    }

	public void createNOWAY() {
        writeInt(Protocol.REPLY_NOWAY);
	}

	public void createReplyConnected(User user, String token) {
        writeInt(Protocol.REPLY_USER);
        writeLong(user.getId());
        writeString(user.getName());
        writeInt(user.getCash());
        writeLong(user.getId());
        writeString(token);
	}
	
	public void createReplyAccount(User user) {
        writeInt(Protocol.REPLY_ACCOUNT);
        writeString(user.getName());
        writeString(user.getMail());
        writeInt(user.getCash());
	}

	private void writeDate(Date day) {
        writeLong(day.getTime());
	}

	private void writeSeatCount(SeatCount count) {
        writeSeatComfort(count.getComfort());
        writeInt(count.getCount());
	}

	private void writeSeatCounts(Map<SeatComfort, SeatCount> counts) {
        writeInt(counts.size());
        for (SeatCount count : counts.values()) {
        	writeSeatCount(count);
        }
	}

	private void writeTravel(TravelReply travel) {
        writeLong(travel.getId());
        writeString(travel.getFrom());
        writeString(travel.getTo());
        writeDate(travel.getTravelDay());
        writeSeatCounts(travel.getRemainingSeatCounts());
	}

	public void createReplyTravels(Collection<TravelReply> travels) {
        writeInt(Protocol.REPLY_TRAVELS);
        writeInt(travels.size());
        for (TravelReply travel : travels) {
        	writeTravel (travel);
        }
	}

	public void createReplyBookedTravel(Ticket ticket) {
        writeInt(Protocol.REPLY_BOOKED_TRAVEL);
        writeLong(ticket.getId());
	}

	private void writeSeatComfort(SeatComfort comfort) {
		writeString(comfort.name());
	}

	private void writeSeatState(SeatState state) {
		writeString(state.name());
	}

	private void writeSeat(Seat seat) {
        writeLong(seat.getId());
        writeInt(seat.getPosition());
        writeLong(seat.getTravelId());
        writeLong(seat.getTicketId());
        writeSeatComfort(seat.getComfort());
        writeSeatState(seat.getState());
        writeInt(seat.getCost());
	}

	private void writeSeats(List<Seat> seats) {
        writeInt(seats.size());
        for (Seat seat : seats) {
        	writeSeat(seat);
        }
	}

	private void writeTicketState(TicketState state) {
		writeString(state.name());
	}

	private void writeTicket(TicketReply ticket) {
        writeLong(ticket.getId());
        writeLong(ticket.getUserId());
        writeLong(ticket.getTravelId());
        writeTicketState(ticket.getState());
        writeSeats(ticket.getSeats());
        writeString(ticket.getPlaneName());
        writeString(ticket.getFrom());
        writeString(ticket.getTo());      
        writeDate(ticket.getDay());      
	}

	public void createReplyTicket(TicketReply ticket) {
        writeInt(Protocol.REPLY_TICKET);
    	writeTicket(ticket);
	}
	
	private void writePaymentState(PaymentState state) {
		writeString(state.name());
	}

	public void createReplyPaymentResult(PaymentResult result) {
        writeInt(Protocol.REPLY_PAYMENT);
		writePaymentState(result.getState());
        writeInt(result.getValue());
	}

	public void createReplyTickets(Collection<TicketReply> tickets) {
        writeInt(Protocol.REPLY_TICKETS);
        writeInt(tickets.size());
        for (TicketReply ticket : tickets) {
        	writeTicket(ticket);
        }
	}

	public void createReplyImage(byte[] image) {
        writeInt(Protocol.REPLY_IMAGE);
        writeInt(image.length);
        for (byte b : image) {
        	writeByte(b);
        }
	}

}
