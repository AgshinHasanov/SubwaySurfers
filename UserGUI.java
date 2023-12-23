import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserGUI extends JFrame implements Serializable{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private User user;
    private MovieDatabase movieDatabase;
    private List<Movie> selectedMovies;
    private static final String USERS_DATA_FILE = "Database/users.ser";
    private List<User> users;

    public UserGUI(MovieDatabase movieDatabase) {
        setTitle("SubwaySurfers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        loginPanel.setPreferredSize(new Dimension(300, 150)); // Set a fixed size for the login panel

        JLabel usernameLabel = new JLabel("Username:");
        loginPanel.add(usernameLabel);

        usernameField = new JTextField();
        loginPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        JButton registerButton = new JButton("Register");
        loginPanel.add(registerButton);

        JButton loginButton = new JButton("Login");
        loginPanel.add(loginButton);

        panel.add(loginPanel, BorderLayout.NORTH);

        this.movieDatabase = movieDatabase;
        user = new User();
        users = loadUsersData();
        selectedMovies = new ArrayList<>();

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
    
                if (findUserByUsername(username) == null) {
                    User newUser = new User(username, password);
                    users.add(newUser);
                    saveUsersData();
                    JOptionPane.showMessageDialog(null, "Registration successful.");
                } else {
                    JOptionPane.showMessageDialog(null, "Username already exists", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User loggedInUser = findUserByUsername(username);
    
                if (loggedInUser != null && loggedInUser.getPassword().equals(password)) {
                    user = loggedInUser;
                    loadWatchlistData();
                    showMovieInfoFrame();
                    JOptionPane.showMessageDialog(null, "Login successful.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel);
        pack(); // Adjust frame size based on components
        setResizable(false); // Disable frame resizing
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
        loadWatchlistData();
    }
    @SuppressWarnings("unchecked")
    private List<User> loadUsersData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_DATA_FILE))) {
            return (List<User>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    private void saveUsersData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private User findUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    private void loadWatchlistData() {
        if (user != null) {
            String userWatchlistFolder = "UserWatchlists/";
            String userWatchlistFileName = user.getUsername() + "_watchlist.ser";
            String userWatchlistFilePath = userWatchlistFolder + userWatchlistFileName;
    
            try {
                // Create the folder if it doesn't exist
                new File(userWatchlistFolder).mkdirs();
    
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userWatchlistFilePath));
                List<Movie> loadedWatchlist = (List<Movie>) ois.readObject();
                user.setWatchlist(loadedWatchlist);
                ois.close();
            } catch (FileNotFoundException e) {
                System.out.println("Watchlist file not found. Creating a new watchlist.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void saveWatchlistData() {
        if (user != null) {
            String userWatchlistFolder = "UserWatchlists/";
            String userWatchlistFileName = user.getUsername() + "_watchlist.ser";
            String userWatchlistFilePath = userWatchlistFolder + userWatchlistFileName;
    
            try {
                // Create the folder if it doesn't exist
                new File(userWatchlistFolder).mkdirs();
    
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userWatchlistFilePath));
                oos.writeObject(user.getWatchlist());
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

    private void showMovieInfoFrame() {
        JFrame movieInfoFrame = new JFrame();
        movieInfoFrame.setTitle("Movie Information");
        movieInfoFrame.setSize(800, 600);
        movieInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        movieInfoFrame.setLocationRelativeTo(null);

        JPanel moviePanel = new JPanel(new GridLayout(0, 2, 10, 10));

        List<Movie> movies = movieDatabase.getMovies();
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);

            JPanel movieContainer = new JPanel(new BorderLayout());
            movieContainer.setBorder(BorderFactory.createTitledBorder("Movie " + (i + 1)));

            if (movie.getPhotoDirectory() != null && !movie.getPhotoDirectory().isEmpty()) {
                ImageIcon originalIcon = new ImageIcon(movie.getPhotoDirectory());
                Image originalImage = originalIcon.getImage();

                int maxWidth = 150;
                int maxHeight = 200;

                int scaledWidth = Math.min(originalImage.getWidth(null), maxWidth);
                int scaledHeight = (int) (((double) scaledWidth / originalImage.getWidth(null)) * originalImage.getHeight(null));

                ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
                JLabel photoLabel = new JLabel(scaledIcon);

                movieContainer.add(photoLabel, BorderLayout.WEST);
            }

            JPanel infoPanel = new JPanel(new GridLayout(4, 1));
            JLabel titleLabel = new JLabel("Title: " + movie.getTitle());
            JLabel directorLabel = new JLabel("Director: " + movie.getDirector());
            JLabel yearLabel = new JLabel("Year: " + movie.getYear());
            JLabel runningTimeLabel = new JLabel("Running Time: " + movie.getRunningTime() + " minutes");

            infoPanel.add(titleLabel);
            infoPanel.add(directorLabel);
            infoPanel.add(yearLabel);
            infoPanel.add(runningTimeLabel);

            movieContainer.add(infoPanel, BorderLayout.CENTER);

            // Add "Select" button for each movie
            JButton selectButton = new JButton("Select");
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add the selected movie to the list
                    if (!selectedMovies.contains(movie)) {
                        selectedMovies.add(movie);
                    }
                }
            });
            movieContainer.add(selectButton, BorderLayout.EAST);

            moviePanel.add(movieContainer);
        }

        JScrollPane scrollPane = new JScrollPane(moviePanel);
        movieInfoFrame.add(scrollPane);

        int scrollSpeed = 20;
        MouseWheelListener wheelListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                int unitsToScroll = e.getUnitsToScroll() * scrollSpeed;
                verticalScrollBar.setValue(verticalScrollBar.getValue() + unitsToScroll);
            }
        };

        scrollPane.addMouseWheelListener(wheelListener);
        scrollPane.getVerticalScrollBar().addMouseWheelListener(wheelListener);

        JButton addToWatchlistButton = new JButton("Add to Watchlist");
        JButton removeFromWatchlistButton = new JButton("Remove from Watchlist");
        JButton displayWatchlistButton = new JButton("Display Watchlist");
        
        addToWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedMovies.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No movies selected to add to Watchlist.");
                } else {
                    boolean movieAlreadyAdded = false;
        
                    for (Movie selectedMovie : selectedMovies) {
                        boolean movieExists = false;
                        for (Movie watchlistMovie : user.getWatchlist()) {
                            if (watchlistMovie.equals(selectedMovie)) {
                                movieExists = true;
                                break;
                            }
                        }
                        if (movieExists) {
                            movieAlreadyAdded = true;
                            break;
                        }
                    }
        
                    if (movieAlreadyAdded) {
                        JOptionPane.showMessageDialog(null, "This movie is already in the Watchlist.");
                    } else {
                        List<Movie> moviesToAdd = new ArrayList<>(selectedMovies); // Create a new list
                        for (Movie selectedMovie : moviesToAdd) {
                            user.addToWatchlist(selectedMovie);
                        }
                        JOptionPane.showMessageDialog(null, "Movies added to Watchlist.");
                        saveWatchlistData(); // Save updated watchlist data
                    }
                }
                selectedMovies.clear(); // Clear the original list after adding movies
            }
        });
          
        removeFromWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedMovies.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No movies selected to remove from Watchlist.");
                } else {
                    boolean moviesRemoved = false;
        
                    for (Movie selectedMovie : selectedMovies) {
                        Iterator<Movie> iterator = user.getWatchlist().iterator();
                        while (iterator.hasNext()) {
                            Movie movie = iterator.next();
                            if (movie.equals(selectedMovie)) {
                                iterator.remove();
                                moviesRemoved = true;
                                break; // Break the loop after removing the movie
                            }
                        }
                    }
        
                    if (moviesRemoved) {
                        JOptionPane.showMessageDialog(null, "Movies removed from Watchlist.");
                        saveWatchlistData(); // Save updated watchlist data
                    } else {
                        JOptionPane.showMessageDialog(null, "No selected movies found in Watchlist.");
                    }
        
                    selectedMovies.clear();
                }
            }
        });
        
        displayWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Movie> watchlist = user.getWatchlist();

                if (watchlist.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Watchlist is empty.");
                } else {
                    StringBuilder watchlistInfo = new StringBuilder("Movies in Watchlist:\n");
                    for (Movie movie : watchlist) {
                        watchlistInfo.append("- ").append(movie.getTitle()).append("\n");
                    }

                    JOptionPane.showMessageDialog(null, watchlistInfo.toString(), "Watchlist", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addToWatchlistButton);
        buttonPanel.add(removeFromWatchlistButton);
        buttonPanel.add(displayWatchlistButton);

        movieInfoFrame.add(buttonPanel, BorderLayout.SOUTH);
        movieInfoFrame.setVisible(true);
        
    }
    public void setAppIcon(String iconPath, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(iconPath);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            setIconImage(scaledImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
