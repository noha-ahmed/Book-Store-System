package GUI;

import Models.Book;
import Models.BookStore;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RoutingHandler {

    public void viewProfile(ActionEvent event,BookStore bookStore) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("userProfile.fxml"));
        Parent root = loader.load();
        UserProfileController userHomeController = loader.getController();
        userHomeController.initData(bookStore);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void viewSignIn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signIn.fxml"));
        Scene signInPage = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(signInPage);
        window.show();

    }

    public void viewManagerHome(ActionEvent event, BookStore bookStore) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("managerHome.fxml"));
        Parent root = loader.load();
        ManagerHomeController managerHomeController = loader.getController();
        managerHomeController.initData(bookStore);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void viewUserHome(ActionEvent event, BookStore bookStore) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("userHome.fxml"));
        Parent root = loader.load();
        UserHomeController userHomeController = loader.getController();
        userHomeController.initData(bookStore);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void viewUserProfile(ActionEvent event, BookStore bookStore) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("userProfile.fxml"));
        Parent root = loader.load();
        UserProfileController userHomeController = loader.getController();
        userHomeController.initData(bookStore);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void viewCart(ActionEvent event, BookStore bookStore) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("cart.fxml"));
        Parent root = loader.load();
        CartController controller = loader.getController();
        controller.initData(bookStore);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }



    public void viewModifyBook(ActionEvent event, BookStore bookStore, Book book) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifyBook.fxml"));
        Parent root = loader.load();
        ModifyBookController controller = loader.getController();
        controller.initData(bookStore, book);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void viewAddBooks(ActionEvent event, BookStore bookStore) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifyBook.fxml"));
        Parent root = loader.load();
        ModifyBookController controller = loader.getController();
        controller.initData(bookStore);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void viewUsers(ActionEvent event, BookStore bookStore) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("users.fxml"));
        Parent root = loader.load();
        UsersController controller = loader.getController();
        controller.initData(bookStore);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void viewLibraryOrders(ActionEvent event, BookStore bookStore) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("libraryOrders.fxml"));
        Parent root = loader.load();
        LibraryOrdersController controller = loader.getController();
        controller.initData(bookStore);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    public void viewSignUp(ActionEvent event,BookStore bookStore) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("signUp.fxml"));
        Parent root = loader.load();
        SignUpController controller = loader.getController();
        controller.initData(bookStore);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
}
