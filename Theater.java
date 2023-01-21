import java.util.*;

public class Theater {
    private String name;
    private ArrayList<Movie> movies;
    private int theaterNumber;

    //constructor
    public Theater (String name, int number) {
        this.name = name;
        theaterNumber = number;
        this.movies = new ArrayList<Movie>();

        // hard code some stuff in the theater constructor 
        this.movies.add(new Movie("0", theaterNumber));
        this.movies.add(new Movie("1", theaterNumber));
        this.movies.add(new Movie("5", theaterNumber));
        this.movies.add(new Movie("9", theaterNumber));
    }

    //constructor with movies
    public Theater(String name, ArrayList<Movie> movies) {
        this.name = name;
        this.movies = movies;
    }

    //add movies 
    public void addMovie(Movie newMovie){
        movies.add(newMovie);
    }

    //add movie by code
    public void addMovieByInt(int movieIndex) {
        movies.add(new Movie(Translator.getMovie(movieIndex), theaterNumber));
    }

    //remove movie
    public void removeMovie(int index){
        movies.remove(index);
    }

    //getters
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public String getName() {
        return name;
    }

    public int getTheaterNumber(){
        return theaterNumber;
    }
}
