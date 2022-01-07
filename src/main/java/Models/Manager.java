package Models;


import java.util.List;

public class Manager extends User {

    public boolean promoteUser(int UserId)
    {
        return false;
    }
    public void addNewBook(Book book){}

    public boolean modifyBook(Book book){return false;}

    /**
     *  place orders and confirm orders
     */

    public int getPrevMonthSales(){return 0;}

    /**
     * The top 5 customers who purchase the most purchase amount in descending order for the last three months
     * @return
     */
    public List<User> getTop5PrevMonth(){return null;}

    /**
     * The top 10 selling books for the last three months
     * @return
     */
    public List<Book> getBestSellerBooks(){return null;}


}
