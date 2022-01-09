package Models;
import java.sql.SQLException;


public class Test {
    public static void main(String[] args) throws SQLException {
//        DatabaseManager db = DatabaseManager.getInstance("root", "root");
//        Connection c = db.getConnection();
//        ResultSet r = db.executeQuery("SELECT * FROM BOOK;");
//        List<Book> books = new ArrayList<>();
//        Book book = new Book();
//        Publisher p = new Publisher();
//        p.setName("asmaa");
//        book.setISBN(3);
//        book.setPrice(56.94);
//        book.setCategory(Category.Art);
//        book.setTitle("hdhks");
//        book.setThreshold(5);
//        book.setNumberOfCopies(10);
//        book.setPublicationDate("2023");
//        book.setPublisher(p);

//        Book book1 = new Book();
//        Publisher p1 = new Publisher();
//        p1.setName("asmaa");
//        book1.setISBN(6);
//        book1.setPrice(56.94);
//        book1.setCategory(Category.Geography);
//        book1.setTitle("hdhks");
//        book1.setThreshold(5);
//        book1.setNumberOfCopies(10);
//        book1.setPublicationDate("2023");
//        book1.setPublisher(p);
//        books.add(book);
//        books.add(book1);
//        while (r.next())
//        {
//            System.out.println(r.getInt("ISBN"));
//            System.out.println(r.getString("title"));
//        }

//        User user = new User();
//        UserBasicInfo userr = new UserBasicInfo();
//        userr.setEmail("a@gmail.com");
//        userr.setFirstName("Ahmed");
//        userr.setLastName("Hazemmmm");
//        userr.setPhoneNumber("0293873");
//        userr.setShippingAddress("kfkfkeoe");
//        userr.setPrivilege(2);
//        userr.setId(1);
//        user.editPersonalInfo(userr);
//
//        Book book = new Book();
//        Publisher p = new Publisher();
//        p.setName("asmaa");
//        book.setISBN(3);
//        book.setPrice(56.94);
//        book.setCategory(Category.Art);
//        book.setTitle("hdhks");
//        book.setThreshold(5);
//        book.setNumberOfCopies(10);
//        book.setPublicationDate("2023");
//        book.setPublisher(p);
//
//        Manager manager = new Manager();
////        manager.addNewBook(book);
//////        manager.modifyBook(book);
//
//        manager.getByTitle()
        User u = new User();
        System.out.println(u.getByPublicationDate("2020").toString());




    }


}
