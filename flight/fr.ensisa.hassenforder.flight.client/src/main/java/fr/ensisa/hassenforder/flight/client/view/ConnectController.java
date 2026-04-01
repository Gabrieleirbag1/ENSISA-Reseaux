package fr.ensisa.hassenforder.flight.client.view;

import fr.ensisa.hassenforder.flight.client.model.Authenticator;
import fr.ensisa.hassenforder.flight.client.model.Connected;
import fr.ensisa.hassenforder.flight.client.model.User;
import fr.ensisa.hassenforder.flight.client.network.ISession;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ConnectController {

	private ISession session;
	private User user;
	private Authenticator authenticator;

	private StringProperty status;
	private StringProperty mail;
	private StringProperty passwd;

	@FXML
	private Label statusLabel;

	@FXML
	private TextField mailField;
	@FXML
	private PasswordField passwdField;

	public void initialize() {
		mail = new SimpleStringProperty("i@m");
		mailField.textProperty().bindBidirectional(mail);
		mail.addListener(l -> status.set(buildStatus()));

    	passwd = new SimpleStringProperty("iii");
    	passwdField.textProperty().bindBidirectional(passwd);
    	passwd.addListener(l -> status.set(buildStatus()));

    	status = new SimpleStringProperty("");
    	statusLabel.textProperty().bind(status);

    	status.set("Ready");
	}

    private String buildStatus() {
    	if (mail.get().isEmpty()) return "mail is empty";
    	if (passwd.get().isEmpty()) return "passwd is empty";
    	if (! authenticator.getIsAuthenticated().get()) return "unconnected";
    	return "connected";
	}

	@FXML
    private void onConnect() {
		Connected connected = session.connect(mail.get(), passwd.get());
		if (connected != null) {
			this.authenticator.setWith(connected.getAuthenticator());
			this.user.setWith(connected.getUser());
			Window w = statusLabel.getScene().getWindow();
			if (w instanceof Stage) ((Stage) w).close();
		} else {
			this.authenticator.setWith(null);
			this.user.setWith(null);
		}
		status.set(buildStatus());
    }

    public void setSession(ISession session) {
		this.session = session;
	}

	public void setUserAndAuthenticator(User user, Authenticator authenticator) {
		this.user = user;
		this.authenticator = authenticator;
	}

}
