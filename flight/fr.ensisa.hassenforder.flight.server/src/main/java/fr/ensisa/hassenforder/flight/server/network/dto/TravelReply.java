package fr.ensisa.hassenforder.flight.server.network.dto;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import fr.ensisa.hassenforder.flight.database.Plane;
import fr.ensisa.hassenforder.flight.database.SeatComfort;
import fr.ensisa.hassenforder.flight.database.SeatCount;
import fr.ensisa.hassenforder.flight.database.Travel;

public class TravelReply {

	private final Travel travel;
	private final Plane plane;
	private final Map<SeatComfort, SeatCount> seatUsed;
	
	public TravelReply(Travel travel, Plane plane, Map<SeatComfort, SeatCount> seatUsed) {
		super();
		this.travel = travel;
		this.plane = plane;
		this.seatUsed = seatUsed;
	}

	public long getId() {
		return travel.getId();
	}

	public String getFrom() {
		return travel.getFrom();
	}

	public String getTo() {
		return travel.getTo();
	}

	public Date getTravelDay() {
		return travel.getTravelDay();
	}

	public Map<SeatComfort, SeatCount> getRemainingSeatCounts() {
		Map<SeatComfort, SeatCount> remains = new TreeMap<>();
		for (SeatComfort comfort: plane.getMaxSeats().keySet()) {
			SeatCount remain = null;
			int max = plane.getMaxSeats().get(comfort).getCount();
			SeatCount used = seatUsed.get(comfort);
			if (used != null) remain = new SeatCount(comfort, max - used.getCount());
			if (used == null) remain = new SeatCount(comfort, max);
			if (remain != null) remains.put(comfort, remain);
		}
		return remains;
	}

}
