import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class UserGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private User user;
    private MovieDatabase movieDatabase;
    private List<Movie> selectedMovies;

    public UserGUI(MovieDatabase movieDatabase) {
        setTitle("User Authentication");
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
        selectedMovies = new ArrayList<>();

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    User.register(username, password);
                    JOptionPane.showMessageDialog(null, "Registration successful.");
                } catch (IllegalArgumentException | NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                boolean loggedIn = user.checkLogin(username, password);
                if (loggedIn) {
                    // Automatically show movies after logging in
                    showMovieInfoFrame();
                    JOptionPane.showMessageDialog(null, "Login successful.");

                    // Close the current login frame
                    dispose(); // This will close the current JFrame (UserGUI)
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
                // Add selected movies to the watchlist
                for (Movie selectedMovie : selectedMovies) {
                    user.addToWatchlist(selectedMovie);
                }
                JOptionPane.showMessageDialog(null, "Movies added to Watchlist.");
                // Clear the selected movies list
                selectedMovies.clear();
            }
        });

        removeFromWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove selected movies from the watchlist
                for (Movie selectedMovie : selectedMovies) {
                    user.removeFromWatchlist(selectedMovie);
                }
                JOptionPane.showMessageDialog(null, "Movies removed from Watchlist.");
                // Clear the selected movies list
                selectedMovies.clear();
            }
        });

        displayWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to display watchlist
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

    private Movie getSelectedMovie() {
        return new Movie(); 
    }

 
}
