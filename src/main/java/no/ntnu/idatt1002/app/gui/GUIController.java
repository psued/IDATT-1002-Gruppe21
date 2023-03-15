package no.ntnu.idatt1002.app.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GUIController {

    private final StringProperty firstName = new SimpleStringProperty("Alloysius");
    private final StringProperty LastName = new SimpleStringProperty("Smith");
    private final StringProperty email = new SimpleStringProperty("a_smith@reallyexpensiveisp.com");
    private final StringProperty phone = new SimpleStringProperty("(999)888-7777");
    private StringProperty bio = new SimpleStringProperty("This is the life story of Alloysius Smith\n\nHe was born a while ago\n");

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return LastName;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public StringProperty bioProperty() {
        return bio;
    }
}