package Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaidOrder {
    private String orderId;
    private Order order;
    private LocalDateTime orderDateTime;
    private String orderStatus;
    private Address address;
    private PaymentMethod paymentMethod;
    private Rider rider;
    
    public PaidOrder(String orderId) {
        this.orderId = orderId;
    }
    
    public PaidOrder(Order order, Address address, PaymentMethod paymentMethod) {
        this.order = order;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }
    
    public PaidOrder(String orderId, Order order, LocalDateTime orderDateTime, String orderStatus, Address address, PaymentMethod paymentMethod, Rider rider) {
        this.orderId = orderId;
        this.order = order;
        this.orderDateTime = orderDateTime;
        this.orderStatus = orderStatus;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.rider = rider;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }
    
    public String getOrderStatus() {
        return orderStatus;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    
    public Rider getRider() {
        return rider;
    }
    
    public void setOrderId(int numOrders) {
        orderId = String.format("PO%5d", numOrders);
    }
    
    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }
    
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public void setRider(Rider rider) {
        this.rider = rider;
    }
    
    public double calDeliveryFee() {
        if (order.getOrderItems().getEntry(0).getItem().getRestaurant().getAddress().getEntry(0).getDistrict().equals(address.getDistrict())) {
            return 3.00;
        }
        else {
            return 5.00;
        }
    }
    
    public double calGrandTotal() {
        return order.calGrandTotal() + calDeliveryFee();
    }
    
    @Override
    public boolean equals(Object paidOrder) {
        if (paidOrder instanceof PaidOrder ) {
        PaidOrder anotherPaidOrder=(PaidOrder)paidOrder;
            return this.orderId.equals(anotherPaidOrder.orderId);
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String str = "";

        str += "Order Details: \n ============== \n";
        str += String.format("Order ID: %s \n", orderId);
        str += String.format("Order Date Time: %s \n", orderDateTime.format(formatter));
        str += String.format("Order Status: %s \n", orderStatus);
        str += order.toString();
        str += "Delivery Details: \n ================ \n";
        str += String.format("Restaurant Address: %s \n", order.getOrderItems().getEntry(0).getItem().getRestaurant().getAddress().toString());
        str += String.format("Delivery Address: ", address.toString());
        str += "Payment Details: \n ================ \n";
        str += String.format("Payment Method: %s \n", paymentMethod.getMethodType());
        if (paymentMethod.getMethodType().equals("Credit Card")) {
            CreditCard creditCard = (CreditCard) paymentMethod;
            
            str += String.format("Card Number: %s \n", creditCard.getCardNo());
        }
        str += "Rider Details: \n =============== \n";
        if (rider != null) {
            str += String.format("Rider Name: %s \n", rider.getName());
            str += String.format("Rider Contact Number: %s \n", rider.getContactNo());
        } else {
            str += "Rider has not been assigned. \n";
        }
        
        return str;
    }
}
