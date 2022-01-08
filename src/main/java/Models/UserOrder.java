package Models;

import lombok.Data;

import java.util.List;

@Data
public class UserOrder {

    private int id;
    private List<Book> purchasedBooks;
    private int customerId;
    private double totalPrice;
    private String paymentMethod;


}
