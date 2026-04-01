package fr.ensisa.hassenforder.flight.client.network;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import fr.ensisa.hassenforder.flight.client.model.Account;
import fr.ensisa.hassenforder.flight.client.model.Authenticator;
import fr.ensisa.hassenforder.flight.client.model.Connected;
import fr.ensisa.hassenforder.flight.client.model.PaymentResult;
import fr.ensisa.hassenforder.flight.client.model.PaymentState;
import fr.ensisa.hassenforder.flight.client.model.Seat;
import fr.ensisa.hassenforder.flight.client.model.SeatComfort;
import fr.ensisa.hassenforder.flight.client.model.SeatCount;
import fr.ensisa.hassenforder.flight.client.model.SeatState;
import fr.ensisa.hassenforder.flight.client.model.Ticket;
import fr.ensisa.hassenforder.flight.client.model.TicketState;
import fr.ensisa.hassenforder.flight.client.model.Travel;
import fr.ensisa.hassenforder.flight.client.model.User;
import fr.ensisa.hassenforder.flight.network.Protocol;
import fr.ensisa.hassenforder.network.BasicAbstractReader;

public class ClientTCPReader extends BasicAbstractReader {

    public ClientTCPReader(InputStream inputStream) {
        super(inputStream);
    }

    private void eraseFields() {
    }

    public void receive() {
        type = readInt();
        eraseFields();
        switch (type) {
        case Protocol.REPLY_NOWAY:			break;
        case Protocol.REPLY_KO:				break;
        case Protocol.REPLY_OK:				break;
        }
    }

}
