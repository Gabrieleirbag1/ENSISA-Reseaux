package fr.ensisa.hassenforder.flight.database;

public class SeatCost {

	private SeatComfort comfort;
	private int cost;
	
	public SeatCost(SeatComfort comfort, int cost) {
		super();
		this.comfort = comfort;
		this.cost = cost;
	}
	
	public SeatComfort getComfort() {
		return comfort;
	}

	public int getCost() {
		return cost;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeatCost [");
		builder.append(comfort);
		builder.append(cost);
		builder.append("]");
		return builder.toString();
	}

	
}
