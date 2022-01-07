package Models;

import lombok.Data;

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
    private Cart shoppingCart;

}
