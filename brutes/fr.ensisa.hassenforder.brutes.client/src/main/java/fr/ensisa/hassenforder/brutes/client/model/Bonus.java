package fr.ensisa.hassenforder.brutes.client.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Bonus implements INamedPicture {

	private StringProperty name;
	private LongProperty picture;
	private IntegerProperty level;
	private IntegerProperty life, speed, strength;

	public Bonus() {
		name = new SimpleStringProperty();
		picture = new SimpleLongProperty (0);
		level = new SimpleIntegerProperty (0);
		life = new SimpleIntegerProperty (0);
		speed = new SimpleIntegerProperty (0);
		strength = new SimpleIntegerProperty (0);
	}

	public StringProperty getName() {
		return name;
	}

	public LongProperty getPicture() {
		return picture;
	}

	public IntegerProperty getLevel() {
		return level;
	}

	public IntegerProperty getLife() {
		return life;
	}

	public IntegerProperty getSpeed() {
		return speed;
	}

	public IntegerProperty getStrength() {
		return strength;
	}

}
