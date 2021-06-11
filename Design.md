# Design Decisions and Refactoring

### Design Decisions
The overall design of this project was based on the MVC design pattern. 
This involved having all source code within the `java` directory, split up 
by `src` and `test`. Furthermore, the `src` directory was split up into a controller 
package for the UI controller classes, a model for the back-end code, and a UI package 
for the FXML files. The `model` package was further split up into classes that represent 
objects, and the `dao` directory used for passing information to, and retrieving from the 
database. Within all the packages, sub-packages were made to further categorise the classes 
and group them based on similar functionalities.

The singleton design pattern was used within the controller class as a way of passing information 
between different scenes in the program. It was found that common information (such as the current 
logged-in user and their current booking) were required to be accessed from inside various controller 
classes, and hence singleton classes were used to ensure the these objects were restricted to only one 
instance.

### Refactoring
A main example of refactoring within this project was utilising the abstract controller to hold a universal 
`back` method for the controller of each scene to utilise. Originally, the back button within the scene would 
be hard coded to take the user back to the previous scene, and this previous scene would have to be hard coded 
within that specific controller. Upon refactoring, an arraylist of Strings was instantiated within the Main 
method, and this arraylist would be added to with a new String of the FXML file of each new scene created. Hence, 
when utilising the back button, the back button method in the abstract controller would now be called, and it 
simply take the user back to the previous scene that were on as this would be the most recent entry in the arraylist.

Another example of refactoring was implementing the singleton class to hold instances of variables needing to be accessed 
by multiple scenes. Originally, these variables were simply names as public within the classes they were created in, and 
then accessed from any other controller class that needed them. Introducing the singleton classes allowed for encapsulation 
to be upheld and ensured that each controller class would no longer have to have variables in the public scope.

Finally, the generateReports method was refactored to ensure that the common functionalty was placed in the abstract DAO 
class rather than each DAO class having their own method. The generate reports method was essentially the same for every 
database table, the only difference being the table name. Hence, this common functionality was refactored to be in the 
abstract DAO class, where each child class would call `super.export` and pass their table name into the method. The 
abstract DAO class was then able to generate the reports regardless of the table name.