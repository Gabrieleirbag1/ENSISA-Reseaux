package fr.ensisa.hassenforder.brutes.client.network;

import java.io.OutputStream;

import fr.ensisa.hassenforder.brutes.client.model.Fight;
import fr.ensisa.hassenforder.brutes.network.Protocol;
import fr.ensisa.hassenforder.network.BasicAbstractWriter;

public class ClientWriter extends BasicAbstractWriter {

    public ClientWriter(OutputStream outputStream) {
        super(outputStream);
    }

}
