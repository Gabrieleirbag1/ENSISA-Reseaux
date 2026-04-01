package fr.ensisa.hassenforder.flight.database;

public class User {

	private long id;
	private String name;
	private String mail;
	private String passwd;
	private int cash;

	public User(String name, String mail, String passwd, int cash) {
		super();
		this.name = name;
		this.mail = mail;
		this.passwd = passwd;
		this.cash = cash;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getMail() {
		return mail;
	}

	public String getPasswd() {
		return passwd;
	}

	public int getCash() {
		return cash;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User ");
		builder.append(name);
		builder.append(", ");
		builder.append(mail);
		builder.append(", ");
		builder.append(cash);
		builder.append("€");
		return builder.toString();
	}

}
