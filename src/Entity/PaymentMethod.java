package Entity;

public class PaymentMethod implements Comparable<PaymentMethod> {

    private String methodType;

    public PaymentMethod(String methodType) {
        this.methodType = methodType;
    }

    @Override
    public boolean equals(Object anotherPaymentMethod) {
        if (anotherPaymentMethod instanceof PaymentMethod) {
            PaymentMethod paymentMethod = (PaymentMethod) anotherPaymentMethod;
            return this.methodType.equals(paymentMethod.methodType);
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(PaymentMethod anotherPaymentMethod) {
        return this.methodType.compareToIgnoreCase(anotherPaymentMethod.methodType);
    }

    public String getMethodType() {
        return this.methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    @Override
    public String toString() {
        return String.format("[%s]\t", methodType);
    }

}
