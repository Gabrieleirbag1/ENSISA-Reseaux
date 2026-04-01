package fr.ensisa.hassenforder.flight.database;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class TravelTable {

	private static long LAST_ID = 0;

	private Map<Long, Travel> travels = null;

	private Map<Long, Travel> getTravels () {
		if (travels == null) {
			travels = new TreeMap<>();
		}
		return travels;
	}

	public long addTravel (Travel travel) {
		travel.setId (++LAST_ID);
		getTravels ().put(travel.getId(), travel);
		return travel.getId();
	}

	public Travel getTravel (long id) {
		return getTravels().get(id);
	}

	public Collection<Travel> getAll() {
		return getTravels().values();
	}
	
	public void removeTravel (long travelId) {
		getTravels().remove(travelId);
	}

}
