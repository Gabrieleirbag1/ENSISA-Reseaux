package fr.ensisa.hassenforder.flight.client.network.fake;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.ensisa.hassenforder.flight.client.model.Account;
import fr.ensisa.hassenforder.flight.client.model.Authenticator;
import fr.ensisa.hassenforder.flight.client.model.Connected;
import fr.ensisa.hassenforder.flight.client.model.PaymentResult;
import fr.ensisa.hassenforder.flight.client.model.Seat;
import fr.ensisa.hassenforder.flight.client.model.SeatComfort;
import fr.ensisa.hassenforder.flight.client.model.SeatCount;
import fr.ensisa.hassenforder.flight.client.model.SeatState;
import fr.ensisa.hassenforder.flight.client.model.Ticket;
import fr.ensisa.hassenforder.flight.client.model.TicketState;
import fr.ensisa.hassenforder.flight.client.model.Travel;
import fr.ensisa.hassenforder.flight.client.model.User;
import fr.ensisa.hassenforder.flight.client.network.ISession;

public class Fake implements ISession {

	@Override
	public boolean open() {
		return true;
	}

	@Override
	public boolean close() {
		return true;
	}

	static Authenticator authenticator = new Authenticator (1, "123456789");
	static User user = new User (1, "momo", 1000);
	@Override
	public Connected connect(String mail, String passwd) {
		return new Connected(authenticator, user);
	}

	@Override
	public Boolean disconnect(Authenticator authenticator) {
		return Boolean.TRUE;
	}

	@Override
	public Boolean createAccount(Account credential) {
		return Boolean.TRUE;
	}

	@Override
	public Account getAccount(Authenticator authenticator) {
		return new Account(1, "momo", "momo@uha.fr", "123456789", 1000);
	}

	@Override
	public Boolean updateAccount(Authenticator authenticator, Account account) {
		return Boolean.TRUE;
	}

	private Date buildDate (Date initial) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(initial);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		int hour = calendar.get(Calendar.HOUR);
		int targetHour = 10;
		if (hour > 16) targetHour = 18;
		if (hour > 10) targetHour = 14;
		calendar.set(Calendar.HOUR_OF_DAY, targetHour);
		return calendar.getTime();
	}

	private Map<Long, Travel> travels = null;
	private long lastTravel = 0;

	public Map<Long, Travel> getTravels() {
		if (travels == null) {
			travels = new TreeMap<>();
			buildAllTravels ();
		}
		return travels;
	}

	private void addTravel (Travel travel) {
		getTravels().put(travel.getId(), travel);
	}
	
	private void buildAllTravels () {
		Date day = buildDate(new Date());
		addTravel(new Travel(++lastTravel, "Mulhouse", "Berlin", day, new SeatCount[] { new SeatCount(SeatComfort.FIRST, 2), new SeatCount(SeatComfort.BUSINESS, 8), new SeatCount(SeatComfort.ECONOMIC, 24) }));
		addTravel(new Travel(++lastTravel, "Mulhouse", "Paris", day, new SeatCount[] { new SeatCount(SeatComfort.FIRST, 4), new SeatCount(SeatComfort.BUSINESS, 16), new SeatCount(SeatComfort.ECONOMIC, 48) }));
		addTravel(new Travel(++lastTravel, "Mulhouse", "Milan", day, new SeatCount[] { new SeatCount(SeatComfort.FIRST, 1), new SeatCount(SeatComfort.BUSINESS, 2), new SeatCount(SeatComfort.ECONOMIC, 8) }));
		addTravel(new Travel(++lastTravel, "Berlin", "Londre", day, new SeatCount[] { new SeatCount(SeatComfort.ECONOMIC, 12) }));
		addTravel(new Travel(++lastTravel, "Milan", "Barcelone", day, new SeatCount[] { new SeatCount(SeatComfort.FIRST, 4)}));
	}

	private Travel getTravelById (long id) {
		return getTravels().get(id);
	}

	@Override
	public Collection<Travel> getAllTravels() {
		return getTravels().values();
	}

	@Override
	public Collection<Travel> getAllTravels(String from, String to, LocalDate localDate) {
		Collection<Travel> list = getAllTravels ();
		if (from != null) {
			from = from.toLowerCase();
			Collection<Travel> filtered = new ArrayList<>();
			for (Travel t : list) {
				if (t.getFrom().get().toLowerCase().startsWith(from))
					filtered.add(t);
			}
			list = filtered;
		}
		if (to != null) {
			to = to.toLowerCase();
			Collection<Travel> filtered = new ArrayList<>();
			for (Travel t : list) {
				if (t.getTo().get().toLowerCase().startsWith(to))
					filtered.add(t);
			}
			list = filtered;
		}
		return list;
	}

	private Map<Long, Ticket> tickets = null;
	private long lastTicket = 0;
	
	private void addTicket (Ticket ticket) {
		if (tickets == null) tickets = new TreeMap<>();
		tickets.put(ticket.getId(), ticket);
	}
	
	private List<Seat> getOccupied (long travel) {
		return new ArrayList<Seat>();
	}

	private List<Seat> getOccupied (List<Seat> all, SeatComfort comfort) {
		List<Seat> real = new ArrayList<Seat>();
		for (Seat seat: all) {
			if (seat.getComfort().get() == comfort) real.add(seat);
		}
		return real;
	}

	private boolean contains (List<Seat> all, int position) {
		for (Seat seat: all) {
			if (seat.getPosition() == position) return true;
		}
		return false;
	}

	private SeatCount getComfort (List<SeatCount> all, SeatComfort comfort) {
		for (SeatCount count: all) {
			if (count.getComfort() == comfort)
				return count;
		}
		return null;
	}

	private Ticket buildTicket (long userId, Travel travel, List<SeatCount> counts) {
// the current id for the ticket
		long ticketId = ++lastTicket;
// should extract the plane from travel on a server
		Plane plane = getPlaneById(101);
// get the list of occupied seats for this travel
		List<Seat> occupiedSeats = getOccupied(travel.getId());
		// must create seats 
		List<Seat> seats = new ArrayList<>();
		for (SeatCount count : counts) {
			SeatCount planeComfortCount = getComfort(plane.getCounts(), count.getComfort());
			if (planeComfortCount == null) return null;
			List<Seat> occupiedComfortSeats = getOccupied(occupiedSeats, count.getComfort());
			if (planeComfortCount.getCount() - occupiedComfortSeats.size() < count.getCount()) return null;
			int cost = travel.getCost(count.getComfort());
			for (int i=0; i < count.getCount(); ++i) {
				for (int position=0; position < planeComfortCount.getCount(); ++position) {
					if (contains(occupiedComfortSeats, position)) continue;
					Seat seat = new Seat (0, travel.getId(), ticketId, count.getComfort(), position, SeatState.RESERVED, cost);
					seats.add(seat);
					occupiedComfortSeats.add(seat);
					break;
				}
			}
		}
		return new Ticket(
				ticketId, userId, travel.getId(),
				TicketState.RESERVED,
				seats,
				// inject plane data
				"Airbus 321", 
				// inject travel data
				travel.getFrom().get(), 
				travel.getTo().get(), 
				travel.getTravelDay()
		);
	}

	@Override
	public Long bookTravel(Authenticator authenticator, long travelId, List<SeatCount> counts) {
		Travel travel = getTravelById(travelId);
		if (travel == null) return null;
		Ticket ticket = buildTicket(authenticator.getId(), travel, counts);
		addTicket(ticket);
		return ticket.getId();
	}

	@Override
	public Ticket getTicket(Authenticator authenticator, long ticketId) {
		if (ticketId == -1) {
			List<SeatCount> counts = new ArrayList<SeatCount>();
			counts.add(new SeatCount(SeatComfort.BUSINESS, 1));
			counts.add(new SeatCount(SeatComfort.ECONOMIC, 2));
			ticketId = bookTravel(authenticator, 1, counts);
		}
		return tickets.get(ticketId);
	}

	private Map<Long, Plane> planes = null;
	private long lastPlane = 100;
	
	private void addPlane (Plane plane) {
		if (planes == null) planes = new TreeMap<>();
		planes.put(plane.getId(), plane);
	}
	
	private void buildAllPlanes () {
		addPlane(new Plane(++lastPlane, "Airbus 321", new SeatCount [] {
					new SeatCount(SeatComfort.FIRST,    4),
					new SeatCount(SeatComfort.BUSINESS, 8),
					new SeatCount(SeatComfort.ECONOMIC, 12),
				}
			)
		);
	}

	private Map<Long, Plane> getPlanes () {
		if (planes == null) buildAllPlanes();
		return planes;
	}

	private Plane getPlaneById (long id) {
		return getPlanes ().get(id);
	}

	@Override
	public PaymentResult payTicket(Authenticator authenticator, long ticketId) {
		int cash = user.getCash().get();
		Ticket ticket = tickets.get(ticketId);
		int cost = ticket.getCost().get();
		if (cash < cost) return PaymentResult.needBank(cost);
		int remains = cash - cost;
		user.getCash().set(remains);
		return PaymentResult.done(remains);
	}

	@Override
	public byte[] getImage(String name) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Ticket> getAllTickets(Authenticator authenticator) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		List<SeatCount> counts1 = new ArrayList<SeatCount>();
		counts1.add(new SeatCount(SeatComfort.ECONOMIC, 4));
		Ticket ticket1 = buildTicket(authenticator.getId(), getTravelById(1), counts1);
		ticket1.getState().set(TicketState.USED);
		tickets.add(ticket1);
		List<SeatCount> counts2 = new ArrayList<SeatCount>();
		counts2.add(new SeatCount(SeatComfort.ECONOMIC, 2));
		counts2.add(new SeatCount(SeatComfort.BUSINESS, 2));
		Ticket ticket2 = buildTicket(authenticator.getId(), getTravelById(2), counts2);
		ticket2.getState().set(TicketState.PAYED);
		tickets.add(ticket2);
		List<SeatCount> counts3 = new ArrayList<SeatCount>();
		counts3.add(new SeatCount(SeatComfort.FIRST, 1));
		Ticket ticket3 = buildTicket(authenticator.getId(), getTravelById(3), counts3);
		tickets.add(ticket3);
		return tickets;
	}

	@Override
	public PaymentResult cancelTicket(Authenticator authenticator, long ticketId) {
		return PaymentResult.done(2345);
	}

}
