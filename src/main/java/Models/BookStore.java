package Models;

import Mappers.UserMapper;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Data
public class BookStore {
    public static final int WRONG_PASSWORD = -1;
    public static final int WRONG_USERNAME = -2;
    public static final int CORRECT_USER = 0;
    public static final int USERNAME_EXISTS = -3;
    public static final int EMAIL_EXISTS = -4;
    public static final int SQL_ERROR = -5;

    public static final DatabaseManager databaseManager = DatabaseManager.getInstance("root", "mariam12345*");

    private Manager manager;
    private User user;

    public int signIn(String username, String password) throws SQLException {
        ResultSet result = BookStore.databaseManager.executeQuery("CALL get_User('" + username + "')");
        if(!result.next())
            return BookStore.WRONG_USERNAME;
        try {
            if (result.getString("password").equals(password)) {
                user = UserMapper.toUser(result);
                if(user.isPrivilege())
                    manager = new Manager();
                return BookStore.CORRECT_USER;
            }
            else
                return BookStore.WRONG_PASSWORD;
        } catch (SQLException e) {
            return BookStore.SQL_ERROR;
        }
    }

    public int signUp(UserBasicInfo new_user) throws SQLException {
        ResultSet result = BookStore.databaseManager.executeQuery("CALL get_User('" + new_user.getUsername() + "')");
        if (result.next())
            return BookStore.USERNAME_EXISTS; // username is taken
        ResultSet result1 = BookStore.databaseManager.executeQuery("CALL get_User_Email('" + new_user.getEmail() + "')");
        if (result1.next())
            return BookStore.EMAIL_EXISTS; // email is taken
        try {
            BookStore.databaseManager.executeQuery("CALL add_User('" + new_user.getUsername() + "','"
                    + new_user.getPassword() + "','" + new_user.getFirstName() + "','" + new_user.getLastName() + "','"
                    + new_user.getEmail() + "','" + new_user.getPhoneNumber() + "','" + new_user.getShippingAddress()
                    + "',0)");
            user = UserMapper.toUser(new_user);
            return BookStore.CORRECT_USER;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return SQL_ERROR; // error in query ex: data input is invalid
        }
    }

//    public List<Book> getAllBooks(){
//
//    }

}
