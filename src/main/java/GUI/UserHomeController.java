package GUI;

import Models.Book;
import Models.BookStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserHomeController  implements Initializable {
    @FXML
    private ComboBox<String> propertyComboBoxUserHome;
    @FXML
    private TextField searchFieldUserHome;
    @FXML
    private Pane bookPaneUserHome;
    @FXML
    private ListView<String> booksListView;

    @FXML
    private TextField authorUserHome;
    @FXML
    private TextField ISBNFieldUserHome;
    @FXML
    private Label titleFieldUserHome;
    @FXML
    private TextField publisherUserHome;
    @FXML
    private TextField categoryUserHome;
    @FXML
    private TextField pubYearUserHome;
    @FXML
    private TextField priceUserHome;

    private BookStore bookStore;
    private List<Book> books;

    private RoutingHandler routingHandler = new RoutingHandler();

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
        bookPaneUserHome.setVisible(false);

    }

    public void populateBook(Book book){
        titleFieldUserHome.setText(book.getTitle());
        priceUserHome.setText(book.getPrice().toString());
        authorUserHome.setText(book.getAuthors().get(0));
        categoryUserHome.setText(book.getCategory().toString());
        publisherUserHome.setText(book.getPublisher().getName());
        pubYearUserHome.setText(book.getPublicationDate());
    }

    public void populateBooks(){
        ObservableList<String> obsBooks = FXCollections.observableArrayList();
        for (int i = 0 ; i < books.size() ; i++) {
            obsBooks.add(books.get(i).getTitle() + " [ " + books.get(i).getCategory().toString() + " ] ");
        }
        booksListView.setItems(obsBooks);

    }

    public void selectBook(MouseEvent event){
        int selectedBook = booksListView.getSelectionModel().getSelectedIndex();
        populateBook(books.get(selectedBook));
        bookPaneUserHome.setVisible(true);
    }
    public void search(ActionEvent event) {
        bookPaneUserHome.setVisible(false);
        if( propertyComboBoxUserHome.getValue().equals("All") ){
            System.out.println(searchFieldUserHome.getText());
            try {
                books = bookStore.getAllBooks();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
             books = bookStore.search(propertyComboBoxUserHome.getValue(),searchFieldUserHome.getText());
        populateBooks();

    }

    public void addToCart(ActionEvent event) {
        bookStore.addToCart(books.get(booksListView.getSelectionModel().getSelectedIndex()));
    }

    public void logOut(ActionEvent event) throws IOException {
        routingHandler.viewSignIn(event);

    }
    public void viewProfile(ActionEvent event) throws IOException {
        routingHandler.viewUserProfile(event,bookStore);
    }

    public void viewCart(ActionEvent event) throws IOException {
        routingHandler.viewCart(event,bookStore);
    }










}
