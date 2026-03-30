package fr.ensisa.hassenforder.brutes.server.model;

import java.util.ArrayList;
import java.util.List;

public class Character {

	private String name;
	private long picture;
	private int level;
	private int life, speed, strength;
	private List<Bonus> bonus;

	public long getPicture() {
		return picture;
	}
	public void setPicture(long picture) {
		this.picture = picture;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public List<Bonus> getBonus() {
		if (bonus == null) bonus = new ArrayList<Bonus>();
		return bonus;
	}
	public void addBonus (Bonus bonus) {
		if (bonus == null) return;
		getBonus().add(bonus);
	}
	public int getFullLife() {
		int full = life;
		if (bonus != null) {
			for (Bonus b : getBonus()) {
				full += b.getLife();
			}
		}
		return full;
	}
	public int getFullSpeed() {
		int full = speed;
		if (bonus != null) {
			for (Bonus b : getBonus()) {
				full += b.getSpeed();
			}
		}
		return full;
	}
	public int getFullStrength() {
		int full = strength;
		if (bonus != null) {
			for (Bonus b : getBonus()) {
				full += b.getStrength();
			}
		}
		return full;
	}

}
