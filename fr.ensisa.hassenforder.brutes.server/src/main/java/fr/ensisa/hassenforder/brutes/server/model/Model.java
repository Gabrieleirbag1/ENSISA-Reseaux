package fr.ensisa.hassenforder.brutes.server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Model {

    private Map <String, Character> characters;
    private Map <String, Bonus> bonus;
    private Random r;

    public Model () {
        initialize();
    }

    private int random (int low, int high) {
        if (r == null) r = new Random ();
        double v = r.nextDouble();
        double w = (high - low) * v + low;
        return (int) w;
    }

    private Map<String, Character> getCharacters() {
        if (characters == null) {
            characters = new TreeMap<String, Character> ();
        }
        return characters;
    }

    private Map<String, Bonus> getBonus() {
        if (bonus == null) {
            bonus = new TreeMap<String, Bonus> ();
        }
        return bonus;
    }

    private void initialize () {
        add (new Bonus ("knife_1", 101, 1, 1, 1, 1));
        add (new Bonus ("axe_1",   102, 1, 1, 1, 1));
        add (new Bonus ("dog_1",   103, 1, 1, 1, 1));
        add (new Bonus ("wolf_1",  104, 1, 1, 1, 1));
        add (new Bonus ("bear_1",  105, 1, 1, 1, 1));
        add (new Bonus ("spear_1", 106, 1, 1, 1, 1));

        add (new Bonus ("knife_2", 111, 2, 1, 1, 2));
        add (new Bonus ("axe_2",   112, 2, 1, 2, 1));
        add (new Bonus ("dog_2",   113, 2, 2, 1, 1));
        add (new Bonus ("wolf_2",  114, 2, 1, 1, 2));
        add (new Bonus ("bear_2",  115, 2, 1, 2, 1));
        add (new Bonus ("spear_2", 116, 2, 2, 1, 1));

        add (new Bonus ("knife_3", 121, 3, 2, 1, 1));
        add (new Bonus ("axe_3",   122, 3, 1, 2, 1));
        add (new Bonus ("dog_3",   123, 3, 1, 1, 2));
        add (new Bonus ("wolf_3",  124, 3, 2, 1, 1));
        add (new Bonus ("bear_3",  125, 3, 1, 2, 1));
        add (new Bonus ("spear_3", 126, 3, 1, 1, 2));

        add (new Bonus ("knife_4", 101, 4, 2, 2, 1));
        add (new Bonus ("axe_4",   102, 4, 2, 1, 2));
        add (new Bonus ("dog_4",   103, 4, 1, 2, 2));
        add (new Bonus ("wolf_4",  104, 4, 2, 2, 1));
        add (new Bonus ("bear_4",  105, 4, 2, 1, 2));
        add (new Bonus ("spear_4", 106, 4, 1, 2, 2));

        add (new Bonus ("knife_5", 111, 5, 2, 2, 1));
        add (new Bonus ("axe_5",   112, 5, 2, 1, 2));
        add (new Bonus ("dog_5",   113, 5, 1, 2, 2));
        add (new Bonus ("wolf_5",  114, 5, 2, 2, 1));
        add (new Bonus ("bear_5",  115, 5, 2, 1, 2));
        add (new Bonus ("spear_5", 116, 5, 1, 2, 2));

        add (new Bonus ("knife_6", 121, 6, 2, 2, 1));
        add (new Bonus ("axe_6",   122, 6, 2, 1, 2));
        add (new Bonus ("dog_6",   123, 6, 1, 2, 2));
        add (new Bonus ("wolf_6",  124, 6, 2, 2, 1));
        add (new Bonus ("bear_6",  125, 6, 2, 1, 2));
        add (new Bonus ("spear_6", 126, 6, 1, 2, 2));

        add (createCharacter ("admin"));
    }

    public Character createCharacter(String name) {
        Character newCharacter = new Character ();
        newCharacter.setName  (name);
        newCharacter.setLevel (1);
        newCharacter.setLife (1);
        newCharacter.setStrength (random (2, 6));
        newCharacter.setSpeed (random (2, 6));
        Bonus bonus = getRandomBonus(newCharacter.getLevel());
        newCharacter.addBonus (bonus);
        newCharacter.setPicture (random (0, 12));
        return newCharacter;
    }

    public void populate () {
        add (createCharacter ("ensisa"));
        add (createCharacter ("iut"));
        add (createCharacter ("enscmu"));
    }

    public boolean add(Character c) {
    	if (existCharacter(c.getName())) return false;
        getCharacters().put(c.getName(), c);
        return true;
    }

    public void add(Bonus b) {
        getBonus().put(b.getName(), b);
    }

    public boolean existCharacter(String name) {
        return getCharacters().containsKey(name);
    }

    public Character getCharacter(String name) {
        return getCharacters().get(name);
    }
/* 
    public Bonus getBonus(long id) {
        return getBonus().get(id);
    }
*/
    private Bonus getRandomBonus(int level) {
        List<Bonus> forLevel = new ArrayList<Bonus> ();
        for (Bonus b : getBonus().values()) {
            if (b.getLevel() == level)
                forLevel.add(b);
        }
        switch (forLevel.size()) {
    	case 0 :
    		return null;
    	case 1 :
    		return forLevel.get(0);
    	default :
	        int index = random (0, forLevel.size());
	        return forLevel.get(index);
        }
    }

    private void enhance(Character character) {
        if (character.getLife() >= character.getLevel()*5) {
            character.setLevel(character.getLevel()+1);
            character.setLife(character.getLife()+1);
            character.setStrength ( character.getStrength() + random (0, 2));
            character.setSpeed ( character.getSpeed() + random (0, 2));
            Bonus bonus = getRandomBonus(character.getLevel());
            character.addBonus (bonus);
        }
    }

    public void win (String first, String second) {
        Character character = getCharacter (first);
        if (character == null) return;
        character.setLife(character.getLife()+2);
        enhance (character);
    }

    public void lose (String first, String second) {
        Character character = getCharacter (first);
        if (character == null) return;
        character.setLife(character.getLife()+1);
        enhance (character);
    }

    public Collection<Character> getAllCharacters() {
        return getCharacters().values();
    }

    public static String characterPictureName(long picture) {
        StringBuilder name = new StringBuilder();
        name.append("images");
        name.append("/");
        name.append("characters");
        name.append("/");
        name.append(picture);
        name.append(".");
        name.append("png");
        return name.toString();
    }

    public static String bonusPictureName(long picture) {
        StringBuilder name = new StringBuilder();
        name.append("images");
        name.append("/");
        name.append("bonus");
        name.append("/");
        name.append(picture);
        name.append(".");
        name.append("png");
        return name.toString();
    }

}
