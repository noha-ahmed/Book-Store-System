import Models.Book;
import Models.BookStore;
import Models.ErrorCodes;
import Models.Search;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class Test1 {

    BookStore bookStore;
    @Before
    public void setUp()
    {
        bookStore = new BookStore();
    }
    @org.junit.Test
    public void TestSignIn() throws SQLException {
        Assert.assertEquals(ErrorCodes.CORRECT_USER.getCode(),bookStore.signIn("asmaa99","password"));
        Assert.assertTrue(bookStore.getUser().isPrivilege());
        List<Book> books = Search.search("Title","The Elegant Universe");
        bookStore.getUser().addToCart(books.get(0));
        Assert.assertEquals(bookStore.getUser().viewCart().size(),1);

    }

    @Test
    public void getAllBooks() throws SQLException {
        Assert.assertEquals(15,bookStore.getAllBooks().size());
    }
}
