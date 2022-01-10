package GUI;

import Models.BookStore;
import Models.ErrorCodes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignInController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private BookStore bookStore = new BookStore();

    RoutingHandler routingHandler = new RoutingHandler();


    public void signIn(ActionEvent event) throws IOException {
        try {
            if( !emailField.getText().equals("") && !emailField.getText().equals("") ){
                int code= bookStore.signIn(emailField.getText(),passwordField.getText());
                System.out.println(emailField.getText());
                System.out.println(passwordField.getText());
                System.out.println(code);
                if( code == ErrorCodes.CORRECT_USER.getCode() ){
                    if( bookStore.isManager() )
                        routingHandler.viewManagerHome(event,bookStore);
                    else
                        routingHandler.viewUserHome(event,bookStore);
                }
                else{
                    if( code == ErrorCodes.WRONG_USERNAME.getCode() )
                        errorLabel.setText("Wrong Username!");
                    else
                        errorLabel.setText("Wrong password!");

                    errorLabel.setVisible(true);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void signupPage(ActionEvent event) throws IOException {
        routingHandler.viewSignUp(event,bookStore);
    }


}
