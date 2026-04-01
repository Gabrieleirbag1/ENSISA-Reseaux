module fr.ensisa.hassenforder.flight.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires fr.ensisa.hassenforder.flight.common;
	requires javafx.base;

    opens fr.ensisa.hassenforder.flight.client.view to javafx.fxml;
    exports fr.ensisa.hassenforder.flight.client;
}
