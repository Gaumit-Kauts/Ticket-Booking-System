import java.util.*;

public class ShowtimeObserver implements Observer {
    private Subject subject;
    private ArrayList<Seat> seats;

    //create a new observer for showtime
    public ShowtimeObserver(Subject sub) {
        subject = sub;
        subject.registerObserver(this);
    }

    //update the observers
    public void update(ArrayList<Seat> seats) {
        this.seats = seats;
    }
    
}
