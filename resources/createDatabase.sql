DROP TABLE IF EXISTS Employee; 

CREATE TABLE Employee (
    empID INTEGER PRIMARY KEY,
    firstName TEXT NOT NULL,
    lastName TEXT NOT NULL,
    role TEXT NOT NULL,
    age INTEGER,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    -- True if Admin; False Otherwise
    isAdmin BOOLEAN
);

DROP TABLE IF EXISTS SecretQuestion; 

CREATE TABLE SecretQuestion (
    empID INTEGER PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,

    FOREIGN KEY (empID)
        REFERENCES Employee (empID)
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
    empID INTEGER NOT NULL,
    seatNo INTEGER NOT NULL,

    PRIMARY KEY (empID, seatNo),

    FOREIGN KEY (empID)
        REFERENCES Employee (empID)
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
    empID INTEGER NOT NULL,
    -- 0- Active; 1- Completed; 2- Cancelled;
    active INTEGER NOT NULL, 
    date DATE NOT NULL,

    FOREIGN KEY (empID)
        REFERENCES Employee (empID)
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    FOREIGN KEY (seatNo)
        REFERENCES Seat (seatNo)
            ON UPDATE CASCADE
            ON DELETE CASCADE

);