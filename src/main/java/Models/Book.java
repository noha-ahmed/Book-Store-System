package Models;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class Book {

    private String ISBN;
    private String title;
    private Double price;
    private int threshold;
    private int numberOfCopies;
    private Publisher publisher;
    private Category category;
    private List<String> authors;
    private String publicationDate;

}
