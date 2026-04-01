package fr.ensisa.hassenforder.flight.client.network;

import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import fr.ensisa.hassenforder.flight.client.model.Account;
import fr.ensisa.hassenforder.flight.client.model.Authenticator;
import fr.ensisa.hassenforder.flight.client.model.SeatCount;
import fr.ensisa.hassenforder.flight.network.Protocol;
import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.DateUtils;

public class ClientTCPWriter extends BasicAbstractWriter {

    public ClientTCPWriter(OutputStream outputStream) {
        super(outputStream);
    }

    private void writeAuthenticator (Authenticator authenticator) {
		writeLong(authenticator.getId());
		writeString(authenticator.getToken());
    }
    
	public void createConnect(String mail, String passwd) {
		writeInt(Protocol.REQUEST_CONNECT);
		writeString(mail);
		writeString(passwd);
	}

	public void createDisconnect(Authenticator authenticator) {
		writeInt(Protocol.REQUEST_DISCONNECT);
		writeAuthenticator(authenticator);
	}

	public void createGetAccount(Authenticator authenticator) {
		writeInt(Protocol.REQUEST_GET_ACCOUNT);
		writeAuthenticator(authenticator);
	}

	public void createCreateAccount(Account account) {
		writeInt(Protocol.REQUEST_CREATE_ACCOUNT);
		writeString(account.getName().get());
		writeString(account.getMail().get());
		writeString(account.getPasswd().get());
		writeInt(account.getCash().get());
	}

	public void createUpdateAccount(Authenticator authenticator, Account account) {
		writeInt(Protocol.REQUEST_UPDATE_ACCOUNT);
		writeAuthenticator(authenticator);
		writeString(account.getName().get());
		writeString(account.getMail().get());
		writeString(account.getPasswd().get());
		writeInt(account.getCash().get());
	}

	public void createGetAllTravels() {
		writeInt(Protocol.REQUEST_GET_ALL_TRAVELS);
	}

	public void createGetFilteredTravels(String from, String to, LocalDate day) {
		writeInt(Protocol.REQUEST_GET_FILTERED_TRAVELS);
		int optional = 0;
		if (from != null) optional |= Protocol.WITH_FROM;
		if (to != null) optional |= Protocol.WITH_TO;
		if (day != null) optional |= Protocol.WITH_DAY;
		writeInt(optional);
		if ((optional & Protocol.WITH_FROM) != 0) writeString(from);
		if ((optional & Protocol.WITH_TO) != 0) writeString(to);
		if ((optional & Protocol.WITH_DAY) != 0) writeLong(DateUtils.asDate(day).getTime());
	}

	private void writeSeatCount(SeatCount count) {
		writeString(count.getComfort().name());
		writeInt(count.getCount());

	}

	public void createBookTravel(Authenticator authenticator, long travelId, List<SeatCount> seatCounts) {
		writeInt(Protocol.REQUEST_BOOK_TRAVEL);
		writeAuthenticator(authenticator);
		writeLong(travelId);
		writeInt(seatCounts.size());
		for (SeatCount count : seatCounts) {
			writeSeatCount(count);
		}
	}

	public void createGetTicket(Authenticator authenticator, long ticketId) {
		writeInt(Protocol.REQUEST_GET_TICKET);
		writeAuthenticator(authenticator);
		writeLong(ticketId);
	}

	public void createPayTicket(Authenticator authenticator, long ticketId) {
		writeInt(Protocol.REQUEST_PAY_TICKET);
		writeAuthenticator(authenticator);
		writeLong(ticketId);
	}

	public void createGetAllTickets(Authenticator authenticator) {
		writeInt(Protocol.REQUEST_GET_ALL_TICKETS);
		writeAuthenticator(authenticator);
	}

	public void createCancelTicket(Authenticator authenticator, long ticketId) {
		writeInt(Protocol.REQUEST_CANCEL_TICKET);
		writeAuthenticator(authenticator);
		writeLong(ticketId);
	}

	public void createGetImage(String name) {
		writeInt(Protocol.REQUEST_GET_IMAGE);
		writeString(name);
	}

}
