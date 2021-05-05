-- Add dummy data to the database

-- Add 6 seats to the database
INSERT INTO Seat VALUES (1, false);
INSERT INTO Seat VALUES (2, true); -- Currently locked indefinitely
INSERT INTO Seat VALUES (3, true); -- Currently locked indefinitely
INSERT INTO Seat VALUES (4, true); -- Currently locked indefinitely
INSERT INTO Seat VALUES (5, false);
INSERT INTO Seat VALUES (6, false);

-- BlockOut seats for certain dates
INSERT INTO BlockOut VALUES (1, DATE('2021-06-14'));
INSERT INTO BlockOut VALUES (3, DATE('2021-06-14'));
INSERT INTO BlockOut VALUES (5, DATE('2021-06-14'));
INSERT INTO BlockOut VALUES (2, DATE('2021-06-15'));
INSERT INTO BlockOut VALUES (4, DATE('2021-06-15'));
INSERT INTO BlockOut VALUES (6, DATE('2021-06-15'));

-- Insert 4 Employees into the database
INSERT INTO Employee VALUES (294873, 'John', 'Snow', 'Project Manager', 23, 'john.snow', 'password', false);
INSERT INTO SecretQuestion VALUES ('john.snow', 'What is your fathers name?', 'Eddard Stark');

INSERT INTO Employee VALUES (294874, 'Jack', 'White', 'Developer', 45, 'jack.white', 'password', false);
INSERT INTO SecretQuestion VALUES ('jack.white', 'What is your favourite band', 'The White Stripes');

INSERT INTO Employee VALUES (294875, 'Ned', 'Flanders', 'Pastor', 52, 'ned.flanders', 'password', false);
INSERT INTO SecretQuestion VALUES ('ned.flanders', 'What religion do you follow?', 'Christianity');

INSERT INTO Employee VALUES (294876, 'Bender', 'Rodriguez', 'Bending Unit', 25, 'bender.rodriguez', 'password', false);
INSERT INTO SecretQuestion VALUES ('bender.rodriguez', 'Who is your best friend?', 'Philip Fry');

-- Insert 2 Admins
INSERT INTO Employee VALUES (294877, 'Bob', 'belcher', 'Cook', 47, 'bob.belcher', 'password', true);
INSERT INTO SecretQuestion VALUES ('bob.belcher', 'What is your youngest daughters name?', 'Louise Belcher');

INSERT INTO Employee VALUES (294878, 'Malory', 'Archer', 'CEO', 65, 'malory.archer', 'password', true);
INSERT INTO SecretQuestion VALUES ('malory.archer', 'What is your sons name?', 'Sterling Archer');

-- Add data to the whitelist for all users
--     John Snow has never had a previous booking
INSERT INTO WhiteList VALUES ('john.snow', 1);
INSERT INTO WhiteList VALUES ('john.snow', 2);
INSERT INTO WhiteList VALUES ('john.snow', 3);
INSERT INTO WhiteList VALUES ('john.snow', 4);
INSERT INTO WhiteList VALUES ('john.snow', 5);
INSERT INTO WhiteList VALUES ('john.snow', 6);

--     Jack White previously sat in seat 4
INSERT INTO WhiteList VALUES ('jack.white', 1);
INSERT INTO WhiteList VALUES ('jack.white', 2);
INSERT INTO WhiteList VALUES ('jack.white', 3);
INSERT INTO WhiteList VALUES ('jack.white', 5);
INSERT INTO WhiteList VALUES ('jack.white', 6);

--     Ned Flanders has never had a previous booking
INSERT INTO WhiteList VALUES ('ned.flanders', 1);
INSERT INTO WhiteList VALUES ('ned.flanders', 2);
INSERT INTO WhiteList VALUES ('ned.flanders', 3);
INSERT INTO WhiteList VALUES ('ned.flanders', 4);
INSERT INTO WhiteList VALUES ('ned.flanders', 5);
INSERT INTO WhiteList VALUES ('ned.flanders', 6);

--     Bender Rodriguez has never had a previous booking
INSERT INTO WhiteList VALUES ('bender.rodriguez', 1);
INSERT INTO WhiteList VALUES ('bender.rodriguez', 2);
INSERT INTO WhiteList VALUES ('bender.rodriguez', 3);
INSERT INTO WhiteList VALUES ('bender.rodriguez', 4);
INSERT INTO WhiteList VALUES ('bender.rodriguez', 5);
INSERT INTO WhiteList VALUES ('bender.rodriguez', 6);

--     Bob Belcher previously sat in seat 3
INSERT INTO WhiteList VALUES ('bob.belcher', 1);
INSERT INTO WhiteList VALUES ('bob.belcher', 2);
INSERT INTO WhiteList VALUES ('bob.belcher', 4);
INSERT INTO WhiteList VALUES ('bob.belcher', 5);
INSERT INTO WhiteList VALUES ('bob.belcher', 6);

--     Malory Archer previously sat in seat 1
INSERT INTO WhiteList VALUES ('malory.archer', 2);
INSERT INTO WhiteList VALUES ('malory.archer', 3);
INSERT INTO WhiteList VALUES ('malory.archer', 4);
INSERT INTO WhiteList VALUES ('malory.archer', 5);
INSERT INTO WhiteList VALUES ('malory.archer', 6);

-- Insert current bookings
INSERT INTO Booking VALUES (5, 'malory.archer', false, DATE('2021-06-18'));
INSERT INTO Booking VALUES (3, 'jack.white', false, DATE('2021-06-18'));
INSERT INTO Booking VALUES (1, 'ned.flanders', true, DATE('2021-06-22'));
INSERT INTO Booking VALUES (1, 'bender.rodriguez', true, DATE('2021-06-23'));