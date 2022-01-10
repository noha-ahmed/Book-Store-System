package GUI;

import Models.BookStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LibraryOrdersController {

    @FXML
    private TextField ISBNField;
    @FXML
    private Label titleField;
    @FXML
    private TextField orderIdField;
    @FXML
    private TextField quantityField;
    @FXML
    private ListView<String> ordersListView;
    @FXML
    private Pane orderPane;

    private int indx;

    private BookStore bookStore;
    private List<String[]> ordersList;
    RoutingHandler routingHandler = new RoutingHandler();

    public void initData(BookStore bookStore){
        this.bookStore = bookStore;
        populateOrders();
    }

    public void populateOrders(){
        orderPane.setVisible(false);
        try {
            ordersList = bookStore.getOrders();
            ObservableList<String> obs = FXCollections.observableArrayList();
            for (int i = 0 ; i < ordersList.size() ; i++) {
                obs.add(ordersList.get(i)[3] + " ( " + ordersList.get(i)[1] + " )");
            }
            ordersListView.setItems(obs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectOrder(MouseEvent event){
        indx = ordersListView.getSelectionModel().getSelectedIndex();
        if( indx == -1 ) return;
        populateOrder(ordersList.get(indx));
    }

    public void populateOrder(String[] order){
        ISBNField.setText(order[2]);
        orderIdField.setText(order[0]);
        titleField.setText(order[3]);
        quantityField.setText(order[1]);
        orderPane.setVisible(true);
    }

    public void confirmOrder(ActionEvent event) throws IOException {
        orderPane.setVisible(false);
        try {
            bookStore.confirmStoreOrder(Integer.parseInt(ordersList.get(indx)[0]));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        populateOrders();
    }

    public void viewHome(ActionEvent event) throws IOException {
        routingHandler.viewManagerHome(event,bookStore);
    }
    public void logOut(ActionEvent event) throws IOException {
        routingHandler.viewSignIn(event);
    }


}
