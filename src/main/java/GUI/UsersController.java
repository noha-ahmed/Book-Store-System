package GUI;

import Models.BookStore;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

    @FXML
    private ListView<String> usersListView;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Pane userPane;


    private RoutingHandler routingHandler = new RoutingHandler();
    private BookStore bookStore;
    private List<String[]> usersList;
    private int selectedUserIndx;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initData(BookStore bookStore) {
        this.bookStore = bookStore;
        userPane.setVisible(false);
        try {
            usersList = bookStore.getAllUsers();
            populateUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignAsManager(ActionEvent event){
        try {
            bookStore.promoteUser(usersList.get(selectedUserIndx)[0]);

            usersList = bookStore.getAllUsers();
            populateUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void selectUser(MouseEvent event){
        selectedUserIndx = usersListView.getSelectionModel().getSelectedIndex();
        if(selectedUserIndx == -1 ) return;
        populateUser(usersList.get(selectedUserIndx));
    }

    public void populateUser(String[] user){
        userNameLabel.setText(user[0]);
        emailLabel.setText(user[1]);
        userPane.setVisible(true);
    }
    public void populateUsers(){
        userPane.setVisible(false);
        ObservableList<String> obs = FXCollections.observableArrayList();
        for (int i = 0 ; i < usersList.size() ; i++) {
            obs.add(usersList.get(i)[0]);
        }
        usersListView.setItems(obs);
    }

    public void viewHome(ActionEvent event) throws IOException {
        routingHandler.viewManagerHome(event,bookStore);

    }
    public void logOut(ActionEvent event) throws IOException {
        routingHandler.viewSignIn(event);

    }



}
