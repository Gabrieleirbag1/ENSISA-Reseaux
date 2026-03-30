package fr.ensisa.hassenforder.brutes.client.network;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import fr.ensisa.hassenforder.brutes.client.model.Bonus;
import fr.ensisa.hassenforder.brutes.client.model.Character;
import fr.ensisa.hassenforder.brutes.client.model.Fight;
import fr.ensisa.hassenforder.brutes.network.Protocol;
import fr.ensisa.hassenforder.network.BasicAbstractReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClientReader extends BasicAbstractReader {


    public ClientReader(InputStream inputStream) {
        super(inputStream);
    }

    private void eraseFields() {
    }

	public void receive() {
        type = readInt();
        eraseFields();
        switch (type) {
        case Protocol.REPLY_OK:
            break;
        case Protocol.REPLY_KO:
        	break;
        }
    }

}
