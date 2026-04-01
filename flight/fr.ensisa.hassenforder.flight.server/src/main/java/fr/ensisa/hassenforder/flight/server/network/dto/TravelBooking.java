package fr.ensisa.hassenforder.flight.server.network.dto;

import java.util.List;

import fr.ensisa.hassenforder.flight.database.SeatCount;

public class TravelBooking {

	private long travelId;
	private List<SeatCount> counts;
	
	public TravelBooking(long travelId, List<SeatCount> counts) {
		super();
		this.travelId = travelId;
		this.counts = counts;
	}

	public long getTravelId() {
		return travelId;
	}

	public List<SeatCount> getCounts() {
		return counts;
	}
	
}
