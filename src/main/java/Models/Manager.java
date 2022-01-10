package Models;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Manager extends User {


    Manager(String username, String password, String firstName, String lastName, String email, String phoneNumber, String shippingAddress, boolean privilege, Cart shoppingCart) {
        super(username, password, firstName, lastName, email, phoneNumber, shippingAddress, privilege, shoppingCart);
    }

    public void promoteUser(String userName) throws SQLException {
        BookStore.databaseManager.executeQuery("UPDATE USER SET privilege = '1' WHERE UserName = '" + userName + "'");
    }

    public void addNewBook(Book book) throws SQLException {

        BookStore.databaseManager.executeQuery("CALL add_Book(' " + book.getISBN()
                + "','" + book.getTitle() + "','" + book.getPublicationDate() + "','" + book.getPrice() + "','" + book.getCategory() +
                "','" + book.getNumberOfCopies() + "','" + book.getThreshold() + "','" + book.getPublisher().getName() +
                "')");


    }

    @Override
    public boolean checkOut(String creditCardNumber, Date expiryDate) throws SQLException {
        return super.checkOut(creditCardNumber, expiryDate);
    }

    @Override
    public void addToCart(Book book) {
        super.addToCart(book);
    }

    @Override
    public Map<Book, Integer> viewCart() {
        return super.viewCart();
    }

    @Override
    public void removeFromCart(Book book) {

        super.removeFromCart(book);
    }

    @Override
    public double getTotalCartPrice() {
        return super.getTotalCartPrice();
    }

    public boolean modifyBook(Book book) throws SQLException {
        try {
            BookStore.databaseManager.executeQuery("UPDATE BOOK SET Title = '" + book.getTitle() + "',Publication_Year = '" + book.getPublicationDate() + "',Selling_Price = '" + book.getPrice() + "',Category = '" + book.getCategory() +
                    "',Copies = '" + book.getNumberOfCopies() + "',Threshold = '" + book.getThreshold() + "',Publisher_Name = '" + book.getPublisher().getName() + "' WHERE ISBN = '" + book.getISBN() + "'"
            );

        } catch (SQLException e) {
            if (e.getMessage().equals("Book out of stock"))
                return false;
        }
        return true;
    }

    public void confirmOrder(int orderID) throws SQLException {
        BookStore.databaseManager.executeQuery("CALL confirm_Book_Store_Order('" + orderID + "')");
    }

    public static ResultSet viewPublisherByName(String name) throws SQLException {
        return BookStore.databaseManager.executeQuery("CALL get_Book_Publisher('" + name + "')");
    }

    public static ResultSet getBookAuthors(String ISBN) throws SQLException {
        return BookStore.databaseManager.executeQuery("CALL get_Book_Authors('" + ISBN + "')");
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
    public void top5PrevMonthCustomersReport() throws JRException {
        JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/Top5Customer.jasper", null, BookStore.databaseManager.getConnection());
        JasperViewer jasperViewer = new JasperViewer(jasperPrint);
        jasperViewer.setVisible(true);
    }

    /**
     * The top 10 selling books for the last three months
     *
     * @return
     */
    public void bestSellerBooksReport() throws JRException {

        JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/Top10Book.jasper", null, BookStore.databaseManager.getConnection());
        JasperViewer jasperViewer = new JasperViewer(jasperPrint);
        jasperViewer.setVisible(true);

    }

    public void totalBookSalesLastMonthReport() throws JRException {

        JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/TotalSalesBooks.jasper", null, BookStore.databaseManager.getConnection());
        JasperViewer jasperViewer = new JasperViewer(jasperPrint);
        jasperViewer.setVisible(true);

    }

    public void totalSalesValueLastMonthReport() throws JRException {

        JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/TotalSales.jasper", null, BookStore.databaseManager.getConnection());
        JasperViewer jasperViewer = new JasperViewer(jasperPrint);
        jasperViewer.setVisible(true);

    }

}
