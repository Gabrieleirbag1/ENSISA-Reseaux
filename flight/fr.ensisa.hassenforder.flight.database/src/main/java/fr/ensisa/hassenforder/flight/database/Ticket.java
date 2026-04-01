package fr.ensisa.hassenforder.flight.database;

public class Ticket {

	private long id;
	private final long userId;
	private final long travelId;
	private TicketState state;

	public Ticket(long userId, long travelId, TicketState state) {
		super();
		this.id = 0;
		this.userId = userId;
		this.travelId = travelId;
		this.state = state;
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

	public TicketState getState() {
		return state;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public void setState(TicketState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ticket ");
		builder.append(userId);
		builder.append(", ");
		builder.append(travelId);
		builder.append(", ");
		builder.append(state);
		return builder.toString();
	}

}
