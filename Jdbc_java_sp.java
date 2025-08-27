import java.sql.*;

/**
 * This class connects to an Oracle database and calls two stored procedures from a package:
 *
 * 1. `your_procedure` - Takes two input parameters and returns an Integer.
 * 2. `your_void_procedure` - Takes three input parameters and returns void.
 *
 * How it works:
 * 1. Establish a connection to the Oracle database using JDBC.
 * 2. Call the first procedure that returns an Integer.
 *    - Set input parameters.
 *    - Register an output parameter (Integer).
 *    - Execute the procedure and retrieve the returned value.
 * 3. Call the second procedure that returns void.
 *    - Set input parameters.
 *    - Execute the procedure (no return value).
 *
 * How to run:
 * 1. Replace `your-host`, `your-service`, `your_username`, and `your_password` with valid database credentials.
 * 2. Update `your_package.your_procedure` and `your_package.your_void_procedure` with actual procedure names.
 * 3. Compile and run the class.
 *
 * Example output:
 * Procedure returned: 123
 * Void procedure executed successfully.
 */

public class OracleProcedureCaller {

    // Database connection details
    private static final String URL = "jdbc:oracle:thin:@//your-host:1521/your-service";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        OracleProcedureCaller caller = new OracleProcedureCaller();

        // Call the procedure that returns an Integer
        int result = caller.callProcedureReturningInteger(100, "test_param");
        System.out.println("Procedure returned: " + result);

        // Call the procedure that returns void
        caller.callProcedureReturningVoid(200, "another_param", 3.14);
        System.out.println("Void procedure executed successfully.");
    }

    /**
     * Calls a stored procedure that returns an Integer.
     * 
     * @param param1 First input parameter (integer).
     * @param param2 Second input parameter (string).
     * @return The integer value returned by the procedure.
     */
    public int callProcedureReturningInteger(int param1, String param2) {
        int output = -1;
        String sql = "{ call your_package.your_procedure(?, ?, ?) }"; // 2 IN, 1 OUT

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement stmt = conn.prepareCall(sql)) {

            // Set input parameters
            stmt.setInt(1, param1);
            stmt.setString(2, param2);

            // Register output parameter (INTEGER)
            stmt.registerOutParameter(3, Types.INTEGER);

            // Execute procedure
            stmt.execute();

            // Retrieve the output value
            output = stmt.getInt(3);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * Calls a stored procedure that returns void.
     * 
     * @param param1 First input parameter (integer).
     * @param param2 Second input parameter (string).
     * @param param3 Third input parameter (double).
     */
    public void callProcedureReturningVoid(int param1, String param2, double param3) {
        String sql = "{ call your_package.your_void_procedure(?, ?, ?) }"; // 3 IN, no OUT

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement stmt = conn.prepareCall(sql)) {

            // Set input parameters
            stmt.setInt(1, param1);
            stmt.setString(2, param2);
            stmt.setDouble(3, param3);

            // Execute procedure (no return value)
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}