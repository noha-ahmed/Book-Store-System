package Models;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class Book {

    private int ISBN;
    private String title;
    private Double price;
    private int threshold;
    private int numberOfCopies;
    private Publisher publisher;
    private Category category;
    private List<Author> authors;
    private Date publicationDate;

}
