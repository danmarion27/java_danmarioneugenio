package one;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ManageSMS {

    void insertSMS(SMS sms, boolean completed);

    ArrayList retrieveSMS();

    ArrayList retrieveSMSStartDate(LocalDateTime startDate, LocalDateTime endDate);

    ArrayList retrieveSMSPromoCode(String promoCode);

    ArrayList retrieveSMSMSISDN(String msisdn);

    ArrayList retrieveSMSBySystem();

    ArrayList retrieveSMSToSystem();

}
