DROP TABLE IF EXISTS Employee; 

CREATE TABLE Employee (
    empID INTEGER NOT NULL UNIQUE,
    firstName TEXT NOT NULL,
    lastName TEXT NOT NULL,
    role TEXT NOT NULL,
    age INTEGER,
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL,
    -- True if Admin; False Otherwise
    isAdmin BOOLEAN
);

DROP TABLE IF EXISTS SecretQuestion; 

CREATE TABLE SecretQuestion (
    username INTEGER PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,

    FOREIGN KEY (username)
        REFERENCES Employee (username)
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

DROP TABLE IF EXISTS Seat; 

CREATE TABLE Seat (
    seatNo INTEGER PRIMARY KEY,
    -- 0- Available; 1- Booked; 2- Blockout;
    available INTEGER NOT NULL
);

DROP TABLE IF EXISTS WhiteList;

CREATE TABLE WhiteList (
    username INTEGER NOT NULL,
    seatNo INTEGER NOT NULL,

    PRIMARY KEY (username, seatNo),

    FOREIGN KEY (username)
        REFERENCES Employee (username)
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    FOREIGN KEY (seatNo)
        REFERENCES Seat (seatNo)
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

DROP TABLE IF EXISTS Booking;

CREATE TABLE Booking (
    bookingNo INTEGER PRIMARY KEY,
    seatNo INTEGER NOT NULL,
    username INTEGER NOT NULL,
    -- 0- Active; 1- Completed; 2- Cancelled;
    active INTEGER NOT NULL, 
    date DATE NOT NULL,

    FOREIGN KEY (username)
        REFERENCES Employee (username)
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    FOREIGN KEY (seatNo)
        REFERENCES Seat (seatNo)
            ON UPDATE CASCADE
            ON DELETE CASCADE

);