package fr.ensisa.hassenforder.flight.client.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Ticket {

	private long id;
	private long userId;
	private long travelId;
	private final ObjectProperty<TicketState> state;
	private ObservableList<Seat> seats = FXCollections.observableArrayList();
	// Join result with travelId
	private final StringProperty plane;
	private final StringProperty from;
	private final StringProperty to;
	private final StringProperty day;
	// full cost computed at load
	private final IntegerProperty cost;
	private final StringProperty seatsAsString;
	
	static private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH'h'mm");

	public Ticket () {
		this (0, 0, 0, TicketState.RESERVED, new ArrayList<Seat>(), "", "", "", new Date());
	}

	public Ticket(long id, long userId, long travelId, TicketState state, List<Seat> seats, String plane, String from, String to, Date day) {
		super();
		this.id = id;
		this.userId = userId;
		this.travelId = travelId;
		this.state = new SimpleObjectProperty<TicketState>(state);
		this.seats.addAll(seats);
		this.plane = new SimpleStringProperty(plane);
		this.from = new SimpleStringProperty(from);
		this.to = new SimpleStringProperty(to);
		this.day = new SimpleStringProperty(sdf.format(day));
		//computed derived properties
		this.cost = new SimpleIntegerProperty(computeCost());
		this.seatsAsString = new SimpleStringProperty(formatSeats());
	}

	private String formatSeats() {
		StringBuilder tmp = new StringBuilder();
		for (Seat seat : seats) {
			tmp.append(seat.getName().get());
			tmp.append(" ");
		}
		return tmp.toString();
	}

	private int computeCost() {
		int sum = 0;
		for (Seat seat : seats) {
			sum += seat.getCost().get();
		}
		return sum;
	}

	public long getId() {
		return id;
	}

	public long getUserId() {
		return userId;
	}

	public long getTravelId() {
		return travelId;
	}

	public ObjectProperty<TicketState> getState() {
		return state;
	}

	public ObservableList<Seat> getSeats() {
		return seats;
	}

	public StringProperty getPlane() {
		return plane;
	}

	public StringProperty getFrom() {
		return from;
	}

	public StringProperty getTo() {
		return to;
	}

	public StringProperty getDay() {
		return day;
	}

	public IntegerProperty getCost() {
		return cost;
	}

	public StringProperty getSeatsAsString() {
		return seatsAsString;
	}

	public void setWith (Ticket with) {
		if (with == null) {
			this.id = 0;
			this.userId = 0;
			this.travelId = 0;
			this.state.set(TicketState.NO);
			this.seats.clear();
			this.plane.set("");
			this.from.set("");
			this.to.set("");
			this.day.set("");
		} else {
			this.id = with.id;
			this.userId = with.userId;
			this.travelId = with.travelId;
			this.state.set(with.getState().get());
			this.seats.clear();
			this.seats.addAll(with.seats);
			this.plane.set(with.plane.get());
			this.from.set(with.from.get());
			this.to.set(with.to.get());
			this.day.set(with.day.get());
		}
		//compute derived properties
		this.cost.set(computeCost());
		this.seatsAsString.set(formatSeats());
	}
}
