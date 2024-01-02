import javax.swing.*;
import javax.swing.border.TitledBorder;

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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The UserGUI class represents the graphical user interface for the SubwaySurfers application.
 * It provides a login and registration system, displays movie information, and allows users to manage their watchlist.
 * The class extends JFrame to create a graphical window.
 *
 * @author [Subway Surfers]
 * @version 1.0
 * @since [12/2023]
 */
public class UserGUI extends JFrame {

    // Fields and constructors...
    private JTextField usernameField;
    private JPasswordField passwordField;
    private User user;
    private MovieDatabase movieDatabase;
    private List<Movie> selectedMovies;
    private static final String USERS_DATA_FILE = "Database/users.ser";
    private List<User> users;
    private JFrame movieInfoFrame;
    private JLabel filterStatusLabel;

    /**
     * Constructs a new UserGUI instance with the specified MovieDatabase.
     *
     * @param movieDatabase The MovieDatabase used in the application.
     */
    public UserGUI(MovieDatabase movieDatabase) {
        setTitle("SubwaySurfers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240)); // Set a light background color for the main panel

        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginPanel.setPreferredSize(new Dimension(300, 150));
        loginPanel.setBackground(new Color(60, 60, 60)); // Set a darker background for the login panel
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the login panel

        JLabel usernameLabel = new JLabel(" Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Changed font to Arial
        usernameLabel.setForeground(Color.WHITE);

        usernameField = new JTextField();
        usernameField.setBackground(Color.WHITE);

        JLabel passwordLabel = new JLabel(" Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Changed font to Arial
        passwordLabel.setForeground(Color.WHITE);

        passwordField = new JPasswordField();
        passwordField.setBackground(Color.WHITE);

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(220, 50, 50)); // Changed button color to red
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false); // Remove focus border
        registerButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding to the button

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(220, 50, 50)); // Changed button color to red
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false); // Remove focus border
        loginButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding to the button

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(registerButton);
        loginPanel.add(loginButton);

        panel.add(loginPanel, BorderLayout.NORTH);

        this.movieDatabase = movieDatabase;
        user = new User();
        users = loadUsersData();
        selectedMovies = new ArrayList<>();

        // Existing ActionListener code
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
        
                try {
                    if (findUserByUsername(username) == null) {
                        User newUser = new User(username, password);
                        users.add(newUser);
                        saveUsersData();
        
                        // Custom success dialog with specified font, color, and style
                        JDialog successDialog = new JDialog();
                        successDialog.setTitle("Registration Successful");
                        successDialog.setSize(300, 110);
                        successDialog.setLayout(new BorderLayout());
                        successDialog.setLocationRelativeTo(null); // Center the dialog
        
                        JPanel successPanel = new JPanel(new BorderLayout());
                        successPanel.setBackground(new Color(60, 60, 60));
        
                        // Add success icon
                        ImageIcon successIcon = new ImageIcon("Database/tick.png"); // Replace with the actual path
                        JLabel successIconLabel = new JLabel(successIcon);
                        successIconLabel.setPreferredSize(new Dimension(40, 40)); // Set the preferred size
                        successPanel.add(successIconLabel, BorderLayout.WEST);
        
                        JLabel successMessage = new JLabel("Registration successful.");
                        successMessage.setFont(new Font("Arial", Font.BOLD, 12));
                        successMessage.setForeground(Color.WHITE);
                        successMessage.setHorizontalAlignment(SwingConstants.LEFT);
                        successMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
                        successPanel.add(successMessage, BorderLayout.CENTER);
        
                        successDialog.add(successPanel, BorderLayout.CENTER);
                        successDialog.setVisible(true);
                    } else {
                        throw new IllegalArgumentException("Username already exists");
                    }
                } catch (IllegalArgumentException ex) {
                    // Custom error dialog with specified font, color, and style
                    JDialog errorDialog = new JDialog();
                    errorDialog.setTitle("Registration Failed");
                    errorDialog.setSize(300, 110);
                    errorDialog.setLayout(new BorderLayout());
                    errorDialog.setLocationRelativeTo(null); // Center the dialog
        
                    JPanel errorPanel = new JPanel(new BorderLayout());
                    errorPanel.setBackground(new Color(60, 60, 60));
        
                    // Add failure icon
                    ImageIcon failureIcon = new ImageIcon("Database/error.png"); // Replace with the actual path
                    JLabel failureIconLabel = new JLabel(failureIcon);
                    failureIconLabel.setPreferredSize(new Dimension(40, 40)); // Set the preferred size
                    errorPanel.add(failureIconLabel, BorderLayout.WEST);
        
                    JLabel errorMessage = new JLabel(ex.getMessage());
                    errorMessage.setFont(new Font("Arial", Font.BOLD, 12));
                    errorMessage.setForeground(Color.WHITE);
                    errorMessage.setHorizontalAlignment(SwingConstants.LEFT);
                    errorMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
                    errorPanel.add(errorMessage, BorderLayout.CENTER);
        
                    errorDialog.add(errorPanel, BorderLayout.CENTER);
                    errorDialog.setVisible(true);
                }
            }
        });
        
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User loggedInUser = findUserByUsername(username);
        
                try {
                    if (loggedInUser != null && loggedInUser.getPassword().equals(password)) {
                        user = loggedInUser;
                        loadWatchlistData();
                        showMovieInfoFrame();
        
                        // Custom success dialog with specified font, color, and style
                        JDialog successDialog = new JDialog();
                        successDialog.setTitle("Login Successful");
                        successDialog.setSize(300, 110);
                        successDialog.setLayout(new BorderLayout());
                        successDialog.setLocationRelativeTo(null); // Center the dialog
        
                        JPanel successPanel = new JPanel(new BorderLayout());
                        successPanel.setBackground(new Color(60, 60, 60));
        
                        // Add success icon
                        ImageIcon successIcon = new ImageIcon("Database/tick.png"); // Replace with the actual path
                        JLabel successIconLabel = new JLabel(successIcon);
                        successIconLabel.setPreferredSize(new Dimension(40, 40)); // Set the preferred size
                        successPanel.add(successIconLabel, BorderLayout.WEST);
        
                        JLabel successMessage = new JLabel("Login successful.");
                        successMessage.setFont(new Font("Arial", Font.BOLD, 12));
                        successMessage.setForeground(Color.WHITE);
                        successMessage.setHorizontalAlignment(SwingConstants.LEFT);
                        successMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
                        successPanel.add(successMessage, BorderLayout.CENTER);
        
                        successDialog.add(successPanel, BorderLayout.CENTER);
                        successDialog.setVisible(true);
        
                        dispose();
                    } else {
                        throw new IllegalArgumentException("Invalid username or password");
                    }
                } catch (IllegalArgumentException ex) {
                    // Custom error dialog with specified font, color, and style
                    JDialog errorDialog = new JDialog();
                    errorDialog.setTitle("Login Failed");
                    errorDialog.setSize(300, 110);
                    errorDialog.setLayout(new BorderLayout());
                    errorDialog.setLocationRelativeTo(null); // Center the dialog
        
                    JPanel errorPanel = new JPanel(new BorderLayout());
                    errorPanel.setBackground(new Color(60, 60, 60));
        
                    // Add failure icon
                    ImageIcon failureIcon = new ImageIcon("Database/error.png"); // Replace with the actual path
                    JLabel failureIconLabel = new JLabel(failureIcon);
                    failureIconLabel.setPreferredSize(new Dimension(40, 40)); // Set the preferred size
                    errorPanel.add(failureIconLabel, BorderLayout.WEST);
        
                    JLabel errorMessage = new JLabel(ex.getMessage());
                    errorMessage.setFont(new Font("Arial", Font.BOLD, 12));
                    errorMessage.setForeground(Color.WHITE);
                    errorMessage.setHorizontalAlignment(SwingConstants.LEFT);
                    errorMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
                    errorPanel.add(errorMessage, BorderLayout.CENTER);
        
                    errorDialog.add(errorPanel, BorderLayout.CENTER);
                    errorDialog.setVisible(true);
                }
            }
        });
        
             
        add(panel);
        pack(); // Adjust frame size based on components
        setResizable(false); // Disable frame resizing
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
        loadWatchlistData();
        filterStatusLabel = new JLabel("Filter By: Title"); // Initial text
        panel.add(filterStatusLabel, BorderLayout.SOUTH);
    }

    /**
     * Loads user data from the serialized file.
     *
     * @return A List of User objects loaded from the file.
     */
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

    /**
     * Saves the current list of users to a serialized file.
     */
    private void saveUsersData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds a user by their username in the list of users.
     *
     * @param username The username to search for.
     * @return The User object if found, or null if not found.
     */
    private User findUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Loads the user's watchlist data from a serialized file.
     */
    @SuppressWarnings("unchecked")
    private void loadWatchlistData() {
        if (user != null) {
            String userWatchlistFolder = "UserWatchlists/";
            String userWatchlistFileName = user.getUsername() + "_watchlist.ser";
            String userWatchlistFilePath = userWatchlistFolder + userWatchlistFileName;
    
            try {
                // Create the folder if it does not exist
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

    /**
     * Saves the user's watchlist data to a serialized file.
     */
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

    /**
     * Displays the movie information in a new JFrame.
     */
    private void showMovieInfoFrame() {

        
        if (movieInfoFrame != null) {
            movieInfoFrame.dispose(); // Close the existing movieInfoFrame before updating
        }
    
        movieInfoFrame = new JFrame();
        movieInfoFrame.setTitle("Movie Information");
        movieInfoFrame.setSize(800, 600);
        movieInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        movieInfoFrame.setLocationRelativeTo(null);
        JPanel moviePanel = new JPanel(new GridLayout(0, 2, 10, 10));
        moviePanel.setBackground(new Color(60, 60, 60)); 
    
        List<Movie> movies = movieDatabase.getMovies();
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
    
            JPanel movieContainer = new JPanel(new BorderLayout());
            movieContainer.setBackground(new Color(60, 60, 60)); 
            TitledBorder titledBorder = BorderFactory.createTitledBorder("");
            titledBorder.setTitleColor(new Color(60, 60, 60)); // Set the title color to match the background
            movieContainer.setBorder(titledBorder);
            movieContainer.setBorder(BorderFactory.createEmptyBorder());

    
            if (movie.getPhotoDirectory() != null && !movie.getPhotoDirectory().isEmpty()) {
                ImageIcon originalIcon = new ImageIcon(movie.getPhotoDirectory());
                Image originalImage = originalIcon.getImage();
    
                int maxWidth = 150;
                int scaledWidth = Math.min(originalImage.getWidth(null), maxWidth);
                int scaledHeight = (int) (((double) scaledWidth / originalImage.getWidth(null)) * originalImage.getHeight(null));
    
                ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
                JLabel photoLabel = new JLabel(scaledIcon);
    
                movieContainer.add(photoLabel, BorderLayout.WEST);
            }
    
            JPanel infoPanel = new JPanel(new GridLayout(4, 1));
            infoPanel.setBackground(new Color(100, 100, 100)); // Set background color of infoPanel  
            JLabel titleLabel = new JLabel(" Title: " + movie.getTitle());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            titleLabel.setForeground(Color.white); 
            JLabel directorLabel = new JLabel(" Director: " + movie.getDirector());
            directorLabel.setForeground(Color.white); 
            directorLabel.setFont(new Font("Arial", Font.BOLD, 16));
            JLabel yearLabel = new JLabel(" Year: " + movie.getYear());
            yearLabel.setFont(new Font("Arial", Font.BOLD, 16));
            yearLabel.setForeground(Color.white);                       
            JLabel runningTimeLabel = new JLabel(" Running Time: " + movie.getRunningTime() + " minutes");
            runningTimeLabel.setForeground(Color.white); 
            runningTimeLabel.setFont(new Font("Arial", Font.BOLD, 16));

            infoPanel.setBorder(BorderFactory.createEmptyBorder());
            infoPanel.add(titleLabel);
            infoPanel.add(directorLabel);
            infoPanel.add(yearLabel);
            infoPanel.add(runningTimeLabel);
    
            movieContainer.add(infoPanel, BorderLayout.CENTER);
    
            JButton selectButton = new JButton("Select");
            selectButton.setBackground(new Color(220, 50, 50)); // Changed button color to red
            selectButton.setForeground(Color.WHITE);
            selectButton.setFocusPainted(false);
            selectButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!selectedMovies.contains(movie)) {
                        selectedMovies.add(movie);
                    }
                }
            });
            movieContainer.add(selectButton, BorderLayout.EAST);
    
            moviePanel.add(movieContainer);
        }
    
        JScrollPane scrollPane = new JScrollPane(moviePanel);
        scrollPane.setBackground(new Color(240, 240, 240)); // Set background color of the scrollPane
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

        // Add horizontal scroll wheel listener
        MouseWheelListener horizontalWheelListener = new MouseWheelListener() {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            int unitsToScroll = e.getUnitsToScroll() * scrollSpeed;
            verticalScrollBar.setValue(verticalScrollBar.getValue() + unitsToScroll);
        }
    };

        scrollPane.addMouseWheelListener(horizontalWheelListener);
        scrollPane.getHorizontalScrollBar().addMouseWheelListener(horizontalWheelListener);



       

        JButton addToWatchlistButton = new JButton("Add to Watchlist");
        JButton removeFromWatchlistButton = new JButton("Remove from Watchlist");
        JButton displayWatchlistButton = new JButton("Display Watchlist");
        
        addToWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedMovies.isEmpty()) {
                    // Custom error dialog for no movies selected
                    JDialog errorDialog = new JDialog();
                    errorDialog.setTitle("Add to Watchlist");
                    errorDialog.setSize(300, 110);
                    errorDialog.setLayout(new BorderLayout());
                    errorDialog.setLocationRelativeTo(null); // Center the dialog
        
                    JPanel errorPanel = new JPanel(new BorderLayout());
                    errorPanel.setBackground(new Color(60, 60, 60));
        
                    JLabel errorMessage = new JLabel("No movie/movies is selected to add to Watchlist.");
                    errorMessage.setFont(new Font("Arial", Font.BOLD, 12));
                    errorMessage.setForeground(Color.WHITE);
                    errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                    errorMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
                    errorPanel.add(errorMessage, BorderLayout.CENTER);
        
                    errorDialog.add(errorPanel, BorderLayout.CENTER);
                    errorDialog.setVisible(true);
                } else {
                    boolean movieAlreadyAdded = false;
                    List<Movie> addedMovies = new ArrayList<>(); // To store added movies
        
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
                        } else {
                            addedMovies.add(selectedMovie); // Store added movies
                        }
                    }
        
                    if (movieAlreadyAdded) {
                        // Custom error dialog for movie already on the Watchlist
                        JDialog errorDialog = new JDialog();
                        errorDialog.setTitle("Add to Watchlist");
                        errorDialog.setSize(350, 110);
                        errorDialog.setLayout(new BorderLayout());
                        errorDialog.setLocationRelativeTo(null); // Center the dialog
        
                        JPanel errorPanel = new JPanel(new BorderLayout());
                        errorPanel.setBackground(new Color(60, 60, 60));
        
                        JLabel errorMessage = new JLabel("At least one of the movies is already on the Watchlist.");
                        errorMessage.setFont(new Font("Arial", Font.BOLD, 12));
                        errorMessage.setForeground(Color.WHITE);
                        errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                        errorMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
                        errorPanel.add(errorMessage, BorderLayout.CENTER);
        
                        errorDialog.add(errorPanel, BorderLayout.CENTER);
                        errorDialog.setVisible(true);
                    }  else {
                        List<Movie> moviesToAdd = new ArrayList<>(addedMovies); // Use the addedMovies list
        
                        for (Movie selectedMovie : moviesToAdd) {
                            user.addToWatchlist(selectedMovie);
                        }
        
                        // Custom success dialog for movies added to Watchlist
                        JDialog successDialog = new JDialog();
                        successDialog.setTitle("Add to Watchlist");
                        successDialog.setSize(400, 160);
                        successDialog.setLayout(new BorderLayout());
                        successDialog.setLocationRelativeTo(null); // Center the dialog

                        JPanel successPanel = new JPanel(new BorderLayout());
                        successPanel.setBackground(new Color(60, 60, 60));

                        JTextArea successTextArea = new JTextArea();
                        successTextArea.setFont(new Font("Arial", Font.BOLD, 12));
                        successTextArea.setForeground(Color.WHITE);
                        successTextArea.setBackground(new Color(30, 30, 30));
                        successTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                        JScrollPane scrollPane = new JScrollPane(successTextArea);
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                        StringBuilder successMessage = new StringBuilder("Selected Movie/Movies added to Watchlist:\n");
                        for (Movie movie : moviesToAdd) {
                            successMessage.append("- ").append(movie.getTitle()).append("\n");
                        }

                        successTextArea.setText(successMessage.toString());
                        successPanel.add(scrollPane, BorderLayout.CENTER);

                        successDialog.add(successPanel, BorderLayout.CENTER);
                        successDialog.setVisible(true);

                        saveWatchlistData(); // Save updated watchlist data
                    }
                    selectedMovies.clear(); // Clear the original list after adding movies
                }
            }
        });
        
          
        removeFromWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedMovies.isEmpty()) {
                    // Custom error dialog for no movies selected to remove
                    JDialog errorDialog = new JDialog();
                    errorDialog.setTitle("Remove from Watchlist");
                    errorDialog.setSize(350, 110);
                    errorDialog.setLayout(new BorderLayout());
                    errorDialog.setLocationRelativeTo(null); // Center the dialog
        
                    JPanel errorPanel = new JPanel(new BorderLayout());
                    errorPanel.setBackground(new Color(60, 60, 60));
        
                    JLabel errorMessage = new JLabel("No movie/movies is/are selected to remove from Watchlist.");
                    errorMessage.setFont(new Font("Arial", Font.BOLD, 12));
                    errorMessage.setForeground(Color.WHITE);
                    errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                    errorMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
                    errorPanel.add(errorMessage, BorderLayout.CENTER);
        
                    errorDialog.add(errorPanel, BorderLayout.CENTER);
                    errorDialog.setVisible(true);
                } else {
                    boolean moviesRemoved = false;
                    List<Movie> removedMovies = new ArrayList<>(); // To store removed movies
        
                    for (Movie selectedMovie : selectedMovies) {
                        Iterator<Movie> iterator = user.getWatchlist().iterator();
                        while (iterator.hasNext()) {
                            Movie movie = iterator.next();
                            if (movie.equals(selectedMovie)) {
                                iterator.remove();
                                moviesRemoved = true;
                                removedMovies.add(selectedMovie); // Store removed movies
                                break; // Break the loop after removing the movie
                            }
                        }
                    }
        
                    if (moviesRemoved) {
                        // Custom success dialog for movies removed from Watchlist
                        JDialog successDialog = new JDialog();
                        successDialog.setTitle("Remove from Watchlist");
                        successDialog.setSize(300, 160);
                        successDialog.setLayout(new BorderLayout());
                        successDialog.setLocationRelativeTo(null); // Center the dialog
        
                        JPanel successPanel = new JPanel(new BorderLayout());
                        successPanel.setBackground(new Color(60, 60, 60));
        
                        JTextArea successTextArea = new JTextArea();
                        successTextArea.setFont(new Font("Arial", Font.BOLD, 12));
                        successTextArea.setForeground(Color.WHITE);
                        successTextArea.setBackground(new Color(30, 30, 30));
                        successTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
                        JScrollPane scrollPane = new JScrollPane(successTextArea);
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
                        StringBuilder successMessage = new StringBuilder("Movies removed from Watchlist:\n");
                        for (Movie movie : removedMovies) {
                            successMessage.append("- ").append(movie.getTitle()).append("\n");
                        }
        
                        successTextArea.setText(successMessage.toString());
                        successPanel.add(scrollPane, BorderLayout.CENTER);
        
                        successDialog.add(successPanel, BorderLayout.CENTER);
                        successDialog.setVisible(true);
        
                        saveWatchlistData(); // Save updated watchlist data
                    } else {
                        // Custom error dialog for no selected movies found in Watchlist
                        JDialog errorDialog = new JDialog();
                        errorDialog.setTitle("Remove from Watchlist");
                        errorDialog.setSize(350, 110);
                        errorDialog.setLayout(new BorderLayout());
                        errorDialog.setLocationRelativeTo(null); // Center the dialog
        
                        JPanel errorPanel = new JPanel(new BorderLayout());
                        errorPanel.setBackground(new Color(60, 60, 60));
        
                        JLabel errorMessage = new JLabel("No selected movies found in Watchlist.");
                        errorMessage.setFont(new Font("Arial", Font.BOLD, 12));
                        errorMessage.setForeground(Color.WHITE);
                        errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
                        errorMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
                        errorPanel.add(errorMessage, BorderLayout.CENTER);
        
                        errorDialog.add(errorPanel, BorderLayout.CENTER);
                        errorDialog.setVisible(true);
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
                    // Custom dialog for an empty watchlist
                    JDialog emptyWatchlistDialog = new JDialog();
                    emptyWatchlistDialog.setTitle("Watchlist");
                    emptyWatchlistDialog.setSize(300, 110);
                    emptyWatchlistDialog.setLayout(new BorderLayout());
                    emptyWatchlistDialog.setLocationRelativeTo(null); // Center the dialog
        
                    JPanel emptyWatchlistPanel = new JPanel(new BorderLayout());
                    emptyWatchlistPanel.setBackground(new Color(60, 60, 60));
        
                    JLabel emptyWatchlistMessage = new JLabel("Watchlist is empty.");
                    emptyWatchlistMessage.setFont(new Font("Arial", Font.BOLD, 12));
                    emptyWatchlistMessage.setForeground(Color.WHITE);
                    emptyWatchlistMessage.setHorizontalAlignment(SwingConstants.CENTER);
                    emptyWatchlistMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
                    emptyWatchlistPanel.add(emptyWatchlistMessage, BorderLayout.CENTER);
        
                    emptyWatchlistDialog.add(emptyWatchlistPanel, BorderLayout.CENTER);
                    emptyWatchlistDialog.setVisible(true);
                } else {
                    int totalWatchTime = movieDatabase.calculateTotalWatchTimeInWatchlist(watchlist);
        
                    StringBuilder watchlistInfo = new StringBuilder("Movies in Watchlist:\n");
                    for (Movie movie : watchlist) {
                        watchlistInfo.append("- ").append(movie.getTitle()).append("\n");
                    }
        
                    // Custom dialog displaying watchlist content and total watch time
                    JDialog watchlistDialog = new JDialog();
                    watchlistDialog.setTitle("Watchlist");
                    watchlistDialog.setSize(400, 250);
                    watchlistDialog.setLayout(new BorderLayout());
                    watchlistDialog.setLocationRelativeTo(null); // Center the dialog
        
                    JPanel watchlistPanel = new JPanel(new BorderLayout());
                    watchlistPanel.setBackground(new Color(60, 60, 60));
        
                    JTextArea watchlistTextArea = new JTextArea();
                    watchlistTextArea.setFont(new Font("Arial", Font.PLAIN, 12));
                    watchlistTextArea.setForeground(Color.WHITE);
                    watchlistTextArea.setBackground(new Color(30, 30, 30));
                    watchlistTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    watchlistTextArea.setText(
                        "Total watch time in watchlist: " + totalWatchTime + " minutes\n\n" + watchlistInfo.toString()
                    );
        
                    watchlistPanel.add(watchlistTextArea, BorderLayout.CENTER);
        
                    watchlistDialog.add(watchlistPanel, BorderLayout.CENTER);
                    watchlistDialog.setVisible(true);
                }
            }
        });
        
        
        JComboBox<String> filterComboBox = new JComboBox<>(new String[]{"Title", "Director", "Running Time", "Release Year"});
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(new Color(60, 60, 60));
        JLabel filterLabel = new JLabel("Filter By:");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 16));
        filterPanel.add(filterLabel);
        filterLabel.setForeground(Color.white);
        filterPanel.add(filterComboBox);
        movieInfoFrame.add(filterPanel, BorderLayout.NORTH);

        filterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFilter = (String) filterComboBox.getSelectedItem();
                if (selectedFilter != null) {
                    // Sort movies based on the selected filter
                    sortMoviesBy(selectedFilter);
                    // Update the filter status label
                    filterStatusLabel.setText("Filter By: " + selectedFilter);
                    // Refresh the movie display within the existing scroll pane
                    updateMovieDisplay(moviePanel, scrollPane);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(60, 60, 60)); // Set background color to gray
    
        addToWatchlistButton.setForeground(Color.WHITE); // Set text color of addToWatchlistButton to white
        addToWatchlistButton.setBackground(new Color(220, 50, 50));
        removeFromWatchlistButton.setForeground(Color.WHITE); // Set text color of removeFromWatchlistButton to white
        removeFromWatchlistButton.setBackground(new Color(220, 50, 50));
        displayWatchlistButton.setForeground(Color.WHITE); // Set text color of displayWatchlistButton to white
        displayWatchlistButton.setBackground(new Color(220, 50, 50));
    
        buttonPanel.add(addToWatchlistButton);
        buttonPanel.add(removeFromWatchlistButton);
        buttonPanel.add(displayWatchlistButton);
    
        movieInfoFrame.add(buttonPanel, BorderLayout.SOUTH);
        movieInfoFrame.setVisible(true);
    }

    /**
     * Sorts the list of movies based on the specified filter.
     *
     * @param filter The filter to use for sorting (Title, Director, Running Time, Release Year).
     */
    private void sortMoviesBy(String filter) {
        List<Movie> movies = movieDatabase.getMovies();
        List<Movie> sortedMovies;
        
        switch (filter) {
            case "Title":
                sortedMovies = Movie.sortMoviesByTitle(movies);
                break;
            case "Director":
                sortedMovies = Movie.sortMoviesByDirector(movies);
                break;
            case "Running Time":
                sortedMovies = Movie.sortMoviesByRunningTime(movies);
                break;
            case "Release Year":
                sortedMovies = Movie.sortMoviesByYear(movies);
                break;
            default:
                sortedMovies = movies; // Default behavior is no sorting
                break;
        }
    
        // Update the original movie list with the sorted movies
        movies.clear();
        movies.addAll(sortedMovies);
    }

    /**
     * Sets the application icon with the specified path, width, and height.
     *
     * @param iconPath The path to the icon image file.
     * @param width    The width of the icon.
     * @param height   The height of the icon.
     */
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

    /**
     * Updates the movie display in the scroll pane based on the current movie list.
     *
     * @param moviePanel The panel containing movie information.
     * @param scrollPane The scroll pane containing the movie panel.
     */
    private void updateMovieDisplay(JPanel moviePanel, JScrollPane scrollPane) {
        moviePanel.removeAll(); // Clear the movie panel
        List<Movie> movies = movieDatabase.getMovies();
        
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
    
            JPanel movieContainer = new JPanel(new BorderLayout());
            movieContainer.setBackground(new Color(60, 60, 60)); 
            movieContainer.setBorder(BorderFactory.createTitledBorder("Movie " + (i + 1)));
            TitledBorder titledBorder = BorderFactory.createTitledBorder("Movie " + (i + 1));
            titledBorder.setTitleColor(Color.white); 
            movieContainer.setBorder(titledBorder);
    
            if (movie.getPhotoDirectory() != null && !movie.getPhotoDirectory().isEmpty()) {
                ImageIcon originalIcon = new ImageIcon(movie.getPhotoDirectory());
                Image originalImage = originalIcon.getImage();
    
                int maxWidth = 150;
                int scaledWidth = Math.min(originalImage.getWidth(null), maxWidth);
                int scaledHeight = (int) (((double) scaledWidth / originalImage.getWidth(null)) * originalImage.getHeight(null));
    
                ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
                JLabel photoLabel = new JLabel(scaledIcon);
    
                movieContainer.add(photoLabel, BorderLayout.WEST);
            }
    
            JPanel infoPanel = new JPanel(new GridLayout(4, 1));
            infoPanel.setBackground(new Color(100, 100, 100)); // Set background color of infoPanel  
            JLabel titleLabel = new JLabel(" Title: " + movie.getTitle());
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            titleLabel.setForeground(Color.white); 
            JLabel directorLabel = new JLabel(" Director: " + movie.getDirector());
            directorLabel.setForeground(Color.white); 
            directorLabel.setFont(new Font("Arial", Font.BOLD, 16));
            JLabel yearLabel = new JLabel(" Year: " + movie.getYear());
            yearLabel.setFont(new Font("Arial", Font.BOLD, 16));
            yearLabel.setForeground(Color.white);                       
            JLabel runningTimeLabel = new JLabel(" Running Time: " + movie.getRunningTime() + " minutes");
            runningTimeLabel.setForeground(Color.white); 
            runningTimeLabel.setFont(new Font("Arial", Font.BOLD, 16));
    
            infoPanel.add(titleLabel);
            infoPanel.add(directorLabel);
            infoPanel.add(yearLabel);
            infoPanel.add(runningTimeLabel);
    
            movieContainer.add(infoPanel, BorderLayout.CENTER);
    
            JButton selectButton = new JButton("Select");
            selectButton.setBackground(new Color(220, 50, 50)); // Changed button color to red
            selectButton.setForeground(Color.WHITE);
            selectButton.setFocusPainted(false);
            selectButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!selectedMovies.contains(movie)) {
                        selectedMovies.add(movie);
                    }
                }
            });
            movieContainer.add(selectButton, BorderLayout.EAST);
    
            moviePanel.add(movieContainer);
        }
        // Refresh the scroll pane with the updated movie panel
        moviePanel.revalidate();
        moviePanel.repaint();
    }
}
