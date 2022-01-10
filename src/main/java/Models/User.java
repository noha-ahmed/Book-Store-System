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

    public void addToCart(Book book) {
        if( shoppingCart == null )
            shoppingCart = new Cart();
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

    public boolean checkOut(String creditCardNumber, Date expiryDate) throws SQLException {
        if(isValidExpiryDate(expiryDate) && isValidCreditCardNum(creditCardNumber)){
            return placeOrder();
        }
        else
            return false;
    }

    private boolean placeOrder() throws SQLException {
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
        shoppingCart = new Cart();
        return true;
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

    private void updateUser(UserBasicInfo user) {
        username = user.getUsername();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        password = user.getPassword();
        shippingAddress = user.getShippingAddress();
        phoneNumber = user.getPhoneNumber();
    }

    public int editPersonalInfo(UserBasicInfo user) throws SQLException {

        System.out.println(user.getUsername());
        ResultSet usernameResult = BookStore.databaseManager.executeQuery("CALL get_User('" + user.getUsername() + "')");
        if (usernameResult.next() && (!username.equals(user.getUsername()))) {
            System.out.println(usernameResult.getNString("UserName"));
            return ErrorCodes.USERNAME_EXISTS.getCode();
        }
        ResultSet emailResult = BookStore.databaseManager.executeQuery("CALL get_User_Email('" + user.getEmail() + "')");
        if (emailResult.next() &&(!email.equals(user.getEmail()))) return ErrorCodes.EMAIL_EXISTS.getCode();

        System.out.println(username);
        BookStore.databaseManager.executeQuery("CALL update_User('" + username + "','"
                + user.getUsername() + "','" + user.getPassword() + "','"
                + user.getFirstName() + "','" + user.getLastName() + "','"
                + user.getEmail() + "','" + user.getPhoneNumber() + "','" + user.getShippingAddress() + "')");
        updateUser(user);

        return ErrorCodes.CORRECT_USER.getCode();
    }

    public UserBasicInfo getInfo(){
        return UserBasicInfo.builder().email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .phoneNumber(phoneNumber)
                .username(username)
                .shippingAddress(shippingAddress)
                .build();
    }


}
