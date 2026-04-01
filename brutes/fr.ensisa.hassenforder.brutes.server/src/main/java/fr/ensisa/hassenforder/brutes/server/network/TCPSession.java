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
		String name = reader.getName();
		if (model.existCharacter(name)) {
			writer.createKO ();
		}
		Character character = model.createCharacter(name);
		if (character == null) {
			writer.createKO ();
		}
		model.add(character);
		writer.createOK();
	}

	private void processGetCharacter(TCPReader reader, TCPWriter writer) {
		String name = reader.getName();
		Character character = model.getCharacter(name);
		if (character == null) {
			writer.createKO ();
		} else {
			writer.createCharacter (character);
		}
	}

	private void processGetPicture(TCPReader reader, TCPWriter writer) {
		long picture = reader.getPicture();
		String pictureName = null;
		if (pictureName == null) {
			pictureName = Model.characterPictureName(picture);
			if (! FileHelper.fileExists(pictureName)) pictureName = null;
		}
		if (pictureName == null) {
			pictureName = Model.bonusPictureName(picture);
			if (! FileHelper.fileExists(pictureName)) pictureName = null;
		}
		byte content [] = FileHelper.readContent(pictureName);
		if (content == null) {
			writer.createKO ();
		} else {
			writer.createPicture (content);
		}
	}

	private void processFight(TCPReader reader, TCPWriter writer) {
		String left = reader.getFirstName();
		String right = reader.getSecondName();
		Fight fight = reader.getFight();
		if (fight == null) {
			writer.createKO ();
		} else {
			if (fight == Fight.WIN) {
				model.win(left, right);
			}
			if (fight == Fight.LOSE) {
				model.lose(left, right);
			}
			writer.createFight (fight);
		}
	}

	private void processGetAll(TCPReader reader, TCPWriter writer) {
		Collection<Character> characters = model.getAllCharacters();
		writer.createCharacters (characters);
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
			case Protocol.REQUEST_GET_CHARACTER	: processGetCharacter (reader, writer); break;
			case Protocol.REQUEST_GET_PICTURE	: processGetPicture (reader, writer); break;
			case Protocol.REQUEST_FIGHT			: processFight(reader, writer); break;
			case Protocol.REQUEST_ALL			: processGetAll (reader, writer); break;
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
