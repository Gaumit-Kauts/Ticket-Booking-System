import java.util.*;
import java.util.regex.*;

public class UnregisteredUser implements User {

    public UnregisteredUser() {}

    //register an unregistered user and convert them to a registered user
    public static RegisteredUser register(String name, String email, String password, 
    String address, String possibleBillingInfo) {
        String regex = "^(.+)@(\\S+)$";
        long billingInfo = 0;
        
        //check billing info valid
        try {
            billingInfo = Long.parseLong(possibleBillingInfo);
        } catch(NumberFormatException e){
            System.out.println("ERROR: billing info not a numeric value");
            return null;
        }

        //check if email is in correct format "-----" + @ + "-----"
        if (!Pattern.compile(regex).matcher(email).matches()) {
            System.out.println("ERROR: Invalid Email");
            return null;
        }

        //set up database connector
        DatabaseConnector c = DatabaseConnector.getOnlyInstance();

        //check if email exists
        if (c.checkEmailExists(email)) {
            System.out.println("Email already exists!");
            return null;
        }
        
        //check if billingInfo is 16 digits long
        billingInfo = Math.abs(billingInfo);
        if (String.valueOf(billingInfo).length() != 16) {
            System.out.println("Issues with credit card numbers");
            return null;
        }
        
        RegisteredUser newUser = new RegisteredUser(name, address, email, 
                                password, billingInfo, 0);
        
        System.out.println("Registration Successful.");
        //register the new registered user into the database.
        return newUser;
    }

    @Override
    public void cancelTicket(String code){
        return;
    }

    @Override
    public int getCredits() {
        return 0;
    }
}