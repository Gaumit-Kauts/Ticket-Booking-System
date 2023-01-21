import java.util.*;

public class Showtime implements Subject {
    private ArrayList<Observer> observers;
    private ArrayList<Seat> seats;
    private int timeInt, dayInt;
    private String timeString, dayString;
    private String showtimeCode;

    //constructor
    public Showtime(int theaterIndex, int movieIndex, int dayInt, int timeInt){
        seats = new ArrayList<Seat>();
        observers = new ArrayList<Observer>();
        this.dayInt = dayInt;
        this.dayString = Translator.getDate(dayInt);
        this.timeInt = timeInt;
        this.timeString = Translator.getTime(timeInt);
        showtimeCode = Integer.toString(theaterIndex) + Integer.toString(movieIndex) + Integer.toString(dayInt) + Integer.toString(timeInt);
    }

    //register the observer
    public void registerObserver(Observer o) {
        observers.add(o);
        o.update(seats);
    }

    //remove the observer
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    //totify all of the observers for showtime
    public void notifyAllObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observer o = observers.get(i);
            o.update(seats);
        }
    }

    //add a seat and notify observers
    public void addSeat(Seat seat) {
        seats.add(seat);
        notifyAllObservers();
    }

    //remove a seat and notify observers
    public void removeSeat(int index) {
        seats.remove(index);
        notifyAllObservers();
    }

    //get all of the seats for this showtime
    public ArrayList<Seat> getAllSeats() {
        return this.seats;
    }

    //getters
    public String getTime() {
        return this.timeString;
    }

    public int getTimeInt() {
        return this.timeInt;
    }

    public String getDTG() {
        String DTG = dayString + " " + timeString;
        return DTG;
    }

    public int getDayAndTimeInt() {
        return (dayInt * 10) + timeInt;
    }

    //populate the showtime with default seats
    public void defaultPopulate() {
        for(int i = 65; i <= 67; i++) {                 //Rows A - C        
            for(int j = 1; j <= 3; j++) {               //Cols 1 - 3
                seats.add(new Seat((char) i, j));
            }
        }
    }

    //populate one seat
    public void populateOneSeat(String seatLocation) {
        char row = seatLocation.charAt(0);
        int col = Character.getNumericValue(seatLocation.charAt(1));
        seats.add(new Seat(row, col));
    }

    //populate many seats
    public void populateManySeat(ArrayList<Character> rows, ArrayList<Integer> cols) {
        for(int i = 0; i < rows.size(); i++) {
            seats.add(new Seat(rows.get(i), cols.get(i)));
        }
    }

    //get the showtime code
    public String getShowtimeCode(){
        return showtimeCode;
    }

}