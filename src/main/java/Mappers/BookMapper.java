package Mappers;

import Models.Book;
import Models.Category;
import Models.Manager;
import Models.Publisher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookMapper {


    public static Book toBook(ResultSet resultSet) throws SQLException {
        String publisherName = resultSet.getNString("Publisher_Name");
        System.out.println(publisherName);
        ResultSet s = Manager.viewPublisherByName(publisherName);
        s.next();
        Publisher publisher = PublisherMapper.toPublisher(s);
        String ISBN = resultSet.getNString("ISBN");
        List<String> authors = new ArrayList<>();
        ResultSet authorNames = Manager.getBookAuthors(ISBN);
        while (authorNames.next()) {
            authors.add(authorNames.getNString("Author_Name"));
        }
        return Book.builder().ISBN(ISBN).
                category(Category.valueOf(resultSet.getNString("Category")))
                .price(resultSet.getDouble("Selling_Price"))
                .numberOfCopies(resultSet.getInt("Copies"))
                .publicationDate(resultSet.getNString("Publication_Year"))
                .threshold(resultSet.getInt("Threshold"))
                .title(resultSet.getNString("Title"))
                .publisher(publisher)
                .authors(authors)
                .build();


    }


}
