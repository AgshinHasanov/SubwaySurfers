import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDatabase {
    private List<Movie> movies;
    private String databaseFileName;

    public MovieDatabase(String databaseFileName) {
        this.movies = new ArrayList<>();
        this.databaseFileName = databaseFileName;
    }

    public void addMovie(Movie movie) {
        if (!movies.contains(movie)) {
            movies.add(movie);
            saveDatabase();
            System.out.println("Movie added to the database: " + movie.getTitle());
        } else {
            System.out.println("Movie already exists in the database.");
        }
    }

    public void removeMovie(Movie movie) {
        if (movies.remove(movie)) {
            saveDatabase();
            System.out.println("Movie removed from the database: " + movie.getTitle());
        } else {
            System.out.println("Movie not found in the database.");
        }
    }

    public Movie getMovie(String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }

    public void printAllMovies() {
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    private void saveDatabase() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(databaseFileName))) {
            oos.writeObject(movies);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDatabase() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(databaseFileName))) {
            movies = (List<Movie>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
