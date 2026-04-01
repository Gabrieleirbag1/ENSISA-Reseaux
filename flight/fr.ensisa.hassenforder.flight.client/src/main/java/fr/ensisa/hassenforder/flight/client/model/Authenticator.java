package fr.ensisa.hassenforder.flight.client.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Authenticator {

	private long id;
	private String token;
	private BooleanProperty isAuthenticated;
	private StringProperty state;
	
	public Authenticator() {
		this(0, null);
	}
	
	public Authenticator(long id, String token) {
		super();
		this.id = id;
		this.token = token;
		this.isAuthenticated = new SimpleBooleanProperty(buildAuthenticated());
		this.state = new SimpleStringProperty(buildState());
	}

	public long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public BooleanProperty getIsAuthenticated() {
		return isAuthenticated;
	}

	public StringProperty getState() {
		return state;
	}

	private String buildState() {
		if (id == 0) return "anonymous";
		if (token == null || token.isEmpty()) return "unconnected";
		return "connected";
	}

	private boolean buildAuthenticated() {
		return id != 0;
	}

	public void setWith (Authenticator with) {
		if (with == null) {
			this.id = 0;
			this.token = null;
		} else {
			this.id = with.id;
			this.token = with.token;
		}
		this.isAuthenticated.set(buildAuthenticated());
		this.state.set(buildState());
	}

}
