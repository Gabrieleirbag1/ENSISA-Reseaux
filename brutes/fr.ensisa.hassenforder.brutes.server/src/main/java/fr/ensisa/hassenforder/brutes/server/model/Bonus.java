package fr.ensisa.hassenforder.brutes.server.model;

public class Bonus {

	public String name;
	private long picture;
	public int level;
	public int life, speed, strength;


	public Bonus(String name, long picture, int level, int life, int speed, int strength) {
		super();
		this.name = name;
		this.picture = picture;
		this.level = level;
		this.life = life;
		this.speed = speed;
		this.strength = strength;
	}

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

}
