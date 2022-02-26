package one;

import database.DatabaseConnect;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SMSTransaction implements ManageSMS{

    final private static Logger logger = Logger.getLogger(SMSTransaction.class.getName());
    final private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy.MM.dd G 'at' HH:mm:ss");

   private static Connection connection = DatabaseConnect.getConnection();

   protected ArrayList<SMS> allSMS = new ArrayList<>();

   String sqlStatement = "";
   Statement statement = null;
   ResultSet resultSet = null;
   boolean isSMSEmpty;
   SMS retrieveSMS;




    @Override //insert sms to DB
    public void insertSMS(SMS sms, boolean completed) {
        sqlStatement = "INSERT INTO " //query
                + "SMS(transactionID, msisdn, sender, recipient, shortCode, completed, timeStamp)"
                + "Values (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, sms.getTransactionID());
            preparedStatement.setString(2, sms.getMsisdn());
            preparedStatement.setString(3, sms.getSender());
            preparedStatement.setString(4, sms.getRecipient());
            preparedStatement.setString(5, sms.getShortCode());
            preparedStatement.setString(6, sms.getTimeStamp().toString());
            preparedStatement.setString(7, String.valueOf(completed));

            preparedStatement.executeUpdate();
            logger.log(Level.INFO, "SMS " + sms.getTransactionID() + " added.");

        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "SQLException", sqle);
        } finally {
            try {
                if (statement != null){
                    statement.close();
                } if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e){
                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
            }
        }
    }

    @Override //retrieve sms from DB
    public ArrayList retrieveSMS() {

        //Clear sms upon calling
        allSMS.clear();
        isSMSEmpty = true; //use for return

        //retrieve SMS
        sqlStatement = "SELECT * FROM sms";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                isSMSEmpty = false;
                retrieveSMS = new SMS(resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                LocalDateTime.parse(resultSet.getString(6), format));

            allSMS.add(retrieveSMS);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        } finally {
            try {
                if (statement != null){
                    statement.close();
                } if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e){
                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
            }
        }

        if (isSMSEmpty){
            logger.log(Level.INFO, "SMS database empty.");
            return null;
        } else {
            return allSMS;
        }
    }

    @Override //retrieve sms from date
    public ArrayList retrieveSMSStartDate(LocalDateTime startDate, LocalDateTime endDate) {

        //Clear sms upon calling in arraylist
        allSMS.clear();
        isSMSEmpty = true; //use for return

        //retrieve SMS between date
        sqlStatement = "SELECT * FROM sms WHERE timeStamp BETWEEN \'" + startDate + "\" AND \"" + endDate + '\"';

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                isSMSEmpty = false;
                retrieveSMS = new SMS(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        LocalDateTime.parse(resultSet.getString(6), format));

                allSMS.add(retrieveSMS);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException", e);
        } finally {
            try {
                if (statement != null){
                    statement.close();
                } if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e){
                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
            }
        }
        if (isSMSEmpty){
            logger.log(Level.INFO, "Empty SMS during " + startDate + " and " + endDate + " .");
            return null;
        } else {
            return allSMS;
        }
    }

    @Override
    public ArrayList retrieveSMSPromoCode(String promoCode)  {
        return null;
    }

    @Override
    public ArrayList retrieveSMSMSISDN(String msisdn)  {
        return null;
    }

    @Override
    public ArrayList retrieveSMSBySystem() {
        return null;
    }

    @Override
    public ArrayList retrieveSMSToSystem() {
        return null;
    }
}
