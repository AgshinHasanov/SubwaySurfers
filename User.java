import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String password;
    private List<Movie> watchlist;
    

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        watchlist = new ArrayList<>();
    }

    public User() {
        this.watchlist = new ArrayList<>();
    }
    

    public String getUsername() {
        return username;
    }

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

    public String getPassword() {
        return password;
    }

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

    @Override
    public String toString() {
        return "Username = " + username + ", Password = " + password;
    }

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

    private static boolean userExists(List<User> existingUsers, String username) {
        for (User user : existingUsers) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private static void saveToDatabase(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Database/UserDatabase.txt"))) {
            for (User user : users) {
                oos.writeObject(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> loadFromDatabase() {
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

    public boolean checkLogin(String username, String password) {
        List<User> users = loadFromDatabase();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Username and password match
            }
        }

        return false; // No matching username and password found
    }
    public void addToWatchlist(Movie movie) {
        watchlist.add(movie);
    }
    public void removeFromWatchlist(Movie movie) {
        watchlist.remove(movie);
    }

    public List<Movie> getWatchlist() {
        return watchlist;
    }

    public void displayWatchlist() {
        // Implement how to display the watchlist (e.g., printing or GUI)
        for (Movie movie : watchlist) {
            System.out.println(movie.toString());
        }
    }
}
