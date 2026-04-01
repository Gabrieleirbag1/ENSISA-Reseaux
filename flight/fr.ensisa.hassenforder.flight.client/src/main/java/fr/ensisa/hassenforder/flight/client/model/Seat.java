package fr.ensisa.hassenforder.flight.client.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Seat {

	private final long id;
	private int position;
	private long travelId;
	private long ticketId;
	private final ObjectProperty<SeatComfort> comfort;
	private final ObjectProperty<SeatState> state;
	private final IntegerProperty cost;
	private final StringProperty name;

	public Seat(long id, long travelId, long ticketId, SeatComfort comfort, int position, SeatState state, int cost) {
		super();
		this.id = id;
		this.travelId = travelId;
		this.ticketId = ticketId;
		this.position = position;
		this.comfort = new SimpleObjectProperty<SeatComfort>(comfort);
		this.state = new SimpleObjectProperty<SeatState>(state);
		this.cost = new SimpleIntegerProperty (cost);
		this.name = new SimpleStringProperty(buildName(comfort, position));
	}

	public long getId() {
		return id;
	}

	public long getTravelId() {
		return travelId;
	}

	public long getTicketId() {
		return ticketId;
	}

	public int getPosition() {
		return position;
	}

	public StringProperty getName() {
		return name;
	}

	public IntegerProperty getCost() {
		return cost;
	}

	static private String buildName (SeatComfort comfort, int position) {
		StringBuilder tmp = new StringBuilder();
		tmp.append(comfort.name().substring(0, 1));
		tmp.append(position);
		return tmp.toString();
	}

	public ObjectProperty<SeatComfort> getComfort() {
		return comfort;
	}

	public ObjectProperty<SeatState> getState() {
		return state;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Seat [id=");
		builder.append(id);
		builder.append(", ");
		builder.append("position=");
		builder.append(position);
		builder.append("] = ");
		builder.append(comfort.get().name().toLowerCase());
		builder.append(" ");
		builder.append(state.get().name().toLowerCase());
		builder.append(" €");
		builder.append(cost.get());
		return builder.toString();
	}

}
