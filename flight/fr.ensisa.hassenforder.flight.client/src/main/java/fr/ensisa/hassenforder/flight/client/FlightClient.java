package fr.ensisa.hassenforder.flight.client;

import java.net.ConnectException;

import fr.ensisa.hassenforder.flight.client.images.ImagesCache;
import fr.ensisa.hassenforder.flight.client.network.ClientTCPSession;
import fr.ensisa.hassenforder.flight.client.network.ISession;
import fr.ensisa.hassenforder.flight.client.network.fake.Fake;
import fr.ensisa.hassenforder.flight.client.view.FlightController;
import fr.ensisa.hassenforder.flight.network.Protocol;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FlightClient extends Application {

	private enum SessionMode {
		FAKE,
		NETWORK,
		;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
	        ISession session = getSession(SessionMode.NETWORK);
	        ImagesCache cache = new ImagesCache(session);
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/Flight.fxml"));
	        Parent root = (Parent) fxmlLoader.load();
	        primaryStage.setScene(new Scene(root));
	        primaryStage.setTitle("Flight Booking");
	        FlightController controller = fxmlLoader.getController();
            controller.setSession(session);
            controller.setImagesCache(cache);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private ISession getSession(SessionMode mode) throws ConnectException {
		ISession session = null;
		switch (mode) {
		case FAKE: 		session = new Fake(); break;
		case NETWORK:	session = new ClientTCPSession("localhost", Protocol.TRAVEL_TCP_PORT);
		}
		if (session == null) throw new ConnectException("Session cannot be created");
		session.open();
		return session;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
