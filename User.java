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
    
    public User(String username, String password) throws IllegalArgumentException, NullPointerException {
        setUsername(username);
        setPassword(password);
    }
    public User(){
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        if(username == null) throw new NullPointerException("Username must contain only a-z/A-Z/0-9!");
        if(username.isBlank()) throw new IllegalArgumentException("Username must be written!");
        for(int i = 0; i < username.length(); i++){
            char c = username.charAt(i);
            if((c >= 'a' && c <= 'z') || (c >= 'A' && c<= 'Z') || (c >= '0' && c <= '9')) continue;
            else{
                throw new IllegalArgumentException("Username must contain only a-z/A-Z/0-9!");
            } 
        }
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        if(password == null) throw new NullPointerException("Password must contain only a-z/A-Z/0-9!");
        if(password.isBlank()) throw new IllegalArgumentException("Password must be written!");
        if((password.length()<=7)) throw new IllegalArgumentException("Password must be at least 8 characters!");
        for(int i = 0; i < password.length(); i++){
            char c = password.charAt(i);
            if((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c<= 'Z')) continue;
            else{
                throw new IllegalArgumentException("Password must contain only a-z/A-Z/0-9!");
            } 
        }
        this.password = password;
    }
    @Override
    public String toString() {
        return "Username = " + username + ", Password = " + password;
    }
    
    public static void register(String username, String password) {
        try {
            // Check if the username is already taken
            if (userExists(username)) {
                System.out.println("Username already exists. Please choose a different username.");
                return;
            }
            // Attempt to create a new User object
            User newUser = new User(username, password);
    
            // Load existing users, add the new user, and save the updated list
            List<User> existingUsers = loadFromDatabase();
            existingUsers.add(newUser);
            saveToDatabase(existingUsers);
    
            System.out.println("Registration successful.");
        } catch (IllegalArgumentException | NullPointerException e) {
            // Handle the exception (print a message or log it)
            System.out.println(e.getMessage());
        }
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

    private static void saveToDatabase(List<User> users) {
        // Save user information to the database file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("UserDatabase.txt"))) {
            for (User user : users) {
                oos.writeObject(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static List<User> loadFromDatabase() {
        // Load user information from the database file
        List<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("UserDatabase.txt"))) {
            while (true) {
                try {
                    User user = (User) ois.readObject();
                    users.add(user);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {

        }
        return users;
    }

    public boolean checkLogin(String username, String password) {
        List<User> users = User.loadFromDatabase();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Username and password match
            }
        }

        return false; // No matching username and password found
    }
}