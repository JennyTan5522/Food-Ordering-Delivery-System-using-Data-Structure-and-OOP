package Entity;

import java.time.YearMonth;

public class CreditCard extends PaymentMethod implements Comparable<PaymentMethod> {

    private String cardNo;
    private YearMonth expiryDate;
    private String cvv;

    public CreditCard(String cardNo, YearMonth expiryDate, String cvv) {
        super("Credit Card");
        this.cardNo = cardNo;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean equals(Object anotherCreditCard) {
        if (anotherCreditCard instanceof CreditCard) {
            CreditCard creditCard = (CreditCard) anotherCreditCard;
            return this.cardNo.equals(creditCard.cardNo) && this.expiryDate.equals(creditCard.expiryDate) && this.cvv.equals(creditCard.cvv);
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(PaymentMethod anotherCreditCard) {
        if (anotherCreditCard instanceof CreditCard) {
            CreditCard creditCard = (CreditCard) anotherCreditCard;
            return this.cardNo.compareTo(creditCard.cardNo);
        }
        return 0;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public String getCvv() {
        return this.cvv;
    }

    public YearMonth getExpiryDate() {
        return this.expiryDate;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setExpiryDate(YearMonth expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("Card Number\t: %s", cardNo);
    }

}
