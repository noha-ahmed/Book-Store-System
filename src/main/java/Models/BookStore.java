package Models;

import lombok.Data;

import java.util.List;

@Data
public class BookStore {

    private List<Manager> managers;
    private List<User> users;
    private List<Book> books;
    private List<UserOrder> userOrders;
}
