package Entity;

/**
 *
 * @author xuanyi
 */

import ADT.Customer.*;

import java.time.LocalTime;

public class Restaurant extends User {

    private LocalTime openingHour;
    private LocalTime closingHour;
    private static int restId = 1;

    public Restaurant() {

    }
    
    public Restaurant(String name) {
        super(name);
    }

    public Restaurant(LocalTime openingHour, LocalTime closingHour) {
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }
    
    public Restaurant(String name, String password, String contactNo, ArrayList<Address> address, LocalTime openingHour, LocalTime closingHour) {
        super(String.format("RES%04d", restId++), name, password, contactNo, address);
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }

    //getters
    public LocalTime getOpeningHour() {
        return openingHour;
    }
    
    public LocalTime getClosingHour() {
        return closingHour;
    }

    //setters
    public void setOpeningHour(LocalTime openingHour) {
        this.openingHour = openingHour;
    }

    public void setClosingHour(LocalTime closingHour) {
        this.closingHour = closingHour;
    }
    
    @Override
    public boolean equals(Object object) {
        if (object instanceof Restaurant ) {
        Restaurant anotherRestaurant=(Restaurant)object;
            return getName().equals(anotherRestaurant.getName());
        } 
        
        return false;
    }
}
