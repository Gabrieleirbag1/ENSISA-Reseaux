package fr.ensisa.hassenforder.flight.server.network;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import fr.ensisa.hassenforder.flight.database.Seat;
import fr.ensisa.hassenforder.flight.database.SeatComfort;
import fr.ensisa.hassenforder.flight.database.SeatCount;
import fr.ensisa.hassenforder.flight.database.SeatState;
import fr.ensisa.hassenforder.flight.database.Ticket;
import fr.ensisa.hassenforder.flight.database.TicketState;
import fr.ensisa.hassenforder.flight.database.User;
import fr.ensisa.hassenforder.flight.network.Protocol;
import fr.ensisa.hassenforder.flight.server.network.dto.PaymentResult;
import fr.ensisa.hassenforder.flight.server.network.dto.PaymentState;
import fr.ensisa.hassenforder.flight.server.network.dto.TicketReply;
import fr.ensisa.hassenforder.flight.server.network.dto.TravelReply;
import fr.ensisa.hassenforder.network.BasicAbstractWriter;

public class ServerTCPWriter extends BasicAbstractWriter {

    public ServerTCPWriter(OutputStream outputStream) {
        super (outputStream);
    }

    public void createOK() {
        writeInt(Protocol.REPLY_OK);
    }

    public void createKO() {
        writeInt(Protocol.REPLY_KO);
    }

    public void createNOWAY() {
        writeInt(Protocol.REPLY_NOWAY);
    }

}
