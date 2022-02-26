package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseConnect {

    final private static Logger logger =
            Logger.getLogger(DatabaseConnect.class.getName());
    private static Connection connection = null;




    private static Connection getConnectionInstance() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms?useTimezone=true&serverTimezone=UTC", "root", "aefijz9S");
                logger.info("Connected");
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                logger.log(Level.INFO, "Not Connected", sqle);
            } catch (Exception e){
                logger.log(Level.SEVERE, "Not Connected", e);
            }
        }
        return connection;
    }
    public static Connection getConnection() {
        return (connection == null)
                ? getConnectionInstance()
                : connection;

    }
    public static void disconnect() {
        try {
            if (connection != null){
                connection.close();
                logger.info("Disconnected");
            }
        }catch (Exception e){
            logger.log(Level.SEVERE, "Not Connected", e);
        }
    }



}