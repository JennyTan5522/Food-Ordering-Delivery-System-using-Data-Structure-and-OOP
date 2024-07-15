package Client;

import java.util.*;
import java.time.LocalTime;

import ADT.Restaurant.*;
import Entity.*;
/**
 *
 * @author xuanyi
 */


public class MenuManagement {
    public static void menuInterface(){

        Scanner scanner = new Scanner(System.in);
        
        int menuManagement=0;
        char createMenu = 'N';

        ListInterface<Item> menuItem = new SinglyLinkedList<>();
        
        //hard code for testing
//        Restaurant restaurant = new Restaurant("8-9");
//
//        Item item1 = new Item("A0001","j","j","Food",2,restaurant);
//        Item item2 = new Item("A0002","kfc","k","Food",2,restaurant);
//        Item item3 = new Item("A0003","l","l","Food",2,restaurant);
//        Item item4 = new Item("A0004","m","m","Beverage",2,restaurant);
//        Item item5 = new Item("A0005","n","n","Beverage",2,restaurant);
//        Item item6 = new Item("A0006","o","o","Beverage",2,restaurant);   
//
//  
//        menuItem.add(item1);
//        menuItem.add(item2);
//        menuItem.add(item3);
//        menuItem.add(item4);
//        menuItem.add(item5);
//        menuItem.add(item6);              
        
        do{
            
            System.out.println("\nRestaurant Menu Management");
            System.out.println("==========================");        
            System.out.println("1. Add ");
            System.out.println("2. Edit ");   
            System.out.println("3. Delete");       
            System.out.println("4. View");       
            System.out.print("Please enter your choice (0 to Exit): ");          
            menuManagement = scanner.nextInt();

            while(menuManagement<0 || menuManagement>4){
                System.out.println("\nInvalid choice entered. Please re-enter. \n");
                System.out.print("Please enter your choice (0 to Exit): ");          
                menuManagement = scanner.nextInt();
            }  
            
            if(menuManagement == 1){
                menuItem = addMenu(menuItem);
            }else if(menuManagement == 2){
                if (menuItem.isEmpty()) {
                    System.out.println("\nYou do not have a Restaurant Menu yet !");

                    System.out.print("\nDo you want to create a new Menu (Y/N)? ");
                    createMenu = scanner.next().charAt(0);

                    // validate user input
                    while (Character.toUpperCase(createMenu) != 'Y' && Character.toUpperCase(createMenu) != 'N') {
                        System.out.println("\nInvalid character entered. Please re-enter. ");
                        System.out.print("\nDo you want to create a new Menu (Y/N)? ");
                        createMenu = scanner.next().charAt(0);
                    }

                    if (Character.toUpperCase(createMenu) == 'Y') {
                        menuItem = addNewMenu();
                    }
                } else {
                    menuItem = editSelection(menuItem);

                }


            }else if(menuManagement == 3){
                
                if (menuItem.isEmpty()) {
                    System.out.println("\nYou do not have a Restaurant Menu to be deleted!");

                } else {
                    menuItem = deleteMenu(menuItem);

                }
                
            }else if(menuManagement == 4){
                
                if (menuItem.isEmpty()) {
                    System.out.println("\nYou do not have a Restaurant Menu yet !");

                    System.out.print("\nDo you want to create a new Menu (Y/N)? ");
                    createMenu = scanner.next().charAt(0);

                    // validate user input
                    while (Character.toUpperCase(createMenu) != 'Y' && Character.toUpperCase(createMenu) != 'N') {
                        System.out.println("\nInvalid character entered. Please re-enter. ");
                        System.out.print("\nDo you want to create a new Menu (Y/N)? ");
                        createMenu = scanner.next().charAt(0);
                    }

                    if (Character.toUpperCase(createMenu) == 'Y') {
                        menuItem = addNewMenu();
                    }
                } else {
                    viewMenu(menuItem);

                }

                
            }
            
        }while(menuManagement != 0);

    }
    
    public static int searchItem(ListInterface<Item> existingMenu){
        
        Scanner scanner = new Scanner(System.in);
        
        int searchOption, matchedPosition=0;
        
        do{
            
            System.out.println("\nSearch Selection");
            System.out.println("================");
            System.out.println("1. ID");
            System.out.println("2. Name");
            System.out.print("Please enter your choice (0 to Exit): ");
            searchOption = scanner.nextInt();

            while (searchOption < 0 || searchOption > 2) {
                System.out.println("\nInvalid choice entered. Please re-enter. \n");
                System.out.print("Please enter your choice (0 to Exit): ");
                searchOption = scanner.nextInt();
            }

            if (searchOption == 1) {
                matchedPosition = searchItemId(existingMenu);

            } else if (searchOption == 2) {
                matchedPosition = searchItemName(existingMenu);
            } else {
                matchedPosition=0;
            }

            
        }while(matchedPosition ==-1);

        return matchedPosition;
        
    }
    
    public static int searchItemName(ListInterface<Item> existingMenu){
        Scanner scanner = new Scanner(System.in);
        
        String name;
        int itemPosition = 0;
        Item matchedItem;
        Item checkName;

        Iterator<Item> checkExistIterator;
        ListInterface<String> nameList = new SinglyLinkedList<>();

        checkExistIterator = existingMenu.getIterator();


        while (checkExistIterator.hasNext()) {

            checkName = checkExistIterator.next();
            nameList.add(checkName.getName());
        }
        
        
        do {

            System.out.print("\nPlease enter the name of the item to be searched (-1 to Exit): ");
            name = scanner.nextLine();
            name = name.toUpperCase();


            if (name.equals("-1")) {
                return -1;
            }

            if (nameList.contains(name)) {
                itemPosition = nameList.search(name);

                matchedItem = existingMenu.getEntry(itemPosition);

                System.out.println("\nAn item in the Restaurant Menu matches the Name entered.");
                System.out.println("\nDetails of the Matched Item");
                System.out.println("===========================");
                System.out.println(matchedItem.toString());

            } else {
                System.out.println("\nNo item in the Restaurant Menu matches the Name entered.");
                itemPosition = 0;
            }

        } while (itemPosition == 0);

        return itemPosition;
        

    }
    
    public static int searchItemId(ListInterface<Item> existingMenu){

        Scanner scanner = new Scanner(System.in);

        String id;
        int itemPosition=0;
        Item matchedItem;
        Item checkId;

        Iterator<Item> checkExistIterator;  
        ListInterface<String> idList = new SinglyLinkedList<>();


        checkExistIterator = existingMenu.getIterator();

        while(checkExistIterator.hasNext()){

             checkId = checkExistIterator.next();         
             idList.add(checkId.getItemId());      

        }              
        
        
        do{
 
            System.out.print("\nPlease enter the ID of the item to be searched (-1 to Exit): ");
            id = scanner.nextLine();   

            if(id.equals("-1")){
                return -1;
            }

            if(idList.contains(id)){
                itemPosition = idList.search(id);

                matchedItem = existingMenu.getEntry(itemPosition);

                System.out.println("\nAn item in the Restaurant Menu matches the ID entered.");                    
                System.out.println("\nDetails of the Matched Item");
                System.out.println("===========================");
                System.out.println(matchedItem.toString());


            }else{
                System.out.println("\nNo item in the Restaurant Menu matches the ID entered.");                    
                itemPosition = 0;
            }            
            
        }while(itemPosition==0);

        return itemPosition;
    }
    
    public static String checkCategory(Item item){
                
        if(item.getCategory() == "Food"){
           return item.getCategory().toUpperCase();
        }else{
            return item.getCategory().toUpperCase();
        }
    }
    
    public static char checkAvailability(Item item){
        if(item.getAvailability()=='Y'){
            return 'Y';
        }else{
            return 'N';
        }
    }
    
    public static ListInterface<Item> editItem(ListInterface<Item> existingMenu){
        
        Scanner scanner = new Scanner(System.in);
        
        //variables
        int editOption, itemSearchPosition, editPosition, choicePosition, addFirst, itemOption=0;
        char editSpecific = 'N', addMore, addOther = 'N', confirmEdit, confirmExit= 'N', editItem, editDetails='N', editSameItem = 'N', editCategory = 'N', editAvailability = 'N';
        boolean added=false, unique=true, priceValid=true;

        String name, description, itemCategory;
        double price;
        
        Item editedItem;
        
        Item removedItem;
        //id of the item to be changed so that can use it to find in the food item list
        String itemId;
        //position of the item to be changed
        int removedPosition = 0, countFood = 0, countBeverage = 0, seq = 0;
        
        //collection of objects
        ListInterface<Item> item= new SinglyLinkedList<>();
        ListInterface<Item> foodItem= new SinglyLinkedList<>();
        ListInterface<Item> beverageItem= new SinglyLinkedList<>();
        
        ListInterface<Item> rearrangeFood = new SinglyLinkedList<>();

        
        ListInterface<String> nameList = new SinglyLinkedList<>();

        Iterator<Item> menuIterator;
        Iterator<Item> checkExistIterator;  
        
        
        displayAll(existingMenu);
        
        System.out.print("\nDo you want to edit the items in the Restaurant Menu (Y/N)? ");
        editItem=scanner.next().charAt(0);      

        // validate user input
        while (Character.toUpperCase(editItem) != 'Y' && Character.toUpperCase(editItem) != 'N') {
            System.out.println("\nInvalid character entered. Please re-enter. ");
            System.out.print("\nDo you want to edit the items in the Restaurant Menu (Y/N)? ");
            editItem=scanner.next().charAt(0);
        }            

        if(Character.toUpperCase(editItem) == 'Y'){      
                  
            
        do{
            
            itemSearchPosition = searchItemId(existingMenu);
                
            if(itemSearchPosition != -1){
   

                System.out.print("\nDo you want to edit the details of the item (Y/N)? ");
                editDetails=scanner.next().charAt(0);      

                // validate user input
                while (Character.toUpperCase(editDetails) != 'Y' && Character.toUpperCase(editDetails) != 'N') {
                    System.out.println("\nInvalid character entered. Please re-enter. ");
                    System.out.print("\nDo you want to edit the details of the item (Y/N)? ");
                    editDetails=scanner.next().charAt(0);   
                }             

                if(Character.toUpperCase(editDetails) == 'Y'){

                    do{

                        System.out.println("\nEdit Selection");
                        System.out.println("==============");        
                        System.out.println("1. Name");
                        System.out.println("2. Description");        
                        System.out.println("3. Category");        
                        System.out.println("4. Availability");        
                        System.out.println("5. Price");        
                        System.out.println("6. Item Sequence");                        

                        System.out.print("Please enter your choice (0 to Exit): ");
                        editOption = scanner.nextInt();

                        while(editOption<0 || editOption>6){
                            System.out.println("\nInvalid choice entered. Please re-enter. \n");
                            System.out.print("Please enter your choice (0 to Exit): ");
                            editOption = scanner.nextInt();
                        }  

                        scanner.nextLine();
                        

                        if(editOption == 1){
                            item.clear();
                            if(checkCategory(existingMenu.getEntry(itemSearchPosition)).equals("FOOD")){

                            menuIterator = existingMenu.getIterator();

                            while (menuIterator.hasNext()) {
                                Item current = menuIterator.next();
                               if(current.getCategory().equals("Food")){
                                     item.add(current);
                                }                            

                            }
                        }else{
                            menuIterator = existingMenu.getIterator();

                            while (menuIterator.hasNext()) {
                                Item current = menuIterator.next();
                               if(current.getCategory().equals("Beverage")){
                                     item.add(current);
                                }                            

                            }                            

                        }


                        do{                            
                            
                            System.out.printf("\nPlease enter the NEW NAME of the %s : ", checkCategory(existingMenu.getEntry(itemSearchPosition)));
                            name = scanner.nextLine();
                            name = name.toUpperCase();

                            nameList = checkDuplicateName(item);



                         //perform checking whether the food name entered by the user already entered by the user
                         if(nameList.contains(name)){
                              System.out.println("\nAn item with the same name have already appear in the Restaurant Menu.");
                              System.out.println("\nPlease enter a UNIQUE name.");

                             unique = false;
                         }else{
                             unique = true;
                         }        

                        }while(unique==false);               

                        //ask to confirm to edit                
                         System.out.printf("\nAre you sure to edit the name of the %s (Y/N)? ",checkCategory(existingMenu.getEntry(itemSearchPosition)));
                         confirmEdit=scanner.next().charAt(0);

                         // validate user input
                         while (Character.toUpperCase(confirmEdit) != 'Y' && Character.toUpperCase(confirmEdit) != 'N') {
                            System.out.println("\nInvalid character entered. Please re-enter. ");
                            System.out.printf("\nAre you sure to edit the name of the %s (Y/N)? ",checkCategory(existingMenu.getEntry(itemSearchPosition)));
                            confirmEdit=scanner.next().charAt(0);
                         }

                         if(Character.toUpperCase(confirmEdit) == 'Y'){
                            existingMenu.getEntry(itemSearchPosition).setName(name);
                            editedItem = existingMenu.getEntry(itemSearchPosition);
                            existingMenu.replace(itemSearchPosition, editedItem);

                            System.out.println("\nUpdated Details of the Item");
                            System.out.println("===========================");                        
                            System.out.println(existingMenu.getEntry(itemSearchPosition));                         

                         }else{

                            System.out.printf("\nTHE NAME OF THE %s ITEM WAS NOT EDITED. \n\n",checkCategory(existingMenu.getEntry(itemSearchPosition)));


                         }
                        }else if(editOption == 2){

                            System.out.printf("\nPlease enter the NEW DESCRIPTION of the %s : ", checkCategory(existingMenu.getEntry(itemSearchPosition)));
                            description = scanner.nextLine();

                            //ask to confirm to edit                
                             System.out.printf("\nAre you sure to edit the description of the %s (Y/N)? ",checkCategory(existingMenu.getEntry(itemSearchPosition)));
                             confirmEdit=scanner.next().charAt(0);

                             // validate user input
                             while (Character.toUpperCase(confirmEdit) != 'Y' && Character.toUpperCase(confirmEdit) != 'N') {
                                System.out.println("\nInvalid character entered. Please re-enter. ");
                                System.out.printf("\nAre you sure to edit the description of the %s (Y/N)? ",checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                confirmEdit=scanner.next().charAt(0);
                             }

                             if(Character.toUpperCase(confirmEdit) == 'Y'){
                                existingMenu.getEntry(itemSearchPosition).setDescription(description);
                                editedItem = existingMenu.getEntry(itemSearchPosition);
                                existingMenu.replace(itemSearchPosition, editedItem);

                                System.out.println("\nUpdated Details of the Item");
                                System.out.println("===========================");                        
                                System.out.println(existingMenu.getEntry(itemSearchPosition));                         

                             }else{

                                System.out.printf("\nTHE DESCRIPTION OF THE %s ITEM WAS NOT EDITED. \n\n",checkCategory(existingMenu.getEntry(itemSearchPosition)));

                             }                            
                            
                        }else if(editOption == 3){
                            foodItem.clear();
                            beverageItem.clear();
                            if(checkCategory(existingMenu.getEntry(itemSearchPosition)).equals("FOOD")){
                           
                                System.out.printf("\nAre you sure to edit the category of the item from %s to BEVERAGE (Y/N)? ",checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                editCategory=scanner.next().charAt(0);      

                                // validate user input
                                while (Character.toUpperCase(editCategory) != 'Y' && Character.toUpperCase(editCategory) != 'N') {
                                    System.out.println("\nInvalid character entered. Please re-enter. ");
                                    System.out.printf("\nAre you sure to edit the category of the item from %s to BEVERAGE (Y/N)? ",checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                    editCategory=scanner.next().charAt(0);   
                                }             

                                if(Character.toUpperCase(editCategory) == 'Y'){
                                    existingMenu.getEntry(itemSearchPosition).setCategory("Beverage");
                                    editedItem = existingMenu.getEntry(itemSearchPosition);
                                    existingMenu.replace(itemSearchPosition, editedItem);

                                    System.out.println("\nUpdated Details of the Item");
                                    System.out.println("===========================");                        
                                    System.out.println(existingMenu.getEntry(itemSearchPosition)); 

                                if(itemSearchPosition == 1){

                                        Iterator<Item> rearrange = existingMenu.getIterator();


                                        while (rearrange.hasNext()) {
                                            Item current = rearrange.next();

                                            if(current.getCategory() == "Food"){

                                                 foodItem.add(current);


                                            }else{
                                                beverageItem.add(current);
                                            }

                                        }  

                                        existingMenu.clear();

                                        while(!foodItem.isEmpty()){
                                             existingMenu.add(foodItem.remove(1));

                                        }        

                                        while (!beverageItem.isEmpty()) {

                                             existingMenu.add(beverageItem.remove(1));
                                         } 

                                }  
                         
                                }else{
                                    
                                    System.out.printf("\nTHE CATEGORY OF THE %s ITEM WAS NOT EDITED. \n\n",checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                    
                                }                 
                                
                                
                            }else{
                                      
                                System.out.printf("\nAre you sure to edit the category of the item from %s to FOOD (Y/N)? ",checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                editCategory=scanner.next().charAt(0);      

                                // validate user input
                                while (Character.toUpperCase(editCategory) != 'Y' && Character.toUpperCase(editCategory) != 'N') {
                                    System.out.println("\nInvalid character entered. Please re-enter. ");
                                    System.out.printf("\nAre you sure to edit the category of the item from %s to FOOD (Y/N)? ",checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                    editCategory=scanner.next().charAt(0);   
                                }             

                                if(Character.toUpperCase(editCategory) == 'Y'){
                                    
                                    existingMenu.getEntry(itemSearchPosition).setCategory("Food");
                                    editedItem = existingMenu.getEntry(itemSearchPosition);
                                    existingMenu.replace(itemSearchPosition, editedItem);

                                    System.out.println("\nUpdated Details of the Item");
                                    System.out.println("===========================");                        
                                    System.out.println(existingMenu.getEntry(itemSearchPosition));      
                                    

                                if(itemSearchPosition == 1){

                                        Iterator<Item> rearrange = existingMenu.getIterator();


                                        while (rearrange.hasNext()) {
                                            Item current = rearrange.next();

                                            if(current.getCategory() == "Food"){

                                                 foodItem.add(current);


                                            }else{
                                                beverageItem.add(current);
                                            }

                                        }  

                                        existingMenu.clear();

                                        while (!beverageItem.isEmpty()) {

                                             existingMenu.add(beverageItem.remove(1));
                                         } 

                                        while(!foodItem.isEmpty()){
                                             existingMenu.add(foodItem.remove(1));

                                        }


                                }                                    
                                                      
                                }else{
                                    
                                    System.out.printf("\nTHE CATEGORY OF THE %s ITEM WAS NOT EDITED. \n\n",checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                    
                                }                 
                                                           
                            }
                            
                            
                        }else if(editOption == 4){
                            if(checkAvailability(existingMenu.getEntry(itemSearchPosition))== 'Y'){

                                System.out.printf("\nAre you sure to edit the availability of the %s to NOT AVAILABLE (Y/N)? ", checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                editAvailability=scanner.next().charAt(0);      

                                // validate user input
                                while (Character.toUpperCase(editAvailability) != 'Y' && Character.toUpperCase(editAvailability) != 'N') {
                                    System.out.println("\nInvalid character entered. Please re-enter. ");
                                    System.out.printf("\nAre you sure to edit the availability of the %s to NOT AVAILABLE (Y/N)? ", checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                    editAvailability=scanner.next().charAt(0);   
                                }                                 

                                if(Character.toUpperCase(editAvailability) == 'Y'){
                                   existingMenu.getEntry(itemSearchPosition).setAvailability('N');
                                   editedItem = existingMenu.getEntry(itemSearchPosition);
                                   existingMenu.replace(itemSearchPosition, editedItem);

                                   System.out.println("\nUpdated Details of the Item");
                                   System.out.println("===========================");                        
                                   System.out.println(existingMenu.getEntry(itemSearchPosition));                         

                                }else{

                                   System.out.printf("\nTHE AVAILABILITY OF THE %s ITEM WAS NOT EDITED. \n\n",checkCategory(existingMenu.getEntry(itemSearchPosition)));

                                }                                   
                                
                            }else{

                                System.out.printf("\nAre you sure to edit the availability of the %s to AVAILABLE (Y/N)? ", checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                editAvailability=scanner.next().charAt(0);      

                                // validate user input
                                while (Character.toUpperCase(editAvailability) != 'Y' && Character.toUpperCase(editAvailability) != 'N') {
                                    System.out.println("\nInvalid character entered. Please re-enter. ");
                                    System.out.printf("\nAre you sure to edit the availability of the %s to NOT AVAILABLE (Y/N)? ", checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                    editAvailability=scanner.next().charAt(0);   
                                }                                 

                                if(Character.toUpperCase(editAvailability) == 'Y'){
                                   existingMenu.getEntry(itemSearchPosition).setAvailability('N');
                                   editedItem = existingMenu.getEntry(itemSearchPosition);
                                   existingMenu.replace(itemSearchPosition, editedItem);

                                   System.out.println("\nUpdated Details of the Item");
                                   System.out.println("===========================");                        
                                   System.out.println(existingMenu.getEntry(itemSearchPosition));                         

                                }else{

                                   System.out.printf("\nTHE AVAILABILITY OF THE %s ITEM WAS NOT EDITED. \n\n",checkCategory(existingMenu.getEntry(itemSearchPosition)));

                                }   

                                
                            }
                            
                        }else if(editOption == 5){

                            do{

                                System.out.printf("\nPlease enter the NEW PRICE of the %s : ", checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                price = scanner.nextDouble();     

                                if(price<=0){
                                     System.out.println("\nInvalid amount entered. Please re-enter. \n");
                                    priceValid = false;
                                }else{
                                    priceValid=true;
                                }    

                            }while(priceValid==false);                    


                            //ask to confirm to edit                
                             System.out.printf("\nAre you sure to edit the price of the %s (Y/N)? ",checkCategory(existingMenu.getEntry(itemSearchPosition)));
                             confirmEdit=scanner.next().charAt(0);

                             // validate user input
                             while (Character.toUpperCase(confirmEdit) != 'Y' && Character.toUpperCase(confirmEdit) != 'N') {
                                System.out.println("\nInvalid character entered. Please re-enter. ");
                                System.out.printf("\nAre you sure to edit the price of the %s (Y/N)? ", checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                confirmEdit=scanner.next().charAt(0);
                             }

                             if(Character.toUpperCase(confirmEdit) == 'Y'){
                                existingMenu.getEntry(itemSearchPosition).setPrice(price);
                                editedItem = existingMenu.getEntry(itemSearchPosition);
                                existingMenu.replace(itemSearchPosition, editedItem);

                                System.out.println("\nUpdated Details of the Item");
                                System.out.println("===========================");                        
                                System.out.println(existingMenu.getEntry(itemSearchPosition));                         

                             }else{

                                System.out.printf("\nTHE CHANGES MADE TO THE %s ITEM WAS NOT EDITED. \n\n",checkCategory(existingMenu.getEntry(itemSearchPosition)));

                             }                                
                            
                            
                        }else{
                            
                            foodItem.clear();
                            beverageItem.clear();
                            //id of the item to be changed so that can use it to find in the food item list
                            itemId = existingMenu.getEntry(itemSearchPosition).getItemId();



                            if(checkCategory(existingMenu.getEntry(itemSearchPosition)).equals("FOOD")){

                                Iterator<Item> rearrange = existingMenu.getIterator();

                                while (rearrange.hasNext()) {
                                    Item current = rearrange.next();

                                    if (current.getCategory() == "Food") {

                                        foodItem.add(current);
                                        if (countFood == 1) {
                                            seq=2;
                                        }
                                        countFood++;
                                    } else {
                                        beverageItem.add(current);
                                        if (countBeverage == 1) {
                                            seq=1;
                                        }
                                        countBeverage++;
                                    }

                                }

                            if (foodItem.getNumberOfEntries() >1) {


                                System.out.println("\nRestaurant Menu Item");
                                System.out.println("======================");
                                System.out.println("\nFOOD");
                                System.out.println("----");

                                displayItemOnly(foodItem);


                                System.out.printf("\nDo you want to edit the sequence of the %s with the ID %s (Y/N)? ", checkCategory(existingMenu.getEntry(itemSearchPosition)),itemId);
                                editSpecific = scanner.next().charAt(0);

                                // validate user input
                                while (Character.toUpperCase(editSpecific) != 'Y' && Character.toUpperCase(editSpecific) != 'N') {
                                    System.out.println("\nInvalid character entered. Please re-enter. ");
                                    System.out.printf("\nDo you want to edit the sequence of the %s with the ID %s (Y/N)? ", checkCategory(existingMenu.getEntry(itemSearchPosition)), itemId);
                                    editSpecific = scanner.next().charAt(0);
                                }

                                do{



                                if(Character.toUpperCase(editSpecific) == 'Y'){

                                    if (foodItem.getNumberOfEntries() > 2) {
                                        System.out.println("\nPosition");
                                        System.out.println("========");
                                        System.out.println("1. Front");
                                        System.out.println("2. Back");
                                        System.out.println("3. Middle");

                                        System.out.print("Please enter your choice: ");
                                        choicePosition = scanner.nextInt();

                                        while (choicePosition < 1 || choicePosition > 3) {
                                            System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                            System.out.print("Please enter your choice: ");
                                            choicePosition = scanner.nextInt();
                                        }

                                    } else {
                                        System.out.println("\nPosition");
                                        System.out.println("========");
                                        System.out.println("1. Front");
                                        System.out.println("2. Back");

                                        System.out.print("Please enter your choice: ");
                                        choicePosition = scanner.nextInt();

                                        while (choicePosition < 1 || choicePosition > 2) {
                                            System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                            System.out.print("Please enter your choice: ");
                                            choicePosition = scanner.nextInt();
                                        }

                                    }

                                    Iterator<Item> foodId = foodItem.getIterator();
                                    int countId = 1;

                                    while (foodId.hasNext()) {
                                        Item current = foodId.next();

                                        if (current.getItemId().equals(itemId)) {

                                            removedPosition = countId;

                                        }
                                        countId++;
                                    }

                                    if (choicePosition == 1) {
                                        if (removedPosition == 1) {
                                            System.out.printf("\nThe item with the ID %s is ALREADY at the FIRST position !!\n",itemId);
                                        } else {
                                            removedItem = foodItem.remove(removedPosition);
                                            added = foodItem.add(1, removedItem);
                                        }

                                    } else if (choicePosition == 2) {
                                        if (removedPosition == foodItem.getNumberOfEntries()) {
                                            System.out.printf("\nThe item with the ID %s is ALREADY at the LAST position !!",itemId);

                                        } else {
                                            removedItem = foodItem.remove(removedPosition);
                                            added = foodItem.add(foodItem.getNumberOfEntries() + 1, removedItem);
                                        }

                                    } else {

                                        if (foodItem.getNumberOfEntries() == 3) {
                                            if (removedPosition == 2) {
                                                System.out.printf("\nThe item with the ID %s is ALREADY in the MIDDLE position !!",itemId);

                                            } else {
                                                removedItem = foodItem.remove(removedPosition);
                                                added = foodItem.add(2, removedItem);
                                            }

                                        } else {

                                            System.out.printf("\nPlease enter the NEW POSITION of the %s : ", checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                            editPosition = scanner.nextInt();

                                            while (editPosition <= 0 || editPosition > foodItem.getNumberOfEntries()) {
                                                System.out.println("\nInvalid position entered. Please re-enter. \n");
                                                System.out.printf("\nPlease enter the NEW POSITION of the %s : ", checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                                editPosition = scanner.nextInt();
                                            }

                                            if (removedPosition == editPosition) {
                                                System.out.printf("\nThe item with the ID %s is ALREADY in the the specified position !!",itemId);

                                            } else {
                                                removedItem = foodItem.remove(removedPosition);
                                                added = foodItem.add(editPosition, removedItem);
                                            }

                                        }

                                    }

                                    if (added) {
                                        System.out.printf("\n\nThe sequence of the %s with the ID %s have been edited successfully.\n\n", checkCategory(existingMenu.getEntry(itemSearchPosition)),itemId);
                                        System.out.printf("\nUpdated Sequence of the %s category\n",checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                        System.out.println("======================================");
                                        displayFood(foodItem);

                                        existingMenu.clear();

                                        if (seq == 1) {
                                            while (!foodItem.isEmpty()) {
                                                existingMenu.add(foodItem.remove(1));

                                            }

                                            while (!beverageItem.isEmpty()) {

                                                existingMenu.add(beverageItem.remove(1));
                                            }

                                        } else {

                                            while (!beverageItem.isEmpty()) {

                                                existingMenu.add(beverageItem.remove(1));
                                            }

                                            while (!foodItem.isEmpty()) {
                                                existingMenu.add(foodItem.remove(1));

                                            }
                                        }
                                        editSpecific = 'N';
                                    } else {
                                        System.out.printf("\n\nThe sequence of the %s with the ID %s was not edited successfully.\n\n", checkCategory(existingMenu.getEntry(itemSearchPosition)), itemId);


                                        System.out.println("\nRestaurant Menu Item");
                                        System.out.println("======================");
                                        System.out.println("\nFOOD");
                                        System.out.println("----");

                                        displayItemOnly(foodItem);

                                        System.out.printf("\nDo you want to edit the sequence of the %s with the ID %s (Y/N)? ", checkCategory(existingMenu.getEntry(itemSearchPosition)), itemId);
                                        editSpecific = scanner.next().charAt(0);

                                        // validate user input
                                        while (Character.toUpperCase(editSpecific) != 'Y' && Character.toUpperCase(editSpecific) != 'N') {
                                            System.out.println("\nInvalid character entered. Please re-enter. ");
                                            System.out.printf("\nDo you want to edit the sequence of the %s with the ID %s (Y/N)? ", checkCategory(existingMenu.getEntry(itemSearchPosition)), itemId);
                                            editSpecific = scanner.next().charAt(0);
                                        }


                                    }

                                }



                                }while(Character.toUpperCase(editSpecific) == 'Y');

                            }else{
                                System.out.printf("\nThere's %d %s in the Restaurant Menu ! No rearrangement can be done. \n\n",foodItem.getNumberOfEntries() ,checkCategory(existingMenu.getEntry(itemSearchPosition)));

                            }


                            }else{

                                Iterator<Item> rearrange = existingMenu.getIterator();

                                while (rearrange.hasNext()) {
                                    Item current = rearrange.next();

                                    if (current.getCategory() == "Beverage") {

                                       beverageItem.add(current);
                                        if (countBeverage == 1) {
                                            seq=2;
                                        }
                                        countBeverage++;
                                    } else {
                                        foodItem.add(current);
                                        if (countFood == 1) {
                                            seq=1;
                                        }
                                        countFood++;
                                    }

                                }

                            if (beverageItem.getNumberOfEntries() >1) {


                                System.out.println("\nRestaurant Menu Item");
                                System.out.println("======================");
                                System.out.println("\nBEVERAGE");
                                System.out.println("--------");

                                displayItemOnly(beverageItem);


                                System.out.printf("\nDo you want to edit the sequence of the %s with the ID %s (Y/N)? ", checkCategory(existingMenu.getEntry(itemSearchPosition)),itemId);
                                editSpecific = scanner.next().charAt(0);

                                // validate user input
                                while (Character.toUpperCase(editSpecific) != 'Y' && Character.toUpperCase(editSpecific) != 'N') {
                                    System.out.println("\nInvalid character entered. Please re-enter. ");
                                    System.out.printf("\nDo you want to edit the sequence of the %s with the ID %s (Y/N)? ", checkCategory(existingMenu.getEntry(itemSearchPosition)), itemId);
                                    editSpecific = scanner.next().charAt(0);
                                }

                                do{



                                if(Character.toUpperCase(editSpecific) == 'Y'){

                                    if (beverageItem.getNumberOfEntries() > 2) {
                                        System.out.println("\nPosition");
                                        System.out.println("========");
                                        System.out.println("1. Front");
                                        System.out.println("2. Back");
                                        System.out.println("3. Middle");

                                        System.out.print("Please enter your choice: ");
                                        choicePosition = scanner.nextInt();

                                        while (choicePosition < 1 || choicePosition > 3) {
                                            System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                            System.out.print("Please enter your choice: ");
                                            choicePosition = scanner.nextInt();
                                        }

                                    } else {
                                        System.out.println("\nPosition");
                                        System.out.println("========");
                                        System.out.println("1. Front");
                                        System.out.println("2. Back");

                                        System.out.print("Please enter your choice: ");
                                        choicePosition = scanner.nextInt();

                                        while (choicePosition < 1 || choicePosition > 2) {
                                            System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                            System.out.print("Please enter your choice: ");
                                            choicePosition = scanner.nextInt();
                                        }

                                    }

                                    Iterator<Item> beverageId = beverageItem.getIterator();
                                    int countId = 1;

                                    while (beverageId.hasNext()) {
                                        Item current = beverageId.next();

                                        if (current.getItemId().equals(itemId)) {

                                            removedPosition = countId;

                                        }
                                        countId++;
                                    }

                                    if (choicePosition == 1) {
                                        if (removedPosition == 1) {
                                            System.out.printf("\nThe item with the ID %s is ALREADY at the FIRST position !!\n",itemId);
                                        } else {
                                            removedItem = beverageItem.remove(removedPosition);
                                            added = beverageItem.add(1, removedItem);
                                        }

                                    } else if (choicePosition == 2) {
                                        if (removedPosition == beverageItem.getNumberOfEntries()) {
                                            System.out.printf("\nThe item with the ID %s is ALREADY at the LAST position !!",itemId);

                                        } else {
                                            removedItem = beverageItem.remove(removedPosition);
                                            added = beverageItem.add(beverageItem.getNumberOfEntries() + 1, removedItem);
                                        }

                                    } else {

                                        if (beverageItem.getNumberOfEntries() == 3) {
                                            if (removedPosition == 2) {
                                                System.out.printf("\nThe item with the ID %s is ALREADY in the MIDDLE position !!",itemId);

                                            } else {
                                                removedItem = beverageItem.remove(removedPosition);
                                                added = beverageItem.add(2, removedItem);
                                            }

                                        } else {

                                            System.out.printf("\nPlease enter the NEW POSITION of the %s : ", checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                            editPosition = scanner.nextInt();

                                            while (editPosition <= 0 || editPosition > beverageItem.getNumberOfEntries()) {
                                                System.out.println("\nInvalid position entered. Please re-enter. \n");
                                                System.out.printf("\nPlease enter the NEW POSITION of the %s : ", checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                                editPosition = scanner.nextInt();
                                            }

                                            if (removedPosition == editPosition) {
                                                System.out.printf("\nThe item with the ID %s is ALREADY in the the specified position !!",itemId);

                                            } else {
                                                removedItem = beverageItem.remove(removedPosition);
                                                added = beverageItem.add(editPosition, removedItem);
                                            }

                                        }

                                    }

                                    if (added) {
                                        System.out.printf("\n\nThe sequence of the %s with the ID %s have been edited successfully.\n\n", checkCategory(existingMenu.getEntry(itemSearchPosition)),itemId);
                                        System.out.printf("\nUpdated Sequence of the %s category\n",checkCategory(existingMenu.getEntry(itemSearchPosition)));
                                        System.out.println("======================================");
                                        displayBeverage(beverageItem);

                                        existingMenu.clear();

                                        if (seq == 1) {
                                            
                                            while (!beverageItem.isEmpty()) {

                                                existingMenu.add(beverageItem.remove(1));
                                            }

                                            while (!foodItem.isEmpty()) {
                                                existingMenu.add(foodItem.remove(1));

                                            }

                                        } else {
                                            
                                            while (!foodItem.isEmpty()) {
                                                existingMenu.add(foodItem.remove(1));

                                            }

                                            while (!beverageItem.isEmpty()) {

                                                existingMenu.add(beverageItem.remove(1));
                                            }
                                            
                                        }
                                        editSpecific = 'N';
                                    } else {
                                        System.out.printf("\n\nThe sequence of the %s with the ID %s was not edited successfully.\n\n", checkCategory(existingMenu.getEntry(itemSearchPosition)), itemId);


                                        System.out.println("\nRestaurant Menu Item");
                                        System.out.println("======================");
                                        System.out.println("\nBEVERAGE");
                                        System.out.println("--------");

                                        displayItemOnly(beverageItem);

                                        System.out.printf("\nDo you want to edit the sequence of the %s with the ID %s (Y/N)? ", checkCategory(existingMenu.getEntry(itemSearchPosition)), itemId);
                                        editSpecific = scanner.next().charAt(0);

                                        // validate user input
                                        while (Character.toUpperCase(editSpecific) != 'Y' && Character.toUpperCase(editSpecific) != 'N') {
                                            System.out.println("\nInvalid character entered. Please re-enter. ");
                                            System.out.printf("\nDo you want to edit the sequence of the %s with the ID %s (Y/N)? ", checkCategory(existingMenu.getEntry(itemSearchPosition)), itemId);
                                            editSpecific = scanner.next().charAt(0);
                                        }


                                    }

                                }

                                }while(Character.toUpperCase(editSpecific) == 'Y');

                            }else{
                                System.out.printf("\nThere's %d %s in the Restaurant Menu ! No rearrangement can be done. \n\n",beverageItem.getNumberOfEntries() ,checkCategory(existingMenu.getEntry(itemSearchPosition)));

                            }
                   
                                
                            }             
      
                            
                        }
                    
                        System.out.print("\nDo you want to edit other details of this item (Y/N)? ");
                        editSameItem=scanner.next().charAt(0);      

                        // validate user input
                        while (Character.toUpperCase(editSameItem) != 'Y' && Character.toUpperCase(editSameItem) != 'N') {
                            System.out.println("\nInvalid character entered. Please re-enter. ");
                            System.out.print("\nDo you want to edit other details of this item (Y/N)? ");
                            editSameItem=scanner.next().charAt(0);   
                        }                     
                    
                    

                    }while(Character.toUpperCase(editSameItem) == 'Y');
                }
                
                    displayAll(existingMenu);
                
        }else{
            menuInterface();
        }
        }while(Character.toUpperCase(editDetails) != 'Y');

        }

        return existingMenu;
        

    }
    
    public static ListInterface<Item> editMenu(ListInterface<Item> existingMenu){
        
        Scanner scanner = new Scanner(System.in);
        
        char editCategorySeq = 'N';
        
        ListInterface<Item> beverageItem = new SinglyLinkedList<>();
        ListInterface<Item> foodItem = new SinglyLinkedList<>();
        
        displayAll(existingMenu);

        if (checkCategory(existingMenu.getEntry(1)).equals("FOOD")) {

            System.out.printf("\nAre you sure to edit the sequence of the category of the menu to display BEVERAGE first (Y/N)? ");
            editCategorySeq = scanner.next().charAt(0);

            // validate user input
            while (Character.toUpperCase(editCategorySeq) != 'Y' && Character.toUpperCase(editCategorySeq) != 'N') {
                System.out.println("\nInvalid character entered. Please re-enter. ");
                System.out.printf("\nAre you sure to edit the sequence of the category of the menu to display BEVERAGE first (Y/N)? ");
                editCategorySeq = scanner.next().charAt(0);
            }

            if (Character.toUpperCase(editCategorySeq) == 'Y') {

                Iterator<Item> rearrange = existingMenu.getIterator();

                while (rearrange.hasNext()) {
                    Item current = rearrange.next();

                    if (current.getCategory() == "Beverage") {

                        beverageItem.add(current);

                    } else {
                        foodItem.add(current);

                    }

                }

                existingMenu.clear();

                while (!beverageItem.isEmpty()) {

                    existingMenu.add(beverageItem.remove(1));
                }

                while (!foodItem.isEmpty()) {
                    existingMenu.add(foodItem.remove(1));

                }

                System.out.println("\nUpdated Sequence of Category");
                System.out.println("============================");
                displayAll(existingMenu);

            } else {

                System.out.println("\nTHE SEQUENCE OF CATEGORY OF THE MENU WAS NOT EDITED. \n");

            }
            
        }else{
            
            System.out.printf("\nAre you sure to edit the sequence of the category of the menu to display FOOD first (Y/N)? ");
            editCategorySeq = scanner.next().charAt(0);

            // validate user input
            while (Character.toUpperCase(editCategorySeq) != 'Y' && Character.toUpperCase(editCategorySeq) != 'N') {
                System.out.println("\nInvalid character entered. Please re-enter. ");
                System.out.printf("\nAre you sure to edit the sequence of the category of the menu to display FOOD first (Y/N)? ");
                editCategorySeq = scanner.next().charAt(0);
            }

            if (Character.toUpperCase(editCategorySeq) == 'Y') {

                Iterator<Item> rearrange = existingMenu.getIterator();

                while (rearrange.hasNext()) {
                    Item current = rearrange.next();

                    if (current.getCategory() == "Beverage") {

                        beverageItem.add(current);

                    } else {
                        foodItem.add(current);

                    }

                }

                existingMenu.clear();

                while (!foodItem.isEmpty()) {
                    existingMenu.add(foodItem.remove(1));

                }
                while (!beverageItem.isEmpty()) {

                    existingMenu.add(beverageItem.remove(1));
                }
                System.out.println("\nUpdated Sequence of Category");
                System.out.println("============================");
                displayAll(existingMenu);

            } else {

                System.out.println("\nTHE SEQUENCE OF CATEGORY OF THE MENU WAS NOT EDITED. \n");

            }
 
            
        }

        return existingMenu;
    }
        
    public static ListInterface<Item> editSelection(ListInterface<Item> existingMenu){
        
        Scanner scanner = new Scanner(System.in);
        
        int editOption;
        
        do{
            
            System.out.println("\nEdit Selection");
            System.out.println("==============");
            System.out.println("1. Edit Item");
            System.out.println("2. Edit Menu");

            System.out.print("Please enter your choice (0 to Exit): ");
            editOption = scanner.nextInt();

            while (editOption < 0 || editOption > 2) {
                System.out.println("\nInvalid choice entered. Please re-enter. \n");
                System.out.print("Please enter your choice (0 to Exit): ");
                editOption = scanner.nextInt();
            }
            
            if(editOption == 1){
                existingMenu = editItem(existingMenu);

            }else if(editOption == 2){
                existingMenu = editMenu(existingMenu);

            }
            
        }while(editOption!=0);
        
        return existingMenu;
        
    }
    
    public static ListInterface<Item> deleteMenu(ListInterface<Item> existingMenu){
        
        Scanner scanner = new Scanner(System.in);
        
        int delOption,delCategory, delPosition, countFood=0, countBeverage=0, seq=0;
        char confDel;
        
        ListInterface<Item> foodItem = new SinglyLinkedList<>();
        ListInterface<Item> beverageItem = new SinglyLinkedList<>();

        do{

            System.out.println("\nDelete Selection");
            System.out.println("================");
            System.out.println("1. Delete Item");
            System.out.println("2. Delete Menu");

            System.out.print("Please enter your choice (0 to Exit): ");
            delOption = scanner.nextInt();

            while (delOption < 0 || delOption > 2) {
                System.out.println("\nInvalid choice entered. Please re-enter. \n");
                System.out.print("Please enter your choice (0 to Exit): ");
                delOption = scanner.nextInt();
            }

            if (existingMenu.getNumberOfEntries() != 0) {
                displayAll(existingMenu);
            }
             
            if (delOption == 2) {

                System.out.println("\nArea of Deletion");
                System.out.println("================");
                System.out.println("1. Delete Food Category");
                System.out.println("2. Delete Beverage Category");
                System.out.println("3. Delete All");
                System.out.print("Please enter your choice (0 to Exit): ");
                delCategory = scanner.nextInt();

                while (delCategory < 0 || delCategory > 3) {
                    System.out.println("\nInvalid choice entered. Please re-enter. \n");
                    System.out.print("Please enter your choice (0 to Exit): ");
                    delCategory = scanner.nextInt();
                }
                
                foodItem.clear();
                beverageItem.clear();
                
                if(delCategory != 0){

                    if (delCategory == 1) {

                        Iterator<Item> menuIterator = existingMenu.getIterator();

                        while (menuIterator.hasNext()) {
                            Item current = menuIterator.next();

                            if (current.getCategory() == "Beverage") {

                                beverageItem.add(current);

                            } else {
                                foodItem.add(current);

                            }

                        }

                        if (foodItem.getNumberOfEntries() != 0) {

                            displayFood(existingMenu);

                            System.out.print("\nAre you sure to delete ALL items in the Food Category (Y/N)? ");
                            confDel = scanner.next().charAt(0);

                            // validate user input
                            while (Character.toUpperCase(confDel) != 'Y' && Character.toUpperCase(confDel) != 'N') {
                                System.out.println("\nInvalid character entered. Please re-enter. ");
                                System.out.print("\nAre you sure to delete ALL items in the Food Category (Y/N)? ");
                                confDel = scanner.next().charAt(0);
                            }

                            if (Character.toUpperCase(confDel) == 'Y') {

                                existingMenu.clear();

                                while (!beverageItem.isEmpty()) {

                                    existingMenu.add(beverageItem.remove(1));
                                }

                                System.out.println("\nUpdated Menu");
                                System.out.println("============");

                            } else {

                                System.out.println("\nNOTHING HAVE BEEN DELETED FROM THE MENU. \n");

                            }

                        } else {
                            System.out.println("\nThe Restaurant Menu does not contain any FOOD item !! \n");

                        }

                    } else if (delCategory == 2) {

                        Iterator<Item> menuIterator = existingMenu.getIterator();

                        while (menuIterator.hasNext()) {
                            Item current = menuIterator.next();

                            if (current.getCategory() == "Beverage") {

                                beverageItem.add(current);

                            } else {
                                foodItem.add(current);

                            }

                        }

                        if (beverageItem.getNumberOfEntries() != 0) {

                            displayBeverage(existingMenu);

                            System.out.print("\nAre you sure to delete ALL items in the Beverage Category (Y/N)? ");
                            confDel = scanner.next().charAt(0);

                            // validate user input
                            while (Character.toUpperCase(confDel) != 'Y' && Character.toUpperCase(confDel) != 'N') {
                                System.out.println("\nInvalid character entered. Please re-enter. ");
                                System.out.print("\nAre you sure to delete ALL items in the Beverage Category (Y/N)? ");
                                confDel = scanner.next().charAt(0);
                            }

                            if (Character.toUpperCase(confDel) == 'Y') {

                                existingMenu.clear();

                                while (!foodItem.isEmpty()) {

                                    existingMenu.add(foodItem.remove(1));
                                }

                                System.out.println("\nUpdated Menu");
                                System.out.println("============");

                            } else {

                                System.out.println("\nNOTHING HAVE BEEN DELETED FROM THE MENU. \n");

                            }

                        } else {
                            System.out.println("\nThe Restaurant Menu does not contain any Beverage item !! \n");

                        }

                    } else {
                        if (existingMenu.getNumberOfEntries() != 0) {

                            displayAll(existingMenu);

                            System.out.print("\nAre you sure to delete ALL items in the Restaurant Menu (Y/N)? ");
                            confDel = scanner.next().charAt(0);

                            // validate user input
                            while (Character.toUpperCase(confDel) != 'Y' && Character.toUpperCase(confDel) != 'N') {
                                System.out.println("\nInvalid character entered. Please re-enter. ");
                                System.out.print("\nAre you sure to delete ALL items in the Restaurant Menu (Y/N)? ");
                                confDel = scanner.next().charAt(0);
                            }

                            if (Character.toUpperCase(confDel) == 'Y') {

                                existingMenu.clear();

                                System.out.println("\nUpdated Menu");
                                System.out.println("============");

                            } else {

                                System.out.println("\nNOTHING HAVE BEEN DELETED FROM THE MENU. \n");

                            }

                        } else {
                            System.out.println("\nThe Restaurant Menu does not contain any item !! \n");

                        }

                    }

                    displayAll(existingMenu);


                }

            } else if(delOption==1){

                System.out.println("\nArea of Deletion");
                System.out.println("================");
                System.out.println("1. Delete Food Item");
                System.out.println("2. Delete Beverage Item");
                System.out.print("Please enter your choice (0 to Exit): ");
                delCategory = scanner.nextInt();

                while (delCategory < 0 || delCategory > 2) {
                    System.out.println("\nInvalid choice entered. Please re-enter. \n");
                    System.out.print("Please enter your choice (0 to Exit): ");
                    delCategory = scanner.nextInt();
                }

                if (delCategory != 0) {

                    if (delCategory == 1) {

                        foodItem = displayFood(existingMenu);

                        if (foodItem.getNumberOfEntries() != 0) {

                            System.out.printf("\nPlease enter the POSITION of the FOOD to be deleted: ");
                            delPosition = scanner.nextInt();

                            while (delPosition <= 0 || delPosition > foodItem.getNumberOfEntries()) {
                                System.out.println("\nInvalid position entered. Please re-enter. \n");
                                System.out.printf("\nPlease enter the POSITION of the FOOD to be deleted: ");
                                delPosition = scanner.nextInt();
                            }

                            System.out.println("\nDetails of the FOOD to be deleted");
                            System.out.println("=================================");
                            System.out.print("1.\n" + foodItem.getEntry(delPosition).toString());

                            System.out.print("\nAre you sure to delete the FOOD shown above (Y/N)? ");
                            confDel = scanner.next().charAt(0);

                            // validate user input
                            while (Character.toUpperCase(confDel) != 'Y' && Character.toUpperCase(confDel) != 'N') {
                                System.out.println("\nInvalid character entered. Please re-enter. ");
                                System.out.print("\nAre you sure to delete the FOOD shown above (Y/N)? ");
                                confDel = scanner.next().charAt(0);
                            }

                            if (Character.toUpperCase(confDel) == 'Y') {

                                foodItem.remove(delPosition);

                                Iterator<Item> rearrange = existingMenu.getIterator();

                                while (rearrange.hasNext()) {
                                    Item current = rearrange.next();

                                    if (current.getCategory() == "Food") {

                                        if (countFood == 1) {
                                            seq = 2;
                                        }
                                        countFood++;
                                    } else {
                                        beverageItem.add(current);
                                        if (countBeverage == 1) {
                                            seq = 1;
                                        }
                                        countBeverage++;
                                    }

                                }

                                existingMenu.clear();

                                if (seq == 1) {
                                    while (!foodItem.isEmpty()) {

                                        existingMenu.add(foodItem.remove(1));
                                    }

                                    while (!beverageItem.isEmpty()) {

                                        existingMenu.add(beverageItem.remove(1));
                                    }
                                } else {

                                    while (!beverageItem.isEmpty()) {

                                        existingMenu.add(beverageItem.remove(1));
                                    }

                                    while (!foodItem.isEmpty()) {

                                        existingMenu.add(foodItem.remove(1));
                                    }
                                }

                                System.out.println("\nUpdated Menu");
                                System.out.println("============");

                            } else {

                                System.out.println("\nNOTHING HAVE BEEN DELETED FROM THE MENU. \n");

                            }

                        } else {
                            System.out.println("\nThe Restaurant Menu does not contain any FOOD item !! \n");

                        }

                    } else {

                        beverageItem = displayBeverage(existingMenu);

                        if (beverageItem.getNumberOfEntries() != 0) {

                            System.out.printf("\nPlease enter the POSITION of the BEVERAGE to be deleted: ");
                            delPosition = scanner.nextInt();

                            while (delPosition <= 0 || delPosition > beverageItem.getNumberOfEntries()) {
                                System.out.println("\nInvalid position entered. Please re-enter. \n");
                                System.out.printf("\nPlease enter the POSITION of the BEVERAGE to be deleted: ");
                                delPosition = scanner.nextInt();
                            }

                            System.out.println("\nDetails of the BEVERAGE to be deleted");
                            System.out.println("=====================================");
                            System.out.print("1.\n" + beverageItem.getEntry(delPosition).toString());

                            System.out.print("\nAre you sure to delete the BEVERAGE shown above (Y/N)? ");
                            confDel = scanner.next().charAt(0);

                            // validate user input
                            while (Character.toUpperCase(confDel) != 'Y' && Character.toUpperCase(confDel) != 'N') {
                                System.out.println("\nInvalid character entered. Please re-enter. ");
                                System.out.print("\nAre you sure to delete the BEVERAGE shown above (Y/N)? ");
                                confDel = scanner.next().charAt(0);
                            }

                            if (Character.toUpperCase(confDel) == 'Y') {

                                beverageItem.remove(delPosition);

                                Iterator<Item> rearrange = existingMenu.getIterator();

                                while (rearrange.hasNext()) {
                                    Item current = rearrange.next();

                                    if (current.getCategory() == "Food") {
                                        foodItem.add(current);

                                        if (countFood == 1) {
                                            seq = 2;
                                        }
                                        countFood++;
                                    } else {

                                        if (countBeverage == 1) {
                                            seq = 1;
                                        }
                                        countBeverage++;
                                    }

                                }

                                existingMenu.clear();

                                if (seq == 1) {
                                    while (!foodItem.isEmpty()) {

                                        existingMenu.add(foodItem.remove(1));
                                    }

                                    while (!beverageItem.isEmpty()) {

                                        existingMenu.add(beverageItem.remove(1));
                                    }
                                } else {

                                    while (!beverageItem.isEmpty()) {

                                        existingMenu.add(beverageItem.remove(1));
                                    }

                                    while (!foodItem.isEmpty()) {

                                        existingMenu.add(foodItem.remove(1));
                                    }
                                }

                                System.out.println("\nUpdated Menu");
                                System.out.println("============");

                            } else {

                                System.out.println("\nNOTHING HAVE BEEN DELETED FROM THE MENU. \n");

                            }

                        } else {
                            System.out.println("\nThe Restaurant Menu does not contain any FOOD item !! \n");

                        }

                    }
                    displayAll(existingMenu);

                }
      
                
            }

            
        }while(delOption != 0);
      
        return existingMenu;
    }
        
    public static void viewMenu(ListInterface<Item> existingMenu){
        
        Scanner scanner = new Scanner(System.in);

        int viewMenu,viewOption,dispOption, dispSeq, found;
        char viewSpecific='N';
        
            
        do{
            System.out.println("\nView Menu");
            System.out.println("=========");
            System.out.println("1. View Specific Item");
            System.out.println("2. View Several Items");
            System.out.print("Please enter your choice (0 to Exit): ");
            viewMenu = scanner.nextInt();

            while (viewMenu < 0 || viewMenu > 2) {
                System.out.println("\nInvalid choice entered. Please re-enter. \n");
                System.out.print("Please enter your choice (0 to Exit): ");
                viewMenu = scanner.nextInt();
            }

            if (viewMenu == 1) {
                
                do{
                    
                    found = searchItem(existingMenu);
                    if(found!=0){

                        System.out.print("\nDo you want to view other details of a specific item (Y/N)? ");
                        viewSpecific = scanner.next().charAt(0);

                        // validate user input
                        while (Character.toUpperCase(viewSpecific) != 'Y' && Character.toUpperCase(viewSpecific) != 'N') {
                            System.out.println("\nInvalid character entered. Please re-enter. ");
                            System.out.print("\nDo you want to view other details of a specific item (Y/N)? ");
                            viewSpecific = scanner.next().charAt(0);
                        }

                    }

                    
                }while(Character.toUpperCase(viewSpecific) == 'Y');
                
            } else {

                do {

                    System.out.println("\nView Selection");
                    System.out.println("================");
                    System.out.println("1. Food Items");
                    System.out.println("2. Beverage Items");
                    System.out.println("3. All Items");

                    System.out.print("Please enter your choice (0 to Exit): ");
                    viewOption = scanner.nextInt();

                    while (viewOption < 0 || viewOption > 3) {
                        System.out.println("\nInvalid choice entered. Please re-enter. \n");
                        System.out.print("Please enter your choice (0 to Exit): ");
                        viewOption = scanner.nextInt();
                    }

                    if (viewOption == 1) {

                        System.out.println("\nDisplay Selection");
                        System.out.println("=================");
                        System.out.println("1. By Name");
                        System.out.println("2. By Price");
                        System.out.println("3. By Availability");
                        System.out.print("Please enter your choice (0 to Exit): ");
                        dispOption = scanner.nextInt();

                        while (dispOption < 0 || dispOption > 3) {
                            System.out.println("\nInvalid choice entered. Please re-enter. \n");
                            System.out.print("Please enter your choice (0 to Exit): ");
                            dispOption = scanner.nextInt();
                        }

                        if (dispOption == 1) {
                            
                            System.out.println("\nDisplay Sequence");
                            System.out.println("================");
                            System.out.println("1. Ascending");
                            System.out.println("2. Descending");
                            System.out.print("Please enter your choice (0 to Exit): ");
                            dispSeq = scanner.nextInt();

                            while (dispSeq < 0 || dispSeq > 2) {
                                System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                System.out.print("Please enter your choice (0 to Exit): ");
                                dispSeq = scanner.nextInt();
                            }
                            
                            if(dispSeq == 1){
                                
                                //sort ascending
                                //call displayFood(pass in the list after sorted
                                
                            }else{
                                
                                //sort descending
                                //call displayFood(pass in the list after sorted

                            }

                        } else if (dispOption == 2) {
                            
                            

                        } else {

                            
                            
                        }

                    } else if (viewOption == 2) {
                        
                        

                    } else {

                    }

                } while (viewOption != 0);

            }




        }while(viewMenu != 0);
                   

        
    }
    
    //create a new method for dispaly all so when edit and additem can call the function to displat before edit or add
    public static void displayAll(ListInterface<Item> menuItem){
        
        Iterator<Item> menuIterator; 
        Item itemCategory;

        String outputStrFood = "",outputStrBeverage = "";
        int countFood=1, countBeverage=1, seq = 0;
        
        
        System.out.println("\nRestaurant Menu Item");
        System.out.println("====================");

        menuIterator = menuItem.getIterator();    
        

        while (menuIterator.hasNext()) {

             itemCategory = menuIterator.next();  
             if(itemCategory.getCategory().equals("Food")){
                 if(countFood==1){
                     outputStrFood += "\nFOOD\n----\n";
                     seq = 2;
                 }
                 outputStrFood += countFood + ".\n" + itemCategory.toString();
                 countFood++;
             }else if(itemCategory.getCategory().equals("Beverage")){
                 if(countBeverage==1){
                     outputStrBeverage += "\nBEVERAGE\n--------\n";
                     seq = 1;
                 }                 
                 outputStrBeverage += countBeverage + ".\n" + itemCategory.toString();
                 countBeverage++;
             }

         }  
                  
        
        if(seq == 1){
            System.out.println(outputStrFood + outputStrBeverage);            
            System.out.printf("%d FOOD item(s) & %d BEVERAGE item(s).\n",countFood-1, countBeverage-1);
            
        }else{
            
            System.out.println(outputStrBeverage + outputStrFood);
            System.out.printf("%d BEVERAGE item(s) & %d FOOD item(s).\n",countBeverage-1, countFood-1);
        }
        
        System.out.println("\nTotal: " + menuItem.getNumberOfEntries() + " item(s) in the Restaurant Menu.");                           
        
    }    
    
    public static ListInterface<Item> displayFood(ListInterface<Item> menuItem){
  
        Iterator<Item> menuIterator; 
        ListInterface<Item> foodItem = new SinglyLinkedList();
        
        int countfood=1,itemPosition=0;
       
        //Display food items in the list        
        System.out.println("\nRestaurant Menu Item");
        System.out.println("====================");
        System.out.println("\nFOOD");
        System.out.println("----");        
        

        menuIterator = menuItem.getIterator();


        while (menuIterator.hasNext()) {
            Item current = menuIterator.next();
            itemPosition++;
            if(current.getCategory() == "Food"){
                 System.out.println(countfood + ".\n" + current.toString());
                 countfood++;      
                 foodItem.add(current);
                 
                 
            }

        }              
        
        System.out.println(countfood-1 + " FOOD item(s) currently in the Restaurant Menu.");

        
        return foodItem;
    }
    
    public static ListInterface<Item> displayBeverage(ListInterface<Item> menuItem){
      
        Iterator<Item> menuIterator; 
        ListInterface<Item> beverageItem = new SinglyLinkedList();
        
        int countbeverage=1,itemPosition=0;
        
        //Display beverage items in the list
        System.out.println("\nRestaurant Menu Item");
        System.out.println("====================");
        System.out.println("\nBEVERAGE");
        System.out.println("--------");

        menuIterator = menuItem.getIterator();

        while (menuIterator.hasNext()) {
            Item current = menuIterator.next();
            itemPosition++;
            if(current.getCategory() == "Beverage"){
                 System.out.println(countbeverage + ".\n" + current.toString());
                 countbeverage++;      
                 beverageItem.add(current);


            }

        }              
        
        System.out.println(countbeverage-1 + " BEVERAGE item(s) currently in the Restaurant Menu.");

        
        return beverageItem;        
        
    }
    
    public static ListInterface<Item> addMenu(ListInterface<Item> menuItem){
                
        Scanner scanner = new Scanner(System.in);
        
        //variables
        int menuOption;
        char createMenu='N', addItem = 'N';
        
        Iterator<Item> menuIterator;
 
        System.out.println("\nAdd Option");
        System.out.println("==========");        
        System.out.println("1. Add New Restaurant Menu");
        System.out.println("2. Add Item to Existing Menu");       
        System.out.println("3. Exit");        
        System.out.print("Please enter your choice: ");
        menuOption = scanner.nextInt();
        
        while(menuOption<1 || menuOption>3){
            System.out.println("\nInvalid choice entered. Please re-enter. \n");
            System.out.print("Please enter your choice: ");
            menuOption = scanner.nextInt();
        }              
        
        
        if(menuOption == 1){
            
            if(menuItem.getNumberOfEntries()!=0){
                System.out.print("\nYou already have a Restaurant Menu. ");
                System.out.print("Do you want to create a new Menu (Y/N)? ");
                createMenu = scanner.next().charAt(0);

                // validate user input
                while (Character.toUpperCase(createMenu) != 'Y' && Character.toUpperCase(createMenu) != 'N') {
                    System.out.println("\nInvalid character entered. Please re-enter.");
                    System.out.print("Do you want to create a new Menu (Y/N)? ");
                    createMenu = scanner.next().charAt(0);
                }
                
                if(Character.toUpperCase(createMenu) == 'N'){
                    System.out.println("\nPleaso go to Edit Item to edit your Menu.");
                    menuInterface();
                }else{
                    menuItem.clear();
                    System.out.println("\nYour Restaurant Menu have been deleted.");
                    menuItem = addNewMenu();

                }
                
            }else{
                menuItem = addNewMenu();

            }
            
 
        }else if(menuOption ==2){
             
            if(menuItem.getNumberOfEntries() == 0){
                System.out.println("\nYou do not have a Restaurant Menu yet !");

                System.out.print("\nDo you want to create a new Menu (Y/N)? ");
                createMenu = scanner.next().charAt(0);

                // validate user input
                while (Character.toUpperCase(createMenu) != 'Y' && Character.toUpperCase(createMenu) != 'N') {
                    System.out.println("\nInvalid character entered. Please re-enter. ");
                    System.out.print("\nDo you want to create a new Menu (Y/N)? ");
                    createMenu = scanner.next().charAt(0);
                }
                
                if (Character.toUpperCase(createMenu) == 'Y'){
                    menuItem = addNewMenu();
                }
                
            }else{
                displayAll(menuItem);

                System.out.print("\nDo you want to add item (Y/N)? ");
                addItem = scanner.next().charAt(0);

                // validate user input
                while (Character.toUpperCase(addItem) != 'Y' && Character.toUpperCase(addItem) != 'N') {
                    System.out.println("\nInvalid character entered. Please re-enter. ");
                    System.out.print("\nDo you want to add item (Y/N)? ");
                    addItem = scanner.next().charAt(0);
                }

                if (Character.toUpperCase(addItem) == 'Y') {
                    menuItem = addItem(menuItem);

                } else {
                    addMenu(menuItem);
                }
            }
 
            
        }else{
            menuInterface();
        }
        
        return menuItem;
    }
 
    public static ListInterface<String> checkDuplicateName(ListInterface<Item> itemChecked){
        
        Iterator<Item> checkExistIterator;        
        ListInterface<String> nameList = new SinglyLinkedList<>();


        checkExistIterator = itemChecked.getIterator();
        Item checkDuplicate;

        //store all the food items added previously to a list for checking of duplicated record entered
        while(checkExistIterator.hasNext()){

             checkDuplicate = checkExistIterator.next();  
             nameList.add(checkDuplicate.getName());                               
        }
        return nameList;
    }
    
    public static int displayItemOnly(ListInterface<Item> item){
        
        Iterator<Item> iterator = item.getIterator();
        int count=1;

        while (iterator.hasNext()) {
            System.out.println(count + ".\n" + iterator.next().toString());
            count++;

        }      
        return count;
    }
    
    public static ListInterface<Item> addItem(ListInterface<Item> existingMenu){
        
        Scanner scanner = new Scanner(System.in);
        
        //variables
        int itemOption, addPosition, choicePosition, addFirst,editOption;
        char addSpecific, addMore, addOther = 'N', confirmAdd, confirmExit= 'N', editMenu;
        boolean added=false, unique=true, priceValid=true;

        String itemId, totalItemCount="";
        Restaurant restaurant = new Restaurant(LocalTime.of(8, 0), LocalTime.of(21, 0));
        String name, description;
        double price;

        //collection of objects
        ListInterface<Item> foodItem= new SinglyLinkedList<>();
        ListInterface<Item> beverageItem = new SinglyLinkedList<>();    
        
        ListInterface<String> nameList = new SinglyLinkedList<>();
        
        Item item;

        Iterator<Item> menuIterator;
        Iterator<Item> checkExistIterator;        
            
        do{

           System.out.println("\nItem Selection");
           System.out.println("==============");        
           System.out.println("1. Add Food");
           System.out.println("2. Add Beverage");        
           System.out.println("3. Exit");        
           System.out.print("Please enter your choice: ");
           itemOption = scanner.nextInt();

           while(itemOption<1 || itemOption>3){
               System.out.println("\nInvalid choice entered. Please re-enter. \n");
               System.out.print("Please enter your choice: ");
               itemOption = scanner.nextInt();
           }  

           if(itemOption == 1){
               
               do{
                scanner.nextLine();                         
                //call displayMethod to display only food items and move food item from existing menu to foodItem
                foodItem = displayFood(existingMenu); 
                nameList.clear();
                    do{
                        
                        itemId = "I" + String.format("%04d", existingMenu.getNumberOfEntries()+1);

                        System.out.print("\nPlease enter the NAME of the food         : ");
                        name = scanner.nextLine();
                        name = name.toUpperCase();
                        nameList = checkDuplicateName(foodItem);

                     //perform checking whether the food name entered by the user already entered by the user
                     if(nameList.contains(name)){
                          System.out.println("\nAn item with the same name have already appear in the Restaurant Menu.");
                          System.out.println("\nPlease enter a UNIQUE name or go to Edit Item function to edit the item.");

                         unique = false;
                     }else{
                         unique = true;
                     }        

                    }while(unique==false);


                   System.out.print("Please enter the DESCRIPTION for the food : ");
                   description = scanner.nextLine();                           

                    do{

                        System.out.print("Please enter the PRICE of the food        : ");
                        price = scanner.nextDouble();     

                        if(price<=0){
                             System.out.println("\nInvalid amount entered. Please re-enter. \n");
                            priceValid = false;
                        }else{
                            priceValid=true;
                        }    

                    }while(priceValid==false);          
                    
                    item = new Item(itemId, name, description, "Food", price, restaurant);
                   
                    do{
                        
                        //edit
                        System.out.print("\nDo you want to edit the FOOD item above (Y/N)? ");
                        editMenu = scanner.next().charAt(0);

                        // validate user input
                        while (Character.toUpperCase(editMenu) != 'Y' && Character.toUpperCase(editMenu) != 'N') {
                            System.out.println("\nInvalid character entered. Please re-enter. ");
                            System.out.print("\nDo you want to edit the FOOD item above (Y/N)? ");
                            editMenu = scanner.next().charAt(0);
                        }
                        
                        if (Character.toUpperCase(editMenu) == 'Y') {
                            
                            System.out.println("\nEdit Selection");
                            System.out.println("==============");
                            System.out.println("1. Name");
                            System.out.println("2. Description");
                            System.out.println("3. Price");
                            System.out.print("Please enter your choice (0 to Exit): ");
                            editOption = scanner.nextInt();

                            while (editOption < 0 || editOption > 3) {
                                System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                System.out.print("Please enter your choice (0 to Exit): ");
                                editOption = scanner.nextInt();
                            }

                            scanner.nextLine();
                            
                            if(editOption==1){
                                
                            do{
                                System.out.print("\nPlease enter the NEW NAME of the FOOD : ");
                                name = scanner.nextLine();
                                name = name.toUpperCase();

                                nameList = checkDuplicateName(foodItem);

                                //perform checking whether the food name entered by the user already entered by the user
                                if (nameList.contains(name)) {
                                    System.out.println("\nAn item with the same name have already appear in the Restaurant Menu.");
                                    System.out.println("\nPlease enter a UNIQUE name or go to Edit Item function to edit the item.");

                                    unique = false;
                                } else {
                                    unique = true;
                                    item.setName(name);
                                }

                            }while (unique == false);
                                System.out.printf("\nTHE NAME OF THE FOOD with the Item ID %s has been EDITED. \n\n",itemId );

                            }else if(editOption==2){
                                System.out.print("Please enter the NEW DESCRIPTION for the food : ");
                                description = scanner.nextLine();
                                item.setDescription(description);
                                
                                System.out.printf("\nTHE DESCRIPTION OF THE FOOD with the Item ID %s has been EDITED. \n\n", itemId);


                        }else if(editOption==3){ 
                                do {

                                    System.out.print("Please enter the NEW PRICE of the food        : ");
                                    price = scanner.nextDouble();

                                    if (price <= 0) {
                                        System.out.println("\nInvalid amount entered. Please re-enter. \n");
                                        priceValid = false;
                                    } else {
                                        priceValid = true;
                                    }

                                } while (priceValid == false);
                                
                                item.setPrice(price);
                        }
                            
                            System.out.printf("\nTHE PRICE OF THE FOOD with the Item ID %s has been EDITED. \n\n", itemId);

                             
                    }else {

                            if (foodItem.getNumberOfEntries() != 0) {
                                System.out.print("\nDo you want to add at a specific position (Y/N)? ");
                                addSpecific = scanner.next().charAt(0);

                                // validate user input
                                while (Character.toUpperCase(addSpecific) != 'Y' && Character.toUpperCase(addSpecific) != 'N') {
                                    System.out.println("\nInvalid character entered. Please re-enter. ");
                                    System.out.print("\nDo you want to add at a specific position (Y/N)? ");
                                    addSpecific = scanner.next().charAt(0);
                                }

                                if (Character.toUpperCase(addSpecific) == 'Y') {

                                    System.out.println("\nRestaurant Menu Item");
                                    System.out.println("======================");
                                    System.out.println("\nFOOD");
                                    System.out.println("----");

                                    displayItemOnly(foodItem);

                                    if (foodItem.getNumberOfEntries() >= 2) {
                                        System.out.println("\nPosition");
                                        System.out.println("========");
                                        System.out.println("1. Front");
                                        System.out.println("2. Back");
                                        System.out.println("3. Middle");

                                        System.out.print("Please enter your choice: ");
                                        choicePosition = scanner.nextInt();

                                        while (choicePosition < 1 || choicePosition > 3) {
                                            System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                            System.out.print("Please enter your choice: ");
                                            choicePosition = scanner.nextInt();
                                        }

                                    } else {
                                        System.out.println("\nPosition");
                                        System.out.println("========");
                                        System.out.println("1. Front");
                                        System.out.println("2. Back");

                                        System.out.print("Please enter your choice: ");
                                        choicePosition = scanner.nextInt();

                                        while (choicePosition < 1 || choicePosition > 2) {
                                            System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                            System.out.print("Please enter your choice: ");
                                            choicePosition = scanner.nextInt();
                                        }

                                    }

                                    if (choicePosition == 1) {

                                        added = foodItem.add(1, item);

                                    } else if (choicePosition == 2) {

                                        added = foodItem.add(foodItem.getLastEntryPosition(), item);

                                    } else {

                                        if (foodItem.getNumberOfEntries() == 2) {
                                            added = foodItem.add(2, item);

                                        } else {

                                            System.out.print("\nPlease enter the position you would like to add to: ");
                                            addPosition = scanner.nextInt();

                                            //                                     while(addPosition<1 || addPosition>3){
                                            //                                         System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                            //                                         System.out.print("Please enter the position you would like to add to: ");
                                            //                                         addPosition = scanner.nextInt();
                                            //                                     }           
                                            added = foodItem.add(addPosition, item);

                                            while (!added) {
                                                System.out.print("\nInvalid postion entered. Please re-enter. \n");
                                                System.out.print("\n\nPlease enter the position you would like to add to: ");
                                                addPosition = scanner.nextInt();

                                                added = foodItem.add(addPosition, item);

                                            }

                                        }

                                    }

                                } else {

                                    added = foodItem.add(item);

                                }

                            } else {
                                added = foodItem.add(item);
                            }

                            if (added) {
                                int foodInMenucount = 1;

                                while (existingMenu.getEntry(foodInMenucount).getCategory() != "Food") {
                                    foodInMenucount++;

                                }

                                checkExistIterator = foodItem.getIterator();
                                Item checkDuplicate;

                                while (checkExistIterator.hasNext()) {

                                    checkDuplicate = checkExistIterator.next();

                                    if (existingMenu.contains(checkDuplicate)) {
                                        foodInMenucount++;
                                    } else {
                                        existingMenu.add(foodInMenucount, checkDuplicate);
                                    }
                                }

                                displayAll(existingMenu);

                                System.out.println("\nThe FOOD Item has been added successfully to the Restaurant Menu.\n");

                            } else {
                                System.out.println("\nThe FOOD Item was not added succsefully to the Restaurant Menu.\n");

                            }

                        }
                    
                    }while(Character.toUpperCase(editMenu) == 'Y');
                    
                               
                    System.out.print("Do you want to add more Food item (Y/N)? ");
                    addMore=scanner.next().charAt(0);      

                    // validate user input
                    while (Character.toUpperCase(addMore) != 'Y' && Character.toUpperCase(addMore) != 'N') {
                        System.out.println("\nInvalid character entered. Please re-enter. ");
                        System.out.print("\nDo you want to add more Food item (Y/N)? ");
                        addMore=scanner.next().charAt(0);
                    }   

              }while(Character.toUpperCase(addMore) == 'Y');    


             System.out.print("\nDo you want to add other item selection to the Menu (Y/N)? ");
             addOther=scanner.next().charAt(0);      

             // validate user input
             while (Character.toUpperCase(addOther) != 'Y' && Character.toUpperCase(addOther) != 'N') {
                System.out.println("\nInvalid character entered. Please re-enter. ");
                System.out.print("\nDo you want to add more item (Y/N)? ");
                addOther=scanner.next().charAt(0);
             }                             

           }else if(itemOption == 2){


              do{

                scanner.nextLine();

                //call displayMethod to display only beverage items and move beverage item from existing menu to foodItem
                beverageItem = displayBeverage(existingMenu); 
                nameList.clear();

                  do{
                    
                    itemId = "I" + String.format("%04d", existingMenu.getNumberOfEntries() + 1);

                    System.out.print("\nPlease enter the NAME of the beverage         : ");
                    name = scanner.nextLine();                          
                    name = name.toUpperCase();

                    
                    nameList = checkDuplicateName(beverageItem);

                     if(nameList.contains(name)){
                          System.out.println("\nAn item with the same name have already appear in the Restaurant Menu.");
                          System.out.println("\nPlease enter a UNIQUE name or go to Edit Item function to edit the item.");

                         unique = false;
                     }else{
                         unique = true;
                     }

                  }while(unique==false);


                   System.out.print("Please enter the DESCRIPTION for the beverage : ");
                   description = scanner.nextLine();                           

                    do{

                        System.out.print("Please enter the PRICE of the beverage        : ");
                        price = scanner.nextDouble();     

                        if(price<=0){
                             System.out.println("\nInvalid amount entered. Please re-enter. \n");
                            priceValid = false;
                        }else{
                            priceValid=true;
                        }    

                    }while(priceValid==false);      
                    
                    item = new Item(itemId, name, description, "Beverage", price, restaurant);
              

                    do{
                        
                        //edit
                        System.out.print("\nDo you want to edit the BEVERAGE item above (Y/N)? ");
                        editMenu = scanner.next().charAt(0);

                        // validate user input
                        while (Character.toUpperCase(editMenu) != 'Y' && Character.toUpperCase(editMenu) != 'N') {
                            System.out.println("\nInvalid character entered. Please re-enter. ");
                            System.out.print("\nDo you want to edit the BEVERAGE item above (Y/N)? ");
                            editMenu = scanner.next().charAt(0);
                        }

                        if (Character.toUpperCase(editMenu) == 'Y') {
                            System.out.println("\nEdit Selection");
                            System.out.println("==============");
                            System.out.println("1. Name");
                            System.out.println("2. Description");
                            System.out.println("3. Price");
                            System.out.print("Please enter your choice (0 to Exit): ");
                            editOption = scanner.nextInt();

                            while (editOption < 0 || editOption > 3) {
                                System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                System.out.print("Please enter your choice (0 to Exit): ");
                                editOption = scanner.nextInt();
                            }

                            scanner.nextLine();

                            if (editOption == 1) {
                                
                                do {
                                    System.out.print("\nPlease enter the NEW NAME of the BEVERAGE : ");
                                    name = scanner.nextLine();
                                    name = name.toUpperCase();

                                    nameList = checkDuplicateName(foodItem);

                                    //perform checking whether the food name entered by the user already entered by the user
                                    if (nameList.contains(name)) {
                                        System.out.println("\nAn item with the same name have already appear in the Restaurant Menu.");
                                        System.out.println("\nPlease enter a UNIQUE name or go to Edit Item function to edit the item.");

                                        unique = false;
                                    } else {
                                        unique = true;
                                        item.setName(name);
                                    }

                                } while (unique == false);
                                
                                System.out.printf("\nTHE NAME OF THE BEVERAGE with the Item ID %s has been EDITED. \n\n", itemId);

                            } else if (editOption == 2) {
                                System.out.print("Please enter the NEW DESCRIPTION for the BEVERAGE : ");
                                description = scanner.nextLine();
                                item.setDescription(description);
                                System.out.printf("\nTHE DESCRIPTION OF THE BEVERAGE with the Item ID %s has been EDITED. \n\n", itemId);

                            } else if (editOption == 3) {
                                do {

                                    System.out.print("Please enter the NEW PRICE of the BEVERAGE        : ");
                                    price = scanner.nextDouble();

                                    if (price <= 0) {
                                        System.out.println("\nInvalid amount entered. Please re-enter. \n");
                                        priceValid = false;
                                    } else {
                                        priceValid = true;
                                    }
                                    System.out.printf("\nTHE PRICE OF THE BEVERAGE with the Item ID %s has been EDITED. \n\n", itemId);

                                } while (priceValid == false);

                                item.setPrice(price);
                            }
     
                        } else {

                            if (beverageItem.getNumberOfEntries() != 0) {
                                System.out.print("\nDo you want to add at a specific position (Y/N)? ");
                                addSpecific = scanner.next().charAt(0);

                                // validate user input
                                while (Character.toUpperCase(addSpecific) != 'Y' && Character.toUpperCase(addSpecific) != 'N') {
                                    System.out.println("\nInvalid character entered. Please re-enter. ");
                                    System.out.print("\nDo you want to add at a specific position (Y/N)? ");
                                    addSpecific = scanner.next().charAt(0);
                                }

                                if (Character.toUpperCase(addSpecific) == 'Y') {

                                    //Display beverage items in the list
                                    System.out.println("\nRestaurant Menu Item");
                                    System.out.println("======================");
                                    System.out.println("\nBEVERAGE");
                                    System.out.println("--------");

                                    displayItemOnly(beverageItem);

                                    if (beverageItem.getNumberOfEntries() >= 2) {
                                        System.out.println("\nPosition");
                                        System.out.println("========");
                                        System.out.println("1. Front");
                                        System.out.println("2. Back");
                                        System.out.println("3. Middle");

                                        System.out.print("Please enter your choice: ");
                                        choicePosition = scanner.nextInt();

                                        while (choicePosition < 1 || choicePosition > 3) {
                                            System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                            System.out.print("Please enter your choice: ");
                                            choicePosition = scanner.nextInt();
                                        }

                                    } else {
                                        System.out.println("\nPosition");
                                        System.out.println("========");
                                        System.out.println("1. Front");
                                        System.out.println("2. Back");

                                        System.out.print("Please enter your choice: ");
                                        choicePosition = scanner.nextInt();

                                        while (choicePosition < 1 || choicePosition > 2) {
                                            System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                            System.out.print("Please enter your choice: ");
                                            choicePosition = scanner.nextInt();
                                        }

                                    }

                                    if (choicePosition == 1) {

                                        added = beverageItem.add(1, item);

                                    } else if (choicePosition == 2) {
                                        added = beverageItem.add(beverageItem.getLastEntryPosition(), item);

                                    } else {

                                        if (beverageItem.getNumberOfEntries() == 2) {
                                            added = beverageItem.add(2, item);

                                        } else {

                                            System.out.print("\nPlease enter the position you would like to add to: ");
                                            addPosition = scanner.nextInt();

                                            added = beverageItem.add(addPosition, item);

                                            while (!added) {
                                                System.out.print("\nInvalid postion entered. Please re-enter. \n");
                                                System.out.print("\nPlease enter the position you would like to add to: ");
                                                addPosition = scanner.nextInt();

                                                added = beverageItem.add(addPosition, item);

                                            }

                                        }

                                    }

                                } else {

                                    added = beverageItem.add(item);

                                }

                            } else {
                                added = beverageItem.add(item);
                            }

                            if (added) {
                                int beverageInMenucount = 1;

                                while (existingMenu.getEntry(beverageInMenucount).getCategory() != "Beverage") {
                                    beverageInMenucount++;

                                }

                                checkExistIterator = beverageItem.getIterator();
                                Item checkDuplicate;

                                while (checkExistIterator.hasNext()) {

                                    checkDuplicate = checkExistIterator.next();

                                    if (existingMenu.contains(checkDuplicate)) {
                                        beverageInMenucount++;
                                    } else {
                                        existingMenu.add(beverageInMenucount, checkDuplicate);
                                    }
                                }

                                displayAll(existingMenu);

                                System.out.println("\nThe BEVERAGE Item has been added successfully to the Restaurant Menu.\n");

                            } else {
                                System.out.println("\nThe BEVERAGE Item was not added succsefully to the Restaurant Menu.\n");

                            }

                        }
                        
                    }while(Character.toUpperCase(editMenu) == 'Y');
                    

                    System.out.print("Do you want to add more BEVERAGE item (Y/N)? ");
                    addMore=scanner.next().charAt(0);      

                    // validate user input
                    while (Character.toUpperCase(addMore) != 'Y' && Character.toUpperCase(addMore) != 'N') {
                        System.out.println("\nInvalid character entered. Please re-enter. ");
                        System.out.print("\nDo you want to add more BEVERAGE item (Y/N)? ");
                        addMore=scanner.next().charAt(0);
                    }   

              }while(Character.toUpperCase(addMore) == 'Y');    


             System.out.print("\nDo you want to add other item selection to the Menu (Y/N)? ");
             addOther=scanner.next().charAt(0);      

             // validate user input
             while (Character.toUpperCase(addOther) != 'Y' && Character.toUpperCase(addOther) != 'N') {
                System.out.println("\nInvalid character entered. Please re-enter. ");
                System.out.print("\nDo you want to add more item (Y/N)? ");
                addOther=scanner.next().charAt(0);
             }                                                          

           }else{
               addMenu(existingMenu);
           }                       

        
        }while(itemOption != 3 && Character.toUpperCase(addOther) != 'N');           
        
        displayAll(existingMenu);

        return existingMenu;
        
    }
    
    public static ListInterface<Item> addNewMenu(){

    Scanner scanner = new Scanner(System.in);

    //variables
    int itemOption, addPosition, choicePosition, addFirst, countFood, countBeverage, countItem=0;
    char addSpecific, addMore, addOther = 'N', confirmAdd, confirmExit= 'N', editMenu;
    boolean added=false, unique=true, priceValid=true;

    String itemId, totalItemCount="";
    Restaurant restaurant = new Restaurant(LocalTime.of(8, 0), LocalTime.of(9, 0));
    String name, description;
    double price;

    //collection of objects
    ListInterface<Item> menuItem = new SinglyLinkedList<>();
    ListInterface<Item> foodItem = new SinglyLinkedList<>();
    ListInterface<Item> beverageItem = new SinglyLinkedList<>();    
    ListInterface<String> nameList = new SinglyLinkedList<>();
    
    Iterator<Item> menuIterator;
    Iterator<Item> checkExistIterator;


        do{

           System.out.println("\nItem Selection");
           System.out.println("==============");        
           System.out.println("1. Add Food");
           System.out.println("2. Add Beverage");        
           System.out.println("3. Exit");        
           System.out.print("Please enter your choice: ");
           itemOption = scanner.nextInt();

           while(itemOption<1 || itemOption>3){
               System.out.println("\nInvalid choice entered. Please re-enter. \n");
               System.out.print("Please enter your choice: ");
               itemOption = scanner.nextInt();
           }  

           if(itemOption == 1){

              do{

                scanner.nextLine();
                nameList.clear();

                    do{
                        countItem++;
                        itemId = "I" + String.format("%04d", countItem);

                        System.out.print("\nPlease enter the NAME of the food         : ");
                        name = scanner.nextLine();
                        name = name.toUpperCase();

                    nameList = checkDuplicateName(foodItem);

                     //perform checking whether the food name entered by the user already entered by the user
                     if(nameList.contains(name)){
                          System.out.println("\nAn item with the same name have already appear in the Restaurant Menu.");
                          System.out.println("\nPlease enter a UNIQUE name or go to Edit Item function to edit the item.");

                         unique = false;
                     }else{
                         unique = true;
                     }        

                    }while(unique==false);


                   System.out.print("Please enter the DESCRIPTION for the food : ");
                   description = scanner.nextLine();                           

                    do{

                        System.out.print("Please enter the PRICE of the food        : ");
                        price = scanner.nextDouble();     

                        if(price<=0){
                             System.out.println("\nInvalid amount entered. Please re-enter. \n");
                            priceValid = false;
                        }else{
                            priceValid=true;
                        }    

                    }while(priceValid==false);                    

                   if(foodItem.getNumberOfEntries()!=0){
                        System.out.print("\nDo you want to add at a specific position (Y/N)? ");
                        addSpecific=scanner.next().charAt(0);      

                        // validate user input
                        while (Character.toUpperCase(addSpecific) != 'Y' && Character.toUpperCase(addSpecific) != 'N') {
                            System.out.println("\nInvalid character entered. Please re-enter. ");
                            System.out.print("\nDo you want to add at a specific position (Y/N)? ");
                            addSpecific=scanner.next().charAt(0);  
                        }                            

                        if(Character.toUpperCase(addSpecific) == 'Y'){

                            System.out.println("\nFood Items Entered Previously");
                            System.out.println("=============================");

                            displayItemOnly(foodItem);                                        

                           if(foodItem.getNumberOfEntries()>=2){
                                System.out.println("\nPosition");                                        
                                System.out.println("========");           
                                System.out.println("1. Front");                                        
                                System.out.println("2. Back");                                        
                                System.out.println("3. Middle");   

                                System.out.print("Please enter your choice: ");
                                choicePosition = scanner.nextInt();        

                                 while(choicePosition<1 || choicePosition>3){
                                     System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                     System.out.print("Please enter your choice: ");
                                     choicePosition = scanner.nextInt();
                                 }                                               

                           }else{
                                System.out.println("\nPosition");                                        
                                System.out.println("========");           
                                System.out.println("1. Front");                                        
                                System.out.println("2. Back");    

                                System.out.print("Please enter your choice: ");
                                choicePosition = scanner.nextInt();        

                                 while(choicePosition<1 || choicePosition>2){
                                     System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                     System.out.print("Please enter your choice: ");
                                     choicePosition = scanner.nextInt();
                                 }                                               

                           }

                           if(choicePosition == 1){

                                added = foodItem.add(1,new Item(itemId,name,description,"Food",price,restaurant));


                           }else if(choicePosition == 2){

                               added = foodItem.add(foodItem.getLastEntryPosition(),new Item(itemId,name,description,"Food",price,restaurant));

                           }else{

                               if(foodItem.getNumberOfEntries()==2){
                                    added = foodItem.add(2,new Item(itemId,name,description,"Food",price,restaurant));

                               }else{

                                System.out.print("\nPlease enter the position you would like to add to: ");
                                addPosition = scanner.nextInt();        

//                                     while(addPosition<1 || addPosition>3){
//                                         System.out.println("\nInvalid choice entered. Please re-enter. \n");
//                                         System.out.print("Please enter the position you would like to add to: ");
//                                         addPosition = scanner.nextInt();
//                                     }           

                                added = foodItem.add(addPosition,new Item(itemId,name,description,"Food",price,restaurant));

                                 while(!added){
                                      System.out.print("\nInvalid postion entered. Please re-enter. \n");
                                      System.out.print("\n\nPlease enter the position you would like to add to: ");
                                      addPosition = scanner.nextInt();       

                                      added = foodItem.add(addPosition,new Item(itemId,name,description,"Food",price,restaurant));

                                 }                                             


                               }                                   

                           }


                        }else{
                            added = foodItem.add(new Item(itemId,name,description,"Food",price,restaurant));

                        }

                   }else{

                       added = foodItem.add(new Item(itemId,name,description,"Food",price,restaurant));                                
                   }

                   if(added){
                       System.out.println("\nItem has been saved successfully.\n");
                   }else{
                       System.out.println("\nItem was not saved succsefully.\n");

                   }                            


                    System.out.print("Do you want to add more item (Y/N)? ");
                    addMore=scanner.next().charAt(0);      

                    // validate user input
                    while (Character.toUpperCase(addMore) != 'Y' && Character.toUpperCase(addMore) != 'N') {
                        System.out.println("\nInvalid character entered. Please re-enter. ");
                        System.out.print("\nDo you want to add more item (Y/N)? ");
                        addMore=scanner.next().charAt(0);
                    }   

              }while(Character.toUpperCase(addMore) == 'Y');    


               //Display items in the list
               System.out.println("\nFood Items Entered");
               System.out.println("==================");

               displayItemOnly(foodItem);               

              System.out.println("Total: " + foodItem.getNumberOfEntries() + " food item(s) to be added.");


             System.out.print("\nDo you want to add other item selection to the Menu (Y/N)? ");
             addOther=scanner.next().charAt(0);      

             // validate user input
             while (Character.toUpperCase(addOther) != 'Y' && Character.toUpperCase(addOther) != 'N') {
                System.out.println("\nInvalid character entered. Please re-enter. ");
                System.out.print("\nDo you want to add more item (Y/N)? ");
                addOther=scanner.next().charAt(0);
             }                             



           }else if(itemOption == 2){


              do{

                scanner.nextLine();
                nameList.clear();

                  do{

                    countItem++;
                    itemId = "I" + String.format("%04d", countItem);
                      
                    System.out.print("\nPlease enter the NAME of the beverage         : ");
                    name = scanner.nextLine();                          
                    name = name.toUpperCase();

                    nameList = checkDuplicateName(beverageItem);

                     if(nameList.contains(name)){
                          System.out.println("\nAn item with the same name have already appear in the Restaurant Menu.");
                          System.out.println("\nPlease enter a UNIQUE name or go to Edit Item function to edit the item.");

                         unique = false;
                     }else{
                         unique = true;
                     }

                  }while(unique==false);


                   System.out.print("Please enter the DESCRIPTION for the beverage : ");
                   description = scanner.nextLine();                           

                    do{

                        System.out.print("Please enter the PRICE of the beverage        : ");
                        price = scanner.nextDouble();     

                        if(price<=0){
                             System.out.println("\nInvalid amount entered. Please re-enter. \n");
                            priceValid = false;
                        }else{
                            priceValid=true;
                        }    

                    }while(priceValid==false);                         

                   if(beverageItem.getNumberOfEntries()!=0){
                        System.out.print("\nDo you want to add at a specific position (Y/N)? ");
                        addSpecific=scanner.next().charAt(0);      

                        // validate user input
                        while (Character.toUpperCase(addSpecific) != 'Y' && Character.toUpperCase(addSpecific) != 'N') {
                            System.out.println("\nInvalid character entered. Please re-enter. ");
                            System.out.print("\nDo you want to add at a specific position (Y/N)? ");
                            addSpecific=scanner.next().charAt(0);  
                        }                            

                        if(Character.toUpperCase(addSpecific) == 'Y'){

                            System.out.println("\nBeverage Items Entered Previously");
                            System.out.println("=================================");

                            displayItemOnly(beverageItem);                                         

                           if(beverageItem.getNumberOfEntries()>=2){
                                System.out.println("\nPosition");                                        
                                System.out.println("========");           
                                System.out.println("1. Front");                                        
                                System.out.println("2. Back");                                        
                                System.out.println("3. Middle");   

                                System.out.print("Please enter your choice: ");
                                choicePosition = scanner.nextInt();        

                                 while(choicePosition<1 || choicePosition>3){
                                     System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                     System.out.print("Please enter your choice: ");
                                     choicePosition = scanner.nextInt();
                                 }                                               

                           }else{
                                System.out.println("\nPosition");                                        
                                System.out.println("========");           
                                System.out.println("1. Front");                                        
                                System.out.println("2. Back");    

                                System.out.print("Please enter your choice: ");
                                choicePosition = scanner.nextInt();        

                                 while(choicePosition<1 || choicePosition>2){
                                     System.out.println("\nInvalid choice entered. Please re-enter. \n");
                                     System.out.print("Please enter your choice: ");
                                     choicePosition = scanner.nextInt();
                                 }                                               

                           }


                           if(choicePosition == 1){

                                added = beverageItem.add(1,new Item(itemId,name,description,"Beverage",price,restaurant));


                           }else if(choicePosition == 2){
                               added = beverageItem.add(beverageItem.getLastEntryPosition(),new Item(itemId,name,description,"Beverage",price,restaurant));                                          

                           }else{

                               if(beverageItem.getNumberOfEntries()==2){
                                    added = beverageItem.add(2,new Item(itemId,name,description,"Beverage",price,restaurant));

                               }else{

                                System.out.print("\nPlease enter the position you would like to add to: ");
                                addPosition = scanner.nextInt();        

//                                     while(addPosition<1 || addPosition>beverageItem.getNumberOfEntries()+1){
//                                         System.out.println("\nInvalid choice entered. Please re-enter. \n");
//                                         System.out.print("Please enter the position you would like to add to: ");
//                                         addPosition = scanner.nextInt();
//                                     }           

                                added = beverageItem.add(addPosition,new Item(itemId,name,description,"Beverage",price,restaurant));

                                 while(!added){
                                      System.out.print("\nInvalid postion entered. Please re-enter. \n");
                                      System.out.print("\nPlease enter the position you would like to add to: ");
                                      addPosition = scanner.nextInt();       

                                      added = beverageItem.add(addPosition,new Item(itemId,name,description,"Beverage",price,restaurant));

                                 }  

                               }

                           }


                        }else{

                            added = beverageItem.add(new Item(itemId,name,description,"Beverage",price,restaurant));


                        }

                   }else{

                            added = beverageItem.add(new Item(itemId,name,description,"Beverage",price,restaurant));                                
                   }

                   if(added){
                       System.out.println("\nItem has been saved successfully.\n");
                   }else{
                       System.out.println("\nItem was not saved succsefully.\n");

                   }                            


                    System.out.print("Do you want to add more item (Y/N)? ");
                    addMore=scanner.next().charAt(0);      

                    // validate user input
                    while (Character.toUpperCase(addMore) != 'Y' && Character.toUpperCase(addMore) != 'N') {
                        System.out.println("\nInvalid character entered. Please re-enter. ");
                        System.out.print("\nDo you want to add more item (Y/N)? ");
                        addMore=scanner.next().charAt(0);
                    }   

              }while(Character.toUpperCase(addMore) == 'Y');    


               //Display items in the list
               System.out.println("\nBeverage Items Entered");
               System.out.println("======================");

               displayItemOnly(beverageItem);               

              System.out.println("Total: " + beverageItem.getNumberOfEntries() + " beverage item(s) to be added.");


             System.out.print("\nDo you want to add other item selection to the Menu (Y/N)? ");
             addOther=scanner.next().charAt(0);      

             // validate user input
             while (Character.toUpperCase(addOther) != 'Y' && Character.toUpperCase(addOther) != 'N') {
                System.out.println("\nInvalid character entered. Please re-enter. ");
                System.out.print("\nDo you want to add more item (Y/N)? ");
                addOther=scanner.next().charAt(0);
             }                                                          

           }else{
               if(!foodItem.isEmpty() || !beverageItem.isEmpty()){
                    System.out.print("\nAll items entered have not been added to the Restaurant Menu.\n");
                    System.out.print("\nALL CHANGES MADE WILL NOT BE STORED !!\n");
                    System.out.print("\nAre you sure to exit? ");
                    confirmExit=scanner.next().charAt(0);

                   // validate user input
                   while (Character.toUpperCase(confirmExit) != 'Y' && Character.toUpperCase(confirmExit) != 'N') {
                       System.out.println("\nInvalid character entered. Please re-enter. ");
                       System.out.print("\nAre you sure to exit? ");
                       confirmExit=scanner.next().charAt(0);
                   }  

                   if(Character.toUpperCase(confirmExit) == 'Y'){
                       foodItem.clear();
                       beverageItem.clear();
                       addMenu(null);
                   }else{
                       itemOption = 0;

                        System.out.print("\nDo you want to add other item selection to the Menu (Y/N)? ");
                        addOther=scanner.next().charAt(0);      

                        // validate user input
                        while (Character.toUpperCase(addOther) != 'Y' && Character.toUpperCase(addOther) != 'N') {
                           System.out.println("\nInvalid character entered. Please re-enter. ");
                           System.out.print("\nDo you want to add more item (Y/N)? ");
                           addOther=scanner.next().charAt(0);
                        }     

                   }

               }else if(menuItem.getNumberOfEntries()==0){
                    System.out.println("\n0 item(s) was added to the Restaurant Menu.\n");
                    addMenu(null);

               }      

           }                       




        }while(itemOption != 3 && Character.toUpperCase(addOther) != 'N');                    

        //no more items to be added and proceed to adding the items entered to the menu
        if(!foodItem.isEmpty() && !beverageItem.isEmpty()){
            
            System.out.println("\nPlease select the sequence of category in the Restaurant Menu.");
            System.out.println("\nCategory to be added first");
            System.out.println("==========================");
            System.out.println("1. Food");
            System.out.println("2. Beverage");
            System.out.print("Please enter your choice: ");
            addFirst = scanner.nextInt();

            while (addFirst < 1 || addFirst > 2) {
                System.out.println("\nInvalid choice entered. Please re-enter. \n");
                System.out.print("Please enter your choice: ");
                addFirst = scanner.nextInt();
            }

            if (addFirst == 1) {

                while (!foodItem.isEmpty()) {

                    menuItem.add(foodItem.remove(1));
                }

                //Display items in the list
                System.out.println("\nRestaurant Menu Item");
                System.out.println("======================");
                System.out.println("\nFOOD");
                System.out.println("----");

                //call displayItemOnly function to display the items
                countFood = displayItemOnly(menuItem);

                totalItemCount += countFood - 1 + " FOOD item(s) & ";

                while (!beverageItem.isEmpty()) {

                    menuItem.add(beverageItem.remove(1));
                }

                //Display items in the list
                System.out.println("\nBEVERAGE");
                System.out.println("--------");

                menuIterator = menuItem.getIterator();
                int countbeverage = 1;

                while (menuIterator.hasNext()) {
                    Item current = menuIterator.next();
                    if (current.getCategory() == "Beverage") {
                        System.out.println(countbeverage + ".\n" + current.toString());
                        countbeverage++;
                    }

                }
                totalItemCount += countbeverage - 1 + " BEVERAGE item(s) was added to the Restaurant Menu.";

                System.out.println(totalItemCount);
                System.out.println("\nTotal: " + menuItem.getNumberOfEntries() + " item(s) in the Restaurant Menu.");

            } else {

                while (!beverageItem.isEmpty()) {

                    menuItem.add(beverageItem.remove(1));
                }

                //Display items in the list
                System.out.println("\nRestaurant Menu Item");
                System.out.println("======================");
                System.out.println("\nBEVERAGE");
                System.out.println("--------");

                countBeverage = displayItemOnly(menuItem);

                totalItemCount += countBeverage - 1 + " BEVERAGE item(s) & ";

                System.out.println("\nFOOD");
                System.out.println("----");

                menuIterator = menuItem.getIterator();
                int countfood = 1;

                while (!foodItem.isEmpty()) {

                    menuItem.add(foodItem.remove(1));
                }

                while (menuIterator.hasNext()) {
                    Item current = menuIterator.next();
                    if (current.getCategory() == "Food") {
                        System.out.println(countfood + ".\n" + current.toString());
                        countfood++;
                    }

                }
                totalItemCount += countfood - 1 + " FOOD item(s) was added to the Restaurant Menu.";

                System.out.println(totalItemCount);
                System.out.println("\nTotal: " + menuItem.getNumberOfEntries() + " item(s) in the Restaurant Menu.");

            }


            do{                    

            //ask to confirm to add to menu                     
            System.out.print("\nAre you sure to add the food and beverage item(s) above to the Restaurant Menu (Y/N)? ");
            confirmAdd=scanner.next().charAt(0);

            // validate user input
            while (Character.toUpperCase(confirmAdd) != 'Y' && Character.toUpperCase(confirmAdd) != 'N') {
                System.out.println("\nInvalid character entered. Please re-enter. ");
                System.out.print("\nAre you sure to add the food and beverage item(s) above to the Restaurant Menu (Y/N)? ");
                confirmAdd=scanner.next().charAt(0);
            }               

            if(Character.toUpperCase(confirmAdd) == 'Y'){

                confirmExit = 'y';


            }else{

                System.out.print("\nDo you want to edit the item(s) above (Y/N)? ");
                editMenu=scanner.next().charAt(0);      

                // validate user input
                while (Character.toUpperCase(editMenu) != 'Y' && Character.toUpperCase(editMenu) != 'N') {
                    System.out.println("\nInvalid character entered. Please re-enter. ");
                    System.out.print("\nDo you want to edit the item(s) above (Y/N)? ");
                    editMenu=scanner.next().charAt(0);  
                }                         

                if(Character.toUpperCase(editMenu) == 'Y'){
                    //call edit function
                    menuItem = editItem(menuItem);
                }else{

                menuItem.clear();
                System.out.print("\nYour Restaurant Menu have not been created.\n");
                System.out.print("\nALL ITEM(S) ENTERED WILL NOT BE STORED !!\n");
                System.out.print("\nAre you sure to exit without creating your Restaurant Menu? ");
                confirmExit=scanner.next().charAt(0);

               // validate user input
               while (Character.toUpperCase(confirmExit) != 'Y' && Character.toUpperCase(confirmExit) != 'N') {
                   System.out.println("\nInvalid character entered. Please re-enter. ");
                   System.out.print("\nAre you sure to exit without creating your Restaurant Menu? ");
                   confirmExit=scanner.next().charAt(0);
               }  

               if(Character.toUpperCase(confirmExit) == 'Y'){
                   foodItem.clear();
                   beverageItem.clear();
                   addNewMenu();
               }
               //if don't want to exit then will loop again using the while loop

                }

            }

            }while(Character.toUpperCase(confirmExit) != 'Y');

        }else{

                do{
                    
                    if (!foodItem.isEmpty()) {

                        while (!foodItem.isEmpty()) {

                            menuItem.add(foodItem.remove(1));
                        }

                        //Display items in the list
                        System.out.println("\nRestaurant Menu Item");
                        System.out.println("======================");
                        System.out.println("\nFOOD");
                        System.out.println("----");

                        displayItemOnly(menuItem);

                        System.out.println(menuItem.getNumberOfEntries() + " FOOD item(s) was added to the Restaurant Menu.");

                    } else if (!beverageItem.isEmpty()) {

                        while (!beverageItem.isEmpty()) {

                            menuItem.add(beverageItem.remove(1));
                        }

                        //Display items in the list
                        System.out.println("\nRestaurant Menu Item");
                        System.out.println("======================");
                        System.out.println("\nBEVERAGE");
                        System.out.println("--------");

                        displayItemOnly(menuItem);

                        System.out.println(menuItem.getNumberOfEntries() + " BEVERAGE item(s) was added to the Restaurant Menu.");

                    }

                    //ask to confirm to add to menu                     
                     System.out.print("\nAre you sure to add the item(s) above to the Restaurant Menu (Y/N)? ");
                     confirmAdd=scanner.next().charAt(0);

                     // validate user input
                     while (Character.toUpperCase(confirmAdd) != 'Y' && Character.toUpperCase(confirmAdd) != 'N') {
                         System.out.println("\nInvalid character entered. Please re-enter. ");
                         System.out.print("\nAre you sure to add the item(s) above to the Restaurant Menu (Y/N)? ");
                         confirmAdd=scanner.next().charAt(0);
                     }               

                     if(Character.toUpperCase(confirmAdd) == 'Y'){


                         confirmExit = 'y';

                     }else{

                         System.out.print("\nDo you want to edit the item(s) above (Y/N)? ");
                         editMenu=scanner.next().charAt(0);      

                         // validate user input
                         while (Character.toUpperCase(editMenu) != 'Y' && Character.toUpperCase(editMenu) != 'N') {
                             System.out.println("\nInvalid character entered. Please re-enter. ");
                             System.out.print("\nDo you want to edit the item(s) above (Y/N)? ");
                             editMenu=scanner.next().charAt(0);  
                         }                         

                         if(Character.toUpperCase(editMenu) == 'Y'){
                             //call edit function
                             menuItem = editItem(menuItem);
                         }else{

                         menuItem.clear();
                         System.out.print("\nYour Restaurant Menu have not been created.\n");
                         System.out.print("\nALL ITEM(S) ENTERED WILL NOT BE STORED !!\n");
                         System.out.print("\nAre you sure to exit without creating your Restaurant Menu? ");
                         confirmExit=scanner.next().charAt(0);

                        // validate user input
                        while (Character.toUpperCase(confirmExit) != 'Y' && Character.toUpperCase(confirmExit) != 'N') {
                            System.out.println("\nInvalid character entered. Please re-enter. ");
                            System.out.print("\nAre you sure to exit without creating your Restaurant Menu? ");
                            confirmExit=scanner.next().charAt(0);
                        }  

                        if(Character.toUpperCase(confirmExit) == 'Y'){
                            foodItem.clear();
                            beverageItem.clear();
                            addNewMenu();
                        }
                        //if don't want to exit then will loop again using the while loop

                    }
                    }


                }while(Character.toUpperCase(confirmExit) != 'Y');

            }

        
        return menuItem;
    }
    
}




