package main.model.dao;

import main.SQLiteConnection;
import main.model.utilities.Utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;


public abstract class AbstractDAO {
    protected Connection connection;
    private BufferedWriter bw;

    /**
     * Constructor attempts to connect to the SQLite server.
     * If connection cannot be established, the program shall terminate.
     */
    public AbstractDAO() {
        try {
            connection = SQLiteConnection.connect();
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Could not connect to database");
            System.exit(1);
        }
    }

    /**
     * Inspired from <https://www.codejava.net/coding/java-code-example-to-export-from-database-to-csv-file>
     * Takes in the table name (or natural join) to export a CSV file for
     * @param sqlStatement the ending of a "Select * From" statement
     * @throws SQLException
     * @throws IOException
     */
    public void export(String sqlStatement) throws SQLException, IOException {
        String[] statementTerms = sqlStatement.split(" ");

        // Creates a new string with the name of the CSV file to be created
        String csvName = formatFileName(statementTerms[0]);
        // The SQL query to select all columns from the given table name
        String query = "SELECT * FROM " + sqlStatement;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        // Create a new BufferedWriter to the given file name
        bw = new BufferedWriter(new FileWriter(csvName));
        // Gets the number of columns contained within the table and writes the header line to the CSV file
        int colCount = writeHeaderLine(rs);

        // Writes each line to the CSV file
        while (rs.next()) {
            StringBuilder ln = new StringBuilder();

            // Adds each column to this row of the CSV file
            for (int i = 1; i <= colCount; i++) {
                // Gets the object from the given column in the results set
                Object o = rs.getObject(i);
                String str = "";

                // Correctly formats the object obtained to a string value
                if (o != null) {
                    str = o.toString();
                    if (o instanceof String) {str = "\"" + Utilities.escapeDoubleQuotes(str) + "\"";}
                }
                // Adds this column to this row of the CSV file
                ln.append(str);
                // Ensures a comma is not added after the last item in the row
                if (i != colCount) {ln.append(",");}
            }

            // Adds a new line separator, and writes the current line to file
            bw.newLine();
            bw.write(ln.toString());
        }
        // Closes the SQL statement and the buffered writer
        st.close();
        bw.close();
    }

    /**
     * Inspired from <https://www.codejava.net/coding/java-code-example-to-export-from-database-to-csv-file>
     *      Comments are featured in code to show understanding of method functionality
     * Creates the file name of the export document
     * @param tableName the name of the table being exported
     * @return string with format tableName_dateTime.csv
     */
    private String formatFileName(String tableName) {
        String str = "export_files/" + tableName;
        // Create a new String of a LocalDate of the current date
        String dateFormat = LocalDate.now().toString();
        // Return the file name of the export file with the new formatted date attached
        return str.concat(String.format("_%s.csv", dateFormat));
    }

    /**
     * Writes the header line to the CSV file with the names of the columns in the table
     *      Comments are featured in code to show understanding of method functionality
     * @param rs the results set of the queried table being written
     * @return the number of columns in the results set
     * @throws SQLException
     * @throws IOException
     */
    private int writeHeaderLine(ResultSet rs) throws SQLException, IOException {
        // Gets the metadata from the given results set
        ResultSetMetaData md = rs.getMetaData();
        // Get the number of columns within the results set
        int columnCount = md.getColumnCount();
        // Sets the header line to an empty string
        StringBuilder header = new StringBuilder();

        // Adds each column name to the header line, separated by commas
        for (int i = 1; i <= columnCount; i++) {
            String colName = md.getColumnName(i);
            header.append(colName);
            // Does not add a comma to the last column
            if (i != columnCount) {header.append(",");}
        }

        // Writes the header line to the CSV file
        bw.write(header.toString());

        // Returns the number of columns in the results set
        return columnCount;
    }
}
