package Models;

import lombok.Data;

import java.util.List;

@Data
public class BookStore {

    public static final DatabaseManager databaseManager = DatabaseManager.getInstance("root", "root");

    private List<Manager> managers;
    private List<User> users;
    private List<Book> books;
    private List<UserOrder> userOrders;
}
