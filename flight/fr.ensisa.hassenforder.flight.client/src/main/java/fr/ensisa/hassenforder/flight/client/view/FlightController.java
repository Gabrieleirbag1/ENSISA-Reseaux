package fr.ensisa.hassenforder.flight.client.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import fr.ensisa.hassenforder.flight.client.images.ImagesCache;
import fr.ensisa.hassenforder.flight.client.model.Authenticator;
import fr.ensisa.hassenforder.flight.client.model.PaymentResult;
import fr.ensisa.hassenforder.flight.client.model.Seat;
import fr.ensisa.hassenforder.flight.client.model.SeatComfort;
import fr.ensisa.hassenforder.flight.client.model.SeatCount;
import fr.ensisa.hassenforder.flight.client.model.SeatState;
import fr.ensisa.hassenforder.flight.client.model.Ticket;
import fr.ensisa.hassenforder.flight.client.model.TicketState;
import fr.ensisa.hassenforder.flight.client.model.Travel;
import fr.ensisa.hassenforder.flight.client.model.User;
import fr.ensisa.hassenforder.flight.client.network.ISession;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FlightController {

	private ImagesCache imagesCache;
	private ISession session;

	private ObservableList<Integer> classesCount = FXCollections.observableArrayList();

	private Authenticator authenticator;
	private User user;
	private ObservableList<Travel> travels = FXCollections.observableArrayList();
	private Ticket ticket;
	private ObservableList<Ticket> tickets = FXCollections.observableArrayList();
	
	private StringProperty from = new SimpleStringProperty();
	private StringProperty to = new SimpleStringProperty();
	private ObjectProperty<LocalDate> day = new SimpleObjectProperty<>();;

	private StringProperty status = new SimpleStringProperty();

	@FXML
	private Label statusLabel;

	@FXML
	private Label nameField;
	@FXML
	private Label stateField;
	@FXML
	private Label cashField;

	@FXML
	private Button userConnectButton;

	@FXML
	private Button userAccountButton;

	@FXML
	private TextField fromField;

	@FXML
	private TextField toField;

	@FXML
	private DatePicker dayField;

	@FXML
	private ChoiceBox<Integer> firstClassField;

	@FXML
	private ChoiceBox<Integer> businessClassField;

	@FXML
	private ChoiceBox<Integer> economicClassField;

    @FXML
    private TabPane mainTabPane;

    @FXML
    private Tab travelTab;

    @FXML
    private Tab reservationTab;

    @FXML
    private Tab ticketsTab;

    @FXML
    private TableView<Travel> travelTable;
    @FXML
    private TableColumn<Travel, String> travelFromColumn;
    @FXML
    private TableColumn<Travel, String>  travelToColumn;
    @FXML
    private TableColumn<Travel, String>  travelDayColumn;
    @FXML
    private TableColumn<Travel, Integer>  travelFreeFirstColumn;
    @FXML
    private TableColumn<Travel, Integer>  travelFreeBusinessColumn;
    @FXML
    private TableColumn<Travel, Integer>  travelFreeEconomicColumn;
    @FXML
    private TableColumn<Travel, Void>  travelBookingColumn;

	@FXML
	private Label planeName;

	@FXML
	private ImageView planePicture;

	@FXML
	private Label ticketFrom;

	@FXML
	private Label ticketTo;

	@FXML
	private Label ticketDay;

    @FXML
    private TableView<Seat> ticketSeatTable;
    @FXML
    private TableColumn<Seat, String> ticketSeatClassColumn;
    @FXML
    private TableColumn<Seat, SeatComfort> ticketSeatComfortColumn;
    @FXML
    private TableColumn<Seat, String>  ticketSeatNameColumn;
    @FXML
    private TableColumn<Seat, Integer>  ticketSeatCostColumn;

	@FXML
	private Label ticketTotalCost;

	@FXML
	private Button ticketPay;

	@FXML
    private TableView<Ticket> ticketsTable;
    @FXML
    private TableColumn<Ticket, String> ticketsFromColumn;
    @FXML
    private TableColumn<Ticket, String>  ticketsToColumn;
    @FXML
    private TableColumn<Ticket, String>  ticketsDayColumn;
    @FXML
    private TableColumn<Ticket, TicketState>  ticketsStateColumn;
    @FXML
    private TableColumn<Ticket, String>  ticketsSeatsColumn;
    @FXML
    private TableColumn<Ticket, Integer>  ticketsCostColumn;
    @FXML
    private TableColumn<Ticket, TicketState>  ticketsCancelColumn;

    @FXML
    private void initialize() {
    	user = new User();
    	authenticator = new Authenticator();
    	nameField.textProperty().bind(user.getName());
    	stateField.textProperty().bind(authenticator.getState());
    	cashField.textProperty().bind(user.getCash().asString());

    	userConnectButton.textProperty().bind(new When(authenticator.getIsAuthenticated()).then("Disconnect").otherwise("Connect"));
    	userAccountButton.textProperty().bind(new When(authenticator.getIsAuthenticated()).then("Update").otherwise("Create"));

        fromField.textProperty().bindBidirectional(from);
    	toField.textProperty().bindBidirectional(to);
    	dayField.valueProperty().bindBidirectional(day);

    	statusLabel.textProperty().bind(status);

    	status.set("Ready");

		classesCount.addAll(0, 1, 2, 3, 4);
		firstClassField.setItems(classesCount);
		businessClassField.setItems(classesCount);
		economicClassField.setItems(classesCount);
		firstClassField.setValue(0);
		businessClassField.setValue(0);
		economicClassField.setValue(0);
		
    	travelFromColumn.setCellValueFactory(cellData -> cellData.getValue().getFrom());
    	travelToColumn.setCellValueFactory(cellData -> cellData.getValue().getTo());
    	travelDayColumn.setCellValueFactory(cellData -> cellData.getValue().getDay());
    	travelFreeFirstColumn.setCellValueFactory(cellData -> cellData.getValue().getFreeFirst().asObject());
    	travelFreeBusinessColumn.setCellValueFactory(cellData -> cellData.getValue().getFreeBusiness().asObject());
    	travelFreeEconomicColumn.setCellValueFactory(cellData -> cellData.getValue().getFreeEconomic().asObject());
    	travelBookingColumn.setCellFactory(
        		new Callback<TableColumn<Travel, Void>, TableCell<Travel, Void>>() {
                    @Override
                    public TableCell<Travel, Void> call(final TableColumn<Travel, Void> param) {
                        final TableCell<Travel, Void> cell = new TableCell<Travel, Void>() {
                            private final Button btn = new Button("Book");
                            {
                                btn.setOnAction((ActionEvent event) -> {
                                	Travel travel = getTableView().getItems().get(getIndex());
                                	onBookTravel(travel);
                                });
                                btn.disableProperty().bind(authenticator.getIsAuthenticated().not());
                            }

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btn);
                                }
                            }
                        };
                        return cell;
                    }
                }
        );

    	ticket = new Ticket();
    	
    	ticket.getPlane().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	planeName.setText(newValue);
	    		ObjectProperty<Image> image = imagesCache.getImage(newValue);
	    		planePicture.imageProperty().bind(image);
			}
		});

    	ticketFrom.textProperty().bind(ticket.getFrom());
    	ticketTo.textProperty().bind(ticket.getTo());
    	ticketDay.textProperty().bind(ticket.getDay());
    	travelTable.setItems(travels);

    	ticketSeatClassColumn.setCellValueFactory(cellData -> cellData.getValue().getComfort().asString());
    	ticketSeatComfortColumn.setCellValueFactory(cellData -> cellData.getValue().getComfort());
    	ticketSeatComfortColumn.setCellFactory(
        		new Callback<TableColumn<Seat, SeatComfort>, TableCell<Seat, SeatComfort>>() {
                    @Override
                    public TableCell<Seat, SeatComfort> call(final TableColumn<Seat, SeatComfort> param) {
                        final TableCell<Seat, SeatComfort> cell = new TableCell<Seat, SeatComfort>();
                        final ImageView graphic = new ImageView();
                        cell.itemProperty().addListener((observableValue, oldValue, newValue) -> {
                        	if (newValue != null) {
	            	    		ObjectProperty<Image> image = imagesCache.getImage(newValue);
	            	    		graphic.imageProperty().bind(image);
	                            cell.setGraphic (graphic);
                        	} else {
                                cell.setGraphic (null);
                        	}
                        });
                        return cell;
                    }
                }
        );
    	ticketSeatNameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
    	ticketSeatCostColumn.setCellValueFactory(cellData -> cellData.getValue().getCost().asObject());
    	ticketSeatTable.setItems(ticket.getSeats());
    	ticketTotalCost.textProperty().bind(ticket.getCost().asString());
    	ticketPay.disableProperty().bind(Bindings.notEqual(ticket.getState(), TicketState.RESERVED));

    	ticketsFromColumn.setCellValueFactory(cellData -> cellData.getValue().getFrom());
    	ticketsToColumn.setCellValueFactory(cellData -> cellData.getValue().getTo());
    	ticketsDayColumn.setCellValueFactory(cellData -> cellData.getValue().getDay());
    	ticketsStateColumn.setCellValueFactory(cellData -> cellData.getValue().getState());
    	ticketsSeatsColumn.setCellValueFactory(cellData -> cellData.getValue().getSeatsAsString());
    	ticketsCostColumn.setCellValueFactory(cellData -> cellData.getValue().getCost().asObject());
    	ticketsCancelColumn.setCellValueFactory(cellData -> cellData.getValue().getState());
    	ticketsCancelColumn.setCellFactory(
        		new Callback<TableColumn<Ticket, TicketState>, TableCell<Ticket, TicketState>>() {
                    @Override
                    public TableCell<Ticket, TicketState> call(final TableColumn<Ticket, TicketState> param) {
                        final TableCell<Ticket, TicketState> cell = new TableCell<Ticket, TicketState>() {
                            private final Button btn = new Button("");
                            {
                                btn.setOnAction((ActionEvent event) -> {
                                	Ticket ticket = getTableView().getItems().get(getIndex());
                                	if (ticket.getState().get() == TicketState.PAYED)
                                		onCancelTicket(ticket);
                                	if (ticket.getState().get() == TicketState.RESERVED)
                                		doLoadTicketPane(ticket.getId());
                                });
                            }

                            @Override
                            public void updateItem(TicketState item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                	boolean disabled = true;
                                	String text = "";
                            		if (authenticator.getIsAuthenticated().get()) {
                            			if (item == TicketState.PAYED) {
                            				disabled = false;
                            				text = "Cancel";
                            			}
                            			if (item == TicketState.RESERVED) {
                            				disabled = false;
                            				text = "Pay";
                            			}
                            		}
                                    btn.setDisable(disabled);
                                    btn.setText(text);
                                    setGraphic(btn);
                                }
                            }
                        };
                        return cell;
                    }
                }
        );
    	ticketsTable.setItems(tickets);
    }

	private void fail () {
		status.set("Last request failed dramatically");
    }

    @FXML
	private void onTicketsTabSelected () {
    	if (ticketsTab.isSelected()  && authenticator.getIsAuthenticated().get()) {
			Collection<Ticket> result = session.getAllTickets (authenticator);
	    	tickets.clear();
			if (result != null) {
				tickets.addAll(result);
				status.set("Fetched all tickets ");
			} else fail();
	    }
    }
    
    private void doConnect() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Connect.fxml"));
		try {
	        Parent root = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Connection");
	        stage.setScene(new Scene(root));
            ConnectController controller = fxmlLoader.getController();
            controller.setSession(session);
            controller.setUserAndAuthenticator(user, authenticator);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private void doDisconnect() {
		Boolean result = session.disconnect(authenticator);
		if (result != null) {
			if (result) {
				this.user.setWith(null);
				this.authenticator.setWith(null);
				status.set("User disconnected");
			} else status.set("User cannot be disconnected");
		} else fail();
    }

    @FXML
    private void onUserConnect() {
    	if (authenticator.getIsAuthenticated().get()) {
    		doDisconnect ();
    	} else {
    		doConnect ();
    	}
    }
    
    @FXML
    private void onUserAccount() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Account.fxml"));
		try {
	        Parent root = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setTitle("Account");
	        stage.setScene(new Scene(root));
            AccountController controller = fxmlLoader.getController();
            controller.setSession(session);
            controller.setAuthenticator(authenticator);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void onClearTravelFields() {
		from.set(null);
		to.set(null);
		day.setValue(null);
	}

    @FXML
    private void onListTravels() {
		Collection<Travel> result;
		if (from.get() == null && to.get() == null && day.get() == null) result = session.getAllTravels ();
		else result = session.getAllTravels (from.get(), to.get(), day.get());
    	travels.clear();
		if (result != null) {
			travels.addAll(result);
			status.set("Fetched all travels ");
		} else fail();
    }

    private void onBookTravel(Travel travel) {
    	List<SeatCount> counts = new ArrayList<>();
    	if (firstClassField.getValue() != 0)
    		counts.add(new SeatCount(SeatComfort.FIRST, firstClassField.getValue()));
    	if (businessClassField.getValue() != 0)
    		counts.add(new SeatCount(SeatComfort.BUSINESS, businessClassField.getValue()));
    	if (economicClassField.getValue() != 0)
    		counts.add(new SeatCount(SeatComfort.ECONOMIC, economicClassField.getValue()));
		Long ticketId = session.bookTravel(authenticator, travel.getId(), counts);
		if (ticketId != null) {
			if (ticketId != 0L) {
				doLoadTicketPane(ticketId);
			} else {
				status.set("Unable to book the travel ... may be not enough place yet on plane");
			}
		} else fail();
    }

    private void doLoadTicketPane (long ticketId) {
		mainTabPane.getSelectionModel().select(1);
		Ticket ticket = session.getTicket(authenticator, ticketId);
		this.ticket.setWith(ticket);
		if (ticket != null) {
			status.set("Ticket loaded ");
		} else fail();
    }

    private void onCancelTicket(Ticket ticket) {
    	PaymentResult result = session.cancelTicket(authenticator, ticket.getId());
		if (result == null) fail ();
		else {
			switch (result.getState()) {
			case NO :
				fail();
			case BANK:
				fail();
				break;
			case DONE:
				status.set("Yes the ticket is now successfully canceled");
				user.getCash().set(result.getValue());
				ticket.getState().set(TicketState.CANCELED);
				break;
			case FAILED:
				status.set("Sorry but the ticket cannot be canceled");
				break;
			default:
				break;
			}
	    }
    }
    
    @FXML
    private void onTicketPay() {
    	if (ticket.getState().get() != TicketState.RESERVED) {
			status.set("sorry but the ticket cannot be payed again");
			return;
    	}
		Set<String> names = new TreeSet<>();
		for (Seat seat : ticket.getSeats()) {
			if (seat.getState().get() != SeatState.RESERVED) continue;
			String name = seat.getName().get(); 
			if (! name.isEmpty()) names.add(name);
		}
		if (ticket.getSeats().size() != names.size()) {
			status.set("Sorry but seat reservations have troubles");
		} else {
			PaymentResult payed = session.payTicket(authenticator, ticket.getId());
			if (payed == null) fail ();
			else {
				switch (payed.getState()) {
				case NO :
					fail();
				case BANK:
					status.set("Contact your bank for : "+payed.getValue());
					break;
				case DONE:
					status.set("Yes the ticket is now successfully booked");
					user.getCash().set(payed.getValue());
					ticket.getState().set(TicketState.PAYED);
					break;
				case FAILED:
					status.set("Sorry but the ticket cannot be booked");
					break;
				default:
					break;
				}
		    }
	    }
    }
    
    public void setSession(ISession session) {
		this.session = session;
	}

	public void setImagesCache(ImagesCache imagesCache) {
		this.imagesCache = imagesCache;
	}

}
