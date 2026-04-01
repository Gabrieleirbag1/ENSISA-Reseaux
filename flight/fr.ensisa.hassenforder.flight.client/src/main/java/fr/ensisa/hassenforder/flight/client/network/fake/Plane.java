package fr.ensisa.hassenforder.flight.client.network.fake;

import java.util.ArrayList;
import java.util.List;

import fr.ensisa.hassenforder.flight.client.model.SeatCount;

public class Plane {

	private final long id;
	private final String name;
	private final List<SeatCount> counts;
	
	public Plane(long id, String name, List<SeatCount> counts) {
		super();
		this.id = id;
		this.name = name;
		this.counts= counts;
	}

	public Plane(long id, String name, SeatCount[] counts) {
		super();
		this.id = id;
		this.name = name;
		this.counts= new ArrayList<SeatCount>();
		for (SeatCount c : counts)
			this.counts.add(c);
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<SeatCount> getCounts() {
		return counts;
	}

}
