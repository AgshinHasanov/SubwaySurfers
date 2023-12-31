
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MovieDatabaseTest {

    private MovieDatabase movieDatabase;
    private Movie movie1;
    private Movie movie2;

    @Before
    public void setUp() {
        movieDatabase = new MovieDatabase("Tests/testDatabase.ser");
        movie1 = new Movie("Movie 1", "Director 1", 2020, 120, "path/to/photo1.jpg");
        movie2 = new Movie("Movie 2", "Director 2", 2019, 90, "path/to/photo2.jpg");
    }

    @Test
    public void testAddMovie() {
        movieDatabase.addMovie(movie1);
        assertTrue(movieDatabase.getMovies().contains(movie1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddExistingMovie() {
        movieDatabase.addMovie(movie1);
        movieDatabase.addMovie(movie1);
    }

    @Test
    public void testRemoveMovie() {
        movieDatabase.addMovie(movie1);
        movieDatabase.removeMovie(movie1);
        assertFalse(movieDatabase.getMovies().contains(movie1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNonExistingMovie() {
        movieDatabase.removeMovie(movie1);
    }

    @Test
    public void testGetMovie() {
        movieDatabase.addMovie(movie1);
        assertEquals(movie1, movieDatabase.getMovie("Movie 1"));
    }

    @Test
    public void testGetNonExistingMovie() {
        assertNull(movieDatabase.getMovie("Non-existing Movie"));
    }

    @Test
    public void testPrintAllMovies() {
        movieDatabase.addMovie(movie1);
        movieDatabase.addMovie(movie2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        movieDatabase.printAllMovies();

        assertEquals("Movie 1 2020 120\nMovie 2 2019 90\n", outContent.toString());

        System.setOut(System.out);
    }

    @Test
    public void testSaveAndLoadDatabase() {
        movieDatabase.addMovie(movie1);
        movieDatabase.addMovie(movie2);

        movieDatabase.saveDatabase();
        movieDatabase.loadDatabase();

        assertTrue(movieDatabase.getMovies().contains(movie1));
        assertTrue(movieDatabase.getMovies().contains(movie2));

        File file = new File("testDatabase.ser");
        file.delete();
    }

    @Test
    public void testCalculateTotalWatchTimeInWatchlist() {
        List<Movie> watchlist = new ArrayList<>();
        watchlist.add(movie1);
        watchlist.add(movie2);

        assertEquals(210, movieDatabase.calculateTotalWatchTimeInWatchlist(watchlist));
    }
}
