import java.util.*;
import java.util.regex.*;

public class Movie {
    private ArrayList<Showtime> showtimes;
    private String name;
    private static final String TRANSLATOR_PATTERN = "^[0-9]{1}$";
    private int movieIndex;
    private int theaterIndex;

    //constructor
    public Movie(String movieNumber, int theaterNumber) throws NumberFormatException{
        this.movieIndex = Integer.parseInt(movieNumber);
        this.name = Translator.getMovie(this.movieIndex);
        this.showtimes = new ArrayList<Showtime>();
        theaterIndex = theaterNumber;

        // hard coded showtimes for movie
        showtimes.add(new Showtime(theaterIndex, movieIndex, 0, 0));
        showtimes.add(new Showtime(theaterIndex, movieIndex, 1, 0));
        showtimes.add(new Showtime(theaterIndex, movieIndex, 5, 6));
        showtimes.add(new Showtime(theaterIndex, movieIndex, 9, 9));
    }

    //getters
    public String getName() {
        return this.name;
    }

    //get showtimes
    public ArrayList<Showtime> getAllShowtimes() {
        return showtimes;
    }

    //add showtime to the movie
    public void addShowtime(String day, String time) throws TimeOutOfBoundsException, InvalidDayException{
        Pattern translatorPattern = Pattern.compile(TRANSLATOR_PATTERN);
        Matcher dayMatcher = translatorPattern.matcher(day);
        Matcher timeMatcher = translatorPattern.matcher(time);

        if(dayMatcher.matches()) {  //Day is good
            if(timeMatcher.matches()) { //Time is good
                int dayInt = Integer.parseInt(day);
                int timeInt = Integer.parseInt(time);

                int dayTime = (dayInt * 10) + timeInt;
                for(int i = 0; i < showtimes.size(); i++) {
                    if(dayTime <= showtimes.get(i).getDayAndTimeInt()) {    //If lower, insert
                        showtimes.add(i, new Showtime(theaterIndex, movieIndex, dayInt, timeInt));
                        break;
                    } else if (i == showtimes.size() - 1) {                 //If end, insert
                        showtimes.add(new Showtime(theaterIndex, movieIndex, dayInt, timeInt));
                    }
                }

                //If showtimes is nothing, insert
                if(showtimes.size() == 0) showtimes.add(new Showtime(theaterIndex, movieIndex, dayInt, timeInt));

            } else {    //Does not fit 24h format
                throw new TimeOutOfBoundsException();
            }
        }
        else {
            throw new InvalidDayException();
        }
    }

    //add many showtimes to the movie
    public void addManyShowtimes(ArrayList<String> days, ArrayList<String> times) throws TimeOutOfBoundsException, InvalidDayException, InvalidInputException{

        if(days.size() != times.size()) throw new InvalidInputException();

        for(int i = 0; i < days.size(); i++) {
            Pattern translatorPattern = Pattern.compile(TRANSLATOR_PATTERN);
            Matcher dayMatcher = translatorPattern.matcher(days.get(i));
            Matcher timeMatcher = translatorPattern.matcher(times.get(i));

            if(dayMatcher.matches()) {  //Day is good
                if(timeMatcher.matches()) { //Time is good
                    int dayInt = Integer.parseInt(days.get(i));
                    int timeInt = Integer.parseInt(times.get(i));

                    int dayTime = (dayInt * 10) + timeInt;
                    for(int j = 0; i < showtimes.size(); j++) {
                        if(dayTime <= showtimes.get(i).getDayAndTimeInt()) {    //If lower, insert
                            showtimes.add(j, new Showtime(theaterIndex, movieIndex, dayInt, timeInt));
                            break;
                        } else if (j == showtimes.size() - 1) {                 //If end, insert
                            showtimes.add(new Showtime(theaterIndex, movieIndex, dayInt, timeInt));
                        }
                    }

                    //If showtimes is nothing, insert
                    if(showtimes.size() == 0) showtimes.add(new Showtime(theaterIndex, movieIndex, dayInt, timeInt));

                } else {    //Does not fit 24h format
                    throw new TimeOutOfBoundsException();
                }
            }
            else {
                throw new InvalidDayException();
            }
        }
    }

    //remove a showtime
    public void removeShowtime(int index) {
        showtimes.remove(index);
        return;
    }

    //getters
    public int getMovieIndex(){
        return movieIndex;
    }

    public int getTheaterIndex(){
        return theaterIndex;
    }
}
