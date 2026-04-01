package fr.ensisa.hassenforder.flight.client.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

	private long id;
	private final StringProperty name;
	private final IntegerProperty cash;

	public User() {
		this (0, null, 0);
	}
	
	public User(long id, String name, int cash) {
		super();
		this.id = id;
		this.name = new SimpleStringProperty(name);
		this.cash = new SimpleIntegerProperty(cash);
	}

	public long getId() {
		return id;
	}

	public StringProperty getName () {
		return name;
	}

	public IntegerProperty getCash () {
		return cash;
	}

	public void setWith (User with) {
		if (with == null) {
			this.name.set("");
			this.cash.set(0);
		} else {
			this.id = with.id;
			this.name.set(with.name.get());
			this.cash.set(with.cash.get());
		}
	}

}
