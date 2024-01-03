# SubwaySurfers
### [Watch the video with the explanation of our code on the google drive](https://drive.google.com/file/d/150avT2690MpcTw_xs42i2lZvs1Wam9cU/view?usp=drive_link)
# User Class

## Overview

The `User` class is a fundamental component of the movie application, representing a user with a username, password, and a watchlist of movies. This class provides functionality for user registration, login validation, watchlist management, and interactions with the user database.

## Usage

### Class Structure

#### Fields

- `username`: The username of the user.
- `password`: The password of the user.
- `watchlist`: A list of movies in the user's watchlist.

#### Constructors

- `User(String username, String password)`: Constructs a user with the specified username and password.
- `User()`: Constructs a default user with an empty watchlist.

#### Getters and Setters

- `getUsername()`: Retrieves the username of the user.
- `setUsername(String username)`: Sets the username for the user, validating against null, blank, and invalid characters.
- `getPassword()`: Retrieves the password of the user.
- `setPassword(String password)`: Sets the password for the user, validating against null, blank, length, and invalid characters.

#### Other Methods

- `toString()`: Returns a string representation of the user.
- `register(String username, String password)`: Registers a new user with the specified username and password.
- `checkLogin(String username, String password)`: Checks if a user with the given username and password exists in the user database.
- `addToWatchlist(Movie movie)`: Adds a movie to the user's watchlist.
- `removeFromWatchlist(Movie movie)`: Removes a movie from the user's watchlist.
- `setWatchlist(List<Movie> watchlist)`: Sets the watchlist for the user.
- `displayWatchlist()`: Displays the user's watchlist.
- `getWatchlist()`: Retrieves the user's watchlist.

#### Private Helper Methods

- `userExists(List<User> existingUsers, String username)`: Checks if a user with a given username already exists in the user database.
- `saveToDatabase(List<User> users)`: Saves the user data to the user database file.
- `loadFromDatabase()`: Loads user data from the user database file.

## Dependencies

- `Movie` class: This class assumes the existence of a `Movie` class, which is not provided in this file.

## Usage Example

```java
// Sample usage of the User class
try {
    User.register("john_doe", "password123");
    boolean loginSuccessful = new User().checkLogin("john_doe", "password123");

    if (loginSuccessful) {
        User user = new User();
        Movie movie = new Movie("Inception", "Sci-Fi", 2010);
        user.addToWatchlist(movie);
        user.displayWatchlist();
    }
} catch (Exception e) {
    e.printStackTrace();
}
```
# Movie Class

## Overview

The `Movie` class represents a movie with attributes such as title, director, year of release, running time, and photo directory. It provides methods for sorting a list of movies based on different criteria.

## Usage

### Class Structure

#### Fields

- `title`: The title of the movie.
- `director`: The director of the movie.
- `year`: The year of release (between 1895 and 2023).
- `runningTime`: The running time of the movie (non-negative).
- `photoDirectory`: The directory path for the movie's photo.

#### Constructors

- `Movie(String title, String director, int year, int runningTime, String photoDirectory)`: Constructs a `Movie` object with the specified parameters.
- `Movie()`: Constructs a default `Movie` object with predefined values.

#### Getters and Setters

- `getPhotoDirectory()`: Retrieves the photo directory of the movie.
- `setPhotoDirectory(String photoDirectory)`: Sets the photo directory of the movie.
- `getRunningTime()`: Retrieves the running time of the movie.
- `getTitle()`: Retrieves the title of the movie.
- `getDirector()`: Retrieves the director of the movie.
- `getYear()`: Retrieves the year of release of the movie.
- `setRunningTime(int runningTime)`: Sets the running time of the movie.
- `setTitle(String title)`: Sets the title of the movie.
- `setYear(int year)`: Sets the year of release of the movie.
- `setDirector(String director)`: Sets the director of the movie.

#### Methods

- `toString()`: Returns a string representation of the movie.
- `equals(Object obj)`: Checks if two movies are equal based on title, director, and year.
- `sortMoviesByTitle(List<Movie> movies)`: Sorts a list of movies alphabetically by title.
- `sortMoviesByDirector(List<Movie> movies)`: Sorts a list of movies alphabetically by director.
- `sortMoviesByYear(List<Movie> movies)`: Sorts a list of movies by release year.
- `sortMoviesByRunningTime(List<Movie> movies)`: Sorts a list of movies by running time.

#### Private Helper Methods

- `validateYear(int year)`: Validates if the given year is within the valid range.
- `validateRunningTime(int runningTime)`: Validates if the given running time is non-negative.

## Usage Example

```java
// Sample usage of the Movie class
Movie movie = new Movie("Inception", "Christopher Nolan", 2010, 148, "/photos/inception.jpg");
System.out.println(movie.toString());

List<Movie> movies = Arrays.asList(movie, new Movie("The Dark Knight", "Christopher Nolan", 2008, 152, "/photos/dark_knight.jpg"));
List<Movie> sortedMoviesByTitle = Movie.sortMoviesByTitle(movies);
System.out.println(sortedMoviesByTitle);
```

# MovieDatabase Class

## Overview

The `MovieDatabase` class manages a collection of movies, providing methods for adding, removing, and retrieving movies. It also supports operations such as printing all movies and calculating the total watch time of a watchlist.

## Usage

### Class Structure

#### Fields

- `movies`: The list of movies in the database.
- `databaseFile`: The file path for storing the serialized database.

#### Constructors

- `MovieDatabase(String databaseFile)`: Constructs a `MovieDatabase` with the specified database file path.

#### Methods

- `addMovie(Movie movie)`: Adds a movie to the database.
- `removeMovie(Movie movie)`: Removes a movie from the database.
- `getMovie(String title)`: Retrieves a movie by its title.
- `getMovies()`: Retrieves the list of movies in the database.
- `printAllMovies()`: Prints all movies in the database.
- `saveDatabase()`: Saves the database to the specified file.
- `loadDatabase()`: Loads the database from the specified file.
- `calculateTotalWatchTimeInWatchlist(List<Movie> watchlist)`: Calculates the total watch time of movies in a watchlist.

### Unit Testing

The `MovieDatabaseTest` class provides JUnit tests for various functionalities:

- `testAddMovie()`: Tests adding a movie to the database.
- `testAddExistingMovie()`: Tests adding an existing movie, expecting an `IllegalArgumentException`.
- `testRemoveMovie()`: Tests removing a movie from the database.
- `testRemoveNonExistingMovie()`: Tests removing a non-existing movie, expecting an `IllegalArgumentException`.
- `testGetMovie()`: Tests retrieving a movie by its title.
- `testGetNonExistingMovie()`: Tests retrieving a non-existing movie.
- `testPrintAllMovies()`: Tests printing all movies in the database.
- `testSaveAndLoadDatabase()`: Tests saving and loading the database.
- `testCalculateTotalWatchTimeInWatchlist()`: Tests calculating the total watch time of movies in a watchlist.


# UserTest Class

## Overview

The `UserTest` class contains JUnit tests for the `User` class, focusing on methods related to username and password validation, watchlist operations, and displaying watchlists.

## Tests

### `testUsernameValidation()`

- Tests username validation including null, empty, invalid, and valid usernames.

### `testPasswordValidation()`

- Tests password validation including null, empty, short, invalid, and valid passwords.

### `testWatchlistOperations()`

- Tests watchlist operations such as adding, removing, and setting watchlist.

### `testDisplayWatchlist()`

- Tests the display of the user's watchlist.

# MovieTest Class

## Overview

The `MovieTest` class contains JUnit tests for the `Movie` class, covering the valid and invalid movie constructor, default constructor, setters, and various sorting methods.

## Tests

### `testValidMovieConstructor()`

- Tests the creation of a valid movie using the constructor.

### `testInvalidYearConstructor()`

- Tests creating a movie with an invalid year, expecting an `IllegalArgumentException`.

### `testInvalidRunningTimeConstructor()`

- Tests creating a movie with an invalid running time, expecting an `IllegalArgumentException`.

### `testDefaultConstructor()`

- Tests the default constructor of the movie.

### `testSetters()`

- Tests the setters for the movie attributes.

### `testInvalidSetRunningTime()`

- Tests setting an invalid running time using the setter, expecting an `IllegalArgumentException`.

### `testInvalidSetYear()`

- Tests setting an invalid year using the setter, expecting an `IllegalArgumentException`.

### `testToString()`

- Tests the `toString()` method of the movie.

### `testEquals()`

- Tests the `equals()` method of the movie.

### `testSortMoviesByTitle()`

- Tests sorting movies by title.

### `testSortMoviesByDirector()`

- Tests sorting movies by director.

### `testSortMoviesByYear()`

- Tests sorting movies by release year.

### `testSortMoviesByRunningTime()`

- Tests sorting movies by running time.

# MovieDatabaseTest Class

## Overview

The `MovieDatabaseTest` class contains JUnit tests for the `MovieDatabase` class, covering methods related to adding, removing, retrieving movies, printing all movies, saving/loading the database, and calculating total watch time in a watchlist.

## Tests

### `testAddMovie()`

- Tests adding a movie to the database.

### `testAddExistingMovie()`

- Tests adding an existing movie, expecting an `IllegalArgumentException`.

### `testRemoveMovie()`

- Tests removing a movie from the database.

### `testRemoveNonExistingMovie()`

- Tests removing a non-existing movie, expecting an `IllegalArgumentException`.

### `testGetMovie()`

- Tests retrieving a movie by its title.

### `testGetNonExistingMovie()`

- Tests retrieving a non-existing movie.

### `testPrintAllMovies()`

- Tests printing all movies in the database.

### `testSaveAndLoadDatabase()`

- Tests saving and loading the database.

### `testCalculateTotalWatchTimeInWatchlist()`

- Tests calculating the total watch time of movies in a watchlist.


# UserGUI Class Explanation

## Description
The `UserGUI` class represents the graphical user interface for the SubwaySurfers application. It provides a login and registration system, displays movie information, and allows users to manage their watchlist. The class extends `JFrame` to create a graphical window.

### Fields
- `private JTextField usernameField`: Text field for entering the username.
- `private JPasswordField passwordField`: Password field for entering the password.
- `private User user`: Represents the currently logged-in user.
- `private MovieDatabase movieDatabase`: Instance of the `MovieDatabase` used in the application.
- `private List<Movie> selectedMovies`: List of selected movies.
- `private static final String USERS_DATA_FILE = "Database/users.ser"`: File path for storing serialized user data.
- `private List<User> users`: List of users loaded from the serialized file.
- `private JFrame movieInfoFrame`: Frame for displaying movie information.
- `private JLabel filterStatusLabel`: Label for displaying the filter status.

### Constructor
- `public UserGUI(MovieDatabase movieDatabase)`: Constructor that initializes the graphical user interface. It sets up the window, login panel, and necessary components.

### Methods
1. **`loadUsersData()`**
   - Loads user data from the serialized file.
   - Returns a `List` of `User` objects loaded from the file.

2. **`saveUsersData()`**
   - Saves the current list of users to a serialized file.

3. **`findUserByUsername(String username)`**
   - Finds a user by their username in the list of users.
   - Returns the `User` object if found, or `null` if not found.

4. **`loadWatchlistData()`**
   - Loads the user's watchlist data from a serialized file.

5. **`saveWatchlistData()`**
   - Saves the user's watchlist data to a serialized file.

### ActionListener for Register Button
- Adds an `ActionListener` to the "Register" button.
- Checks if the entered username is unique.
- If unique, creates a new user, adds it to the list, and displays a success dialog.
- If the username already exists, displays an error dialog.

### ActionListener for Login Button
- Adds an `ActionListener` to the "Login" button.
- Checks if the entered username and password match a user in the list.
- If valid, logs in the user, loads the watchlist, and displays a success dialog.
- If invalid, displays an error dialog.

### GUI Components
- Creates and configures GUI components such as labels, text fields, buttons, and panels.
- Sets fonts, colors, and styles for a consistent appearance.

### Serialization
- Uses serialization to save and load user and watchlist data.
- Handles exceptions for file operations and class casting.

### Watchlist Management
- Manages the user's watchlist by loading and saving data.
- Creates a folder for each user's watchlist file if it doesn't exist.

### GUI Initialization
- Initializes the graphical user interface by setting up panels, components, and event listeners.
- Adjusts frame size, disables resizing, centers the frame, and makes it visible.

**Note:** Replace placeholder paths like "Database/tick.png" and "Database/error.png" with the actual paths to image resources.



# ShowMovieInfoFrame Method Explanation

## Description
The `showMovieInfoFrame` method is responsible for displaying movie information in a new `JFrame`. It creates a dynamic layout with movie details, including a photo, title, director, year, running time, and a "Select" button for adding movies to the watchlist. It utilizes various Swing components and handles user interactions through action listeners.

### Method Implementation

1. **Initialization and Disposal**
   - Disposes of the existing `movieInfoFrame` if it already exists.
   - Creates a new `JFrame` named "Movie Information" with specified dimensions and properties.
   - Creates a panel (`moviePanel`) for organizing movie information in a grid layout.

2. **Movie Information Display Loop**
   - Retrieves the list of movies from the `movieDatabase`.
   - Iterates through each movie and creates a container panel (`movieContainer`) for displaying movie details.
   - Sets background colors, borders, and title color for a consistent appearance.
   
3. **Photo Display**
   - Checks if the movie has a photo directory specified.
   - If present, scales and displays the movie's photo on the left side of the container panel.

4. **Information Panel**
   - Creates an information panel (`infoPanel`) to display movie details such as title, director, year, and running time.
   - Sets fonts, foreground colors, and border for a polished appearance.
   - Adds these components to the `movieContainer`.

5. **"Select" Button**
   - Creates a "Select" button (`selectButton`) with red background color and white text.
   - Adds an action listener to the button to add the selected movie to the `selectedMovies` list.

6. **Scroll Pane**
   - Wraps the `moviePanel` in a `JScrollPane` for scrollable content.
   - Adds vertical and horizontal mouse wheel listeners to allow scrolling.

7. **Watchlist Management Buttons**
   - Creates buttons for adding to watchlist, removing from watchlist, and displaying the watchlist.
   - Adds action listeners to handle the respective operations based on user interactions.
   - Displays custom dialogs for success, errors, and informational messages.

### Watchlist Management
   - Provides functionality to add selected movies to the watchlist, remove selected movies from the watchlist, and display the user's watchlist.
   - Utilizes custom dialogs to communicate success, errors, and informational messages to the user.

### Note
   - Paths to resources such as movie photos are assumed to be correctly specified. Ensure the paths are accurate for proper functionality.

**Important:** Replace placeholder paths like "Database/tick.png" and "Database/error.png" with the actual paths to image resources.


# MovieInfoFrame Class Explanation

## Description
The `MovieInfoFrame` class manages the display of movie information in a dynamic `JFrame`. It includes functionalities for sorting movies, updating the display, and managing the watchlist. The class utilizes Swing components and provides a user-friendly interface for interacting with the movie database.

### Constructor
- Creates a new instance of the `MovieInfoFrame` class.
- Initializes the `movieDatabase` and `selectedMovies` lists.

### Method: `showMovieInfoFrame`
- Displays movie information in a `JFrame`.
- Includes sections for displaying movie details, watchlist management, and filtering options.
- Provides functionalities for adding, removing, and displaying movies in the watchlist.
- Utilizes custom dialogs for user feedback on watchlist operations.
- Incorporates a filtering mechanism for sorting movies based on different criteria.

### Method: `sortMoviesBy`
- Sorts the list of movies based on the specified filter criteria (Title, Director, Running Time, Release Year).

### Method: `setAppIcon`
- Sets the application icon with the specified path, width, and height.

### Method: `updateMovieDisplay`
- Updates the movie display in the scroll pane based on the current movie list.
- Clears the existing movie panel, retrieves the updated movie list, and dynamically creates movie containers with details.

### Note
- The `MovieInfoFrame` assumes the existence of certain UI elements like buttons and combo boxes, which are expected to be present in the user interface for proper interaction.
- Ensure that paths to resources, such as movie photos, are correctly specified.
- Placeholder paths like "Database/tick.png" and "Database/error.png" need to be replaced with the actual paths to image resources.

**Important:** Replace placeholder paths like "Database/tick.png" and "Database/error.png" with the actual paths to image resources.
