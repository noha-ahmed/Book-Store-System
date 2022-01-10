package GUI;

import Models.Book;
import Models.BookStore;
import Models.Manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerHomeController implements Initializable {
    @FXML
    private ComboBox<String> propertyComboBox;
    @FXML
    private ComboBox<String> reportComboBox;
    @FXML
    private TextField searchField;
    @FXML
    private Pane bookPane;
    @FXML
    private ListView<String> booksListView;
    @FXML
    private ListView<String> authorsListView;
    @FXML
    private TextField ISBNField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField pubYearField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;

    private RoutingHandler routingHandler = new RoutingHandler();
    private BookStore bookStore;
    private List<Book> books;
    private int selectedBookIndx;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(BookStore bookStore) {
        this.bookStore = bookStore;
        try {
            books = bookStore.getAllBooks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        populateBooks();
        bookPane.setVisible(false);

    }

    public void populateBook(Book book){
        titleField.setText(book.getTitle());
        ISBNField.setText(book.getISBN());
        priceField.setText(book.getPrice().toString());
        categoryField.setText(book.getCategory().toString());
        publisherField.setText(book.getPublisher().getName());
        pubYearField.setText(book.getPublicationDate());
        quantityField.setText(book.getNumberOfCopies() + "");
        populateAuthors(book.getAuthors());
    }

    public void populateAuthors(List<String> authors){
        ObservableList<String> obsAuth = FXCollections.observableArrayList();
        for (int i = 0 ; i < authors.size() ; i++) {
            obsAuth.add(authors.get(i));
        }
        authorsListView.setItems(obsAuth);
    }

    public void populateBooks(){
        ObservableList<String> obsBooks = FXCollections.observableArrayList();
        for (int i = 0 ; i < books.size() ; i++) {
            obsBooks.add(books.get(i).getTitle() + " [ " + books.get(i).getCategory().toString() + " ] ");
        }
        booksListView.setItems(obsBooks);

    }

    public void selectBook(MouseEvent event){
        selectedBookIndx = booksListView.getSelectionModel().getSelectedIndex();
        if( selectedBookIndx == -1 ) return;
        populateBook(books.get(selectedBookIndx));
        bookPane.setVisible(true);
    }
    public void search(ActionEvent event) {
        selectedBookIndx = -1;
        bookPane.setVisible(false);
        if( propertyComboBox.getValue().equals("All") ){
            System.out.println(searchField.getText());
            try {
                books = bookStore.getAllBooks();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
            books = bookStore.search(propertyComboBox.getValue(),searchField.getText());
        populateBooks();

    }

    public void addToCart(ActionEvent event) {
        bookStore.addToCart(books.get(booksListView.getSelectionModel().getSelectedIndex()));
    }

    public void showReport(ActionEvent event) {
        Manager manager = (Manager)bookStore.getUser();
        if( reportComboBox.getValue().equals("Report 1")){
            try {
                manager.totalBookSalesLastMonthReport();
            } catch (JRException e) {
                e.printStackTrace();
            }
        }
        else if(reportComboBox.getValue().equals("Report 2")){
            try {
                manager.top5PrevMonthCustomersReport();
            } catch (JRException e) {
                e.printStackTrace();
            }
        }
        else if(reportComboBox.getValue().equals("Report 3")){
            try {
                manager.bestSellerBooksReport();
            } catch (JRException e) {
                e.printStackTrace();
            }
        }
    }

    public void placeLibraryOrder(ActionEvent event) {

    }


    public void viewReport(ActionEvent event) throws IOException {
    }

    public void viewLibraryOrders(ActionEvent event) throws IOException {
        routingHandler.viewLibraryOrders(event,bookStore);
    }

    public void viewUsers(ActionEvent event) throws IOException {
        routingHandler.viewUsers(event,bookStore);
    }


    public void addBooks(ActionEvent event) throws IOException {
        routingHandler.viewAddBooks(event,bookStore);
    }

    public void viewCart(ActionEvent event) throws IOException {
        routingHandler.viewCart(event,bookStore);
    }



    public void modifyBook(ActionEvent event) throws IOException {
        routingHandler.viewModifyBook(event,bookStore,books.get(selectedBookIndx));
    }
    public void logOut(ActionEvent event) throws IOException {
        routingHandler.viewSignIn(event);

    }

    public void viewProfile(ActionEvent event) throws IOException {
        routingHandler.viewProfile(event,bookStore);
    }
}
