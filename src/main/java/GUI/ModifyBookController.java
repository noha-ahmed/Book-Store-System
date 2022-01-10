package GUI;

import Models.Book;
import Models.BookStore;
import Models.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ModifyBookController implements Initializable {

    @FXML
    private ListView<String> authorsListView;
    @FXML
    private TextField authorField;
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
    @FXML
    private TextField thresholdField;
    @FXML
    private Label pageTitle;

    private boolean isModify;

    private RoutingHandler routingHandler = new RoutingHandler();
    private BookStore bookStore;
    private Book book;
    private List<String> authorsList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData (BookStore bookStore) {
        this.bookStore = bookStore;
        pageTitle.setText("Add Book");
        authorsList = new ArrayList<>();
        isModify = false;
    }

    public void initData(BookStore bookStore, Book book) {
        this.bookStore = bookStore;
        pageTitle.setText("Modify Book");
        authorsList = new ArrayList<>();
        populateBook(book);
        isModify = true;

    }
    public void saveBook(ActionEvent event) {
        Book book = Book.builder()
                .ISBN(ISBNField.getText())
                .category(Category.valueOf(categoryField.getText()))
                .title(titleField.getText())
                .price(Double.parseDouble(priceField.getText()))
                .threshold(Integer.parseInt(thresholdField.getText()))
                .numberOfCopies(Integer.parseInt(quantityField.getText()))
                .publicationDate(pubYearField.getText())
                .authors(authorsList)
                .build();
        if(isModify){
        }
        else{
            try {
                bookStore.addBook(book,publisherField.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void populateBook(Book book){
        ISBNField.setText(book.getISBN());
        titleField.setText(book.getTitle());
        priceField.setText(book.getPrice().toString());
        categoryField.setText(book.getCategory().toString());
        publisherField.setText(book.getPublisher().getName());
        pubYearField.setText(book.getPublicationDate());
        quantityField.setText(book.getNumberOfCopies() + "");
        thresholdField.setText(book.getThreshold() + "");
        authorsList = book.getAuthors();
        populateAuthors();
    }

    public void populateAuthors(){
        ObservableList<String> obsAuth = FXCollections.observableArrayList();
        for (int i = 0 ; i < authorsList.size() ; i++) {
            obsAuth.add(authorsList.get(i));
        }
        authorsListView.setItems(obsAuth);
    }


    public void addAuthor(ActionEvent event) {
        if(!authorField.getText().contentEquals("")){
            authorsList.add(authorField.getText());
            populateAuthors();
        }
        authorField.setText("");
    }

    public void deleteAuthor(ActionEvent event) {
        int indx = authorsListView.getSelectionModel().getSelectedIndex();
        authorsListView.getItems().remove(indx);
        authorsList.remove(indx);
    }

    public void viewHome(ActionEvent event) throws IOException {
        routingHandler.viewManagerHome(event,bookStore);

    }
    public void logOut(ActionEvent event) throws IOException {
        routingHandler.viewSignIn(event);

    }

}
