package Models;
import java.sql.SQLException;
import java.util.List;


public class Test {
    public static void main(String[] args) throws SQLException {

//        Manager manager = new Manager();
////        manager.addNewBook(book);
//////        manager.modifyBook(book);
//
//        manager.getByTitle()
        User u = new User();
        u.setUsername("asmaa ramadan");
        Manager manager = new Manager();
        String s = "The Artist's Eye";
        List<Book> b = u.getByTitle("Beautiful World, where are You?");
        u.addToCart(b.get(0));
        u.placeOrder();









    }


}
