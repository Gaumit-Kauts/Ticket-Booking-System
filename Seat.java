public class Seat implements Cloneable{
    private char row;
    private int index;

    //constructor 
    public Seat(char row, int index) {
        this.row = row;
        this.index = index;
    }

    //constructor with only seat object
    public Seat(Seat seat){
        this.row = seat.getRow();
        this.index = seat.getIndex();
    }

    //getters
    public char getRow() {
        return row;
    }

    public int getIndex() {
        return index;
    }

    public String getSeatLocation() {
        return Character.toString(row) + Integer.toString(index);
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    //getter for seat number
    public String getSeatNumber(){
        int seatNumber = row - 65;
        seatNumber *= 3;
        seatNumber += index-1;
        return Integer.toString(seatNumber);
    }

}
