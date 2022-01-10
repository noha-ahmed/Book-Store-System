package Models;

import Mappers.BookMapper;
import Mappers.PublisherMapper;
import Mappers.UserMapper;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class BookStore {
    public static final DatabaseManager databaseManager = DatabaseManager.getInstance("root", "@mysqlNoha117");
    private User user;

    public int signIn(String username, String password) throws SQLException {
        ResultSet result = BookStore.databaseManager.executeQuery("CALL get_User('" + username + "')");
        if (!result.next())
            return ErrorCodes.WRONG_USERNAME.getCode();
        try {
            if (result.getString("password").equals(password)) {
                user = UserMapper.toUser(result);
                if (user.isPrivilege())
                    user = new Manager(user.getUsername()
                            , user.getPassword()
                            , user.getFirstName()
                            , user.getLastName()
                            , user.getEmail()
                            , user.getPhoneNumber()
                            , user.getShippingAddress()
                            , user.isPrivilege()
                            , new Cart());
                return ErrorCodes.CORRECT_USER.getCode();
            } else
                return ErrorCodes.WRONG_PASSWORD.getCode();
        } catch (SQLException e) {
            return ErrorCodes.SQL_ERROR.getCode();
        }
    }

    public int signUp(UserBasicInfo new_user) throws SQLException {
        ResultSet result = BookStore.databaseManager.executeQuery("CALL get_User('" + new_user.getUsername() + "')");
        if (result.next())
            return ErrorCodes.USERNAME_EXISTS.getCode(); // username is taken
        ResultSet result1 = BookStore.databaseManager.executeQuery("CALL get_User_Email('" + new_user.getEmail() + "')");
        if (result1.next())
            return ErrorCodes.EMAIL_EXISTS.getCode(); // email is taken
        try {
            BookStore.databaseManager.executeQuery("CALL add_User('" + new_user.getUsername() + "','"
                    + new_user.getPassword() + "','" + new_user.getFirstName() + "','" + new_user.getLastName() + "','"
                    + new_user.getEmail() + "','" + new_user.getPhoneNumber() + "','" + new_user.getShippingAddress()
                    + "',0)");
            user = UserMapper.toUser(new_user);
            return ErrorCodes.CORRECT_USER.getCode();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return ErrorCodes.SQL_ERROR.getCode(); // error in query ex: data input is invalid
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        ResultSet resultSet = BookStore.databaseManager.executeQuery("CALL get_Books()");
        List<Book> allBooks = new ArrayList<>();
        while (resultSet.next()) {
            allBooks.add(BookMapper.toBook(resultSet));
        }
        return allBooks;
    }

    public boolean isManager(){
        return this.user.isPrivilege();
    }

    public List<Book> search(String type, String input){
        try {
            return Search.search(type, input);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void addToCart(Book book){
        user.addToCart(book);
    }

    public Map<Book,Integer> viewCart(){
        return user.viewCart();
    }

    public UserBasicInfo getProfile(){
        return  user.getInfo();
    }

    public int editProfile(UserBasicInfo userBasicInfo){
        try {
            return user.editPersonalInfo(userBasicInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ErrorCodes.SQL_ERROR.getCode();
    }

    public double getCartTotalPrice(){
        return user.getShoppingCart().getTotalPrice();
    }

    public void removeFromCart(Book book){
        user.removeFromCart(book);
    }

    public boolean checkOutCart(String creditCardNumber, Date expiryDate){
        try {
            return user.checkOut(creditCardNumber,expiryDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addBook(Book book, String publisherName) throws SQLException {
        book.setPublisher(PublisherMapper.toPublisher(Manager.viewPublisherByName(publisherName)));
        Manager manager = (Manager)user;
        manager.addNewBook(book);
        for(String author : book.getAuthors()){
            databaseManager.executeQuery("CALL add_Author('" + author + "','"
                    + book.getISBN() + "')");
        }
    }

    public List<String[]> getAllUsers() throws SQLException {
        ResultSet users = databaseManager.executeQuery("CALL get_User_by_privilege()");
        List<String[]> usersInfo = new ArrayList<>();
        while (users.next()){
            String[] info = new String[2];
            info[0] = users.getNString("UserName");
            info[1] = users.getNString("Email");
            usersInfo.add(info);
        }
        return usersInfo;
    }

    public void promoteUser(String userName) throws SQLException {
        Manager manager = (Manager)user;
        manager.promoteUser(userName);
    }


    public void confirmStoreOrder(int orderID) throws SQLException {
        Manager manager = (Manager)user;
        manager.confirmOrder(orderID);
    }

    public List<String[]> getOrders() throws SQLException {
        ResultSet orders = databaseManager.executeQuery("CALL get_Book_Store_Orders()");
        List<String[]> orderInfo = new ArrayList<>();
        while (orders.next()){
            String[] info = new String[4];
            info[0] = String.valueOf(orders.getInt("Order_ID"));
            info[1] = String.valueOf(orders.getInt("Quantity"));
            info[2] = orders.getNString("ISBN");
            info[3] = orders.getNString("Title");
            orderInfo.add(info);
        }
        return orderInfo;
    }


}
