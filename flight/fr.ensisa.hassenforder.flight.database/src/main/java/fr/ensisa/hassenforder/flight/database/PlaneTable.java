package fr.ensisa.hassenforder.flight.database;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class PlaneTable {

	private static long LAST_ID = 0;

	private Map<Long, Plane> planes = null;

	private Map<Long, Plane> getPlanes () {
		if (planes == null) {
			planes = new TreeMap<>();
		}
		return planes;
	}

	public long addPlane (Plane plane) {
		plane.setId (++LAST_ID);
		getPlanes ().put(plane.getId(), plane);
		return plane.getId();
	}

	public Plane getPlane (long id) {
		return getPlanes().get(id);
	}

	public Collection<Plane> getAll() {
		return getPlanes().values();
	}
	
	public void removePlane (long planeId) {
		getPlanes().remove(planeId);
	}

}
