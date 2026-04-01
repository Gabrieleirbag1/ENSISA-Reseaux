package fr.ensisa.hassenforder.flight.server.network.dto;

import java.util.Date;
import java.util.List;

import fr.ensisa.hassenforder.flight.database.Plane;
import fr.ensisa.hassenforder.flight.database.Seat;
import fr.ensisa.hassenforder.flight.database.Ticket;
import fr.ensisa.hassenforder.flight.database.TicketState;
import fr.ensisa.hassenforder.flight.database.Travel;

public class TicketReply {

	private final Ticket ticket;
	private final List<Seat> seats;
	private final Travel travel;
	private final Plane plane;
	
	public TicketReply(Ticket ticket, List<Seat> seats, Travel travel, Plane plane) {
		super();
		this.ticket = ticket;
		this.seats = seats;
		this.travel = travel;
		this.plane = plane;
	}

	public long getId() {
		return ticket.getId();
	}

	public long getUserId() {
		return ticket.getUserId();
	}

	public long getTravelId() {
		return ticket.getTravelId();
	}

	public TicketState getState() {
		return ticket.getState();
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public String getPlaneName() {
		return plane.getName();
	}

	public String getFrom() {
		return travel.getFrom();
	}

	public String getTo() {
		return travel.getTo();
	}

	public Date getDay() {
		return travel.getTravelDay();
	}

}
