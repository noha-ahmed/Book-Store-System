package GUI;

import Models.BookStore;
import Models.UserBasicInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UserProfileController {

    @FXML
    private TextField passwordFieldProfile;
    @FXML
    private TextField firstNameFieldProfile;
    @FXML
    private TextField lastNameFieldProfile;
    @FXML
    private TextField shippingAddressFieldProfile;
    @FXML
    private TextField phoneNumberFieldProfile;
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;


    private RoutingHandler routingHandler = new RoutingHandler();
    private BookStore bookStore;

    public void initData(BookStore bookStore) {
        this.bookStore = bookStore;
        UserBasicInfo userProfile = bookStore.getProfile();
        populateProfile(userProfile);
    }

    public void populateProfile(UserBasicInfo userProfile){
        firstNameFieldProfile.setText(userProfile.getFirstName());
        lastNameFieldProfile.setText(userProfile.getLastName());
        shippingAddressFieldProfile.setText(userProfile.getShippingAddress());
        passwordFieldProfile.setText(userProfile.getPassword());
        emailField.setText(userProfile.getEmail());
        usernameField.setText(userProfile.getUsername());
        phoneNumberFieldProfile.setText(userProfile.getPhoneNumber());
    }

    public void logOut(ActionEvent event) throws IOException {
        routingHandler.viewSignIn(event);

    }

    public void viewHome(ActionEvent event) throws IOException {
        if( bookStore.isManager() )
            routingHandler.viewManagerHome(event,bookStore);
        else
            routingHandler.viewUserHome(event,bookStore);

    }


    public void saveChanges(ActionEvent event) {
        UserBasicInfo userBasicInfo = UserBasicInfo.builder()
                .firstName(firstNameFieldProfile.getText())
                .lastName(lastNameFieldProfile.getText())
                .email(emailField.getText())
                .username(usernameField.getText())
                .password(passwordFieldProfile.getText())
                .privilege(bookStore.isManager())
                .shippingAddress(shippingAddressFieldProfile.getText())
                .phoneNumber(phoneNumberFieldProfile.getText())
                .build();
        bookStore.editProfile(userBasicInfo);
        populateProfile(userBasicInfo);

    }

}
