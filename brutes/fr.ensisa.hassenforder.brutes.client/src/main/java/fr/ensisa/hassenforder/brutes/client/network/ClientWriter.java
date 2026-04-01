package fr.ensisa.hassenforder.brutes.client.network;

import java.io.OutputStream;

import fr.ensisa.hassenforder.brutes.client.model.Fight;
import fr.ensisa.hassenforder.brutes.network.Protocol;
import fr.ensisa.hassenforder.network.BasicAbstractWriter;

public class ClientWriter extends BasicAbstractWriter {

    public ClientWriter(OutputStream outputStream) {
        super(outputStream);
    }

	public void populateCharacters() {
        writeInt(Protocol.REQUEST_POPULATE);
	}

	public void createCreateCharacter(String name) {
        writeInt(Protocol.REQUEST_CREATE);
        writeString(name);
	}

	public void createGetCharacter(String name) {
        writeInt(Protocol.REQUEST_GET_CHARACTER);
        writeString(name);
	}

	public void createGetPicture(long id) {
        writeInt(Protocol.REQUEST_GET_PICTURE);
        writeLong(id);
	}

	public void createFight(String left, String right, Fight expected) {
        writeInt(Protocol.REQUEST_FIGHT);
        writeString(left);
        writeString(right);
        writeInt(expected.ordinal());
	}

	public void getAllCharacters() {
        writeInt(Protocol.REQUEST_ALL);
	}

}
