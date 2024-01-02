import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The `User` class represents a user in the movie application, with a username, password, and watchlist.
 * It provides methods for user registration, login validation, managing the watchlist, and interacting with the user database.
 */
public class User implements Serializable {

    // Fields

    private String username;
    private String password;
    private List<Movie> watchlist;

    // Constructors

    /**
     * Constructs a `User` with the specified username and password.
     *
     * @param username The username for the user.
     * @param password The password for the user.
     */
    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        watchlist = new ArrayList<>();
    }

    /**
     * Constructs a default `User` with an empty watchlist.
     */
    public User() {
        this.watchlist = new ArrayList<>();
    }

    // Getters and Setters

    /**
     * Gets the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the user.
     *
     * @param username The username to set.
     * @throws NullPointerException     If the username is null.
     * @throws IllegalArgumentException If the username is blank or contains invalid characters.
     */
    public void setUsername(String username) {
        if (username == null) throw new NullPointerException("Username must contain only a-z/A-Z/0-9!");
        if (username.isBlank()) throw new IllegalArgumentException("Username must be written!");
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) continue;
            else {
                throw new IllegalArgumentException("Username must contain only a-z/A-Z/0-9!");
            }
        }
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user.
     *
     * @param password The password to set.
     * @throws NullPointerException     If the password is null.
     * @throws IllegalArgumentException If the password is blank, less than 8 characters, or contains invalid characters.
     */
    public void setPassword(String password) {
        if (password == null) throw new NullPointerException("Password must contain only a-z/A-Z/0-9!");
        if (password.isBlank()) throw new IllegalArgumentException("Password must be written!");
        if ((password.length() <= 7)) throw new IllegalArgumentException("Password must be at least 8 characters!");
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) continue;
            else {
                throw new IllegalArgumentException("Password must contain only a-z/A-Z/0-9!");
            }
        }
        this.password = password;
    }

    // Other Methods

    /**
     * Returns a string representation of the user.
     *
     * @return A string containing the username and password of the user.
     */
    @Override
    public String toString() {
        return "Username = " + username + ", Password = " + password;
    }

    /**
     * Registers a new user with the specified username and password.
     *
     * @param username The username for the new user.
     * @param password The password for the new user.
     * @throws Exception If there is an issue during user registration.
     */
    public static void register(String username, String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        List<User> existingUsers = loadFromDatabase();

        if (userExists(existingUsers, username)) {
            throw new IllegalArgumentException("Username already exists. Please choose a different username.");
        }
        existingUsers.add(new User(username, password));
        saveToDatabase(existingUsers);

        //System.out.println("Registration successful.");
    }

    /**
     * Checks if a user with the given username and password exists in the user database.
     *
     * @param username The username to check.
     * @param password The password to check.
     * @return True if the user exists, false otherwise.
     */
    public boolean checkLogin(String username, String password) {
        List<User> users = loadFromDatabase();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Username and password match
            }
        }

        return false; // No matching username and password found
    }

    /**
     * Adds a movie to the user's watchlist.
     *
     * @param movie The movie to add to the watchlist.
     */
    public void addToWatchlist(Movie movie) {
        watchlist.add(movie);
    }

    /**
     * Removes a movie from the user's watchlist.
     *
     * @param movie The movie to remove from the watchlist.
     */
    public void removeFromWatchlist(Movie movie) {
        watchlist.remove(movie);
    }

    /**
     * Sets the watchlist for the user.
     *
     * @param watchlist The list of movies to set as the user's watchlist.
     */
    public void setWatchlist(List<Movie> watchlist) {
        this.watchlist = watchlist;
    }

    /**
     * Displays the user's watchlist.
     */
    public void displayWatchlist() {
        for (Movie movie : watchlist) {
            System.out.println(movie.toString());
        }
    }

    /**
     * Gets the watchlist of the user.
     *
     * @return The user's watchlist.
     */
    public List<Movie> getWatchlist() {
        if (watchlist == null) {
            watchlist = new ArrayList<>();
        }
        return watchlist;
    }

    /**
     * Checks if a username already exists in the list of users.
     *
     * @param existingUsers List of existing users to search through.
     * @param username      The username to check for existence.
     * @return {@code true} if the username exists in the list, {@code false} otherwise.
     */
    private static boolean userExists(List<User> existingUsers, String username) {
        for (User user : existingUsers) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }


    
    /**
     * Saves a list of users to a database file using serialization.
     *
     * @param users The list of users to be saved in the database file.
     */
    private static void saveToDatabase(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Database/UserDatabase.txt"))) {
            for (User user : users) {
                oos.writeObject(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a list of users from a database file using deserialization.
     *
     * @return The list of users loaded from the database file.
     */
    private static List<User> loadFromDatabase() {
        List<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Database/UserDatabase.txt"))) {
            while (true) {
                try {
                    User user = (User) ois.readObject();
                    users.add(user);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }
}
