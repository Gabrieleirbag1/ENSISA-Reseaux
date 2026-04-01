package fr.ensisa.hassenforder.flight.database;

import java.util.Date;

public class Travel {

	private long id;
	private final String from;
	private final String to;
	private final Date travelDay;
	private final long planeId;

	public Travel(String from, String to, Date travelDay, long planeId) {
		super();
		this.id = 0;
		this.from = from;
		this.to = to;
		this.travelDay = travelDay;
		this.planeId = planeId;
	}

	public long getId() {
		return id;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public Date getTravelDay() {
		return travelDay;
	}

	public long getPlaneId() {
		return planeId;
	}

	protected void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Travel ");
		builder.append(from);
		builder.append(" -> ");
		builder.append(to);
		builder.append(" @ ");
		builder.append(travelDay);
		return builder.toString();
	}

}
