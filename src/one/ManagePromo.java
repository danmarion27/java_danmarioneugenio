package one;

import java.util.ArrayList;
import java.util.Map;

public interface ManagePromo {

    void insertPromo(Promo promo);

    ArrayList retrievePromo();

    String retrievePromoCodeByShortCode(String shortCode);

    String retrieveShortCodeByPromoCode(String promoCode);

    Map reteievePromoStartEndDates (String shortCode);
}
