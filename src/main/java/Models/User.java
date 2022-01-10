package Models;

import Mappers.BookMapper;
import lombok.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class User {

    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String shippingAddress;
    private Cart shoppingCart = new Cart();

    public void editPersonalInfo(UserBasicInfo user) throws SQLException {
        BookStore.databaseManager.executeQuery("UPDATE USER SET UserName = '" + user.getUsername()
                + "',Password = '" + user.getPassword() + "',First_Name = '" + user.getFirstName() + "',Last_Name = '" + user.getLastName() + "',Email = '" + user.getEmail() +
                "',Phone_Number = '" + user.getPhoneNumber() + "',Shipping_Address = '" + user.getShippingAddress() + "',privilege = " + user.getPrivilege() +
                " WHERE User_id = " + user.getId());
    }

    public List<Book> getByTitle(String title) throws SQLException {
        List<Book> books = new ArrayList<>();
        ResultSet resultSet = BookStore.databaseManager.executeQuery("CALL search_By_Title(" + "'" + title + "'" + ")");
        while (resultSet.next()) {
            books.add(BookMapper.toBook(resultSet));
        }
        return books;

    }

    public List<Book> getByISBN(String ISBN) throws SQLException {
        List<Book> books = new ArrayList<>();
        ResultSet resultSet = BookStore.databaseManager.executeQuery("CALL search_By_ISBN(" + "'" + ISBN + "'" + ")");
        while (resultSet.next()) {
            books.add(BookMapper.toBook(resultSet));
        }
        return books;
    }

    public List<Book> getByPublisherName(String publisherName) throws SQLException {
        List<Book> books = new ArrayList<>();
        ResultSet resultSet = BookStore.databaseManager.executeQuery("CALL search_By_Publisher(" + "'" + publisherName + "'" + ")");
        while (resultSet.next()) {
            books.add(BookMapper.toBook(resultSet));
        }
        return books;

    }

    public List<Book> getByAuthor(String authorName) throws SQLException {
        List<Book> books = new ArrayList<>();
        ResultSet resultSet = BookStore.databaseManager.executeQuery("CALL search_By_Author(" + "'" + authorName + "'" + ")");
        while (resultSet.next()) {
            books.add(BookMapper.toBook(resultSet));
        }
        return books;

    }

    public List<Book> getByCategory(String category) throws SQLException {
        List<Book> books = new ArrayList<>();
        ResultSet resultSet = BookStore.databaseManager.executeQuery("CALL search_By_Category(" + "'" + category + "'" + ")");
        while (resultSet.next()) {
            books.add(BookMapper.toBook(resultSet));
        }
        return books;

    }

    public List<Book> getByPublicationDate(String publicationYear) throws SQLException {
        List<Book> books = new ArrayList<>();
        ResultSet resultSet = BookStore.databaseManager.executeQuery("CALL search_By_Publication_year(" + "'" + publicationYear + "'" + ")");
        while (resultSet.next()) {
            books.add(BookMapper.toBook(resultSet));
        }
        return books;

    }

    public void addToCart(Book book) {
        shoppingCart.addBook(book);
    }

    public void removeFromCart(Book book) {
        shoppingCart.removeBook(book);
    }

    public Map<Book,Integer> viewCart() {
        return shoppingCart.getBooks();
    }

    public double getTotalCartPrice() {
        return shoppingCart.getTotalPrice();
    }

    public boolean placeOrder()
    {
        for( Map.Entry<Book,Integer> m : shoppingCart.getBooks().entrySet())
        {
            m.getKey();
            m.getValue();
        }
        shoppingCart.getBooks().forEach((key, value) ->
        {
            try {
                BookStore.databaseManager.executeQuery("CALL add_Customer_Order('"
                        + username + "','"
                        + key.getISBN() + "',"
                        + value + ","
                        + (value *key.getPrice()) + ")");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return true;
    }
    private void addItem()
    {

    }


}
