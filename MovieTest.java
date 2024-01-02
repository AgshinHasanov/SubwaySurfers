import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MovieTest {

    @Test
    public void testValidMovieConstructor() {
        Movie movie = new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo");
        assertEquals("Inception", movie.getTitle());
        assertEquals("Christopher Nolan", movie.getDirector());
        assertEquals(2010, movie.getYear());
        assertEquals(148, movie.getRunningTime());
        assertEquals("/path/to/photo", movie.getPhotoDirectory());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidYearConstructor() {
        new Movie("Inception", "Christopher Nolan", 1890, 148, "/path/to/photo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRunningTimeConstructor() {
        new Movie("The Shawshank Redemption", "Frank Darabont", 1994, -142, "/path/to/photo");
    }

    @Test
    public void testDefaultConstructor() {
        Movie movie = new Movie();
        assertEquals("The Godfather", movie.getTitle());
        assertEquals(1972, movie.getYear());
        assertEquals(175, movie.getRunningTime());
    }

    @Test
    public void testSetters() {
        Movie movie = new Movie();
        movie.setTitle("Titanic");
        movie.setDirector("James Cameron");
        movie.setYear(1997);
        movie.setRunningTime(195);
        movie.setPhotoDirectory("/path/to/titanic/photo");

        assertEquals("Titanic", movie.getTitle());
        assertEquals("James Cameron", movie.getDirector());
        assertEquals(1997, movie.getYear());
        assertEquals(195, movie.getRunningTime());
        assertEquals("/path/to/titanic/photo", movie.getPhotoDirectory());
    }

  
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSetRunningTime() {
        Movie movie = new Movie("The Dark Knight", "Christopher Nolan", 2008, 152, "/path/to/photo");
        movie.setRunningTime(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSetYear() {
        Movie movie = new Movie("Pulp Fiction", "Quentin Tarantino", 1994, 154, "/path/to/photo");
        movie.setYear(2025);
    }

    @Test
    public void testToString() {
        Movie movie = new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo");
        assertEquals("Inception 2010 148", movie.toString());
    }

    @Test
    public void testEquals() {
        Movie movie1 = new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo");
        Movie movie2 = new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo");
        Movie movie3 = new Movie("Interstellar", "Christopher Nolan", 2014, 169, "/path/to/photo");

        assertTrue(movie1.equals(movie2));
        assertFalse(movie1.equals(movie3));
    }

    @Test
    public void testSortMoviesByTitle() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo"));
        movies.add(new Movie("Titanic", "James Cameron", 1997, 195, "/path/to/photo"));
        movies.add(new Movie("The Godfather", "Francis Ford Coppola", 1972, 175, "/path/to/photo"));

        List<Movie> sortedMovies = Movie.sortMoviesByTitle(movies);

        assertEquals("Inception", sortedMovies.get(0).getTitle());
        assertEquals("The Godfather", sortedMovies.get(1).getTitle());
        assertEquals("Titanic", sortedMovies.get(2).getTitle());
    }

    @Test
    public void testSortMoviesByDirector() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo"));
        movies.add(new Movie("Titanic", "James Cameron", 1997, 195, "/path/to/photo"));
        movies.add(new Movie("The Godfather", "Francis Ford Coppola", 1972, 175, "/path/to/photo"));

        List<Movie> sortedMovies = Movie.sortMoviesByDirector(movies);

        assertEquals("Christopher Nolan", sortedMovies.get(0).getDirector());
        assertEquals("Francis Ford Coppola", sortedMovies.get(1).getDirector());
        assertEquals("James Cameron", sortedMovies.get(2).getDirector());
    }

    @Test
    public void testSortMoviesByYear() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo"));
        movies.add(new Movie("Titanic", "James Cameron", 1997, 195, "/path/to/photo"));
        movies.add(new Movie("The Godfather", "Francis Ford Coppola", 1972, 175, "/path/to/photo"));

        List<Movie> sortedMovies = Movie.sortMoviesByYear(movies);

        assertEquals(1972, sortedMovies.get(0).getYear());
        assertEquals(1997, sortedMovies.get(1).getYear());
        assertEquals(2010, sortedMovies.get(2).getYear());
    }

    @Test
    public void testSortMoviesByRunningTime() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Inception", "Christopher Nolan", 2010, 148, "/path/to/photo"));
        movies.add(new Movie("Titanic", "James Cameron", 1997, 195, "/path/to/photo"));
        movies.add(new Movie("The Godfather", "Francis Ford Coppola", 1972, 175, "/path/to/photo"));

        List<Movie> sortedMovies = Movie.sortMoviesByRunningTime(movies);

        assertEquals(148, sortedMovies.get(0).getRunningTime());
        assertEquals(175, sortedMovies.get(1).getRunningTime());
        assertEquals(195, sortedMovies.get(2).getRunningTime());
    }
}
