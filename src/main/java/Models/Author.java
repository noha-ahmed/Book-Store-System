package Models;

import lombok.Data;

import java.util.List;

@Data
public class Author {

    private String name;
    private List<Book> bookList;
    private String address;

}
