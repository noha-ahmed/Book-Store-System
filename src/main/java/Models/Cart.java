package Models;

import lombok.Data;

import java.util.List;

@Data
public class Cart {

    private List<Book> books;
    private Double totalPrice;

    public List<Book> viewCart()
    {
        return this.books;
    }

    public Book getBookById(Integer id)
    {
        return books.get(id);
    }

    public void removeBook(Integer id)
    {
        books.remove(id);
    }
}
