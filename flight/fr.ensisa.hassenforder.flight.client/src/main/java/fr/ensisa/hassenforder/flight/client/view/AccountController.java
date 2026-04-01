package fr.ensisa.hassenforder.flight.client.view;

import java.net.URL;
import java.util.ResourceBundle;

import fr.ensisa.hassenforder.flight.client.model.Account;
import fr.ensisa.hassenforder.flight.client.model.Authenticator;
import fr.ensisa.hassenforder.flight.client.network.ISession;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class AccountController implements Initializable {

	private ISession session;

	private Authenticator authenticator;
	private Account account = new Account();
	
	@FXML
	private Label statusLabel;
	private StringProperty status = new SimpleStringProperty("ready");
	
	@FXML
	private TextField nameField;
	@FXML
	private TextField mailField;
	@FXML
	private TextField passwdField;
	@FXML
	private TextField cashField;
	@FXML
	private Label stateLabel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	nameField.textProperty().bindBidirectional(account.getName());
    	mailField.textProperty().bindBidirectional(account.getMail());
    	passwdField.textProperty().bindBidirectional(account.getPasswd());
    	cashField.textProperty().bindBidirectional(account.getCash(), new NumberStringConverter());

    	stateLabel.textProperty().bind(account.getState());
    	
    	statusLabel.textProperty().bind(status);
	}

	private void fail () {
		status.set("Last request failed dramatically");
    }

	private void onLoad () {
		Account result = session.getAccount(authenticator);
		if (result != null) {
			account.setWith(result);
			status.set("Account fetched");
		} else fail();
	}
	
	private void onUpdate() {
		Boolean result = session.updateAccount(authenticator, account);
		if (result == null) fail();
		else if (result) status.set("Successfully updated account "+account.getName().get());
		else status.set("Can't update account "+account.getName().get());
	}

	private void onCreate() {
		Boolean result = session.createAccount(account);
		if (result == null) fail();
		else if (result) status.set("Successfully create account "+account.getName().get());
		else status.set("Can't create account "+account.getName().get());
	}

	@FXML
    private void onValid() {
    	if (authenticator.getIsAuthenticated().get()) {
    		onUpdate ();
    	} else {
    		onCreate ();
    	}
    }

	@FXML
    private void onCancel() {
		getStage().close();
    }

	private Stage getStage () {
		return (Stage) statusLabel.getScene().getWindow();
	}

	public void setSession(ISession session) {
		this.session = session;
	}

    public void setAuthenticator (Authenticator authenticator) {
    	this.authenticator = authenticator;
    	if (authenticator.getIsAuthenticated().get()) {
    		getStage().setTitle("Update an Account");
    		nameField.setDisable(true);
    		onLoad();
    	} else {
    		getStage().setTitle("Create an Account");
    	}
    }

}
