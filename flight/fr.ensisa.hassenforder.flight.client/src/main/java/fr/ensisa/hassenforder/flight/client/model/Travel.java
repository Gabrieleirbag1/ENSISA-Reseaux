package fr.ensisa.hassenforder.flight.client.model;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Travel {

	private final long id;
	private final Date travelDay;
	private final StringProperty from;
	private final StringProperty to;
	private final StringProperty day;
	private final IntegerProperty freeFirst;
	private final IntegerProperty freeBusiness;
	private final IntegerProperty freeEconomic;

	static private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH'h'mm");

	public Travel(long id, String from, String to, Date travelDay, List<SeatCount> counts) {
		super();
		this.id = id;
		this.travelDay = travelDay;
		this.from = new SimpleStringProperty(from);
		this.to = new SimpleStringProperty(to);
		this.day = new SimpleStringProperty(sdf.format(travelDay));
		this.freeFirst = new SimpleIntegerProperty(getComfortCount(counts, SeatComfort.FIRST));
		this.freeBusiness = new SimpleIntegerProperty(getComfortCount(counts, SeatComfort.BUSINESS));
		this.freeEconomic = new SimpleIntegerProperty(getComfortCount(counts, SeatComfort.ECONOMIC));
	}

	public Travel(long id, String from, String to, Date travelDay, SeatCount[] seatCounts) {
		this(id, from, to, travelDay, Arrays.asList(seatCounts));
	}

	private int getComfortCount(List<SeatCount> counts, SeatComfort comfort) {
		for (SeatCount count : counts) {
			if (count.getComfort() == comfort)
				return count.getCount();
		}
		return 0;
	}

	public long getId() {
		return id;
	}

	public Date getTravelDay() {
		return travelDay;
	}

	public StringProperty getFrom() {
		return from;
	}

	public StringProperty getTo() {
		return to;
	}

	public StringProperty getDay() {
		return day;
	}

	public IntegerProperty getFreeFirst() {
		return freeFirst;
	}

	public IntegerProperty getFreeBusiness() {
		return freeBusiness;
	}

	public IntegerProperty getFreeEconomic() {
		return freeEconomic;
	}

	public int getCost(SeatComfort comfort) {
		switch (comfort) {
		case BUSINESS:	return 200;
		case ECONOMIC:	return 100;
		case FIRST:		return 400;
		default:		return   0;
		}
	}

}
