package Entity;

public class OrderItem {
    // variables
    private Item item;
    private int quantity;
    
    // constructors
    public OrderItem(Item item) {
        this.item = item;
    }
    
    public OrderItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
    
    // getters
    public Item getItem() {
        return item;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    // setters
    public void setItem(Item item) {
        this.item = item;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public boolean equals(Object orderItem) {
        if (orderItem instanceof OrderItem ) {
            OrderItem anotherOrderItem =(OrderItem)orderItem;
            return this.item.equals(anotherOrderItem.item);
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s \t %s \t %d \t %.2f \n", item.getItemId(), item.getName(), quantity, item.getPrice());
    }
}
