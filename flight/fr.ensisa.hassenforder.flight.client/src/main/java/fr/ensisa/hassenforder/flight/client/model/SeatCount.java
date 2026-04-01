package fr.ensisa.hassenforder.flight.client.model;

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

	
}
