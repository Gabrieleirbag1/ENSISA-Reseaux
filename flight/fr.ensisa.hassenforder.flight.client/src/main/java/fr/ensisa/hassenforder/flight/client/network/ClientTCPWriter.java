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

    public void createAccount(Account account) {
        writeInt(Protocol.REQUEST_CREATE_ACCOUNT);
        writeString(account.getName().get());
        writeString(account.getMail().get());
        writeString(account.getPasswd().get());
        writeInt(account.getCash().get());
    }

    public void createConnect(String mail, String passwd) {
        writeInt(Protocol.REQUEST_CONNECT);
        writeString(mail);
        writeString(passwd);
    }

    public void createDisconnect(Authenticator authenticator) {
        writeInt(Protocol.REQUEST_DISCONNECT);
        writeLong(authenticator.getId());
        writeString(authenticator.getToken());
    }

    public void createGetAccount(Authenticator authenticator) {
        writeInt(Protocol.REQUEST_GET_ACCOUNT);
        writeLong(authenticator.getId());
        writeString(authenticator.getToken());
    }

    public void createUpdateAccount(Authenticator authenticator, Account account) {
        writeInt(Protocol.REQUEST_UPDATE_ACCOUNT);
        writeLong(authenticator.getId());
        writeString(authenticator.getToken());
        writeString(account.getName().get());
        writeString(account.getMail().get());
        writeString(account.getPasswd().get());
        writeInt(account.getCash().get());
    }

    public void createGetAllTravels() {
        writeInt(Protocol.REQUEST_GET_ALL_TRAVELS);
    }

    public void createGetFilteredTravels(String from, String to, LocalDate localDate) {
        writeInt(Protocol.REQUEST_GET_FILTERED_TRAVELS);
        int optional = 0;
        if (from != null && !from.isEmpty()) optional |= Protocol.WITH_FROM;
        if (to != null && !to.isEmpty()) optional |= Protocol.WITH_TO;
        if (localDate != null) optional |= Protocol.WITH_DAY;
        writeInt(optional);
        if (from != null && !from.isEmpty()) writeString(from);
        if (to != null && !to.isEmpty()) writeString(to);
        if (localDate != null) {
            writeLong(DateUtils.asDate(localDate).getTime());
        }
    }

    private void writeSeatCount(SeatCount seatCount) {
        writeString(seatCount.getComfort().name());
        writeInt(seatCount.getCount());
    }

    public void createBookTravel(Authenticator authenticator, long travelId, List<SeatCount> seatCounts) {
        writeInt(Protocol.REQUEST_BOOK_TRAVEL);
        writeLong(authenticator.getId());
        writeString(authenticator.getToken());
        writeLong(travelId);
        writeInt(seatCounts.size());
        for (SeatCount sc : seatCounts) {
            writeSeatCount(sc);
        }
    }

    public void createGetTicket(Authenticator authenticator, long ticketId) {
        writeInt(Protocol.REQUEST_GET_TICKET);
        writeLong(authenticator.getId());
        writeString(authenticator.getToken());
        writeLong(ticketId);
    }

    public void createPayTicket(Authenticator authenticator, long ticketId) {
        writeInt(Protocol.REQUEST_PAY_TICKET);
        writeLong(authenticator.getId());
        writeString(authenticator.getToken());
        writeLong(ticketId);
    }

    public void createGetAllTickets(Authenticator authenticator) {
        writeInt(Protocol.REQUEST_GET_ALL_TICKETS);
        writeLong(authenticator.getId());
        writeString(authenticator.getToken());
    }

    public void createCancelTicket(Authenticator authenticator, long ticketId) {
        writeInt(Protocol.REQUEST_CANCEL_TICKET);
        writeLong(authenticator.getId());
        writeString(authenticator.getToken());
        writeLong(ticketId);
    }

    public void createGetImage(String name) {
        writeInt(Protocol.REQUEST_GET_IMAGE);
        writeString(name);
    }

}
