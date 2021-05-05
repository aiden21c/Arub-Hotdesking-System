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
    username TEXT PRIMARY KEY,
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
    blockOut BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS BlockOut;

CREATE TABLE BlockOut (
      seatNo INTEGER NOT NULL ,
      date DATE NOT NULL,

      PRIMARY KEY (seatNo, date),

      FOREIGN KEY (seatNo)
          REFERENCES Seat (seatNo)
          ON UPDATE CASCADE
          ON DELETE CASCADE
);


DROP TABLE IF EXISTS WhiteList;

CREATE TABLE WhiteList (
    username TEXT NOT NULL,
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
    seatNo INTEGER NOT NULL,
    username TEXT NOT NULL,
--  False if booking is active
    pending BOOLEAN NOT NULL,
    date DATE NOT NULL,

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









