package fr.ensisa.hassenforder.flight.database;

import java.util.Map;
import java.util.TreeMap;

public class Plane {

	private long id;
	private final String name;
	private final Map<SeatComfort, SeatCount> maxSeats;

	public Plane(String name, SeatCount [] counts) {
		super();
		this.id = 0;
		this.name = name;
		this.maxSeats = new TreeMap<>();
		for (SeatCount count : counts) {
			this.maxSeats.put(count.getComfort(), count);
		}
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Map<SeatComfort, SeatCount> getMaxSeats() {
		return maxSeats;
	}

	protected void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Plane ");
		builder.append(name);
		for (SeatCount count : maxSeats.values()) {
			builder.append(", ");
			builder.append(count.getComfort().name().toLowerCase());
			builder.append("=");
			builder.append(count.getCount());
		}
		return builder.toString();
	}

}
