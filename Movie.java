public class Movie {
    private String title;
    private String director;
    private int year;
    private int RunningTime;

    public Movie(String title,String director, int year , int RunningTime){
        if(year<1895 || year>2023){
            System.out.println("You should write between 1895 and 2023");
        }
        if(RunningTime < 0){
           System.out.println("Running time cannot be a negative number");
        }
        else{
        this.title=title;
        this.director=director;
        this.year=year;
        this.RunningTime=RunningTime;
        }
    }

    public Movie(){
        this.title="The Godfather";
        this.year=1972;
        this.RunningTime=175;
    }

    public int getRunningTime() {
        return RunningTime;
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
        if(RunningTime < 0){
           System.out.println("Running time cannot be a negative number");
        }
        this.RunningTime = runningTime;
        
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
         if(year<1895 || year>2023){
            System.out.println("You should write between 1895 and 2023");
        }
        this.year = year;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString(){
        return title + " " + year + " " + RunningTime;
    }




}