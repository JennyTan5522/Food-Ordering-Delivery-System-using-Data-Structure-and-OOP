package Entity;

public class Ewallet extends PaymentMethod implements Comparable<PaymentMethod> {

    private double balance;

    public Ewallet(double balance) {
        super("E-wallet");
        this.balance = balance;
    }

    @Override
    public boolean equals(Object anotherEwallet) {
        if (anotherEwallet instanceof Ewallet) {
            Ewallet ewallet = (Ewallet) anotherEwallet;
            return this.balance == ewallet.balance;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(PaymentMethod anotherEwallet) {
        if (anotherEwallet instanceof Ewallet) {
            Ewallet ewallet = (Ewallet) anotherEwallet;
            return Double.compare(this.balance, ewallet.balance);
        }
        return 0;
    }

    public double getBalance() {
        return this.balance;
    }

    public void deductBalance(double amount) {
        this.balance -= amount;
    }

    public void topUpBalance(double amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("\tBalance\t\t: RM %.2f", balance);
    }

}
