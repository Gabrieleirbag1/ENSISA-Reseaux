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

    private void writeBytes(byte[] fileContent) {
        if (fileContent == null) {
            writeInt (0);
        } else {
            writeInt (fileContent.length);
            for (int i=0; i<fileContent.length; ++i) {
                writeByte(fileContent[i]);
            }
        }
    }

    public void createCharacter(Character character) {
    	writeInt(Protocol.REPLY_CHARACTER);
    	writeString (character.getName());
    	writeLong(character.getPicture());
    	writeInt(character.getLevel());
    	writeInt(character.getLife());
    	writeInt(character.getStrength());
    	writeInt(character.getSpeed());
    	writeInt(character.getBonus().size());
    	for (Bonus b : character.getBonus()) {
        	writeString (b.getName());
        	writeLong(b.getPicture());
        	writeInt(b.getLevel());
        	writeInt(b.getLife());
        	writeInt(b.getStrength());
        	writeInt(b.getSpeed());
    	}
	}

	public void createPicture(byte[] content) {
    	writeInt(Protocol.REPLY_PICTURE);
    	writeBytes(content);
	}

	public void createFight(Fight fight) {
    	writeInt(Protocol.REPLY_FIGHT);
    	writeInt(fight.ordinal());
	}

	public void createCharacters(Collection<Character> characters) {
    	writeInt(Protocol.REPLY_CHARACTERS);
    	writeInt(characters.size());
    	for (Character character : characters) {
    		writeString (character.getName());
        	writeLong(character.getPicture());
        	writeInt(character.getLevel());
        	writeInt(character.getFullLife());
        	writeInt(character.getFullStrength());
        	writeInt(character.getFullSpeed());
        	writeInt(0); // no bonus
    	}
	}

}
