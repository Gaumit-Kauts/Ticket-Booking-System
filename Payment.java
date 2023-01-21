import java.util.*;
import java.util.regex.*;

public class Payment {
    public final static int OGPRICE = 20;
    private ArrayList<Seat> seatsToPurchase;
    private RegisteredUser theUser;
    private final String REGEX_EMAIL = "^(.+)@(\\S+)$";
    private final String REGEX_EXPIRY = "(?:0[1-9]|1[0-2])/[0-9]{2}";
    private DatabaseConnector DBconnector = DatabaseConnector.getOnlyInstance();
    private String showtimeCode; // 4 digit code where seat number can be added to get complete ticket code

    //payment constructor
    public Payment(ArrayList<Seat> seatsToPurchase, String showtimeCode) {
        this.showtimeCode = showtimeCode;
        this.seatsToPurchase = seatsToPurchase;
    }
    //payment constructor for registered
    public Payment(ArrayList<Seat> seatsToPurchase, RegisteredUser user, String showtimeCode){
        this.showtimeCode = showtimeCode;
        this.seatsToPurchase = seatsToPurchase;
        theUser = user;
    }

    //Unregistered User pay for ticket
    public int pay(String email, String possibleBillingInfo, String csv, String expiry) {
        long billingInfo = 0;
        int csvInt;
        
        //check billing info
        try {
            billingInfo = Long.parseLong(possibleBillingInfo);
        } catch(NumberFormatException e){
            System.out.println("ERROR: billing info not a numeric value");
            return -1;
        }

        //check csv
        try {
            csvInt = Integer.parseInt(csv);
        } catch (NumberFormatException e) {
            System.out.printf("\nERRORS: csv - %s not a numeric value", csv);
            return -1;
        }

        //check if email is in correct format "-----" + @ + "-----"
        if (!Pattern.compile(REGEX_EMAIL).matcher(email).matches()) {
            System.out.println("ERROR: Invalid Email");
            return -1;
        }
        //check billing info
        billingInfo = Math.abs(billingInfo);
        if (String.valueOf(billingInfo).length() != 16) {
            System.out.println("Issues with credit card numbers");
            return -1;
        }
        //check csv
        if (String.valueOf(csv).length() != 3) {
            System.out.println("ERROR: CSV is of incorrect format");
            return -1;
        }
        //check expiry date
        if (!Pattern.compile(REGEX_EXPIRY).matcher(expiry).matches()) {
            System.out.println("ERROR: Expiry Date is of incorrect format");
            return -1;
        }

        // =------------------------ Info is valid ------------------------------=

        int price2Pay = OGPRICE * seatsToPurchase.size();

        int credit = DBconnector.getCreditUnregistered(email);

        if(credit == -1) {
            System.out.printf("\nEmail was not registered");
            credit = 0;
        }
        if(credit == 0) {
            System.out.printf("\nNo credit available.");

        } else {
            System.out.printf("\nCredit : %d", credit);
        }

        //check movie price and subtract credit
        if(credit < OGPRICE) {
            price2Pay -= credit;
            DBconnector.setCreditUnregistered(email, 0);
        } else if(credit >= OGPRICE) {
            price2Pay = 0;
            DBconnector.setCreditUnregistered(email, credit - OGPRICE);
        }

        //purchase all selected seats and update DATABASE
        for (Seat seat : seatsToPurchase){
            String ticketCode = showtimeCode + seat.getSeatNumber();
            DBconnector.assignTicket(ticketCode, email);
        }

        return price2Pay;
    }

    //Registered User pay for ticket
    public int pay(String csv, String expiry) {
        int csvInt;
        try {
            csvInt = Integer.parseInt(csv);
        } catch (NumberFormatException e) {
            System.out.printf("\nERRORS: csv - %s not a numeric value", csv);
            return -1;
        }

        if (String.valueOf(csvInt).length() != 3) {
            System.out.println("ERROR: CSV is of incorrect format");
            return -1;
        }
        if (!Pattern.compile(REGEX_EXPIRY).matcher(expiry).matches()) {
            System.out.println("ERROR: Expiry Date is of incorrect format");
            return -1;
        }

        // =------------------------ Info is valid ------------------------------=

        int price2Pay = OGPRICE * seatsToPurchase.size();

        int credit = DBconnector.getCreditRegistered(theUser.getEmail());
        
        if(credit == 0) {
            System.out.printf("\nNo credit available.");

        } else {
            System.out.printf("\nCredit : %d", credit);
        }

        //check that credit is applied accordingly
        if(credit < OGPRICE) {
            price2Pay -= credit;
            DBconnector.setCreditRegistered(theUser.getEmail(), 0);
        } else if(credit >= OGPRICE) {
            price2Pay = 0;
            DBconnector.setCreditRegistered(theUser.getEmail(), credit - OGPRICE);
        }

        //loop through and register all selected seats
        for (Seat seat : seatsToPurchase){
            String ticketCode = showtimeCode + seat.getSeatNumber();
            DBconnector.assignTicket(ticketCode, theUser.getEmail());
        }

        return price2Pay;
    }


}
