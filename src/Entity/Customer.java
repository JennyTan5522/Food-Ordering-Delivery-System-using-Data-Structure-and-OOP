package Entity;

import ADT.Customer.*;

public class Customer extends User implements Comparable<User> {

    private String email;
    private ArrayList<PaymentMethod> paymentMethodList = new ArrayList<>();
    private static int custIdCounter = 10000;

    public Customer(String name, String password, String contactNo, ArrayList<Address> address, String email, PaymentMethod paymentMethodList) {
        super(String.format("C%05d", custIdCounter++), name, password, contactNo, address);
        this.email = email;
        this.paymentMethodList.add(paymentMethodList);
    }

    public Customer(String name, String password, String contactNo, String email) {
        super(String.format("C%05d", custIdCounter++), name, password, contactNo);
        this.email = email;
        paymentMethodList.add(new Ewallet(0.00));
    }

    public Customer(String name, String password, String contactNo, ArrayList<Address> address, String email) {
        super(String.format("C%05d", custIdCounter++), name, password, contactNo, address);
        this.email = email;
        paymentMethodList.add(new Ewallet(0.00));
    }

    @Override
    public boolean equals(Object anotherCustomer) {
        if (anotherCustomer instanceof Customer) {
            Customer customer = (Customer) anotherCustomer;
            return this.getUserId().equals(customer.getUserId()) && this.email.equals(customer.email) && this.paymentMethodList.equals(customer.paymentMethodList);
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(User anotherCustomer) {
        if (anotherCustomer instanceof Customer) {
            Customer customer = (Customer) anotherCustomer;
            return this.getUserId().compareTo(customer.getUserId());
        }
        return 0;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<PaymentMethod> getPaymentMethodList() {
        return paymentMethodList;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPaymentMethods(PaymentMethod paymentMethodList) {
        this.paymentMethodList.add(paymentMethodList);
    }

    @Override
    public String toString() {
        return super.toString() + String.format("\t\t\t\tUser ID\t: %s \n\t\t\t\tEmail Address\t: %s \n\t\t\t\tPayment Method\t: %s", getUserId(), email, paymentMethodList);
    }

}
