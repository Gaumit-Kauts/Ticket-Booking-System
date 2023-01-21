public class Ticket {
    private Seat seat;
    private Showtime showtime;
    private Movie movie;
    private String code;

    //ticket constructor
    public Ticket(Seat seat, Showtime showtime, Movie movie) {
        this.seat = seat;
        this.showtime = showtime;
        this.movie = movie;
    }

    //getters
    public Seat getSeat() {
        return seat;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getCode() {
        return code;
    }

}
