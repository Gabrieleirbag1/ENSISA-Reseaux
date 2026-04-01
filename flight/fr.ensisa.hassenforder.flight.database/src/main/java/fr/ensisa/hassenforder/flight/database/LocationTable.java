package fr.ensisa.hassenforder.flight.database;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class LocationTable {

	@SuppressWarnings("unused")
	private static long LAST_ID = 0;

	private Map<String, Location> locations = null;

	private Map<String, Location> getLocations () {
		if (locations == null) {
			locations = new TreeMap<>();
		}
		return locations;
	}

	public void addLocation (Location location) {
		getLocations ().put(location.getName(), location);
	}

	public Location getLocation (String name) {
		return getLocations().get(name);
	}

	public Collection<Location> getAll() {
		return getLocations().values();
	}
	
	public void removeLocation (String name) {
		getLocations().remove(name);
	}

}
