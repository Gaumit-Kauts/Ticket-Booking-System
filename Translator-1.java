
//translates movies and other data from the ticket codes used in this program.
public class Translator {
    public static String[] theater = {"Jacked Flamingo Cinema", "Big Ape Theater", "Cherry Blossom Cineplex", 
    "Bollywood Picture Palace"};
    public static String[] movie = {"Avatar", "Top Gun", "Black Adam", "Wakanda Forever", "Dr. Strange", 
    "Puss In Boots", "Babylon", "Devotion", "Inception", "Insidious"};
    public static String[] date = {"2022-12-08", "2022-12-09", "2022-12-10", "2022-12-11", "2022-12-12", 
    "2022-12-13", "2022-12-14", "2022-12-15", "2022-12-16", "2022-12-17"};
    public static String[] time = {"12:00", "14:00", "15:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
    public static String[] seat = {"A1","A2","A3","B1","B2","B3","C1","C2","C3"};

    //getters
    public static String getTheater(int index){
        return theater[index];
    }
    public static String getMovie(int index){
        return movie[index];
    }
    public static String getDate(int index){
        return date[index];
    }
    public static String getTime(int index){
        return time[index];
    }
    public static String getSeat(int index){
        return seat[index];
    }


}
