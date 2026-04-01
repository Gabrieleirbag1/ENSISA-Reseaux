package fr.ensisa.hassenforder.flight.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SeatTable {

	private static long LAST_ID = 0;

	private Map<Long, Seat> seats = null;

	private Map<Long, Seat> getSeats () {
		if (seats == null) {
			seats = new TreeMap<>();
		}
		return seats;
	}

	public long addSeat (Seat seat) {
		seat.setId (++LAST_ID);
		getSeats().put(seat.getId(), seat);
		return seat.getId();
	}

	public Seat getSeat (long id) {
		return getSeats().get(id);
	}

	public Collection<Seat> getAll() {
		return getSeats().values();
	}

	public void removeSeat (long seatId) {
		getSeats().remove(seatId);
	}

	public Map<String, Seat> getAllByTravelId(long travelId) {
		Map<String, Seat> selected = new TreeMap<>(); 
		for (Seat seat : getAll()) {
			if (seat.getTravelId() == travelId)
				selected.put(seat.getName(), seat);
		}
		return selected;
	}

	public List<Seat> getAllByTicketId(long ticketId) {
		List<Seat> selected = new ArrayList<Seat>();
		for (Seat seat : getAll()) {
			if (seat.getTicketId() == ticketId)
				selected.add(seat);
		}
		return selected;
	}

}
