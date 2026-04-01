package fr.ensisa.hassenforder.flight.database;

public class SeatCount {

	private SeatComfort comfort;
	private int count;
	
	public SeatCount(SeatComfort comfort, int count) {
		super();
		this.comfort = comfort;
		this.count = count;
	}
	
	public SeatComfort getComfort() {
		return comfort;
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeatCount [");
		builder.append(comfort);
		builder.append(count);
		builder.append("]");
		return builder.toString();
	}


}
