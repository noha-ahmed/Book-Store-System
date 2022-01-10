package Models;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Cart {

    private Map<Book,Integer> books = new HashMap<>();
    private Double totalPrice = 0.0;

    public Map<Book,Integer> viewCart()
    {
        return this.books;
    }

    public void removeBook(Book book)
    {
        books.put(book,books.getOrDefault(book,0)-1);
        if(books.get(book) == 0) books.remove(book);
        this.totalPrice -= book.getPrice();
    }

    public void addBook(Book book) {
        books.put(book,books.getOrDefault(book,0)+1);
        this.totalPrice += book.getPrice();
    }


}
