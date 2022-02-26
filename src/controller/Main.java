package controller;

import database.DatabaseConnect;
import one.*;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    final private static DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy.MM.dd G 'at' HH:mm:ss");
    final private static Logger logger = Logger.getLogger(SMSTransaction.class.getName());
    private static SMSTransaction smsTransaction = new SMSTransaction();
    private static PromoTransaction promoTransaction = new PromoTransaction();

    
    private static SMS sms = new SMS();
    private static Promo promo = new Promo();
    
    private static String transactionID = "";

    public static void main(String[] args) {
    }


    private void smsChecker(Map<String, String> sms) {

        logger.log(Level.INFO, "Mobile Number: " + sms.get("mobileNumber"));
        logger.log(Level.INFO, "Message: " + sms.get("message"));
        logger.log(Level.INFO, "Short Code: " + sms.get("shortCode"));
    }




}
