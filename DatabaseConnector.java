import java.sql.*;
import java.util.*;

public class DatabaseConnector { 
    private static volatile DatabaseConnector instance; //singleton instance

    private Connection dbConnect; //connection object
    private ResultSet result; //resultset object

    private DatabaseConnector() {}

    public static DatabaseConnector getOnlyInstance() { //singleton constructor
        if (instance == null) {
            synchronized(DatabaseConnector.class) { 
                if (instance == null) {
                    instance = new DatabaseConnector();
                }
            }
        }
        instance.createConnection(); //start SQL connection
        return instance;
    }

    //make SQL connection
    public void createConnection() { // create a connection
        try {
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/Theatre_Inventory", 
            "root", "sqlpassword");
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    //insert user to database
    public void insert_user(String user_email, String user_password, String user_address,
    String user_billing, int user_credits) { //inserting a new user
        try {

            String query = "INSERT INTO Reg_User(user_email,user_password,user_address,user_billing,user_credits) VALUES(?,?,?,?,?)";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
     
            //add statements
            myStmt.setString(1, user_email);
            myStmt.setString(2, user_password);
            myStmt.setString(3, user_address);
            myStmt.setString(4, user_billing);
            myStmt.setInt(5, user_credits);
            
            int rowCount = myStmt.executeUpdate();

            myStmt.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    //insert seat into database
    public void insert_seats(String theatre_name, String movie_name, String movie_showdate, 
    String movie_showtime, String seat_number, String ticket_code) {
        try {
            String query = "INSERT INTO available_seats(theatre_name, movie_name, movie_showDate,movie_showTime,seat_number,ticket_code) VALUES(?,?,?,?,?,?)";
            PreparedStatement myStmt=dbConnect.prepareStatement(query);
     
            //add statements
            myStmt.setString(1, theatre_name);
            myStmt.setString(2, movie_name);
            myStmt.setString(3, movie_showdate);
            myStmt.setString(4, movie_showtime);
            myStmt.setString(5, seat_number);
            myStmt.setString(6, ticket_code);
        
            myStmt.executeUpdate();

            myStmt.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    //check if email exists
    public boolean checkEmailExists(String user_email) {
        boolean emailExists = false; //check if email exists

        try {
            String query = "SELECT * FROM Reg_User where user_email = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query); //creating connection
            myStmt.setString(1, user_email);
            result = myStmt.executeQuery();
            emailExists = result.next();
            
            myStmt.close(); //closing connection
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return emailExists;
    }

    //takes available ticket and assigns to a user
    public void assignTicket(String ticket_code, String user_email) {
        try {

            String query = "SELECT * FROM available_seats WHERE ticket_code = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query); //creating connection
            myStmt.setString(1, ticket_code);
            result = myStmt.executeQuery();
            result.next();

            String query2 = "INSERT INTO reserved_seats(theatre_name, movie_name, movie_showDate,movie_showTime,seat_number,ticket_code,user_email) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement myStmt2 = dbConnect.prepareStatement(query2);
            
            //add statements
            myStmt2.setString(1, result.getString("theatre_name"));
            myStmt2.setString(2, result.getString("movie_name"));
            myStmt2.setString(3, result.getString("movie_showdate"));
            myStmt2.setString(4, result.getString("movie_showtime"));
            myStmt2.setString(5, result.getString("seat_number"));
            myStmt2.setString(6, ticket_code);
            myStmt2.setString(7, user_email);
            myStmt2.executeUpdate();

            String query3 = "DELETE FROM available_seats WHERE ticket_code = ?";
            PreparedStatement myStmt3 = dbConnect.prepareStatement(query3); //creating connection
            myStmt3.setString(1, ticket_code);
            myStmt3.executeUpdate();

            
            myStmt.close(); //closing connection
            myStmt2.close();
            myStmt3.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //takes assigned ticket and unassigns it, adding it back to available tickets, returns if valid
    public boolean cancelTicket(String ticket_code) {
        try {

            String query = "SELECT * FROM reserved_seats WHERE ticket_code = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query); //creating connection
            myStmt.setString(1, ticket_code);
            result = myStmt.executeQuery();
            if (!result.next()) {
                return false;
            }


            String query2 = "INSERT INTO available_seats(theatre_name, movie_name, movie_showDate,movie_showTime,seat_number,ticket_code) VALUES(?,?,?,?,?,?)";
            PreparedStatement myStmt2 = dbConnect.prepareStatement(query2);
            
            //set statements
            myStmt2.setString(1, result.getString("theatre_name"));
            myStmt2.setString(2, result.getString("movie_name"));
            myStmt2.setString(3, result.getString("movie_showdate"));
            myStmt2.setString(4, result.getString("movie_showtime"));
            myStmt2.setString(5, result.getString("seat_number"));
            myStmt2.setString(6, ticket_code);
            myStmt2.executeUpdate();

            //set query
            String query3 = "DELETE FROM reserved_seats WHERE ticket_code = ?";
            PreparedStatement myStmt3 = dbConnect.prepareStatement(query3); //creating connection
            myStmt3.setString(1, ticket_code);
            myStmt3.executeUpdate();

            
            myStmt.close(); //closing connection
            myStmt2.close();
            myStmt3.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    //check if user is valid and in database
    public RegisteredUser checkAuthentication(String user_email, String user_password) {
        //if email doesn't exist in database, don't even bother with the rest
        if (!checkEmailExists(user_email)) {
            return null;
        }
        RegisteredUser user = null;
        //ok we made it here, now check if password matches email
        try {
            String query = "SELECT * FROM Reg_User WHERE user_email = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query); //creating connection
            myStmt.setString(1, user_email);
            result = myStmt.executeQuery();
            result.next();
            
           // success = result.getString("user_password").equals(user_password);

            if (result.getString("user_password").equals(user_password)) {
                user = new RegisteredUser(result.getString("user_email"), 
                    result.getString("user_address"), result.getString("user_email"),
                    result.getString("user_password"), Long.parseLong(result.getString("user_billing")),
                    Integer.parseInt(result.getString("user_credits")));
            }
            
            myStmt.close(); //closing connection
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    //set the seats from the database
    public void setSeatsFromDatabase (Showtime st) {
        String showCode = st.getShowtimeCode();
        showCode = showCode.replaceFirst("^0+(?!$)", "");
        try {
            for (int i = 0; i < 9; i++) {
                String query = "SELECT * FROM available_seats WHERE ticket_code = ?";
                PreparedStatement myStmt = dbConnect.prepareStatement(query); //creating connection
                myStmt.setString(1, showCode + i);
                result = myStmt.executeQuery();
                if (result.next()) {
                    st.populateOneSeat(result.getString("seat_number"));
                }
                myStmt.close(); //closing connection
            }
        
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //get all of the info from tickets assigned to a user
    public ArrayList<String> getAllTicketInfo(String user_email) {
        ArrayList<String> info = new ArrayList<String>();
        try {

            String query = "SELECT * FROM reserved_seats WHERE user_email = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query); //creating connection
            myStmt.setString(1, user_email);
            result = myStmt.executeQuery();

            //get all statements and ticket info
            while (result.next()) {
                String ticket_info = "";
                ticket_info += (result.getString("theatre_name")) + " ";
                ticket_info += (result.getString("movie_name")) + " ";
                ticket_info += (result.getString("movie_showDate")) + " ";
                ticket_info += (result.getString("movie_showTime")) + " ";
                ticket_info += (result.getString("seat_number")) + " ";
                ticket_info += (result.getString("user_email")) + " ";
                ticket_info += (result.getString("ticket_code"));
            
                info.add(ticket_info);
            }
            
            myStmt.close(); //closing connection
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return info;
    }

    //set the credit amount for the registered user
    public void setCreditRegistered(String user_email, int credits) {
        try {

            String query = "UPDATE Reg_User SET user_credits = ? WHERE user_email = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query); //creating connection
            myStmt.setInt(1, credits);
            myStmt.setString(2, user_email);
            myStmt.executeUpdate();
            
            myStmt.close(); //closing connection
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //set the credit amount for the unregistered user
    public void setCreditUnregistered(String user_email, int credits) {
        try {

            String query = "UPDATE unregistered_user SET user_credits = ? WHERE user_email = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query); //creating connection
            myStmt.setInt(1, credits);
            myStmt.setString(2, user_email);
            myStmt.executeUpdate();
            
            myStmt.close(); //closing connection
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    //get the credit amount for a specific registered user
    public int getCreditRegistered(String user_email) {
        int credits = -1;
        try {

            String query = "SELECT * FROM Reg_User WHERE user_email = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query); //creating connection
            myStmt.setString(1, user_email);
            result = myStmt.executeQuery();
            if (!result.next()) {
                return credits;
            }

            credits = result.getInt("user_credits");
            
            myStmt.close(); //closing connection
        } catch (SQLException ex) {
            ex.printStackTrace();
            return credits;
        }

        return credits;
    }

    //get the credit amount for a specific unregistered user
    public int getCreditUnregistered(String user_email) {
        int credits = -1;
        try {

            //activate query from SQL
            String query = "SELECT * FROM unregistered_user WHERE user_email = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query); //creating connection
            myStmt.setString(1, user_email);
            result = myStmt.executeQuery();
            if (!result.next()) {
                return credits;
            }

            credits = result.getInt("user_credits");
            
            myStmt.close(); //closing connection
        } catch (SQLException ex) {
            ex.printStackTrace();
            return credits;
        }

        return credits;
    }

    public void close() { //closing the connection
            try {
                //result.close();
                dbConnect.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }
    
}
