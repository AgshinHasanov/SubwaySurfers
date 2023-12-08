import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private User user;
    private MovieDatabase movieDatabase;

    public UserGUI() {
        setTitle("User Authentication");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton registerButton = new JButton("Register");
        panel.add(registerButton);

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);

        user = new User();
        movieDatabase = new MovieDatabase("movies.dat");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    User.register(username, password);

                    // Display registration successful message
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
                    // Open a new frame for the movie information
                    showMovieInfoFrame();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel);
        setVisible(true);
    }

    private void showMovieInfoFrame() {
        JFrame movieInfoFrame = new JFrame();
        movieInfoFrame.setTitle("Movie Information");
        movieInfoFrame.setSize(400, 300);
        movieInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        movieInfoFrame.setLocationRelativeTo(null);

        JPanel moviePanel = new JPanel(new GridLayout(0, 1));

        // Retrieve movies from the database and display their information
        for (Movie movie : movieDatabase.getMovies()) {
            JLabel movieLabel = new JLabel(movie.toString());
            moviePanel.add(movieLabel);
        }

        JScrollPane scrollPane = new JScrollPane(moviePanel);
        movieInfoFrame.add(scrollPane);

        movieInfoFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UserGUI();
            }
        });
    }
}
