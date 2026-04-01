package fr.ensisa.hassenforder.flight.database;

public class Seat {

	private long id;
	private final int position;
	private final long travelId;
	private final long ticketId;
	private final SeatComfort comfort;
	private final SeatState state;
	private final int cost;
	//derived	
	private final String name;

	public Seat(long travelId, long ticketId, SeatComfort comfort, int position, SeatState state, int cost) {
		super();
		this.id = 0;
		this.travelId = travelId;
		this.ticketId = ticketId;
		this.position = position;
		this.comfort = comfort;
		this.state = state;
		this.cost = cost;
		this.name = buildName(comfort, position);
	}

	public Seat(SeatComfort comfort, int position) {
		this (0, 0, comfort, position, SeatState.RESERVED, 0);
	}

	public long getId() {
		return id;
	}

	public int getPosition() {
		return position;
	}

	public long getTravelId() {
		return travelId;
	}

	public long getTicketId() {
		return ticketId;
	}

	public SeatComfort getComfort() {
		return comfort;
	}

	public SeatState getState() {
		return state;
	}

	public int getCost() {
		return cost;
	}

	public String getName () {
		return name;
	}
	
	public static String buildName(SeatComfort comfort, int position) {
		StringBuilder tmp = new StringBuilder();
		tmp.append(comfort.name().substring(0,1));
		tmp.append(position);
		return tmp.toString();
	}

	protected void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Seat ");
		builder.append(name);
		builder.append(", ");
		builder.append(travelId);
		builder.append(", ");
		builder.append(ticketId);
		builder.append(", ");
		builder.append(state);
		builder.append(", ");
		builder.append(cost);
		builder.append("€");
		return builder.toString();
	}

}
