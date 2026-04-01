package fr.ensisa.hassenforder.flight.server.network.dto;

import java.util.Date;

public class TravelFilter {

	private final String from;
	private final String to;
	private final Date day;
	
	public TravelFilter(String from, String to, Date day) {
		super();
		this.from = from;
		this.to = to;
		this.day = day;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public Date getDay() {
		return day;
	}

}
