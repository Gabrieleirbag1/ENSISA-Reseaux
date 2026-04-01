package fr.ensisa.hassenforder.flight.server.network.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.ensisa.hassenforder.flight.database.Model;
import fr.ensisa.hassenforder.flight.database.Plane;
import fr.ensisa.hassenforder.flight.database.Seat;
import fr.ensisa.hassenforder.flight.database.SeatComfort;
import fr.ensisa.hassenforder.flight.database.SeatCount;
import fr.ensisa.hassenforder.flight.database.Ticket;
import fr.ensisa.hassenforder.flight.database.Travel;

public class dto {

	public static TicketReply buildTicketReply(Ticket ticket, Model model) {
		Travel travel = model.getTravels().getTravel(ticket.getTravelId());
		Plane plane = model.getPlanes().getPlane(travel.getPlaneId());
		List<Seat> seats = model.getSeats().getAllByTicketId(ticket.getId());
		return new TicketReply(ticket, seats, travel, plane);
	}

	public static Collection<TicketReply> buildTicketReply(Collection<Ticket> tickets, Model model) {
		List<TicketReply> list = new ArrayList<TicketReply>(tickets.size());
		for (Ticket ticket : tickets) {
			list.add(buildTicketReply(ticket, model));
		}
		return list;
	}

	public static TravelReply buildTravelReply(Travel travel, Model model) {
		Plane plane = model.getPlanes().getPlane(travel.getPlaneId());
		Map<String, Seat> seats = model.getSeats().getAllByTravelId(travel.getId());
		Map<SeatComfort, Integer> counts = new TreeMap<>();
		for (Seat seat : seats.values()) {
			Integer count = counts.get(seat.getComfort());
			if (count == null) {
				counts.put(seat.getComfort(), 1);
			} else {
				counts.put(seat.getComfort(), count + 1);
			}
		}
		Map<SeatComfort, SeatCount> usedSeats = new TreeMap<>();
		for (Map.Entry<SeatComfort, Integer> entry : counts.entrySet()) {
			usedSeats.put(entry.getKey(), new SeatCount(entry.getKey(), entry.getValue()));
		}
		return new TravelReply(travel, plane, usedSeats);
	}

	public static Collection<TravelReply> buildTravelReply(Collection<Travel> travels, Model model) {
		List<TravelReply> list = new ArrayList<TravelReply>(travels.size());
		for (Travel travel : travels) {
			list.add(buildTravelReply(travel, model));
		}
		return list;
	}

}
