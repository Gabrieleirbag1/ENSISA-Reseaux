package fr.ensisa.hassenforder.brutes.server.network;

import java.io.InputStream;

import fr.ensisa.hassenforder.brutes.network.Protocol;
import fr.ensisa.hassenforder.brutes.server.model.Fight;
import fr.ensisa.hassenforder.network.BasicAbstractReader;

public class TCPReader extends BasicAbstractReader {

	public TCPReader(InputStream inputStream) {
		super (inputStream);
	}

	private void eraseFields() {
	}

	public void receive() {
		type = readInt ();
		eraseFields ();
		switch (type) {
		case 0 : break;
		}
	}

}
