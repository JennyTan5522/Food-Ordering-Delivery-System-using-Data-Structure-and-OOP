package Entity;

/**
 *
 * @author xuanyi
 */
public class Item {
    
    private String itemId;
    private String name;
    private String description;
    private String category;
    private char availability;    
    private double price;
    private Restaurant restaurant;
    
    public Item(){
        
    }
    
    public Item(String itemId) {
        this.itemId = itemId;
    }
    
    public Item(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
    public Item(String itemId, String name, String description, String category, double price, Restaurant restaurant){
        this(itemId, name, description, 'Y', category , price, restaurant);
    }  
    
    public Item(String itemId, String name, String description, char availability, String category, double price, Restaurant restaurant){
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.availability = availability;
        this.category = category;        
        this.price = price;
        this.restaurant = restaurant;
        
    }    
    
    //getter
    public String getItemId(){
        return itemId;
    }
    
    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
    
     public String getCategory(){
        return category;
    }   
    
    public char getAvailability(){
        return availability;
    }
    
    public double getPrice(){
        return price;
    }
    
    public Restaurant getRestaurant(){
        return restaurant;
    }
    
            
    //setter   
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setAvailability(char availability){
        this.availability = availability;
    }
    
    public void setPrice(double price){
        this.price = price;
    }
    
    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }    
    
    @Override
    public String toString() {
        
        
      return String.format("Item ID: %s\nName: %s\nDescription: %s\nCategory: %s\nPrice: RM %.2f\n", itemId,name,description,category,price);
    }    
    
}
