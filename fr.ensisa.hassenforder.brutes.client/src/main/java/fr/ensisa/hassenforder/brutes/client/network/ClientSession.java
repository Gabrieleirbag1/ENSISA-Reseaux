package fr.ensisa.hassenforder.brutes.client.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import fr.ensisa.hassenforder.brutes.network.Protocol;
import fr.ensisa.hassenforder.brutes.client.model.Character;
import fr.ensisa.hassenforder.brutes.client.model.Fight;

public class ClientSession implements ISession {

    private Socket tcp;
    private String host;
    private int port;

    public ClientSession(String host, int port) {
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
	public Boolean populateCharacters() {
        try {
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
//
            ClientReader r = new ClientReader(tcp.getInputStream());
//
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	@Override
	public Boolean createCharacter(String name) {
        try {
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
//
            ClientReader r = new ClientReader(tcp.getInputStream());
//
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	@Override
	public Character getCharacter(String name) {
        try {
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
//
            ClientReader r = new ClientReader(tcp.getInputStream());
//
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	@Override
	public byte[] getPicture(long id) {
        try {
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
//
            ClientReader r = new ClientReader(tcp.getInputStream());
//
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	@Override
	public Fight doFight(String left, String right, Fight expected) {
        try {
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
//
            ClientReader r = new ClientReader(tcp.getInputStream());
//
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

	@Override
	public List<Character> getAllCharacters() {
        try {
        	ClientWriter w = new ClientWriter(tcp.getOutputStream());
//
            ClientReader r = new ClientReader(tcp.getInputStream());
//
    		return null;
        } catch (IOException e) {
    		return null;
        }
	}

}
