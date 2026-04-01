package fr.ensisa.hassenforder.flight.server.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.ensisa.hassenforder.flight.database.SeatComfort;
import fr.ensisa.hassenforder.flight.database.SeatCount;
import fr.ensisa.hassenforder.flight.database.User;
import fr.ensisa.hassenforder.flight.network.Protocol;
import fr.ensisa.hassenforder.flight.server.network.dto.Authenticator;
import fr.ensisa.hassenforder.flight.server.network.dto.TravelBooking;
import fr.ensisa.hassenforder.flight.server.network.dto.TravelFilter;
import fr.ensisa.hassenforder.network.BasicAbstractReader;

public class ServerTCPReader extends BasicAbstractReader {

	public ServerTCPReader(InputStream inputStream) {
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
