package fr.ensisa.hassenforder.brutes.server.network;

import java.io.InputStream;

import fr.ensisa.hassenforder.brutes.network.Protocol;
import fr.ensisa.hassenforder.brutes.server.model.Fight;
import fr.ensisa.hassenforder.network.BasicAbstractReader;

public class TCPReader extends BasicAbstractReader {
	
	private String name;
	private long id;

	public TCPReader(InputStream inputStream) {
		super (inputStream);
	}

	private void eraseFields() {
		name = null;
	}

	private String readCreate() {
		return readString();
	}

	private String readGetCharacter() {
		return readString();
	}

	private Long readGetpicture() {
		return readLong();
	}

	public void receive() {
		type = readInt ();
		eraseFields ();
		switch (type) {
		case 0 : break;
		case Protocol.REQUEST_CREATE: name = readCreate(); break;
		case Protocol.REQUEST_GET_CHARACTER: name = readGetCharacter(); break;
		case Protocol.REQUEST_GET_PICTURE: id = readGetpicture(); break;
		}
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

}
