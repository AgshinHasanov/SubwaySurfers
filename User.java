import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String password;
    
    public User(String username, String password) {
        try {
            setUsername(username);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Invalid input detected in Username. Please provide valid credentials.");
        }
        try {
            setPassword(password);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Invalid input detected in Password. Please provide valid credentials.");
        }
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        if(username == null) throw new NullPointerException();
        if(username.isBlank()) throw new IllegalArgumentException();
        for(int i = 0; i < username.length(); i++){
            char c = username.charAt(i);
            if((c >= 'a' && c <= 'z') || (c >= 'A' && c<= 'Z') || (c >= '0' && c <= '9')) continue;
            else{
                throw new IllegalArgumentException();
            } 
        }
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        if(password == null) throw new NullPointerException();
        if(password.isBlank()) throw new IllegalArgumentException();
        for(int i = 0; i < password.length(); i++){
            char c = password.charAt(i);
            if((c >= 'a' && c <= 'z') || (c >= 'A' && c<= 'Z') || (c >= '0' && c <= '9')) continue;
            else{
                throw new IllegalArgumentException();
            } 
        }
        this.password = password;
    }
    @Override
    public String toString() {
        return "Username = " + username + ", Password = " + password;
    }
    
    public static void register(String username, String password) {
        // Check if the username is already taken
        if (userExists(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }
        User newUser = new User(username, password);
        // Save the user information to the database file
        saveToDatabase(newUser);
        System.out.println("Registration successful.");
    }

    private static boolean userExists(String username) {
        // Check if the username exists in the database
        List<User> users = loadFromDatabase();
        for (User user : users) {
            if (user.username.equals(username)) {
                return true;
            }
        }
        return false;
    }

    private static void saveToDatabase(User user) {
        // Save user information to the database file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("UserDatabase/user_database.txt", true))) {
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<User> loadFromDatabase() {
        // Load user information from the database file
        List<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("UserDatabase/user_database.txt"))) {
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


