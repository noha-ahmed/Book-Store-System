package GUI;

import Models.BookStore;
import Models.ErrorCodes;
import Models.UserBasicInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable{
    @FXML
    private TextField emailFieldSignUp;
    @FXML
    private PasswordField passwordFieldSignUp;
    @FXML
    private TextField firstnameFieldSignUp;
    @FXML
    private TextField lastnameFieldSignUp;
    @FXML
    private TextField shippingAddressFieldSignUp;
    @FXML
    private TextField phoneNumberSignUp;
    @FXML
    private TextField userNameFieldSignUp;

    RoutingHandler routingHandler = new RoutingHandler();

    private BookStore bookStore;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public void initData(BookStore bookStore){
        this.bookStore = bookStore;
    }

    public void signUp(ActionEvent event) throws IOException {
        try {
            int code = bookStore.signUp(UserBasicInfo.builder()
                    .email(emailFieldSignUp.getText())
                    .firstName(firstnameFieldSignUp.getText())
                    .lastName(lastnameFieldSignUp.getText())
                    .password(passwordFieldSignUp.getText())
                    .shippingAddress(shippingAddressFieldSignUp.getText())
                    .phoneNumber(phoneNumberSignUp.getText())
                    .username(userNameFieldSignUp.getText())
                    .privilege(false)
                    .build());

            if( code == ErrorCodes.CORRECT_USER.getCode() ){
                routingHandler.viewUserHome(event,bookStore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void backToSignInPage (ActionEvent event) throws IOException {
        routingHandler.viewSignIn(event);
    }





}
