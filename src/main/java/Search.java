import Mappers.BookMapper;
import Models.Book;
import Models.BookStore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Search {

    public static List<Book> search(String type, String input) throws SQLException {
        if(type.equalsIgnoreCase("Title"))
            return getByTitle(input);
        else if(type.equalsIgnoreCase("ISBN"))
            return getByISBN(input);
        else if(type.equalsIgnoreCase("Publisher"))
            return getByPublisherName(input);
        else if(type.equalsIgnoreCase("Author"))
            return getByAuthor(input);
        else if(type.equalsIgnoreCase("Category"))
            return getByCategory(input);
        else if(type.equalsIgnoreCase("Date"))
            return getByPublicationDate(input);
        return null;
    }

    private static List<Book> getByTitle(String title) throws SQLException {
        List<Book> books = new ArrayList<>();
        ResultSet resultSet = BookStore.databaseManager.executeQuery("CALL search_By_Title(" + "'" + title + "'" + ")");
        while (resultSet.next()) {
            books.add(BookMapper.toBook(resultSet));
        }
        return books;

    }

    private static List<Book> getByISBN(String ISBN) throws SQLException {
        List<Book> books = new ArrayList<>();
        ResultSet resultSet = BookStore.databaseManager.executeQuery("CALL search_By_ISBN(" + "'" + ISBN + "'" + ")");
        while (resultSet.next()) {
            books.add(BookMapper.toBook(resultSet));
        }
        return books;
    }

    private static List<Book> getByPublisherName(String publisherName) throws SQLException {
        List<Book> books = new ArrayList<>();
        ResultSet resultSet = BookStore.databaseManager.executeQuery("CALL search_By_Publisher(" + "'" + publisherName + "'" + ")");
        while (resultSet.next()) {
            books.add(BookMapper.toBook(resultSet));
        }
        return books;

    }

    private static List<Book> getByAuthor(String authorName) throws SQLException {
        List<Book> books = new ArrayList<>();
        ResultSet resultSet = BookStore.databaseManager.executeQuery("CALL search_By_Author(" + "'" + authorName + "'" + ")");
        while (resultSet.next()) {
            books.add(BookMapper.toBook(resultSet));
        }
        return books;

    }

    private static List<Book> getByCategory(String category) throws SQLException {
        List<Book> books = new ArrayList<>();
        ResultSet resultSet = BookStore.databaseManager.executeQuery("CALL search_By_Category(" + "'" + category + "'" + ")");
        while (resultSet.next()) {
            books.add(BookMapper.toBook(resultSet));
        }
        return books;

    }

    private static List<Book> getByPublicationDate(String publicationYear) throws SQLException {
        List<Book> books = new ArrayList<>();
        ResultSet resultSet = BookStore.databaseManager.executeQuery("CALL search_By_Publication_year(" + "'" + publicationYear + "'" + ")");
        while (resultSet.next()) {
            books.add(BookMapper.toBook(resultSet));
        }
        return books;
    }
}
