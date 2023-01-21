import java.util.*;

public class RegisteredUser implements User {
    private ArrayList<Ticket> tickets;
    private DatabaseConnector instance = DatabaseConnector.getOnlyInstance();
    private String name;
    private String address;
    private String password;
    private String email;
    private long billingInfo;
    private int credits;

    //constructor
    public RegisteredUser(String name, String address, String email, 
    String password, long billingInfo, int credits) {

        this.name = name;
        this.address = address;
        this.password = password;
        this.email = email;
        this.billingInfo = billingInfo;
        this.credits = credits;
        this.tickets = new ArrayList<Ticket>();

        if (!instance.checkEmailExists(email)) {
            instance.insert_user(email, password, address, String.valueOf(billingInfo), credits);
        }
    }
    //getters
    public String getString() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public long getBillingInfo() {
        return billingInfo;
    }

    @Override
    public int getCredits() {
        return credits;
    }

    //set ticket
    @Override
    public void cancelTicket(String code) {
        instance.cancelTicket(code);
    }

}
