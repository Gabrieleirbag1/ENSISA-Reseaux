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

    private Character character;

    public ClientReader(InputStream inputStream) {
        super(inputStream);
    }

    private Character readCharacter() {
        Character character = new Character();
        character.getName().set(readString());
        character.getPicture().set(readLong());
        character.getLevel().set(readInt());
        character.getLife().set(readInt());
        character.getSpeed().set(readInt());
        character.getStrength().set(readInt());
        readInt(); // number of bonus
        return character;
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
        case Protocol.REPLY_CHARACTER:
         character = readCharacter();
         break;
        }
    }

    public Character getCharacter() {
        return character;
    }

}
