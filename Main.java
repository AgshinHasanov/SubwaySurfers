import javax.swing.SwingUtilities;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Create and add movies to the movie database
        MovieDatabase movieDatabase = new MovieDatabase("movies.dat");
        movieDatabase.addMovie(new Movie("The Godfather", "Francis Ford Coppola", 1972, 175, "SubwaySurfers\\Database\\GodFather.jpg"));
        movieDatabase.addMovie(new Movie("Interstellar", "Christopher Nolan", 2014, 169, "Database\\InterStellar.jpg"));
        movieDatabase.addMovie(new Movie("Titanic", "James Cameron", 1997, 195, "Database\\Titanic.jpg"));
        movieDatabase.addMovie(new Movie("The Shawshank Redemption", "Frank Darabont", 1994, 142, "Database\\ShawshankRedemption.jpg"));
        movieDatabase.addMovie(new Movie("The Dark Knight", "Christopher Nolan", 2008, 152, "Database\\DarkKnight.jpg"));
        movieDatabase.addMovie(new Movie("Jurassic Park", "Steven Spielberg", 1993, 127, "Database\\JurassicPark.jpg"));
        movieDatabase.addMovie(new Movie("The Matrix", "The Wachowskis", 1999, 136, "Database\\TheMatrix.jpg"));
        movieDatabase.addMovie(new Movie("Avatar", "James Cameron", 2009, 162, "Database\\Avatar.jpg"));
        movieDatabase.addMovie(new Movie("The Lord of the Rings: The Fellowship of the Ring", "Peter Jackson", 2001, 178, "Database\\LOTRFellowship.jpg"));
        movieDatabase.addMovie(new Movie("Inception", "Christopher Nolan", 2010, 148, "Database\\Inception.jpg"));

        // Start the UserGUI with the movie database
        SwingUtilities.invokeLater(() -> new UserGUI(movieDatabase));
    }
}
