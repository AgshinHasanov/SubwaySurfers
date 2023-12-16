import javax.swing.SwingUtilities;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Create and add movies to the movie database
        MovieDatabase movieDatabase = new MovieDatabase("movies.dat");
        movieDatabase.addMovie(new Movie("The Godfather", "Francis Ford Coppola", 1972, 175));
        movieDatabase.addMovie(new Movie("Interstellar", "Christopher Nolan", 2014, 169));

        // Start the UserGUI with the movie database
        SwingUtilities.invokeLater(() -> new UserGUI(movieDatabase));
    }
}
