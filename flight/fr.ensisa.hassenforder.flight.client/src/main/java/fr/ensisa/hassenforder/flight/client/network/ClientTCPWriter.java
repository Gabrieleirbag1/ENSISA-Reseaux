package fr.ensisa.hassenforder.flight.client.network;

import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import fr.ensisa.hassenforder.flight.client.model.Account;
import fr.ensisa.hassenforder.flight.client.model.Authenticator;
import fr.ensisa.hassenforder.flight.client.model.SeatCount;
import fr.ensisa.hassenforder.flight.network.Protocol;
import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.DateUtils;

public class ClientTCPWriter extends BasicAbstractWriter {

    public ClientTCPWriter(OutputStream outputStream) {
        super(outputStream);
    }


}
