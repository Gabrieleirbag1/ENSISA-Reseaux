package fr.ensisa.hassenforder.brutes.client.network;

import java.util.List;

import fr.ensisa.hassenforder.brutes.client.model.Character;
import fr.ensisa.hassenforder.brutes.client.model.Fight;

/**
 *
 * @author hassenforder
 */
public interface ISession {

    boolean open ();
    boolean close ();

    Boolean createCharacter (String name);
    Character getCharacter (String name);
    byte [] getPicture (long id);
    Fight doFight (String left, String right, Fight expected);

    Boolean populateCharacters ();
    List<Character> getAllCharacters ();
}
