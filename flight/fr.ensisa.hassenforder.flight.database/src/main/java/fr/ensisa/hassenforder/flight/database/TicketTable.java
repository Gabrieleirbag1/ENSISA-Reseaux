package fr.ensisa.hassenforder.flight.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TicketTable {

	private static long LAST_ID = 0;

	private Map<Long, Ticket> tickets = null;

	private Map<Long, Ticket> getTickets () {
		if (tickets == null) {
			tickets = new TreeMap<>();
		}
		return tickets;
	}

	public long addTicket (Ticket ticket) {
		ticket.setId (++LAST_ID);
		getTickets ().put(ticket.getId(), ticket);
		return ticket.getId();
	}

	public Ticket getTicket (long id) {
		return getTickets().get(id);
	}

	public Collection<Ticket> getAll() {
		return getTickets().values();
	}

	public Collection<Ticket> getAllByUserId (long userId) {
		List<Ticket> selected = new ArrayList<Ticket>();
		for (Ticket ticket : getAll()) {
			if (ticket.getUserId() == userId)
				selected.add(ticket);
		}
		return selected;
	}

	public void removeTicket (long travelId) {
		getTickets().remove(travelId);
	}

}
