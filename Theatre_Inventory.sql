
CREATE TABLE Reg_User(
    user_id INT PRIMARY KEY auto_increment,
    user_email VARCHAR(30) ,
    user_password VARCHAR(50)NOT NULL,
    user_address VARCHAR(200)NOT NULL,
    user_billing VARCHAR(16) NOT NULL,
    user_credits int
);

CREATE TABLE unregistered_user(
    user_email VARCHAR(30) NOT NULL
);

CREATE TABLE reserved_seats(
    theatre_name varchar(100),
    movie_name VARCHAR(100),
    movie_showDate VARCHAR(150),
    movie_showTime VARCHAR(150),
    seat_number VARCHAR(10),
    ticket_code VARCHAR(100),
    user_email VARCHAR(100)
);

CREATE TABLE available_seats (
    theatre_name varchar(100),
    movie_name VARCHAR(100),
    movie_showDate VARCHAR(100),
    movie_showTime VARCHAR(150),
    seat_number VARCHAR(10),
    ticket_code VARCHAR(100)
);







