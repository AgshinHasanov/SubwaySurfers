import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private User user;
    private MovieDatabase movieDatabase;

    public UserGUI(MovieDatabase movieDatabase) {
        setTitle("User Authentication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
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
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel);
        pack(); // Adjust frame size based on components
        setLocationRelativeTo(null); // Center the frame on the screen
        setMinimumSize(new Dimension(400, 300)); // Set a minimum size for the frame
        setVisible(true);
    }


    private void showMovieInfoFrame() {
        JFrame movieInfoFrame = new JFrame();
        movieInfoFrame.setTitle("Movie Information");
        movieInfoFrame.setSize(800, 600);
        movieInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        movieInfoFrame.setLocationRelativeTo(null);

        JPanel moviePanel = new JPanel(new GridLayout(0, 1, 10, 10));

        List<Movie> movies = movieDatabase.getMovies();
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);

            JPanel movieContainer = new JPanel(new BorderLayout());
            movieContainer.setBorder(BorderFactory.createTitledBorder("Movie " + (i + 1)));

            JLabel movieLabel = new JLabel(movie.getTitle());

            // Display scaled photo if the photo directory is available
            if (movie.getPhotoDirectory() != null && !movie.getPhotoDirectory().isEmpty()) {
                ImageIcon originalIcon = new ImageIcon(movie.getPhotoDirectory());
                Image originalImage = originalIcon.getImage();

                // Define the maximum width and height for the displayed image
                int maxWidth = 150;
                int maxHeight = 200;

                // Scale the image proportionally
                int scaledWidth = Math.min(originalImage.getWidth(null), maxWidth);
                int scaledHeight = (int) (((double) scaledWidth / originalImage.getWidth(null)) * originalImage.getHeight(null));

                // Create a scaled ImageIcon
                ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH));
                JLabel photoLabel = new JLabel(scaledIcon);

                movieContainer.add(photoLabel, BorderLayout.NORTH);
            }

            movieContainer.add(movieLabel, BorderLayout.CENTER);
            moviePanel.add(movieContainer);
        }

        JScrollPane scrollPane = new JScrollPane(moviePanel);
        movieInfoFrame.add(scrollPane);

        JButton addToWatchlistButton = new JButton("Add to Watchlist");
        JButton removeFromWatchlistButton = new JButton("Remove from Watchlist");
        JButton displayWatchlistButton = new JButton("Display Watchlist");

        // Add action listeners for the watchlist buttons
        addToWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Movie selectedMovie = getSelectedMovie();
                user.addToWatchlist(selectedMovie);
                JOptionPane.showMessageDialog(null, "Movie added to Watchlist.");
            }
        });

        removeFromWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Movie selectedMovie = getSelectedMovie();
                user.removeFromWatchlist(selectedMovie);
                JOptionPane.showMessageDialog(null, "Movie removed from Watchlist.");
            }
        });

        displayWatchlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.displayWatchlist();
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
