package fr.ensisa.hassenforder.brutes.server.network;

import java.io.OutputStream;
import java.util.Collection;

import fr.ensisa.hassenforder.brutes.network.Protocol;
import fr.ensisa.hassenforder.brutes.server.model.Bonus;
import fr.ensisa.hassenforder.brutes.server.model.Character;
import fr.ensisa.hassenforder.brutes.server.model.Fight;
import fr.ensisa.hassenforder.network.BasicAbstractWriter;

public class TCPWriter extends BasicAbstractWriter {

    public TCPWriter(OutputStream outputStream) {
        super (outputStream);
    }

    public void createOK() {
        writeInt(Protocol.REPLY_OK);
    }

    public void createKO() {
        writeInt(Protocol.REPLY_KO);
    }

    public void createGetCharacter(Character character) {
        writeInt(Protocol.REPLY_OK);
        writeCharacter(character);
    }

    private void writeCharacter(Character character) {
        writeString(character.getName());
        writeLong(character.getPicture());
        writeInt(character.getLevel());
        writeInt(character.getLife());
        writeInt(character.getSpeed());
        writeInt(character.getStrength());
        writeInt(0);
    }

}
