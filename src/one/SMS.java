package one;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SMS {
    protected String transactionID = "";
    protected String msisdn = "";
    protected String recipient = "";
    protected String sender = "";
    protected String shortCode = "";
    protected LocalDateTime timeStamp;

    public SMS() {
    }

    public SMS(String transactionID, String msisdn, String recipient, String sender, String shortCode, LocalDateTime timeStamp) {
        this.transactionID = transactionID;
        this.msisdn = msisdn;
        this.recipient = recipient;
        this.sender = sender;
        this.shortCode = shortCode;
        this.timeStamp = timeStamp;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }



    @Override
    public String toString() {
        return "SMS{" +
                "transactionID='" + transactionID + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", recipient='" + recipient + '\'' +
                ", sender='" + sender + '\'' +
                ", shortCode='" + shortCode + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
