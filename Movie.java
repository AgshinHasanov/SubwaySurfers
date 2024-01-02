import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a movie with attributes such as title, director, year of release, running time, and photo directory.
 * Provides methods for sorting a list of movies based on different criteria.
 */
public class Movie implements Serializable {

    // Fields

    private String title;
    private String director;
    private int year;
    private int runningTime;
    private String photoDirectory;

    // Constructors

    /**
     * Constructs a `Movie` object with the specified parameters.
     *
     * @param title           The title of the movie.
     * @param director        The director of the movie.
     * @param year            The year of release (between 1895 and 2023).
     * @param runningTime     The running time of the movie (non-negative).
     * @param photoDirectory  The directory path for the movie's photo.
     * @throws IllegalArgumentException If the year or running time is out of valid range.
     */
    public Movie(String title, String director, int year, int runningTime, String photoDirectory) {
        validateYear(year);
        validateRunningTime(runningTime);
        this.title = title;
        this.director = director;
        this.year = year;
        this.runningTime = runningTime;
        this.photoDirectory = photoDirectory;
    }

    /**
     * Constructs a default `Movie` object with predefined values.
     */
    public Movie() {
        this.title = "The Godfather";
        this.year = 1972;
        this.runningTime = 175;
    }

    // Getters and Setters

    /**
     * Gets the photo directory of the movie.
     *
     * @return The photo directory.
     */
    public String getPhotoDirectory() {
        return photoDirectory;
    }

    /**
     * Sets the photo directory of the movie.
     *
     * @param photoDirectory The directory path for the movie's photo.
     */
    public void setPhotoDirectory(String photoDirectory) {
        this.photoDirectory = photoDirectory;
    }

    /**
     * Gets the running time of the movie.
     *
     * @return The running time in minutes.
     */
    public int getRunningTime() {
        return runningTime;
    }

    /**
     * Gets the title of the movie.
     *
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the director of the movie.
     *
     * @return The director.
     */
    public String getDirector() {
        return director;
    }

    /**
     * Gets the year of release of the movie.
     *
     * @return The year of release.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the running time of the movie.
     *
     * @param runningTime The running time in minutes.
     * @throws IllegalArgumentException If the running time is negative.
     */
    public void setRunningTime(int runningTime) {
        validateRunningTime(runningTime);
        this.runningTime = runningTime;
    }

    /**
     * Sets the title of the movie.
     *
     * @param title The title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the year of release of the movie.
     *
     * @param year The year of release.
     * @throws IllegalArgumentException If the year is out of valid range.
     */
    public void setYear(int year) {
        validateYear(year);
        this.year = year;
    }

    /**
     * Sets the director of the movie.
     *
     * @param director The director.
     */
    public void setDirector(String director) {
        this.director = director;
    }

    // Methods

    /**
     * Returns a string representation of the movie.
     *
     * @return A string containing the title, year, and running time of the movie.
     */
    @Override
    public String toString() {
        return title + " " + year + " " + runningTime;
    }

    /**
     * Checks if two movies are equal based on title, director, and year.
     *
     * @param obj The object to compare.
     * @return True if the movies are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Movie otherMovie = (Movie) obj;
        return getTitle().equals(otherMovie.getTitle())
                && getDirector().equals(otherMovie.getDirector())
                && getYear() == otherMovie.getYear();
    }

    /**
     * Sorts a list of movies alphabetically by title.
     *
     * @param movies The list of movies to be sorted.
     * @return A sorted list of movies by title.
     */
    public static List<Movie> sortMoviesByTitle(List<Movie> movies) {
        return movies.stream()
                .sorted((movie1, movie2) -> movie1.getTitle().compareTo(movie2.getTitle()))
                .collect(Collectors.toList());
    }

    /**
     * Sorts a list of movies alphabetically by director.
     *
     * @param movies The list of movies to be sorted.
     * @return A sorted list of movies by director.
     */
    public static List<Movie> sortMoviesByDirector(List<Movie> movies) {
        return movies.stream()
                .sorted((movie1, movie2) -> movie1.getDirector().compareTo(movie2.getDirector()))
                .collect(Collectors.toList());
    }

    /**
     * Sorts a list of movies by release year.
     *
     * @param movies The list of movies to be sorted.
     * @return A sorted list of movies by year.
     */
    public static List<Movie> sortMoviesByYear(List<Movie> movies) {
        return movies.stream()
                .sorted((movie1, movie2) -> Integer.compare(movie1.getYear(), movie2.getYear()))
                .collect(Collectors.toList());
    }

    /**
     * Sorts a list of movies by running time.
     *
     * @param movies The list of movies to be sorted.
     * @return A sorted list of movies by running time.
     */
    public static List<Movie> sortMoviesByRunningTime(List<Movie> movies) {
        return movies.stream()
                .sorted((movie1, movie2) -> Integer.compare(movie1.getRunningTime(), movie2.getRunningTime()))
                .collect(Collectors.toList());
    }


    /**
     * Validates whether the given year falls within the acceptable range (1895 to 2023, inclusive).
     *
     * @param year The year to be validated.
     * @throws IllegalArgumentException If the year is not within the acceptable range.
     */
    private void validateYear(int year) {
        if (year < 1895 || year > 2023) {
            throw new IllegalArgumentException("Year should be between 1895 and 2023");
        }
    }


    /**
     * Validates whether the given running time is a non-negative number.
     *
     * @param runningTime The running time of a movie (in minutes) to be validated.
     * @throws IllegalArgumentException If the running time is a negative number.
     */
    private void validateRunningTime(int runningTime) {
        if (runningTime < 0) {
            throw new IllegalArgumentException("Running time cannot be a negative number");
        }
    }
}
