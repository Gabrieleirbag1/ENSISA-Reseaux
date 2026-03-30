package fr.ensisa.hassenforder.brutes.client.view;

import java.util.List;

import fr.ensisa.hassenforder.brutes.client.model.Bonus;
import fr.ensisa.hassenforder.brutes.client.model.Character;
import fr.ensisa.hassenforder.brutes.client.model.Fight;
import fr.ensisa.hassenforder.brutes.client.model.ImagePool;
import fr.ensisa.hassenforder.brutes.client.network.ISession;
import fr.ensisa.hassenforder.io.FileHelper;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class BrutesController {

	private Character left = new Character();
	private Character right = new Character();
	private ObservableList<Character> characters = FXCollections.observableArrayList();
	private ISession session;

	private StringProperty status;
	private StringProperty name;

	@FXML
    private Parent pane;
	@FXML
	private Label statusLabel;
	@FXML
	private TextField nameField;
    @FXML
    private TableView<Character> characterTable;
    @FXML
    private TableColumn<Character, String> tableNameColumn;
    @FXML
    private TableColumn<Character, Integer>  tableLevelColumn;
    @FXML
    private TableColumn<Character, Integer>  tableLifeColumn;
    @FXML
    private TableColumn<Character, Integer>  tableSpeedColumn;
    @FXML
    private TableColumn<Character, Integer>  tableStrengthColumn;
    @FXML
    private TableColumn<Character, Long>  tablePictureColumn;

    @FXML
    private TableView<Bonus> leftBonus;
    @FXML
    private TableColumn<Bonus, Bonus> tableLeftPictureColumn;
    @FXML
    private TableView<Bonus> rightBonus;
    @FXML
    private TableColumn<Bonus, Bonus> tableRightPictureColumn;

    @FXML
	private Label leftName;
    @FXML
	private Label leftLevel;
    @FXML
	private Label leftLife;
    @FXML
	private Label leftStrength;
    @FXML
	private Label leftSpeed;
    @FXML
	private ImageView leftPicture;

    @FXML
	private Label rightName;
    @FXML
	private Label rightLevel;
    @FXML
	private Label rightLife;
    @FXML
	private Label rightStrength;
    @FXML
	private Label rightSpeed;
    @FXML
	private ImageView rightPicture;

    private void updateStatus (String text) {
    	status.set(text);
    }

    @FXML
    private void initialize() {
    	status = new SimpleStringProperty();
    	statusLabel.textProperty().bind(status);

    	name = new SimpleStringProperty();
    	nameField.textProperty().bindBidirectional(name);

    	leftName.textProperty().bind(left.getName());
    	leftLevel.textProperty().bind(left.getLevel().asString());
    	leftLife.textProperty().bind(left.getFullLife().asString());
    	leftSpeed.textProperty().bind(left.getFullSpeed().asString());
    	leftStrength.textProperty().bind(left.getFullStrength().asString());
    	leftPicture.imageProperty().bind(
    			Bindings.createObjectBinding(
    					() -> ImagePool.getINSTANCE().getCharacterImage(left.getPicture().get()),
    					left.getPicture()
				)
		);
    	leftBonus.setFixedCellSize(70.0);
    	leftBonus.setItems(left.getBonus());
    	tableLeftPictureColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<Bonus>(cellData.getValue()));
    	tableLeftPictureColumn.setCellFactory(cellStructure -> new ImageTableCell<Bonus, Bonus> ());

    	rightName.textProperty().bind(right.getName());
    	rightLevel.textProperty().bind(right.getLevel().asString());
    	rightLife.textProperty().bind(right.getFullLife().asString());
    	rightSpeed.textProperty().bind(right.getFullSpeed().asString());
    	rightStrength.textProperty().bind(right.getFullStrength().asString());
    	rightPicture.imageProperty().bind(
    			Bindings.createObjectBinding(
    					() -> ImagePool.getINSTANCE().getCharacterImage(right.getPicture().get()),
    					right.getPicture()
				)
		);

    	rightBonus.setFixedCellSize(70.0);
    	rightBonus.setItems(right.getBonus());
    	tableRightPictureColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<Bonus>(cellData.getValue()));
    	tableRightPictureColumn.setCellFactory(cellStructure -> new ImageTableCell<Bonus, Bonus> ());

    	characterTable.setItems(characters);
    	characterTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> characterSelected (newValue)
    		);
    	tableNameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
    	tableLevelColumn.setCellValueFactory(cellData -> cellData.getValue().getLevel().asObject());
    	tableLifeColumn.setCellValueFactory(cellData -> cellData.getValue().getLife().asObject());
    	tableSpeedColumn.setCellValueFactory(cellData -> cellData.getValue().getSpeed().asObject());
    	tableStrengthColumn.setCellValueFactory(cellData -> cellData.getValue().getStrength().asObject());
    	tablePictureColumn.setCellValueFactory(cellData -> cellData.getValue().getPicture().asObject());
    	tablePictureColumn.setCellFactory(cellStructure -> new ImageTableCell<Character, Long> ());

    	status.set("Ready");
	}

    private void characterSelected(Character newValue) {
		if (newValue != null) {
			name.set(newValue.getName().get());
		}
    }

    @FXML
    private void onCreate() {
    	updateStatus("Create character "+name);
    	Boolean result = session.createCharacter(name.get());
    	if (result != null) {
        	if (result == Boolean.TRUE) {
        		updateStatus("Creation successfull");
        	} else {
        		updateStatus("Creation failed");
        	}
    	} else {
    		updateStatus("Creation wrong");
    	}
    }

    private void updateCharacter (Character from, Character to) {
    	if (to == null) return;
    	if (from == null) {
    		to.getName().set("");
    		to.getLevel().set(0);
    		to.getLife().set(0);
    		to.getStrength().set(0);
    		to.getSpeed().set(0);
    		to.getPicture().set(0);
    		to.getBonus().clear();
    		to.update();
    	} else {
    		to.getName().set(from.getName().get());
    		to.getLevel().set(from.getLevel().get());
    		to.getLife().set(from.getLife().get());
    		to.getStrength().set(from.getStrength().get());
    		to.getSpeed().set(from.getSpeed().get());
    		to.getPicture().set(from.getPicture().get());
    		to.getBonus().clear();
			to.getBonus().addAll(from.getBonus());
    		to.update();
    	}
    }

    private void loadPictures (Character... characters) {
    	if (characters == null) return;
    	for (Character character : characters) {
    		if (character == null) continue;
	    	long picture = character.getPicture().get();
	    	String pictureName = ImagePool.pictureName(picture);
	    	if (! FileHelper.fileExists(pictureName)) {
	        	FileHelper.writeContent(pictureName, session.getPicture(picture));
	    	}
	    	for (Bonus b : character.getBonus()) {
	        	long picture2 = b.getPicture().get();
	        	String picture2Name = ImagePool.pictureName(picture2);
	        	if (! FileHelper.fileExists(picture2Name)) {
	            	FileHelper.writeContent(picture2Name, session.getPicture(picture2));
	        	}
	    	}
    	}
    }

    @FXML
    private void onLeft() {
    	updateStatus("Fetching left character "+name);
    	Character character = session.getCharacter(name.get());
    	loadPictures(character);
		updateCharacter(character, left);
    	if (character != null) {
        	updateStatus("Left character updated");
    	} else {
    		updateStatus("Left character is null");
    	}
    }

    @FXML
    private void onRight() {
    	updateStatus("Fetching right character "+name);
    	Character character = session.getCharacter(name.get());
    	loadPictures(character);
		updateCharacter(character, right);
    	if (character != null) {
        	updateStatus("Right character updated");
    	} else {
    		updateStatus("Right character is null");
    	}
    }

    @FXML
    private void onWin() {
    	updateStatus("Start a wining fight");
    	Fight fight = session.doFight(left.getName().get(), right.getName().get(), Fight.WIN);
    	if (fight != null) {
    		if (fight == Fight.WIN) {
    			updateStatus("Left character wins");
	    	} else {
    			updateStatus("Left character lost");
	    	}
        	Character cl = session.getCharacter(left.getName().get());
        	loadPictures(cl);
    		updateCharacter(cl, left);
        	Character cr = session.getCharacter(right.getName().get());
        	loadPictures(cr);
    		updateCharacter(cr, right);
    	} else {
    		updateStatus("Fight failed");
    	}
    }

    @FXML
    private void onLose() {
    	updateStatus("Start a losing fight");
    	Fight fight = session.doFight(left.getName().get(), right.getName().get(), Fight.LOSE);
    	if (fight != null) {
    		if (fight == Fight.WIN) {
    			updateStatus("Left character wins");
	    	} else {
    			updateStatus("Left character lost");
	    	}
        	Character cl = session.getCharacter(left.getName().get());
        	loadPictures(cl);
    		updateCharacter(cl, left);
        	Character cr = session.getCharacter(right.getName().get());
        	loadPictures(cr);
    		updateCharacter(cr, right);
    	} else {
    		updateStatus("Fight failed");
    	}
    }

    @FXML
    private void onList() {
    	updateStatus("Start a list of characters");
    	List<Character> all = session.getAllCharacters();
    	if (all != null) {
        	loadPictures(all.toArray(new Character[0]));
    		characters.clear();
    		characters.addAll(all);
        	updateStatus("End of a list of characters");
    	} else {
    		updateStatus("Get list failed");
    	}
    }

    @FXML
    private void onPopulate() {
    	updateStatus("Populates");
    	Boolean result = session.populateCharacters();
    	if (result != null) {
        	if (result == Boolean.TRUE) {
        		updateStatus("Populates successfull");
        	} else {
        		updateStatus("Populates failed");
        	}
    	} else {
    		updateStatus("Populates wrong");
    	}
    }

	public void setSession(ISession session) {
		this.session = session;
	}

}
