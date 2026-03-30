package fr.ensisa.hassenforder.brutes.server.network;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

import fr.ensisa.hassenforder.brutes.network.Protocol;
import fr.ensisa.hassenforder.brutes.server.model.Character;
import fr.ensisa.hassenforder.brutes.server.model.Fight;
import fr.ensisa.hassenforder.brutes.server.model.Model;
import fr.ensisa.hassenforder.io.FileHelper;

public class TCPSession extends Thread {

	private Socket connection;
	private Model model;

	public TCPSession(Socket connection, Model model) {
		this.connection = connection;
		this.model = model;
	}

	public void close () {
		this.interrupt();
		try {
			if (connection != null)
				connection.close();
		} catch (IOException e) {
		}
		connection = null;
	}

	private void processPopulate(TCPReader reader, TCPWriter writer) {
		if (model.getAllCharacters().size() == 1) {
			model.populate();
		}
		writer.createOK();
	}

	private void processCreate(TCPReader reader, TCPWriter writer) {
		Character character = model.createCharacter(reader.getName());
		boolean result = model.add(character);
		if (result) {
			writer.createOK();
		} else {
			writer.createKO();
		}
	}

	private void processGetCharacter(TCPReader reader, TCPWriter writer) {
		Character character = model.getCharacter(reader.getName());
		if (character != null) {
			writer.createGetCharacter(character);
		} else {
			writer.createKO();
		}
	}

	public boolean operate() {
		try {
			TCPWriter writer = new TCPWriter (connection.getOutputStream());
			TCPReader reader = new TCPReader (connection.getInputStream());
			reader.receive ();
			switch (reader.getType ()) {
			case 0 : return false; // socket closed
			case Protocol.REQUEST_POPULATE		: processPopulate (reader, writer); break;
			case Protocol.REQUEST_CREATE		: processCreate (reader, writer); break;
			case Protocol.REQUEST_GET_CHARACTER	: processGetCharacter (reader, writer); break; // TODO
			default: return false; // connection jammed
			}
			writer.send ();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public void run() {
		while (true) {
			if (! operate())
				break;
		}
		try {
			if (connection != null) connection.close();
		} catch (IOException e) {
		}
	}

}
