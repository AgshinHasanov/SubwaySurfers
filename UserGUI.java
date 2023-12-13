import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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

        String imagePath = "Database/SSpng.png";
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
        setIconImage(icon.getImage());

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
        movieInfoFrame.setSize(600, 400);
        movieInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        movieInfoFrame.setLocationRelativeTo(null);

        // Retrieve movies from the database
        List<Movie> movies = movieDatabase.getMovies();

        // Create a JTable to display movie information
        JTable movieTable = new JTable(new MovieTableModel(movies));
        JScrollPane scrollPane = new JScrollPane(movieTable);

        movieInfoFrame.add(scrollPane);
        movieInfoFrame.setVisible(true);
    }

    private static class MovieTableModel extends AbstractTableModel {
        private List<Movie> movies;
        private final String[] columnNames = {"Title", "Director", "Release Year", "Genre"};

        public MovieTableModel(List<Movie> movies) {
            this.movies = movies;
        }

        @Override
        public int getRowCount() {
            return movies.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Movie movie = movies.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return movie.getTitle();
                case 1:
                    return movie.getDirector();
                case 2:
                    return movie.getYear();
                default:
                    return null;
            }
        }
    }
}
