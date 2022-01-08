package Models;


import java.sql.SQLException;
import java.util.List;

public class Manager extends User {

    public boolean promoteUser(int UserId) {
        return false;
    }

    public void addNewBook(Book book) throws SQLException {

        BookStore.databaseManager.executeQuery("INSERT INTO BOOK VALUES('" + book.getISBN()
                + "','" + book.getTitle() + "','" + book.getPublicationDate() + "','" + book.getPrice() + "','" + book.getCategory() +
                "','" + book.getNumberOfCopies() + "','" + book.getThreshold() + "','" + book.getPublisher().getName() +
                "')");
    }


    public void modifyBook(Book book) throws SQLException {
        BookStore.databaseManager.executeQuery("UPDATE BOOK SET title = '" + book.getTitle() + "',Publication_Year = '" + book.getPublicationDate() + "',Selling_Price = '" + book.getPrice() + "',Category = '" + book.getCategory() +
                "',Quantity = '" + book.getNumberOfCopies() + "',Threshold = '" + book.getThreshold() + "',Publisher_Name = '" + book.getPublisher().getName() + "' WHERE ISBN = " + book.getISBN()
        );
    }


    /**
     * place orders and confirm orders
     */

    public int getPrevMonthSales() {
        return 0;
    }

    /**
     * The top 5 customers who purchase the most purchase amount in descending order for the last three months
     *
     * @return
     */
    public List<User> getTop5PrevMonth() {
        return null;
    }

    /**
     * The top 10 selling books for the last three months
     *
     * @return
     */
    public List<Book> getBestSellerBooks() {
        return null;
    }


}
