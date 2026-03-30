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
    private byte [] picture;
    private Fight fight;
    private List<Character> characters;

    public ClientReader(InputStream inputStream) {
        super(inputStream);
    }

    private void eraseFields() {
        this.character = null;
        this.picture = null;
        this.fight = null;
        this.characters = null;
    }

	private Bonus readBonus() {
		Bonus bonus = new Bonus();
		bonus.getName().set(readString());
		bonus.getPicture().set(readLong());
		bonus.getLevel().set(readInt());
		bonus.getLife().set(readInt());
		bonus.getStrength().set(readInt());
		bonus.getSpeed().set(readInt());
		return bonus;
	}

	private Character readCharacter() {
		Character character = new Character();
		character.getName().set(readString());
		character.getPicture().set(readLong());
		character.getLevel().set(readInt());
		character.getLife().set(readInt());
		character.getStrength().set(readInt());
		character.getSpeed().set(readInt());
		int size = readInt();
		for (int i=0; i<size;++i) {
			character.addBonus(readBonus());
		}
		return character;
	}

	private byte [] readPicture() {
		int size = readInt();
		byte [] array = new byte [size];
		if (size == 0) return array;
		try {
			inputStream.readFully (array);
		} catch (IOException e) {
			return null;
		}
		return array;
	}

	private Fight readFight() {
		Fight fight = null;
		fight = Fight.values()[readInt()];
		return fight;
	}

	private List<Character> readCharacters() {
		ObservableList<Character> characters = FXCollections.observableArrayList();
		int size = readInt();
		for (int i=0; i<size;++i) {
			characters.add(readCharacter());
		}
		return characters;
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
        case Protocol.REPLY_PICTURE:
        	picture = readPicture();
            break;
        case Protocol.REPLY_FIGHT:
        	fight = readFight();
            break;
        case Protocol.REPLY_CHARACTERS:
        	characters = readCharacters();
            break;
        }
    }

	public Character getCharacter() {
		return character;
	}

	public byte[] getPicture() {
		return picture;
	}

	public Fight getFight() {
		return fight;
	}

	public List<Character> getCharacters() {
		return characters;
	}

}
