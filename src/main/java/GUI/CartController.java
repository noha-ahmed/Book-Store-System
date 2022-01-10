package GUI;

import Models.Book;
import Models.BookStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class CartController {

    @FXML
    private TextField authorField;
    @FXML
    private TextField ISBNField;
    @FXML
    private Label titleField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField pubYearField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField bookPriceField;
    @FXML
    private TextField bookTotalPriceField;
    @FXML
    private TextField totalPriceField;
    @FXML
    private ListView booksListView;
    @FXML
    private TextField cardNoField;
    @FXML
    private TextField expiryDateField;
    @FXML
    private Pane bookPane;
    @FXML
    private Pane confirmPane;
    @FXML
    private Button confirmOrderBtn;
    @FXML
    private Label errorLabel;

    private RoutingHandler routingHandler = new RoutingHandler();
    private BookStore bookStore;
    private ArrayList<Map.Entry<Book,Integer>> booksList;
    private int selectedBookIndx;


    public void initData(BookStore bookStore) {
        selectedBookIndx = -1;
        bookPane.setVisible(false);
        confirmPane.setVisible(false);
        booksListView.setVisible(true);
        confirmOrderBtn.setVisible(true);
        errorLabel.setVisible(false);
        this.bookStore = bookStore;
        System.out.println(bookStore.viewCart().size());
        loadData();
    }

    public void loadData(){
        Map<Book,Integer> books = bookStore.viewCart();
        populateBooks(books);
        totalPriceField.setText(bookStore.getCartTotalPrice() + "");
        if(selectedBookIndx != -1){
            populateBook(booksList.get(selectedBookIndx));
        }
        else{
            bookPane.setVisible(false);
        }
    }

    public void populateBooks(Map<Book,Integer> books){
        ObservableList<String> obsBooks = FXCollections.observableArrayList();
        booksList =  new ArrayList<>();
        for (Map.Entry<Book,Integer> entry : books.entrySet()) {
            obsBooks.add(entry.getKey().getTitle() + " [ " + entry.getKey().getCategory().toString() + " ] " + " | "
                    + entry.getValue());
            booksList.add(entry);
        }
        booksListView.setItems(obsBooks);
    }

    public void populateBook(Map.Entry<Book,Integer> bookEntry){

        Book book = bookEntry.getKey();
        titleField.setText(book.getTitle());
        bookPriceField.setText(book.getPrice().toString());
        authorField.setText(book.getAuthors().get(0));
        categoryField.setText(book.getCategory().toString());
        publisherField.setText(book.getPublisher().getName());
        pubYearField.setText(book.getPublicationDate());
        bookTotalPriceField.setText(book.getPrice()* bookEntry.getValue() + "");
        quantityField.setText(bookEntry.getValue().toString());
    }

    public void selectBook(MouseEvent event) throws IOException {
        System.out.println(booksListView.getSelectionModel().getSelectedIndex() + " indx ");
        System.out.println(booksList.size() + " size");
        selectedBookIndx = booksListView.getSelectionModel().getSelectedIndex();
        populateBook(booksList.get(selectedBookIndx));
        bookPane.setVisible(true);
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

    public void confirmOrder(ActionEvent event)  {
        booksListView.setVisible(false);
        confirmOrderBtn.setVisible(false);
        confirmPane.setVisible(true);
        bookPane.setVisible(false);
        errorLabel.setVisible(false);
    }

    public void finalConfirmOrder(ActionEvent event){
        String cardNo = cardNoField.getText();
        String date = expiryDateField.getText() + "-01";
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        try {
            boolean bool = bookStore.checkOutCart(cardNo,formatter.parse(date));
            if( !bool ){
                errorLabel.setText("Invalid Transaction!");
                errorLabel.setVisible(true);
            }
            else{
                initData(bookStore);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void cancel(ActionEvent event)  {
        booksListView.setVisible(true);
        confirmOrderBtn.setVisible(true);
        confirmPane.setVisible(false);
        errorLabel.setVisible(false);
        loadData();
    }

    public void increment(ActionEvent event) throws IOException {
        Book book = booksList.get(selectedBookIndx).getKey();
        if( book.getNumberOfCopies() > booksList.get(selectedBookIndx).getValue() ){
            bookStore.addToCart(book);
            loadData();
        }
    }

    public void decrement(ActionEvent event) throws IOException {
        Book book = booksList.get(selectedBookIndx).getKey();
        int quantity = booksList.get(selectedBookIndx).getValue();
        if( quantity > 0){
            bookStore.removeFromCart(book);
            if( quantity == 1 )
                selectedBookIndx = -1;
            loadData();
        }


    }


}
