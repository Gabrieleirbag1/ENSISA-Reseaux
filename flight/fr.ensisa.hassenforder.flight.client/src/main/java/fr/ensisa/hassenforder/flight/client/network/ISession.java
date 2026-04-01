package fr.ensisa.hassenforder.flight.client.network;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import fr.ensisa.hassenforder.flight.client.model.Account;
import fr.ensisa.hassenforder.flight.client.model.Authenticator;
import fr.ensisa.hassenforder.flight.client.model.Connected;
import fr.ensisa.hassenforder.flight.client.model.PaymentResult;
import fr.ensisa.hassenforder.flight.client.model.SeatCount;
import fr.ensisa.hassenforder.flight.client.model.Ticket;
import fr.ensisa.hassenforder.flight.client.model.Travel;

/**
 *
 * @author hassenforder
 */
public interface ISession {

    boolean open ();
    boolean close ();

    Connected connect(String mail, String passwd);
    Boolean disconnect(Authenticator authenticator);
    
	Boolean createAccount(Account account);
	Account getAccount(Authenticator authenticator);
	Boolean updateAccount(Authenticator authenticator, Account account);

	Collection<Travel> getAllTravels();
	Collection<Travel> getAllTravels(String from, String to, LocalDate localDate);
	Long bookTravel(Authenticator authenticator, long travelId, List<SeatCount> counts);

	Ticket getTicket(Authenticator authenticator, long ticketId);
	PaymentResult payTicket(Authenticator authenticator, long ticketId);
	
	Collection<Ticket> getAllTickets(Authenticator authenticator);
	PaymentResult cancelTicket(Authenticator authenticator, long ticketId);

	byte[] getImage(String name);
}
