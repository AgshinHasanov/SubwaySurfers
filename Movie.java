import java.io.Serializable;

public class Movie implements Serializable {
    private String title;
    private String director;
    private int year;
    private int runningTime;
    private String photoDirectory;

    public Movie(String title, String director, int year, int runningTime, String photoDirectory) {
        try {
            if (year < 1895 || year > 2023) throw new IllegalArgumentException("Year should be between 1895 and 2023");
            if (runningTime < 0) throw new IllegalArgumentException("Running time cannot be a negative number");
            this.title = title;
            this.director = director;
            this.year = year;
            this.runningTime = runningTime;
            this.photoDirectory = photoDirectory;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Getter and setter for photo directory
    public String getPhotoDirectory() {
        return photoDirectory;
    }

    public void setPhotoDirectory(String photoDirectory) {
        this.photoDirectory = photoDirectory;
    }

    public Movie() {
        this.title = "The Godfather";
        this.year = 1972;
        this.runningTime = 175;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    public void setRunningTime(int runningTime) {
        try {
            if (runningTime < 0) {
                throw new IllegalArgumentException("Running time cannot be a negative number");
            }
            this.runningTime = runningTime;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        try {
            if (year < 1895 || year > 2023) {
                throw new IllegalArgumentException("Year should be between 1895 and 2023");
            }
            this.year = year;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return title + " " + year + " " + runningTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Movie otherMovie = (Movie) obj;
        return getTitle().equals(otherMovie.getTitle())
               && getDirector().equals(otherMovie.getDirector())
               && getYear() == otherMovie.getYear();
    }
    
}
