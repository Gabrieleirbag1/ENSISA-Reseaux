package fr.ensisa.hassenforder.brutes.server.network;

import java.io.InputStream;

import fr.ensisa.hassenforder.brutes.network.Protocol;
import fr.ensisa.hassenforder.brutes.server.model.Fight;
import fr.ensisa.hassenforder.network.BasicAbstractReader;

public class TCPReader extends BasicAbstractReader {

	private String name;
	private String firstName;
	private String secondName;
	private long picture;
	private Fight fight;

	public TCPReader(InputStream inputStream) {
		super (inputStream);
	}

	private void eraseFields() {
		name = null;
		firstName = null;
		secondName = null;
		picture = 0;
		fight = null;
	}

	private void readGetAll() {
	}

	private void readFight() {
		firstName = readString();
		secondName = readString();
		fight = Fight.values()[readInt()];
	}

	private void readGetPicture() {
		picture = readLong();
	}

	private void readGetCharacter() {
		name = readString();
	}

	private void readCreate() {
		name = readString();
	}

	private void readPopulate() {
	}

	public void receive() {
		type = readInt ();
		eraseFields ();
		switch (type) {
		case 0 : break;
		case Protocol.REQUEST_POPULATE		: readPopulate (); break;
		case Protocol.REQUEST_CREATE		: readCreate (); break;
		case Protocol.REQUEST_GET_CHARACTER	: readGetCharacter (); break;
		case Protocol.REQUEST_GET_PICTURE	: readGetPicture (); break;
		case Protocol.REQUEST_FIGHT			: readFight(); break;
		case Protocol.REQUEST_ALL			: readGetAll (); break;
		}
	}

	public String getName() {
		return name;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public long getPicture() {
		return picture;
	}

	public Fight getFight() {
		return fight;
	}

}
