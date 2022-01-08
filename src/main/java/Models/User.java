package Models;

import lombok.Data;

import java.sql.SQLException;

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

    public void editPersonalInfo(UserBasicInfo user) throws SQLException {
        BookStore.databaseManager.executeQuery("UPDATE USER SET UserName = '" + user.getUsername()
                + "',Password = '" + user.getPassword() + "',First_Name = '" + user.getFirstName() + "',Last_Name = '" + user.getLastName() + "',Email = '" + user.getEmail() +
                "',Phone_Number = '" + user.getPhoneNumber() + "',Shipping_Address = '" + user.getShippingAddress() + "',privilege = " + user.getPrivilege() +
                " WHERE User_id = " + user.getId());
    }




}
