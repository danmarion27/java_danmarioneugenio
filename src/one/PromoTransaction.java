package one;

import database.DatabaseConnect;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PromoTransaction implements ManagePromo{

    final private static Logger logger = Logger.getLogger(SMSTransaction.class.getName());
    final private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy.MM.dd G 'at' HH:mm:ss");

    private static Connection connection = DatabaseConnect.getConnection();


    protected ArrayList<Promo> allPromo = new ArrayList<>();

    String sqlStatement = "";
    Statement statement = null;
    ResultSet resultSet = null;
    boolean isPromoEmpty;
    Promo retrievePromo;


    @Override //insert promo in DB
    public void insertPromo(Promo promo) {
        boolean promoExist = false;

        //retrieve promo in DB
        retrievePromo();

        //verify if promo exist in DB
        for (Promo promo1 : allPromo) {
            if(promo1.getPromoCode().equalsIgnoreCase(promo.getPromoCode()) || promo1.getShortCode().equalsIgnoreCase(promo.getShortCode())){
                promoExist = true;
                logger.log(Level.INFO, "Promo Exist!");

                break;
            }

            //promo is not in DB
            if (!promoExist){
                sqlStatement = "INSERT INTO "
                        + "promo(promoCode, details, shortCode, startDate, endDate)"
                        + "values (?,?,?,?,?)";

                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

                    preparedStatement.setString(1, promo.getPromoCode());
                    preparedStatement.setString(2, promo.getDetails());
                    preparedStatement.setString(3, promo.getShortCode());
                    preparedStatement.setString(4, promo.getStartDate().toString());
                    preparedStatement.setString(4, promo.getEndDate().toString());

                    preparedStatement.executeUpdate();
                    logger.log(Level.INFO, "Promo " + promo.getPromoCode() + " added.");

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
        }
    }

    @Override //retrieve promo from DB
    public ArrayList retrievePromo() {
        //Clear promo upon calling in arraylist
        allPromo.clear();
        isPromoEmpty = true; //use for return

        //retrieve promo between date
        sqlStatement = "SELECT * FROM promo";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                isPromoEmpty = false;
                retrievePromo = new Promo(resultSet.getString("promoCode"),
                        resultSet.getString("details"),
                        resultSet.getString("shortCode"),
                        LocalDateTime.parse(resultSet.getString("startDate"), format),
                        LocalDateTime.parse(resultSet.getString("endDate"), format));

                allPromo.add(retrievePromo);
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
        if (isPromoEmpty){
            logger.log(Level.INFO, "Database in promo is empty");
            return null;
        } else {
            return allPromo;
        }
    }

    @Override //retrieve promocode from db using shortcode
    public String retrievePromoCodeByShortCode(String shortCode) {
        String promoCode = "";
        isPromoEmpty = false;
        sqlStatement = "SELECT promoCode FROM promo WHERE shortCode = \"" + shortCode + "\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            if (!resultSet.next()) {
                isPromoEmpty = true;
            } else {
                promoCode = resultSet.getString("promoCode");
            }
        }catch (SQLException sqle){
            logger.log(Level.SEVERE, "SQLException", sqle);
        }finally {
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
        if (isPromoEmpty){
            logger.log(Level.INFO, "No promo with " + shortCode + "shortCode.");
            return null;
        } else {
            return promoCode;
        }
    }

    @Override
    public String retrieveShortCodeByPromoCode(String promoCode) {
        String shortCode = "";
        isPromoEmpty = false;
        sqlStatement = "SELECT shortCode FROM promo WHERE promoCode = \"" + promoCode + "\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            if (!resultSet.next()) {
                isPromoEmpty = true;
            } else {
                shortCode = resultSet.getString("shortCode");
            }
        }catch (SQLException sqle){
            logger.log(Level.SEVERE, "SQLException", sqle);
        }finally {
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
        if (isPromoEmpty){
            logger.log(Level.INFO, "No promo with " + promoCode + "shortCode.");
            return null;
        } else {
            return shortCode;
        }
    }

    @Override
    public Map reteievePromoStartEndDates(String shortCode) {
        Map<String, String> dates = new HashMap<>();
        //retrieve a promo
        sqlStatement = "SELECT startDate, endDate FROM promo WHERE shortCpde = \"" + shortCode +"\"";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while (!resultSet.next()) {
                dates.put("startDate", resultSet.getString("startDate"));
                dates.put("endDate", resultSet.getString("endDate"));
            }
        }catch (SQLException sqle){
            logger.log(Level.SEVERE, "SQLException", sqle);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "ERROR IN CLOSING", e);
            }
        }return dates;
    }
}
