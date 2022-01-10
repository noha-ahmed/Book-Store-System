package Models;

import Mappers.BookMapper;
import lombok.Builder;
import lombok.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class User {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String shippingAddress;
    private boolean privilege;
    private Cart shoppingCart = new Cart();

//    public void editPersonalInfo(UserBasicInfo user) throws SQLException {
//        BookStore.databaseManager.executeQuery("UPDATE USER SET UserName = '" + user.getUsername()
//                + "',Password = '" + user.getPassword() + "',First_Name = '" + user.getFirstName() + "',Last_Name = '" + user.getLastName() + "',Email = '" + user.getEmail() +
//                "',Phone_Number = '" + user.getPhoneNumber() + "',Shipping_Address = '" + user.getShippingAddress() + "',privilege = " + user.isPrivilege() +
//                " WHERE User_id = " + user.getId());
//    }

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

    private boolean placeOrder()
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

    public boolean placeOrder2() throws SQLException {
        ResultSet id = BookStore.databaseManager.executeQuery("CALL add_Customer_Order('" + username + "'," + shoppingCart.getTotalPrice()+" )");
        id.next();
        int orderId = id.getInt("LAST_INSERT_ID()");
        for(Map.Entry<Book, Integer> book : shoppingCart.getBooks().entrySet()){
            try {
                BookStore.databaseManager.executeQuery("CALL add_Order_item("
                        + book.getValue() + ","
                        + orderId + ",'"
                        + book.getKey().getISBN() + "')");
            } catch (SQLException e) {
                e.printStackTrace();
                if (e.getMessage().equals("Book out of stock")) {
                    BookStore.databaseManager.executeQuery("CALL delete_Last_Order(" + orderId + " )");
                    return false;
                }
            }
        }
        return true;
    }
    public boolean checkOut(String creditCardNumber, Date expiryDate)
    {
        if(isValidExpiryDate(expiryDate) && isValidCreditCardNum(creditCardNumber)){
            return placeOrder();
        }
        else
            return false;
    }

    private boolean isValidCreditCardNum(String creditCardNumber){
        if(creditCardNumber.length() != 16)
            return false;
        for(int i = 0; i < creditCardNumber.length(); i++){
            if(!Character.isDigit(creditCardNumber.charAt(i)))
                return false;
        }
        return true;
    }
    private boolean isValidExpiryDate(Date expiryDate){
        Date todayDate = new Date();
        if(todayDate.before(expiryDate))
            return true;
        else
            return false;
    }


}
