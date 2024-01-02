import org.junit.Test;

import static org.junit.Assert.*;

import java.io.*;
import java.util.List;

public class UserTest {

    @Test
    public void testUsernameValidation() {
        User user = new User();
        try {
            user.setUsername(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
        }

        try {
            user.setUsername("");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            user.setUsername("user@name");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
        try {
            user.setUsername("validUsername123");
            assertEquals("validUsername123", user.getUsername());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testPasswordValidation() {
        User user = new User();
        try {
            user.setPassword(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
        }

        try {
            user.setPassword("");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            user.setPassword("short");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            user.setPassword("invalid#password");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            user.setPassword("ValidPassword123");
            assertEquals("ValidPassword123", user.getPassword());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testWatchlistOperations() {
        User user = new User();
        Movie movie1 = new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo");
        Movie movie2 = new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo");

        user.addToWatchlist(movie1);
        user.addToWatchlist(movie2);
        assertEquals(2, user.getWatchlist().size());

        user.removeFromWatchlist(movie1);
        assertEquals(1, user.getWatchlist().size());
        assertTrue(user.getWatchlist().contains(movie1));
        assertTrue(user.getWatchlist().contains(movie2));

        List<Movie> newWatchlist = List.of(new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo"),
                                         new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo"));

        user.setWatchlist(newWatchlist);
        assertEquals(2, user.getWatchlist().size());
        assertTrue(user.getWatchlist().containsAll(newWatchlist));
    }

    @Test
    public void testDisplayWatchlist() {
        User user = new User();
        Movie movie1 = new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo");
        Movie movie2 = new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo");


        user.addToWatchlist(movie1);
        user.addToWatchlist(movie2);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        user.displayWatchlist();
        String expectedOutput = movie1.toString() + System.lineSeparator() + movie2.toString() + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());


        System.setOut(System.out);
    }


}
