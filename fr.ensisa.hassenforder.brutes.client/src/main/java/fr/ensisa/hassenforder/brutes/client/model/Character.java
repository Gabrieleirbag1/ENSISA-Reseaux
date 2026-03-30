package fr.ensisa.hassenforder.brutes.client.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Character implements INamedPicture {

	private StringProperty name;
	private LongProperty picture;
	private IntegerProperty level;
	private IntegerProperty life, speed, strength;
	private IntegerProperty fullLife, fullSpeed, fullStrength;
	private ObservableList<Bonus> bonus;

	public Character() {
		super();
		name = new SimpleStringProperty();
		picture = new SimpleLongProperty (0);
		level = new SimpleIntegerProperty (0);
		life = new SimpleIntegerProperty (0);
		speed = new SimpleIntegerProperty (0);
		strength = new SimpleIntegerProperty (0);
		fullLife = new SimpleIntegerProperty(0);
		fullSpeed = new SimpleIntegerProperty(0);
		fullStrength = new SimpleIntegerProperty(0);
		bonus = FXCollections.observableArrayList();
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

	public ObservableList<Bonus> getBonus() {
		return bonus;
	}

	public void addBonus (Bonus bonus) {
		getBonus().add(bonus);
	}

	public void update () {
		int fl = life.get();
		int fs = speed.get();
		int fz = strength.get();
		if (bonus != null) {
			for (Bonus b : getBonus()) {
				fl += b.getLife().get();
				fs += b.getSpeed().get();
				fz += b.getStrength().get();
			}
		}
		fullLife.set(fl);
		fullSpeed.set(fs);
		fullStrength.set(fz);
	}

	public IntegerProperty getFullLife() {
		return fullLife;
	}

	public IntegerProperty getFullSpeed() {
		return fullSpeed;
	}

	public IntegerProperty getFullStrength() {
		return fullStrength;
	}

}
