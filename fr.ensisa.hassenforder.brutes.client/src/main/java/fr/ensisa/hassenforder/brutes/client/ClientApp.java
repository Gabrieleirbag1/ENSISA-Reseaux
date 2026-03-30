package fr.ensisa.hassenforder.brutes.client;

import java.io.IOException;

import fr.ensisa.hassenforder.brutes.client.view.BrutesController;
import fr.ensisa.hassenforder.brutes.client.network.ClientSession;
import fr.ensisa.hassenforder.brutes.client.network.FakeSession;
import fr.ensisa.hassenforder.brutes.client.network.ISession;
import fr.ensisa.hassenforder.brutes.network.Protocol;
import fr.ensisa.hassenforder.io.FileHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApp extends Application {

	private ISession session;

	private Scene initRootLayout() {
       try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClientApp.class.getResource("view/BrutesClient.fxml"));
            Parent rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            BrutesController controller = loader.getController();
            controller.setSession(getSession());
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}

    public ISession getSession() {
		if (session == null) {
			session = new ClientSession("localhost", Protocol.BRUTES_TCP_PORT);
//			session = new FakeSession("localhost", Protocol.BRUTES_TCP_PORT);
			session.open();
		}
		return session;
	}

	@Override
	public void start(Stage primaryStage) {
		Scene scene = initRootLayout();
        primaryStage.setScene(scene);
		primaryStage.setTitle("Brutes - Client");
        primaryStage.show();
	}

    public static void main(String[] args) {
		FileHelper.guessSrcDir("fr.ensisa.hassenforder.brutes.client");
		launch(args);
	}

}
