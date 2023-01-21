// Developed by Brenek Spademan for ENSF 480 group 1

//-- Imports --
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI extends JFrame implements ActionListener{
    // -- Member Variables --
    private RegisteredUser theUser;
    private JPanel headerPanel;
    private JPanel mainPanel;
    private JPanel warningPanel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel warningLabel;
    private JTextField nameInput;
    private JTextField emailInput;
    private JTextField passwordInput;
    private JTextField addressInput;
    private JTextField creditCardInput;
    private ArrayList<Theater> theaters;
    private JTextField adminInput;
    private String adminPassword;
    private JComboBox<String> theaterDropDown;
    private Theater currentTheater;
    private JComboBox<String> moviesInTheater;
    private JComboBox<String> showtimesForMovie;
    private JComboBox<String> availableSeats;
    private JTextField newMovieNumber;
    private Movie currentMovie;
    private Showtime currentShowtime;
    private JTextField newTimeInput;
    private JTextField showtimeDayInput;
    private ArrayList<Seat> possibleBookedSeats;
    private JComboBox<String> possibleSeatsDropdown;
    private DatabaseConnector DBconnector = DatabaseConnector.getOnlyInstance();
    private JTextField securityInput;
    private JTextField expiryInput;
    private int pricePaid;
    private ArrayList<String> ticketsForEmail;
    private JComboBox<String> ticketsDropdown;

    // -- Constructor --
    public GUI(){
        // set up initial screen
        super("Movie Menu");
        setupScreen1();
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // initialize member variables
        theaters = new ArrayList<Theater>();
        theaterDropDown = new JComboBox<String>();
        theaters.add(new Theater("Jacked Flamingo Cinema", 0));
        theaterDropDown.addItem(theaters.get(0).getName());
        theaters.add(new Theater("Big Ape Theater", 1));
        theaterDropDown.addItem(theaters.get(1).getName());
        theaters.add(new Theater("Cherry Blossom Cineplex", 2));
        theaterDropDown.addItem(theaters.get(2).getName());
        theaters.add(new Theater("Bollywood Picture Palace", 3));
        theaterDropDown.addItem(theaters.get(3).getName());
        adminPassword = "Beans";
        possibleBookedSeats = new ArrayList<Seat>();
    }

	// displays inforation for the first screen
    public void setupScreen1(){
        //Button set up
        JButton seeTheaters = new JButton("Theaters");
        seeTheaters.addActionListener(this);
        JButton accountLogin = new JButton("Account Login");
        accountLogin.addActionListener(this);
        JButton admin = new JButton("Admin");
        admin.addActionListener(this);
        JButton viewTickets = new JButton("View Tickets");
        viewTickets.addActionListener(this);

        //Panel set up 
        headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        warningPanel = new JPanel();
        warningPanel.setLayout(new FlowLayout());

        // set up multi use labels
        emailLabel = new JLabel("Email:");
		emailInput = new JTextField(7);
		passwordLabel = new JLabel("Password:");
        passwordInput = new JPasswordField(7);

        // add to panels
        warningLabel = new JLabel();
        warningPanel.add(warningLabel);
        headerPanel.add(admin);
        headerPanel.add(accountLogin);
        headerPanel.add(seeTheaters);
        headerPanel.add(viewTickets);

		//Add panels to frame
		this.add(headerPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(warningPanel, BorderLayout.SOUTH);
        frameRefresh();

    }

    // display information for the login screen
    public void setupAccountLoginScreen(){
        resetMainPanel();
        //Button set up
        JButton login = new JButton("Login");
        login.addActionListener(this);
        JButton register = new JButton("Register");
        register.addActionListener(this);

        // Add to panels 
        mainPanel.add(emailLabel);
        mainPanel.add(emailInput);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordInput);
        mainPanel.add(login);
        mainPanel.add(register);
        frameRefresh();
    }

    // display infromation for registration screen
    public void registerScreen(){
        resetMainPanel();
        //Label and text field set up
        JLabel nameLabel = new JLabel("Full Name:");
        nameInput = new JTextField(7);
        JLabel addressLabel = new JLabel("Address:");
        addressInput = new JTextField(7);
        JLabel creditCardLabel = new JLabel("Credit Card Number:");
        creditCardInput = new JTextField(7);
        JLabel securityLabel = new JLabel("Security Code:");
        securityInput = new JTextField(3);
        JLabel expiryDate = new JLabel("Expiry Date:");
        expiryInput = new JTextField("MM/YY", 4);
        JLabel annualFee = new JLabel("There is a $20 annual fee to be a registered member");

        //Button set up
        JButton regAccount = new JButton("Register Account");
        regAccount.addActionListener(this);

        //Add to Panels
        mainPanel.add(nameLabel);
        mainPanel.add(nameInput);
        mainPanel.add(emailLabel);
        mainPanel.add(emailInput);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordInput);
        mainPanel.add(addressLabel);
        mainPanel.add(addressInput);
        mainPanel.add(creditCardLabel);
        mainPanel.add(creditCardInput);
        mainPanel.add(securityLabel);
        mainPanel.add(securityInput);
        mainPanel.add(expiryDate);
        mainPanel.add(expiryInput);
        mainPanel.add(annualFee);
        mainPanel.add(regAccount);
        //Add panels to frame
        this.add(mainPanel, BorderLayout.CENTER);
        frameRefresh();
    }

    // display information for theater screen
    public void theaterSelectionScreen() {
        resetMainPanel();
        // component set up
        JLabel theaterLabel = new JLabel("Select Theater:");
        JButton Jacked_Flamingo_Cinema = new JButton(theaters.get(0).getName());
        Jacked_Flamingo_Cinema.addActionListener(this);
        JButton Big_Ape_Theater = new JButton(theaters.get(1).getName());
        Big_Ape_Theater.addActionListener(this);
        JButton Cherry_Blossom_Cineplex = new JButton(theaters.get(2).getName());
        Cherry_Blossom_Cineplex.addActionListener(this);
        JButton Bollywood_Picture_Palace = new JButton(theaters.get(3).getName());
        Bollywood_Picture_Palace.addActionListener(this);

        // add components to panel
        mainPanel.add(theaterLabel);
        mainPanel.add(Jacked_Flamingo_Cinema);
        mainPanel.add(Big_Ape_Theater);
        mainPanel.add(Cherry_Blossom_Cineplex);
        mainPanel.add(Bollywood_Picture_Palace);

        this.add(mainPanel, BorderLayout.CENTER);
        frameRefresh();
    }

    // display information so that the admin can login 
    public void adminLoginScreen(){
        resetMainPanel();

        // component set up
        JLabel adminInfo = new JLabel("Enter admin password:");
        adminInput = new JPasswordField(7);
        JButton adminLogin = new JButton("Admin Login");
        adminLogin.addActionListener(this);

        // add components to panel
        mainPanel.add(adminInfo);
        mainPanel.add(adminInput);
        mainPanel.add(adminLogin);
        frameRefresh();
    }

    // display information so that admin can select what theater they want to edit information for 
    public void adminTheaterScreen(){
        resetMainPanel();

        // component set up
        JButton adminSelect = new JButton("Select");
        adminSelect.addActionListener(this);

        // add components to panel
        mainPanel.add(theaterDropDown); 
        mainPanel.add(adminSelect);
        frameRefresh();
    }

    // display info so that admin can edit movies for a particular theater
    public void editMovieScreen(){
        resetMainPanel();

        // component set up
        moviesInTheater = new JComboBox<String>();
        for (Movie movie : currentTheater.getMovies()){
            moviesInTheater.addItem(movie.getName());
        }
        JButton removeMovie = new JButton("Remove Movie");
        removeMovie.addActionListener(this);
        JButton editShowtimes = new JButton("Edit Showtimes");
        editShowtimes.addActionListener(this);
        JLabel addMovieLabel = new JLabel("Enter the number of the movie you would like to add to this theater:");
        newMovieNumber = new JTextField(4);
        JButton addMovie = new JButton("Add Movie");
        addMovie.addActionListener(this);

        // add components to panel
        mainPanel.add((moviesInTheater));
        mainPanel.add(removeMovie);
        mainPanel.add(editShowtimes);
        mainPanel.add(addMovieLabel);
        mainPanel.add(newMovieNumber);
        mainPanel.add(addMovie);
        frameRefresh();
    }

    // allow admins to edit the showtimes for a movie at a particular theater
    public void editShowtimesScreen(){
        resetMainPanel();

        // component set up
        showtimesForMovie = new JComboBox<String>();
        for (Showtime showtime : currentMovie.getAllShowtimes()){
            showtimesForMovie.addItem(showtime.getDTG());
        }
        JButton removeTime = new JButton("Remove Showtime");
        removeTime.addActionListener(this);
        JLabel newTimeLabel = new JLabel("Enter the new time option:");
        newTimeInput = new JTextField(3);
        JLabel dayLabel = new JLabel("Enter the day option:");
        showtimeDayInput = new JTextField(3);
        JButton addTime = new JButton("Add Showtime");
        addTime.addActionListener(this);

        // add components to panel
        mainPanel.add(showtimesForMovie);
        mainPanel.add(removeTime);
        mainPanel.add(newTimeLabel);
        mainPanel.add(newTimeInput);
        mainPanel.add(dayLabel);
        mainPanel.add(showtimeDayInput);
        mainPanel.add(addTime);
        frameRefresh();
    }

    // display the movies at a theater to a user
    public void displayMovies(){
        resetMainPanel();
        // component set up
        moviesInTheater = new JComboBox<String>();
        for (Movie movie : currentTheater.getMovies()){
            moviesInTheater.addItem(movie.getName());
        }
        JButton viewShowtimes = new JButton("View Showtimes");
        viewShowtimes.addActionListener(this);

        // add components to panel 
        mainPanel.add(moviesInTheater);
        mainPanel.add(viewShowtimes);
        frameRefresh();
    }

    // display the showtimes for a movie to the user
    public void userShowtimeScreen(){
        resetMainPanel();
        // component set up
        JLabel timeLabel = new JLabel("Showtime:");
        showtimesForMovie = new JComboBox<String>();
        for (Showtime showtime : currentMovie.getAllShowtimes()){
            showtimesForMovie.addItem(showtime.getDTG());
        }
        JButton selectTime = new JButton("Select Showtime");
        selectTime.addActionListener(this);
        possibleBookedSeats = new ArrayList<Seat>();

        // add components to frame
        mainPanel.add(timeLabel);
        mainPanel.add(showtimesForMovie);
        mainPanel.add(selectTime);
        frameRefresh();
    }

    // display the seats to a user for a showtime
    public void seatDisplay(){
        resetMainPanel();
        // get seats from the database
        if (possibleBookedSeats.isEmpty()) DBconnector.setSeatsFromDatabase(currentShowtime);
        // component set up
        JLabel bookedSeatsLabel = new JLabel("Booked Seats:");
        possibleSeatsDropdown = new JComboBox<String>();
        for (Seat seat : possibleBookedSeats){
            System.out.println("In the loop");
            possibleSeatsDropdown.addItem(seat.getSeatLocation());
        }
        JButton purchaseSeats = new JButton("Purchase Seats");
        purchaseSeats.addActionListener(this);
        JButton moreSeats = new JButton("Book seat");
        moreSeats.addActionListener(this);
        JButton lessSeats = new JButton("Remove seat");
        lessSeats.addActionListener(this);
        JLabel availSeatsLabel = new JLabel("Available Seats:");
        availableSeats = new JComboBox<String>();
        for (int i=0; i < currentShowtime.getAllSeats().size(); i++){
            availableSeats.addItem(currentShowtime.getAllSeats().get(i).getSeatLocation());
        }

        // add components to panel
        mainPanel.add(bookedSeatsLabel);
        mainPanel.add(possibleSeatsDropdown);
        mainPanel.add(purchaseSeats);
        mainPanel.add(moreSeats);
        mainPanel.add(lessSeats);
        mainPanel.add(availSeatsLabel);
        mainPanel.add(availableSeats);
        frameRefresh();
    }

    // display purchase screen for unregistered user
    public void unregisteredPurchaseScreen(){
        resetMainPanel();
        // component set up
        JLabel seatsToPurchaseLabel = new JLabel("Purchase the following seats for $20 per seat");
        JLabel securityLabel = new JLabel("Security Code:");
        securityInput = new JTextField(3);
        JLabel expiryDate = new JLabel("Expiry Date:");
        expiryInput = new JTextField("MM/YY", 4);
        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.addActionListener(this);
        addressInput = new JTextField(7);
        JLabel creditCardLabel = new JLabel("Credit Card Number:");
        creditCardInput = new JTextField(7);

        // add components to panel
        mainPanel.add(seatsToPurchaseLabel);
        mainPanel.add(possibleSeatsDropdown);
        mainPanel.add(emailLabel);
        mainPanel.add(emailInput);
        mainPanel.add(creditCardLabel);
        mainPanel.add(creditCardInput);
        mainPanel.add(securityLabel);
        mainPanel.add(securityInput);
        mainPanel.add(expiryDate);
        mainPanel.add(expiryInput);
        mainPanel.add(purchaseButton);
        frameRefresh();
    }

    //dispaly purchase screen for registered user
    public void registeredPurchaseScreen(){
        resetMainPanel();
        // component set up
        JLabel seatsToPurchaseLabel = new JLabel("Purchase the following seats for $20 per seat");
        JLabel securityLabel = new JLabel("Security Code:");
        securityInput = new JTextField(3);
        JLabel expiryDate = new JLabel("Expiry Date:");
        expiryInput = new JTextField("MM/YY", 4);
        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.addActionListener(this);
        
        // add componenets to panel 
        mainPanel.add(seatsToPurchaseLabel);
        mainPanel.add(possibleSeatsDropdown);
        mainPanel.add(securityLabel);
        mainPanel.add(securityInput);
        mainPanel.add(expiryDate);
        mainPanel.add(expiryInput);
        mainPanel.add(purchaseButton);

        frameRefresh();
    }

    // screen showing receipt of ticket purchase
    public void afterPurchaseScreen(){
        resetMainPanel();
        // component set up
        String message = "The following seats were booked for " + currentMovie.getName() + " at " + 
        currentTheater.getName() + " for the showtime at " + currentShowtime.getDTG();
        JLabel messageLabel = new JLabel(message);
        String chargeTotal = "Credit card was charged $" + Integer.toString(pricePaid) + " rest was covered by account credit.";
        JLabel chargeLabel = new JLabel(chargeTotal);

        // add components to panel
        mainPanel.add(messageLabel);
        mainPanel.add(possibleSeatsDropdown);
        mainPanel.add(chargeLabel);
        frameRefresh();
    }

    // screen so that unregistered user can see their tickets
    public void enterEmailScreen(){
        resetMainPanel();
        // component set up
        JButton seeTicketsButton = new JButton("See tickets");
        seeTicketsButton.addActionListener(this);

        // add components to panel
        mainPanel.add(emailLabel);
        mainPanel.add(emailInput);
        mainPanel.add(seeTicketsButton);
        frameRefresh();
    }

    // show user their tickets and allow them to cancel 
    public void viewTickets(){
        resetMainPanel();
        // component set up
        JLabel usersTickets = new JLabel("Your tickets are:");
        ticketsDropdown = new JComboBox<String>();
        for (String ticketInfo : ticketsForEmail){
            ticketsDropdown.addItem(ticketInfo);
        }
        JButton cancelTicket = new JButton("Cancel Ticket");
        cancelTicket.addActionListener(this);

        // add components to panel
        mainPanel.add(usersTickets);
        mainPanel.add(ticketsDropdown);
        mainPanel.add(cancelTicket);
        frameRefresh();
    }

    // refresh frame after updating information
    private void frameRefresh(){
        //Refresh frame
		this.invalidate();
		this.validate();
		this.repaint();
    }

    // reset the main panel along with warning label so it is ready for next screen
    private void resetMainPanel(){
        if (warningLabel.getForeground() != Color.white){
            warningLabel.setText("");
        }
        warningLabel.setForeground(Color.blue);
        if (mainPanel != null){
            mainPanel.removeAll();
        }
    }

    @Override
    // listener functions for ActionEvents
    public void actionPerformed(ActionEvent event){
        try{
            if (event.getActionCommand().compareTo("Account Login") == 0){
                setupAccountLoginScreen();
            }
            else if (event.getActionCommand().compareTo("Theaters") == 0){
                theaterSelectionScreen();
            }
            else if (event.getActionCommand().compareTo("Admin") == 0){
                adminLoginScreen();
            }
            else if (event.getActionCommand().compareTo("View Tickets") == 0){
                if (theUser != null){ // for registered user
                    ticketsForEmail = DBconnector.getAllTicketInfo(theUser.getEmail()); // get tickets
                    viewTickets();
                }
                else { // for unregistered user
                    // go to intermediate screen before so unregistered user can enter email
                    enterEmailScreen();
                } 
            }
            else if (event.getActionCommand().compareTo("See tickets") == 0){
                // get tickets for unregistered user
                ticketsForEmail = DBconnector.getAllTicketInfo(emailInput.getText());
                viewTickets();
            }
            else if (event.getActionCommand().compareTo("Cancel Ticket") == 0){
                int index = ticketsDropdown.getSelectedIndex();
                String ticket = ticketsForEmail.get(index);
                String ticketCode = (ticket.substring(ticket.length()-5));
                DBconnector.cancelTicket(ticketCode);
                ticketsForEmail.remove(index);
                if (theUser != null){ // registered user
                    int credit = DBconnector.getCreditRegistered(theUser.getEmail());
                    credit += 20;
                    DBconnector.setCreditRegistered(theUser.getEmail(), credit); // update user credit
                    warningLabel.setText("Ticket was cancelled, and credit was increased by $20");
                    warningLabel.setForeground(Color.white);
                }
                else{ // unregistered user
                    int credit = DBconnector.getCreditUnregistered(emailInput.getText());
                    credit += 17;
                    DBconnector.setCreditUnregistered(emailInput.getText(), credit); // update user credit
                    warningLabel.setText("Ticket was cancelled, and credit was increased by $17");
                    warningLabel.setForeground(Color.white); 
                }
                viewTickets();
            }
            else if (event.getActionCommand().compareTo("Register") == 0){
                registerScreen(); // show registration screen
            }
            else if (event.getActionCommand().compareTo("Login") == 0){
                // authenticate user login
                theUser = DBconnector.checkAuthentication(emailInput.getText(), passwordInput.getText());
                if (theUser != null) { // login registered user
                    warningLabel.setText("Login Successful!");
                    warningLabel.setForeground(Color.white);
                    // Place holder change probably will go to a different screen in the future
                    theaterSelectionScreen();
                }  
                else{ // display warning message
                    warningLabel.setText("Login unsuccessful, please re-enter email and password, or register for an account");
                    warningLabel.setForeground(Color.red);
                    frameRefresh();
                } 
            }
            else if (event.getActionCommand().compareTo("Register Account") == 0){
                RegisteredUser possibleNewReg = UnregisteredUser.register(nameInput.getText(), emailInput.getText(), passwordInput.getText(), addressInput.getText(), creditCardInput.getText());
                if (possibleNewReg != null){ // account registered
                    warningLabel.setText("Registered successfully, account charge $20");
                    warningLabel.setForeground(Color.white);
                    setupAccountLoginScreen();
                }
                else{ // registration failed
                    warningLabel.setText("Please ensure all information is entered correctly!");
                    warningLabel.setForeground(Color.red);
                    frameRefresh();
                }
            }
            else if (event.getActionCommand().compareTo("Jacked Flamingo Cinema") == 0){
                currentTheater = theaters.get(0);
                displayMovies();
            }
            else if (event.getActionCommand().compareTo("Big Ape Theater") == 0){
                currentTheater = theaters.get(1);
                displayMovies();
            }
            else if (event.getActionCommand().compareTo("Cherry Blossom Cineplex") == 0){
                currentTheater = theaters.get(2);
                displayMovies();
            }
            else if (event.getActionCommand().compareTo("Bollywood Picture Palace") == 0){
                currentTheater = theaters.get(3);
                displayMovies();
            }
            else if (event.getActionCommand().compareTo("View Showtimes") == 0){
                int index = moviesInTheater.getSelectedIndex();
                currentMovie = currentTheater.getMovies().get(index);
                userShowtimeScreen(); // show showtimes
            }
            else if (event.getActionCommand().compareTo("Select Showtime") == 0){
                currentShowtime = currentMovie.getAllShowtimes().get(showtimesForMovie.getSelectedIndex());
                //display screen with seat buttons as well as button to pay for seats
                seatDisplay();
            }
            else if (event.getActionCommand().compareTo("Admin Login") == 0){
                if (adminInput.getText().equals(adminPassword)){ // matches admin password
                    adminTheaterScreen();
                }
                else{ // password does not match
                    warningLabel.setText("That is not the administration password! Get out of here you thief!!! or else we'll release our ape on you!");
                    warningLabel.setForeground(Color.red);
                    frameRefresh();
                }
            }
            else if (event.getActionCommand().compareTo("Select") == 0){
                int index = theaterDropDown.getSelectedIndex();
                currentTheater = theaters.get(index);
                editMovieScreen();
            }
            // remove movie from theater
            else if (event.getActionCommand().compareTo("Remove Movie") == 0){
                int index = moviesInTheater.getSelectedIndex();
                currentTheater.removeMovie(index);
                warningLabel.setText("Movie Removed Successfully");
                warningLabel.setForeground(Color.white);
                editMovieScreen();
            }
            else if (event.getActionCommand().compareTo("Edit Showtimes") == 0){
                int index = moviesInTheater.getSelectedIndex();
                currentMovie = currentTheater.getMovies().get(index);
                editShowtimesScreen();
            }
            // add showtime to movie
            else if (event.getActionCommand().compareTo("Add Showtime") == 0){
                try {
                    currentMovie.addShowtime(showtimeDayInput.getText(), newTimeInput.getText());
                    editShowtimesScreen();
                } catch (Exception e) {
                    warningLabel.setText("Invalid time format, please enter time and day option as a digit from 0-9");
                    warningLabel.setForeground(Color.red);
                }
            }
            // remove showtime from movie
            else if (event.getActionCommand().compareTo("Remove Showtime") == 0){
                currentMovie.removeShowtime(showtimesForMovie.getSelectedIndex());
                editShowtimesScreen();
            }
            // add movie to theater
            else if (event.getActionCommand().compareTo("Add Movie") == 0){
                try {
                    currentMovie = new Movie(newMovieNumber.getText(), currentTheater.getTheaterNumber());
                    currentTheater.addMovie(currentMovie);
                    warningLabel.setText("Movie Added Successfully");
                    warningLabel.setForeground(Color.white);
                    editMovieScreen();
                } catch (NumberFormatException e){
                    warningLabel.setText("Invalid entry please select a number from 0-9");
                    warningLabel.setForeground(Color.red);
                    frameRefresh();
                }
            }
            // take user to purchase page
            else if (event.getActionCommand().compareTo("Purchase Seats") == 0){
                if (theUser != null){ // registered user
                    registeredPurchaseScreen();
                }
                else{ // unregistered user
                    unregisteredPurchaseScreen();
                }
            }
            // complete transactoin 
            else if (event.getActionCommand().compareTo("Purchase") == 0){
                if (theUser != null){ // registered user
                    Payment thePayment = new Payment(possibleBookedSeats, theUser, currentShowtime.getShowtimeCode());
                    pricePaid = (thePayment.pay(securityInput.getText(), expiryInput.getText()));
                    if (pricePaid == -1){
                        warningLabel.setText("Invalid information entered");
                        warningLabel.setForeground(Color.red);
                        frameRefresh();
                    }
                    else afterPurchaseScreen();
                }
                else { // unregistered user
                    Payment thePayment = new Payment(possibleBookedSeats, currentShowtime.getShowtimeCode());
                    pricePaid = thePayment.pay(emailInput.getText(), creditCardInput.getText(), securityInput.getText(), expiryInput.getText());
                    if (pricePaid == -1){
                        warningLabel.setText("Invalid information entered");
                        warningLabel.setForeground(Color.red);
                        frameRefresh();
                    }
                    else afterPurchaseScreen();
                }
            }
            // book seat for showtime
            else if (event.getActionCommand().compareTo("Book seat") == 0){
                int index = availableSeats.getSelectedIndex();
                Seat temp = (Seat)currentShowtime.getAllSeats().get(index).clone();
                possibleBookedSeats.add(temp);
                currentShowtime.removeSeat(index);
                System.out.println(possibleBookedSeats.get(0).getSeatLocation());
                seatDisplay();
            }
            // remove seat so it is not booked
            else if (event.getActionCommand().compareTo("Remove seat") == 0){
                int index = possibleSeatsDropdown.getSelectedIndex();
                Seat temp = (Seat)possibleBookedSeats.get(index).clone();
                currentShowtime.addSeat(temp);
                possibleBookedSeats.remove(index);
                seatDisplay();
            }
        // catches any exception 
        } catch (Exception e) {}
    }

	/**
	 * Main method that initializes entire program.
	 * @param args All command line arguments will be ignorded by the program as they are not needed.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			new GUI().setVisible(true);
		});
	}
};
