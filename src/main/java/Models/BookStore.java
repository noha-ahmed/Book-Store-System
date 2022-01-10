package Models;

import Mappers.BookMapper;
import Mappers.UserMapper;
import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookStore {
    public static final DatabaseManager databaseManager = DatabaseManager.getInstance("root", "root");
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


}
