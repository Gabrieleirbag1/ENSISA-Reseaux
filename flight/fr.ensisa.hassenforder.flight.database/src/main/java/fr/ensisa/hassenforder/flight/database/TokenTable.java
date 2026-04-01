package fr.ensisa.hassenforder.flight.database;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class TokenTable {

	private Map<Long, String> tokens = new TreeMap<>();

	static public String newToken () {
		Random rnd = new Random();
		long value = rnd.nextInt(9000) + 1000;
		return ""+value;
	}

	public void addToken (long id, String token) {
		tokens.put(id, token);
	}

	public String addNewToken (long id) {
		String value = newToken ();
		addToken (id, value);
		return value;
	}

	public void removeToken (long id) {
		tokens.remove(id);
	}

	public String getToken (long id) {
		return tokens.get(id);
	}

}
