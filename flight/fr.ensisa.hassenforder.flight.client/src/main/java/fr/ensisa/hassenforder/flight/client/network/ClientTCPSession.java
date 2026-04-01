package fr.ensisa.hassenforder.flight.client.network;

import java.io.IOException;
import java.net.Socket;
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
import fr.ensisa.hassenforder.flight.network.Protocol;

public class ClientTCPSession implements ISession {

	private Socket tcp;
    private String host;
    private int port;

    public ClientTCPSession(String host, int port) {
    	this.host = host;
    	this.port = port;
    }

    @Override
    synchronized public boolean close() {
        try {
            if (tcp != null) {
                tcp.close();
            }
            tcp = null;
        } catch (IOException e) {
        }
        return true;
    }

    @Override
    synchronized public boolean open() {
        this.close();
        try {
            tcp = new Socket(this.host, this.port);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

	@Override
	public Connected connect(String mail, String passwd) {
        try {
        	ClientTCPWriter w = new ClientTCPWriter(tcp.getOutputStream());
//
          ClientTCPReader r = new ClientTCPReader(tcp.getInputStream());
//
      		return null;
        } catch (IOException e) {
      		return null;
        }
	}

	@Override
	public Boolean disconnect(Authenticator authenticator) {
        try {
        	ClientTCPWriter w = new ClientTCPWriter(tcp.getOutputStream());
//
          ClientTCPReader r = new ClientTCPReader(tcp.getInputStream());
//
      		return null;
        } catch (IOException e) {
      		return null;
        }
	}

	@Override
	public Account getAccount(Authenticator authenticator) {
        try {
        	ClientTCPWriter w = new ClientTCPWriter(tcp.getOutputStream());
//
          ClientTCPReader r = new ClientTCPReader(tcp.getInputStream());
//
      		return null;
        } catch (IOException e) {
    		  return null;
        }
	}

	@Override
	public Boolean createAccount(Account account) {
        try {
        	ClientTCPWriter w = new ClientTCPWriter(tcp.getOutputStream());
//
          ClientTCPReader r = new ClientTCPReader(tcp.getInputStream());
//
      		return null;
        } catch (IOException e) {
      		return null;
        }
	}

	@Override
	public Boolean updateAccount(Authenticator authenticator, Account account) {
        try {
        	ClientTCPWriter w = new ClientTCPWriter(tcp.getOutputStream());
//
          ClientTCPReader r = new ClientTCPReader(tcp.getInputStream());
//
      		return null;
          } catch (IOException e) {
    		return null;
        }
	}
	
	@Override
	public Collection<Travel> getAllTravels() {
        try {
        	ClientTCPWriter w = new ClientTCPWriter(tcp.getOutputStream());
//
          ClientTCPReader r = new ClientTCPReader(tcp.getInputStream());
//
      		return null;
        } catch (IOException e) {
      		return null;
        }
	}

	@Override
	public Collection<Travel> getAllTravels(String from, String to, LocalDate localDate) {
        try {
        	ClientTCPWriter w = new ClientTCPWriter(tcp.getOutputStream());
//
          ClientTCPReader r = new ClientTCPReader(tcp.getInputStream());
//
      		return null;
        } catch (IOException e) {
      		return null;
        }
	}

	@Override
	public Long bookTravel(Authenticator authenticator, long travelId, List<SeatCount> seatCounts) {
        try {
        	ClientTCPWriter w = new ClientTCPWriter(tcp.getOutputStream());
//
          ClientTCPReader r = new ClientTCPReader(tcp.getInputStream());
//
      		return null;
        } catch (IOException e) {
      		return null;
        }
	}

	@Override
	public Ticket getTicket(Authenticator authenticator, long ticketId) {
        try {
        	ClientTCPWriter w = new ClientTCPWriter(tcp.getOutputStream());
//
          ClientTCPReader r = new ClientTCPReader(tcp.getInputStream());
//
      		return null;
        } catch (IOException e) {
    	  	return null;
        }
	}

	@Override
	public PaymentResult payTicket(Authenticator authenticator, long ticketId) {
        try {
        	ClientTCPWriter w = new ClientTCPWriter(tcp.getOutputStream());
//
          ClientTCPReader r = new ClientTCPReader(tcp.getInputStream());
//
      		return null;
        } catch (IOException e) {
    		  return null;
        }
	}

	@Override
	public Collection<Ticket> getAllTickets(Authenticator authenticator) {
        try {
        	ClientTCPWriter w = new ClientTCPWriter(tcp.getOutputStream());
//
          ClientTCPReader r = new ClientTCPReader(tcp.getInputStream());
//
      		return null;
        } catch (IOException e) {
      		return null;
        }
	}

	@Override
	public PaymentResult cancelTicket(Authenticator authenticator, long ticketId) {
        try {
        	ClientTCPWriter w = new ClientTCPWriter(tcp.getOutputStream());
//
          ClientTCPReader r = new ClientTCPReader(tcp.getInputStream());
//
      		return null;
        } catch (IOException e) {
      		return null;
        }
	}

	@Override
	public byte[] getImage(String name) {
        try {
        	ClientTCPWriter w = new ClientTCPWriter(tcp.getOutputStream());
//
          ClientTCPReader r = new ClientTCPReader(tcp.getInputStream());
//
      		return null;
        } catch (IOException e) {
      		return null;
        }
	}

}
