package Entity;

import ADT.Customer.*;

import java.util.Comparator;

public class Order {
    // variables
    private ArrayList<OrderItem> orderItems;
    private Customer customer;
    private final static double TAX_PERCENTAGE = 0.06;
    
    // constructors
    public Order(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    public Order(ArrayList<OrderItem> orderItems, Customer customer) {
        this.orderItems = orderItems;
        this.customer = customer;
    }
    
    // getters
    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    // setters
    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    } 
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    // methods
    public double calSubtotal() {
        double subtotal = 0;
        
        for (int i = 1; i <= orderItems.getNumberOfEntries(); i++) {
            subtotal += orderItems.getEntry(i).getQuantity() * orderItems.getEntry(i).getItem().getPrice(); 
        }
        
        return subtotal;
    }
    
    public double calTax() {
        return calSubtotal() * TAX_PERCENTAGE;
    }
    
    public double calGrandTotal() {
        return calSubtotal() + calTax();
    }
    
    @Override
    public boolean equals(Object order) {
        if (order instanceof Order ) {
            Order anotherOrder=(Order)order;
            return this.orderItems.getEntry(0).getItem().getRestaurant().equals(anotherOrder.orderItems.getEntry(0).getItem().getRestaurant());
        } else {
            return false;
        }
    }
    
    @Override 
    public String toString() {
        String str = "";
        String separator = "============================================================\n";
        
        str += String.format("Customer User ID: %s \n", customer.getUserId());
        str += String.format("Customer Name: %s \n", customer.getName());
        str += separator;
        str += "No. Item ID		Item Name		Quantity		Price (RM)\n";
        str += separator;
        for (int i = 1; i <= orderItems.getNumberOfEntries(); i++) {
            str += String.format("%d. %s", i, orderItems.getEntry(i).toString());
        }
        str += separator;
        str += String.format("\t\t\t\t\t\tSubtotal (RM)\t%.2f \n", calSubtotal());
        str += String.format("\t\t\t\t\t\tTax (6%%)\t\t%.2f \n", calTax());
        str += separator;
        str += String.format("\t\t\t\t\t\tGrand Total (RM)\t%.2f \n", calGrandTotal());
        str += separator;
        
        return str;
    }
    
    public Comparator<Order> getAlphabeticalComparator() {
        return new AlphabeticalComparator();
    }
    
    public Comparator getSubtotalComparator() {
        return new SubtotalComparator();
    }
    
    private class AlphabeticalComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            Order order1 = (Order) o1;
            Order order2 = (Order) o2;
            String restuarantOrder1 = order1.getOrderItems().getEntry(0).getItem().getRestaurant().getName();
            String restaurantOrder2 = order2.getOrderItems().getEntry(0).getItem().getRestaurant().getName();
            
            return restuarantOrder1.compareTo(restaurantOrder2);
        }
    }
    
    private class SubtotalComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            Order order1 = (Order) o1;
            Order order2 = (Order) o2;
            double difference = order1.calGrandTotal() - order2.calGrandTotal(); 
            
            if (difference > 0) {
                return 1;
            } else if (difference < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
