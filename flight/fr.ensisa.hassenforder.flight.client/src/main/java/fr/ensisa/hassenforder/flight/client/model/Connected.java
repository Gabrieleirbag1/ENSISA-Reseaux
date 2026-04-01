package fr.ensisa.hassenforder.flight.client.model;

public class Connected {

	private Authenticator authenticator;
	private User user;

	public Connected(Authenticator authenticator, User user) {
		super();
		this.authenticator = authenticator;
		this.user = user;
	}

	public Authenticator getAuthenticator () {
		return authenticator;
	}

	public User getUser() {
		return user;
	}


}
