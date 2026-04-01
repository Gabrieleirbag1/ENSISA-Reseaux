package fr.ensisa.hassenforder.brutes.client.network;

import java.util.List;
import java.util.Random;

import fr.ensisa.hassenforder.brutes.client.model.Bonus;
import fr.ensisa.hassenforder.brutes.client.model.Character;
import fr.ensisa.hassenforder.brutes.client.model.Fight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FakeSession implements ISession {

	static Random rnd = new Random();

    public FakeSession(String host, int port) {
    }

    private Bonus buildFakeBonus (String name, int id) {
    	Bonus bonus = new Bonus ();
    	bonus.getName().set(name);
    	bonus.getPicture().set(id+100+rnd.nextInt(6)+1);
    	bonus.getLevel().set(id+1);
    	bonus.getLife().set(id*3);
    	bonus.getStrength().set(id*5);
    	bonus.getSpeed().set(id*2);
        return bonus;
    }

    private Character buildFakeCharacter (String name, int id) {
        Character character = new Character();
        character.getName().set(name);
        character.getPicture().set(id);
        character.getLevel().set(id+1);
        character.getLife().set(id*2);
        character.getStrength().set(id*3);
        character.getSpeed().set(id*5);
        return character;
    }

    static String NAMES [] = new String [] { "ensisa", "ensitm", "iut", "fst", "flsh", "fsesj", "uha" };
    private Character buildFakeCharacter (int id) {
    	if (id < 0) id = 0;
    	if (id >= NAMES.length) id = 0;
    	String name = NAMES[id];
    	return buildFakeCharacter (name, id+1);
    }

    @Override
    synchronized public boolean close() {
        return true;
    }

    @Override
    synchronized public boolean open() {
        return true;
    }

    @Override
    public Boolean populateCharacters() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean createCharacter(String name) {
        return Boolean.TRUE;
    }

    private int counter=0;
    @Override
    public Character getCharacter(String name) {
        Character character = buildFakeCharacter (name, ++counter);
        for (int i=0; i < character.getLevel().get()-1; ++i) {
        	character.addBonus(buildFakeBonus("zzz", i));
        }
        return character;
    }

    @Override
    public byte[] getPicture(long id) {
        return null;
    }

    @Override
    public Fight doFight(String left, String right, Fight expected) {
        return expected;
    }

    @Override
    public List<Character> getAllCharacters() {
        ObservableList<Character> characters = FXCollections.observableArrayList();
        for (int i=0; i<3; ++i) {
            characters.add(buildFakeCharacter(i));
        }
        return characters;
    }

}
