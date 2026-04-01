package fr.ensisa.hassenforder.flight.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.ensisa.hassenforder.flight.database.Location;
import fr.ensisa.hassenforder.flight.database.Model;
import fr.ensisa.hassenforder.flight.database.Plane;
import fr.ensisa.hassenforder.flight.database.Seat;
import fr.ensisa.hassenforder.flight.database.SeatComfort;
import fr.ensisa.hassenforder.flight.database.SeatCost;
import fr.ensisa.hassenforder.flight.database.SeatCount;
import fr.ensisa.hassenforder.flight.database.SeatState;
import fr.ensisa.hassenforder.flight.database.Ticket;
import fr.ensisa.hassenforder.flight.database.TicketState;
import fr.ensisa.hassenforder.flight.database.Travel;
import fr.ensisa.hassenforder.flight.database.User;
import fr.ensisa.hassenforder.flight.server.images.ImagesCache;
import fr.ensisa.hassenforder.flight.server.network.dto.PaymentResult;
import fr.ensisa.hassenforder.flight.server.network.dto.PaymentState;
import fr.ensisa.hassenforder.flight.server.network.dto.TravelBooking;

public class Business {

	private Model model;
	private ImagesCache cache;

	@SuppressWarnings("exports")
	public Business(Model model, ImagesCache cache) {
		super();
		this.model = model;
		this.cache = cache;
	}

	public Model getModel() {
		return model;
	}

	public User getUserById(long userId) {
		return model.getUsers().getUser(userId);
	}

	public String getToken(long userId) {
		return model.getTokens().getToken(userId);
	}

	public Ticket getTicket(long ticketId) {
		return model.getTickets().getTicket(ticketId);
	}

	public long validateConnection(User user) {
		User actual = model.getUsers().getUserByMail(user.getMail());
		if (actual == null) return 0;
		if (! actual.getPasswd().equals(user.getPasswd())) {
			model.getTokens().removeToken(actual.getId());
			return 0;
		}
		return actual.getId();
	}

	public void connect (long userId) {
		model.getTokens().addNewToken(userId);
	}
	
	public void disconnect (long userId) {
		model.getTokens().removeToken(userId);
	}
	
	public boolean validateAuthenticator(long userId, String expectedToken) {
		String effectiveToken = model.getTokens().getToken(userId);
		return expectedToken.equals(effectiveToken);
	}

	private boolean checkUser (User user) {
		if (user.getName().isBlank()) return false;
		if (user.getMail().isBlank()) return false;
		if (user.getPasswd().isBlank()) return false;
		return true;
	}

	public boolean createUser (User user) {
		if (! checkUser(user)) return false;
		User actual = model.getUsers().getUserByName(user.getName());
		if (actual != null) return false;
		User bymail = model.getUsers().getUserByMail(user.getMail());
		if (bymail != null) return false;
		model.getUsers().addUser(user);
		return true;
	}

	public boolean updateUser (User user) {
		if (! checkUser(user)) return false;
		User actual = model.getUsers().getUserByName(user.getName());
		if (actual == null) return false;
		User bymail = model.getUsers().getUserByMail(user.getMail());
		if (bymail.getId() != actual.getId()) return false;
		actual.setMail(user.getMail());
		actual.setPasswd(user.getPasswd());
		actual.setCash(user.getCash());
		return true;
	}

	public Collection<Travel> getAllTravels() {
		return model.getTravels().getAll();
	}

	public Collection<Travel> getFilteredTravels(String from, String to, Date day) {
		Collection<Travel> list = model.getTravels().getAll();
		if (from != null) {
			from = from.toLowerCase();
			Collection<Travel> filtered = new ArrayList<>();
			for (Travel t : list) {
				if (t.getFrom().toLowerCase().startsWith(from))
					filtered.add(t);
			}
			list = filtered;
		}
		if (to != null) {
			to = to.toLowerCase();
			Collection<Travel> filtered = new ArrayList<>();
			for (Travel t : list) {
				if (t.getTo().toLowerCase().startsWith(to))
					filtered.add(t);
			}
			list = filtered;
		}
		return list;
	}

	private Map<SeatComfort, SeatCost> estimateCost(String from, String to) {
		Location a = model.getLocations().getLocation(from);
		Location b = model.getLocations().getLocation(to);
		double distance = Location.distance(a, b);
		int cost = (int) (distance * 10.0);
		Map<SeatComfort, SeatCost> costs = new TreeMap<>();
		costs.put(SeatComfort.FIRST, new SeatCost(SeatComfort.FIRST, cost * 8 ));
		costs.put(SeatComfort.BUSINESS, new SeatCost(SeatComfort.BUSINESS, cost * 4 ));
		costs.put(SeatComfort.ECONOMIC, new SeatCost(SeatComfort.ECONOMIC, cost * 1 ));
		return costs;
	}


	private List<Seat> findSeats (List<SeatCount> ticketCount, Map<String, Seat> occupiedSeats, Map<SeatComfort, SeatCount> planeSeatMax) {
		List<Seat> reservedSeats = new ArrayList<>();
		int maxSeats = 0;
		for (SeatCount count : ticketCount) {
			maxSeats += count.getCount();
			SeatCount planeComfortMax = planeSeatMax.get(count.getComfort());
			// no seats for the comfort
			if (planeComfortMax == null) return null;
			for (int i=0; i < count.getCount(); ++i) {
				for (int position=0; position < planeComfortMax.getCount(); ++position) {
					String name = Seat.buildName(count.getComfort(), position);
					if (occupiedSeats.containsKey(name)) continue;
					Seat seat = new Seat (count.getComfort(), position);
					occupiedSeats.put(name, seat);
					reservedSeats.add(seat);
					break;
				}
			}
		}
		// some seats cannot be reserved yet
		if (reservedSeats.size() != maxSeats) return null;
		return reservedSeats;
	}

	@SuppressWarnings("exports")
	public Ticket bookTravel(long userId, TravelBooking booking) {
		Travel travel = model.getTravels().getTravel(booking.getTravelId());
		Plane plane = model.getPlanes().getPlane(travel.getPlaneId());
		Map<String, Seat> occupiedSeats = model.getSeats().getAllByTravelId(travel.getId());
		List<Seat> reservedSeats = findSeats (booking.getCounts(), occupiedSeats, plane.getMaxSeats());
		if (reservedSeats == null) return null;
		// seams we have a set of seats
		Ticket ticket = new Ticket(userId, booking.getTravelId(), TicketState.RESERVED);
		model.getTickets().addTicket(ticket);
		Map<SeatComfort, SeatCost> costs = estimateCost (travel.getFrom(), travel.getTo());
		for (Seat seat : reservedSeats) {
			SeatCost cost = costs.get(seat.getComfort());
			Seat toReserve = new Seat (ticket.getTravelId(), ticket.getId(), seat.getComfort(), seat.getPosition(), SeatState.RESERVED, cost.getCost());
			model.getSeats().addSeat(toReserve);
		}
		return ticket;
	}

	@SuppressWarnings("exports")
	public PaymentResult payTicket(long ticketId) {
		Ticket ticket = model.getTickets().getTicket(ticketId);
		if (ticket.getState() != TicketState.RESERVED) return new PaymentResult(PaymentState.FAILED, 0);
		List<Seat> seats = model.getSeats().getAllByTicketId(ticketId);
		int cost = 0;
		for (Seat seat : seats) {
			cost += seat.getCost();
		}
		User user = model.getUsers().getUser(ticket.getUserId());
		int cash = user.getCash();
		int remains = cash - cost;
		if (remains >= 0) {
			user.setCash(remains);
			ticket.setState(TicketState.PAYED);
			return new PaymentResult(PaymentState.DONE, remains);
		} else {
			return new PaymentResult(PaymentState.BANK, cost);
		}
	}

	public Collection<Ticket> getAllTickets(long userId) {
		return model.getTickets().getAllByUserId(userId);
	}

	@SuppressWarnings("exports")
	public PaymentResult cancelTicket(long ticketId) {
		Ticket ticket = model.getTickets().getTicket(ticketId);
		if (ticket.getState() != TicketState.PAYED) return new PaymentResult(PaymentState.FAILED, 0);
		List<Seat> seats = model.getSeats().getAllByTicketId(ticketId);
		int cost = 0;
		for (Seat seat : seats) {
			cost += seat.getCost();
		}
		User user = model.getUsers().getUser(ticket.getUserId());
		int cash = user.getCash();
		int remains = cash + cost;
		user.setCash(remains);		
		ticket.setState(TicketState.CANCELED);
		List<Seat> seatsToCancel = model.getSeats().getAllByTicketId(ticketId);
		for (Seat seat : seatsToCancel) {
			model.getSeats().removeSeat(seat.getId());
		}
		return new PaymentResult(PaymentState.DONE, remains);
	}

	public byte[] getImageByName(String imageName) {
		return cache.getImage(imageName);
	}

}
