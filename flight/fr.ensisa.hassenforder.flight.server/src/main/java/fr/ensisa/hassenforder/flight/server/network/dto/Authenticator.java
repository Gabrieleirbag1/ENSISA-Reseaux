package fr.ensisa.hassenforder.flight.server.network.dto;

public class Authenticator {

	private long id;
	private String token;
	
	public Authenticator(long id, String token) {
		super();
		this.id = id;
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

}
