import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The `MovieDatabase` class manages a collection of movies, providing methods to add, remove, and retrieve movies.
 * It also supports saving and loading the movie database to and from a file.
 * Additionally, it includes a method to calculate the total watch time of a given watchlist.
 */
public class MovieDatabase {

    // Fields

    private List<Movie> movies;
    private String databaseFileName;

    // Constructors

    /**
     * Constructs a `MovieDatabase` with the specified database file name.
     *
     * @param databaseFileName The name of the file to save and load the movie database.
     */
    public MovieDatabase(String databaseFileName) {
        this.movies = new ArrayList<>();
        this.databaseFileName = databaseFileName;
    }

    // Methods

    /**
     * Adds a movie to the database.
     *
     * @param movie The movie to be added.
     * @throws IllegalArgumentException If the movie already exists in the database.
     */
    public void addMovie(Movie movie) {
        if (!movies.contains(movie)) {
            movies.add(movie);
            saveDatabase();
            System.out.println("Movie added to the database: " + movie.getTitle());
        } else {
            throw new IllegalArgumentException("Movie already exists in the database.");
        }
    }

    /**
     * Removes a movie from the database.
     *
     * @param movie The movie to be removed.
     * @throws IllegalArgumentException If the movie is not found in the database.
     */
    public void removeMovie(Movie movie) {
        if (movies.remove(movie)) {
            saveDatabase();
            System.out.println("Movie removed from the database: " + movie.getTitle());
        } else {
            throw new IllegalArgumentException("Movie not found in the database.");
        }
    }

    /**
     * Retrieves a movie from the database based on its title.
     *
     * @param title The title of the movie to retrieve.
     * @return The movie with the specified title, or null if not found.
     */
    public Movie getMovie(String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Prints all movies in the database.
     */
    public void printAllMovies() {
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    /**
     * Saves the current movie database to a file.
     */
    void saveDatabase() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(databaseFileName))) {
            oos.writeObject(movies);
        } catch (IOException e) {
            System.err.println("Error saving the database: " + e.getMessage());
        }
    }

    /**
     * Loads the movie database from a file.
     */
    @SuppressWarnings("unchecked")
    public void loadDatabase() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(databaseFileName))) {
            movies = (List<Movie>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading the database: " + e.getMessage());
        }
    }

    /**
     * Retrieves the list of movies in the database.
     *
     * @return The list of movies in the database.
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * Calculates the total watch time of a given watchlist.
     *
     * @param watchlist The list of movies in the watchlist.
     * @return The total watch time in minutes.
     */
    public int calculateTotalWatchTimeInWatchlist(List<Movie> watchlist) {
        int totalWatchTime = 0;

        for (Movie movie : watchlist) {
            totalWatchTime += movie.getRunningTime();
        }

        return totalWatchTime;
    }
}
