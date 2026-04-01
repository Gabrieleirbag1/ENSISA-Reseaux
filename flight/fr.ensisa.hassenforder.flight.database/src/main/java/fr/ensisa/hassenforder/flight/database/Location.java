package fr.ensisa.hassenforder.flight.database;

public class Location {

	private final String name;
	private final double longitude;
	private final double latitude;

	public Location(String name, double longitude, double latitude) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public String getName() {
		return name;
	}

	static public double distance (Location a, Location b) {
		if (a == null) return 1000;
		if (b == null) return 1000;
		return Math.sqrt(a.latitude*b.latitude + a.longitude*b.longitude);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("@ ");
		builder.append(longitude);
		builder.append(", ");
		builder.append(latitude);
		return builder.toString();
	}

}
