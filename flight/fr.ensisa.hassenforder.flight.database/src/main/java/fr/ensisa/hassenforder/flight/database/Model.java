package fr.ensisa.hassenforder.flight.database;

import java.util.Calendar;
import java.util.Date;

public class Model {

	private TokenTable tokens = new TokenTable();
	private UserTable users = new UserTable();
	private PlaneTable planes = new PlaneTable();
	private LocationTable locations = new LocationTable();
	private TravelTable travels = new TravelTable();
	private TicketTable tickets = new TicketTable();
	private SeatTable seats = new SeatTable();

	public UserTable getUsers() {
		return users;
	}

	public LocationTable getLocations() {
		return locations;
	}

	public PlaneTable getPlanes() {
		return planes;
	}

	public TravelTable getTravels() {
		return travels;
	}

	public TicketTable getTickets() {
		return tickets;
	}

	public SeatTable getSeats() {
		return seats;
	}

	public TokenTable getTokens() {
		return tokens;
	}

	static private Date buildDate (Date initial) {
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

	public void initialize () {
		@SuppressWarnings("unused")
		long mimi = users.addUser (new User ("mimi", "i@m", "iii", 10000));
		long momo = users.addUser (new User ("momo", "o@m", "ooo", 2000));
		long mama = users.addUser (new User ("mama", "a@m", "aaa", 0));
		
		long plane1 = getPlanes().addPlane(new Plane("Airbus 321", new SeatCount[] { new SeatCount(SeatComfort.FIRST, 2), new SeatCount(SeatComfort.BUSINESS,  8), new SeatCount(SeatComfort.ECONOMIC, 24) }));
		long plane2 = getPlanes().addPlane(new Plane("Boeing 737", new SeatCount[] { new SeatCount(SeatComfort.FIRST, 2), new SeatCount(SeatComfort.BUSINESS,  8), new SeatCount(SeatComfort.ECONOMIC, 24) }));
		long plane3 = getPlanes().addPlane(new Plane("Airbus 321", new SeatCount[] { new SeatCount(SeatComfort.FIRST, 4), new SeatCount(SeatComfort.BUSINESS, 16), new SeatCount(SeatComfort.ECONOMIC, 48) }));
		long plane4 = getPlanes().addPlane(new Plane("Embraer Phenom", new SeatCount[] { new SeatCount(SeatComfort.FIRST, 1), new SeatCount(SeatComfort.BUSINESS,  2), new SeatCount(SeatComfort.ECONOMIC,  8) }));
		long plane5 = getPlanes().addPlane(new Plane("bombardier 5500", new SeatCount[] { new SeatCount(SeatComfort.FIRST, 4) }));
		
		getLocations().addLocation(new Location("Mulhouse",		47.759,	7.528));
		getLocations().addLocation(new Location("Paris",		49.002,	2.519));
		getLocations().addLocation(new Location("Berlin",		52.449,	13.495));
		getLocations().addLocation(new Location("Milan",		45.479,	 9.189));
		getLocations().addLocation(new Location("Londre",		51.603, -0.104));
		getLocations().addLocation(new Location("Barcelone",	41.359,	 2.158));
		
		Date day = buildDate(new Date());
		long travel1 = getTravels().addTravel(new Travel("Mulhouse", "Berlin", day, plane1));
		long travel2 = getTravels().addTravel(new Travel("Mulhouse", "Paris", day, plane2));
		long travel3 = getTravels().addTravel(new Travel("Mulhouse", "Milan", day, plane5));
		long travel4 = getTravels().addTravel(new Travel("Berlin", "Londre", day, plane3));
		long travel5 = getTravels().addTravel(new Travel("Milan", "Barcelone", day, plane5));
		long travel6 = getTravels().addTravel(new Travel("Milan", "Berlin", day, plane4));
		
		// rien mimi
		long ticketId1 = getTickets().addTicket(new Ticket(momo, travel1, TicketState.PAYED));
		getSeats().addSeat(new Seat (travel1, ticketId1, SeatComfort.FIRST, 0, SeatState.RESERVED, 1000));
		getSeats().addSeat(new Seat (travel1, ticketId1, SeatComfort.FIRST, 1, SeatState.RESERVED, 1000));
		long ticketId2 = getTickets().addTicket(new Ticket(momo, travel2, TicketState.PAYED));
		getSeats().addSeat(new Seat (travel2, ticketId2, SeatComfort.BUSINESS, 0, SeatState.RESERVED, 500));
		getSeats().addSeat(new Seat (travel2, ticketId2, SeatComfort.BUSINESS, 1, SeatState.RESERVED, 500));
		getSeats().addSeat(new Seat (travel2, ticketId2, SeatComfort.BUSINESS, 2, SeatState.RESERVED, 500));
		getSeats().addSeat(new Seat (travel2, ticketId2, SeatComfort.BUSINESS, 3, SeatState.RESERVED, 500));
		getSeats().addSeat(new Seat (travel2, ticketId2, SeatComfort.BUSINESS, 4, SeatState.RESERVED, 500));
		getSeats().addSeat(new Seat (travel2, ticketId2, SeatComfort.BUSINESS, 5, SeatState.RESERVED, 500));
		getSeats().addSeat(new Seat (travel2, ticketId2, SeatComfort.BUSINESS, 6, SeatState.RESERVED, 500));
		long ticketId3 = getTickets().addTicket(new Ticket(momo, travel3, TicketState.PAYED));
		getSeats().addSeat(new Seat (travel3, ticketId3, SeatComfort.FIRST, 0, SeatState.RESERVED, 1000));
		getSeats().addSeat(new Seat (travel3, ticketId3, SeatComfort.FIRST, 1, SeatState.RESERVED, 1000));
		getSeats().addSeat(new Seat (travel3, ticketId3, SeatComfort.FIRST, 2, SeatState.RESERVED, 1000));

		//trois tickets mama (reserved, payed, cancel)
		long ticketId11 = getTickets().addTicket(new Ticket(mama, travel4, TicketState.RESERVED));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC,  0, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC,  1, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC,  2, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC,  3, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC,  4, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC,  5, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC,  6, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC,  7, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC,  8, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC,  9, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 10, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 12, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 14, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 16, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 18, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 20, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 21, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 22, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 23, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 24, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 25, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 26, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 27, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 28, SeatState.RESERVED, 100));
		getSeats().addSeat(new Seat (travel4, ticketId11, SeatComfort.ECONOMIC, 29, SeatState.RESERVED, 100));
		long ticketId12 = getTickets().addTicket(new Ticket(mama, travel5, TicketState.PAYED));
		getSeats().addSeat(new Seat (travel5, ticketId12, SeatComfort.FIRST, 0, SeatState.RESERVED, 1000));
		@SuppressWarnings("unused")
		long ticketId13 = getTickets().addTicket(new Ticket(mama, travel6, TicketState.CANCELED));
		
	}

}
