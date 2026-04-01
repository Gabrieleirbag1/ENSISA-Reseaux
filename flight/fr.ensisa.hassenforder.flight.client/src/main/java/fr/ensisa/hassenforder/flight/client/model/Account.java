package fr.ensisa.hassenforder.flight.client.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account {

	private LongProperty id;
	private StringProperty name;
	private StringProperty mail;
	private StringProperty passwd;
	private IntegerProperty cash;
	
	//derived property
	private StringProperty state;

	public Account() {
		this (0, "", "", "", 0);
	}

	public Account(long id, String name, String mail, String passwd, int cash) {
		super();
		this.id = new SimpleLongProperty(id);
		this.name = new SimpleStringProperty(name);
		this.mail = new SimpleStringProperty(mail);
		this.passwd = new SimpleStringProperty(passwd);
		this.state = new SimpleStringProperty(buildState());
		this.cash = new SimpleIntegerProperty(cash);
		this.name.addListener( l -> this.state.set(buildState()));
		this.mail.addListener( l -> this.state.set(buildState()));
		this.passwd.addListener( l -> this.state.set(buildState()));
	}

	private String buildState() {
		if (name.get().isEmpty()) return "can't use an empty name";
		if (mail.get().isEmpty()) return "can't use an empty mail";
		if (passwd.get().isEmpty()) return "can't use an empty passwd";
		return "valid ... or not";
	}

	public long getId() {
		return id.get();
	}

	public StringProperty getName() {
		return name;
	}

	public StringProperty getMail() {
		return mail;
	}

	public StringProperty getPasswd() {
		return passwd;
	}

	public IntegerProperty getCash() {
		return cash;
	}

	public StringProperty getState() {
		return state;
	}

	public void setWith(Account with) {
		if (with == null) {
			this.id.set(0);
			this.name.set("");
			this.mail.set("");
			this.passwd.set("");
			this.cash.set(0);
		} else {
			this.id.set(with.getId());
			this.name.set(with.getName().get());
			this.mail.set(with.getMail().get());
			this.passwd.set("");
			this.cash.set(with.getCash().get());
		}
	}

}
