package Client;

import ADT.Customer.*;
import Entity.*;

import java.util.Scanner;
import java.util.Iterator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class MainMenu {

    public static void main(String[] args) {
        // initial customers
        ArrayList<Address> cust1AddressList = new ArrayList<>();
        cust1AddressList.add(new Address("Blk 10-10-6 Azuria Condominium, Jalan Lembah Permai", "11200", "Tanjung Bungah", "Penang"));
        Customer customer1 = new Customer("Zeey", "Zeey@0000", "012-3456789", cust1AddressList, "zeey@gmail.com");
        //C10000
        ArrayList<Address> cust2AddressList = new ArrayList<>();
        cust2AddressList.add(new Address("No. 316 Jln Batu 22 Kampung Pasir Tuntung Bukit Rotan Bukit Rotan", "10050", "Georgetown", "Penang"));
        cust2AddressList.add(new Address("18 Lbh Pasar", "10200", "Georgetown", "Penang"));
        Customer customer2 = new Customer("Andrew Lim", "andrew123!", "019-2348543", cust2AddressList, "andrew@gmail.com");
        
        ArrayList<Address> cust3AddressList = new ArrayList<>();
        cust3AddressList.add(new Address("Megan Phileo Avenue, No. 12, Jalan Yap Kwan Seng", "50450", "Kuala Lumpur", "Wilayah Persekutuan"));
        Customer customer3 = new Customer("Jeremy", "jeremy234@", "018-276354", cust3AddressList, "jeremy@gmail.com");
        customer3.getPaymentMethodList().add(new CreditCard("1234567890123456", YearMonth.of(2023, 5), "143"));
        
        // initial restaurants
        ArrayList<Address> rest1AddressList = new ArrayList<>();
        rest1AddressList.add(new Address("Plaza Gurney, 170-02-59, 2nd Floor, Persiaran Gurney", "10250", "Georgetown", "10250"));
        Restaurant restaurant1 = new Restaurant("Dragon-i Restaurant", "dragon1123!", "04-2271686", rest1AddressList, LocalTime.of(11, 0), LocalTime.of(22, 0));
        
        ArrayList<Address> rest2AddressList = new ArrayList<>();
        rest2AddressList.add(new Address("15, Changkat Bukit Bintang, Bukit Bintang", "10250", "Georgetown", "10250"));
        Restaurant restaurant2 = new Restaurant("Ciccio", "ciccio123!", "03-21418605", rest2AddressList, LocalTime.of(17, 0), LocalTime.of(0, 0));
        
        ArrayList<Address> rest3AddressList = new ArrayList<>();
        rest3AddressList.add(new Address("Lot 3 . 08 . 00 & 3 . 09 . 00, 168, Bukit Bintang St, Bukit Bintang", "55100", "Kuala Lumpur", "Wilayah Persekutuan"));
        Restaurant restaurant3 = new Restaurant("Starbucks", "coffee123@", "03-28569112", rest3AddressList, LocalTime.of(8, 0), LocalTime.of(0, 0));

         //Initial rider shift time
 
        //Only Penang and KL
        //============== User 1 : Tan Ah Hock shift time(Penang) ================
        //Ah Hock - First shift
        ArrayList<Shift> ahHockShiftTime = new ArrayList<Shift>();
        LocalDateTime[] ahHockShift1 = new LocalDateTime[2];
        ahHockShift1[0] = LocalDateTime.of(2022, 9, 18, 9, 0);//Start time
        ahHockShift1[1] = LocalDateTime.of(2022, 9, 18, 12, 0);//End time
        //Ah Hock - Second shift
        LocalDateTime[] ahHockShift2 = new LocalDateTime[2];
        ahHockShift2[0] = LocalDateTime.of(2022, 9, 18, 12, 0);//Start time
        ahHockShift2[1] = LocalDateTime.of(2022, 9, 18, 15, 0);//End time
        //Ah Hock - Third shift
        LocalDateTime[] ahHockShift3 = new LocalDateTime[2];
        ahHockShift3[0] = LocalDateTime.of(2022, 9, 18, 15, 0);//Start time
        ahHockShift3[1] = LocalDateTime.of(2022, 9, 18, 18, 0);//End time
        //Add Ah Hock shift time to array list
        ahHockShiftTime.add(new Shift(ahHockShift1));
        ahHockShiftTime.add(new Shift(ahHockShift2));
        ahHockShiftTime.add(new Shift(ahHockShift3));
 
        //============== User 2 : Tan Abu shift time (Penang)================
        ArrayList<Shift> abuShiftTime = new ArrayList<Shift>();
        //Ah Abu - First shift
        LocalDateTime[] abuShift1 = new LocalDateTime[2];
        abuShift1[0] = LocalDateTime.of(2022, 9, 18, 18, 0);//Start time
        abuShift1[1] = LocalDateTime.of(2022, 9, 18, 21, 0);//End time
        //Add Ah Abu shift time to array list
        abuShiftTime.add(new Shift(abuShift1));
        
        
        //============== User 3 : Tam shift time (KL)================
        ArrayList<Shift> tamShiftTime = new ArrayList<Shift>();
        LocalDateTime[] tamShift1 = new LocalDateTime[2];
        tamShift1[0] = LocalDateTime.of(2022, 9, 18, 15, 0);//Start time
        tamShift1[1] = LocalDateTime.of(2022, 9, 18, 18, 0);//End time
        tamShiftTime.add(new Shift(tamShift1));
        
        LocalDateTime[] tamShift2 = new LocalDateTime[2];
        tamShift2[0] = LocalDateTime.of(2022, 9, 18, 18, 0);//Start time
        tamShift2[1] = LocalDateTime.of(2022, 9, 18, 21, 0);//End time
        tamShiftTime.add(new Shift(tamShift2));
    
    
        // initial riders
        ArrayList<Address> rider1AddressList = new ArrayList<>();
        rider1AddressList.add(new Address("Taman Bagan", "10250", "Penang Island", "Penang"));
        Rider rider1 = new Rider("abc123", "Tan Ah Hock", "010-3423302", rider1AddressList, "021522-04-7894", "PMD2280", "Tan Yan", "010-4431579", LocalDate.of(1993, 1, 8), LocalDate.now(),ahHockShiftTime);
    
       
        
        ArrayList<Address> rider2AddressList = new ArrayList<>();
        rider2AddressList.add(new Address("Taman Kimsar", "10050", "Penang Island", "Penang"));
        Rider rider2 = new Rider("asdpop", "Tan Abu", "012-78954621", rider2AddressList, "015489-07-7865", "PQR1236", "Wong wong", "010-5581759", LocalDate.of(1996, 7, 7), LocalDate.now(),abuShiftTime);
      
        
        ArrayList<Address> rider3AddressList = new ArrayList<>();
        rider3AddressList.add(new Address("Taman Titi", "13700", "Kuala Lumpur", "Wilayah Persekutuan"));
        Rider rider3 = new Rider("asdpklm", "Tam", "016-47323472", rider3AddressList, "015489-07-7865", "PQR1243", "Mei", "010-9514241", LocalDate.of(1990, 7, 2), LocalDate.now(),tamShiftTime);
         
        
        ArrayList<Address> rider4AddressList = new ArrayList<>();
        rider4AddressList.add(new Address("Taman Prai", "13700", "Kuala Lumpur", "Wilayah Persekutuan"));
        Rider emergencyRider = new Rider("R000E","xxxxx", "Emergency Rider", "010-898213", rider4AddressList, "021522-04-7894", "PRA 1571", "Yeohnny", "010-4431579", LocalDate.of(1988, 2, 8), LocalDate.now(), null);
      
        // initial items
        Item item1 = new Item("I0001", "Crispy Pan-Fried La Mian with Seafood", "Served with entirely fresh ingredients such as carrot, seafood, mushrooms and made to order crispy Yee Mee", "Food", 32.50, restaurant1);
        Item item2 = new Item("I0002", "Prawns in Szechuan Style with Fried Bun!", "The feeling of dipping our Fried Bun into Szechuan Style sauce and enjoying it’s hot, crispy texture and spicy taste is truly irreplaceable. This dish is a unique staple of deep fried fresh prawns sauteed with a Szechuan style sauce, paired perfectly with a Dragon-i exclusive fried bun.", "Food", 18.90, restaurant1);
        Item item3 = new Item("I0003", "Shanghai Signature Noodle", "Shanghai’s signature dish is a bowl of silky Scallion Noodles! Although simple, this dish is cooked to perfection with shredded pork scallion oil and our chef’s secret recipe for seasoning.", "Food", 30.53, restaurant1);
        Item item4 = new Item("I0004", "Brown Sugar Soymilk Iced Shaken Espresso", "Starbucks signature espresso, Brown sugar and cinnamon shaken together with ice and topped with soymilk", "Beverage", 22.26, restaurant3);
        Item item5 = new Item("I0005", "Green Tea Latte", "Invigorating green tea and steamed milk", "Beverage", 16.96, restaurant3);
        Item item6 = new Item("I0006", "Hazelnut Chocolate", "Four cocoas, hazelnut syrup and fresh steamed milk.", "Beverage", 15.90, restaurant3);
        Item item7 = new Item("I0007", "Ravioli pesce", "Seafood ravioli with garlic, prawns, white wine and cherry tomato sauce", "Food", 52, restaurant2);
        Item item8 = new Item("I0008", "Ravioli spinaci e ricotta", "Ricotta and spinach ravioli with butter-sage and parmesan", "Food", 48, restaurant2);
        Item item9 = new Item("I0009", "Spaghetti carbonara", "Bacon, egg yolk, black pepper and parmesan cheese", "Food", 42, restaurant2);
        Item item10 = new Item("I0010", "Huntaway, Marlborough (Pinot Noir)", "Ripe fruit armoas with hints of liquorice and spice, soft tannin", "Beverage", 230, restaurant2);        
        
        // initial order items
        OrderItem orderItem1 = new OrderItem(item2, 2);
        OrderItem orderItem2 = new OrderItem(item1, 1);
        OrderItem orderItem3 = new OrderItem(item4, 2);
        OrderItem orderItem4 = new OrderItem(item6, 4);
        OrderItem orderItem5 = new OrderItem(item9, 1);
        OrderItem orderItem6 = new OrderItem(item10, 1);
        OrderItem orderItem7 = new OrderItem(item7, 2);
        OrderItem orderItem8 = new OrderItem(item8, 3);
        OrderItem orderItem9 = new OrderItem(item3, 1);
        
        // initial cart orders
        ArrayList<OrderItem> orderItemList1 = new ArrayList<>();
        orderItemList1.add(orderItem1);
        orderItemList1.add(orderItem2);
        Order order1 = new Order(orderItemList1, customer1);
        
        ArrayList<OrderItem> orderItemList2 = new ArrayList<>();
        orderItemList2.add(orderItem3);
        orderItemList2.add(orderItem4);
        Order order2 = new Order(orderItemList2, customer3);
        
        ArrayList<OrderItem> orderItemList3 = new ArrayList<>();
        orderItemList3.add(orderItem5);
        orderItemList3.add(orderItem6);
        orderItemList3.add(orderItem7);
        Order order3 = new Order(orderItemList3, customer3);
        
        ArrayList<OrderItem> orderItemList4 = new ArrayList<>();
        orderItemList4.add(orderItem8);
        Order order4 = new Order(orderItemList4, customer3);
        
        ArrayList<OrderItem> orderItemList5 = new ArrayList<>();
        orderItemList5.add(orderItem9);
        Order order5 = new Order(orderItemList4, customer2);
        
        // initial paid orders
        PaidOrder paidOrder1 = new PaidOrder("P000000", order1, LocalDateTime.of(2022, 9, 18, 10, 0), "Delivering", customer1.getAddress().getEntry(1), customer1.getPaymentMethodList().getEntry(1), null);//Penang
        PaidOrder paidOrder2 = new PaidOrder("PO00001", order4, LocalDateTime.of(2022, 9, 18, 13, 48), "Delivering", customer2.getAddress().getEntry(1), customer2.getPaymentMethodList().getEntry(1), null);//Penang
        PaidOrder paidOrder3 = new PaidOrder("PO00002", order3, LocalDateTime.of(2022, 9, 18, 14, 40), "Delivering", customer3.getAddress().getEntry(1), customer3.getPaymentMethodList().getEntry(1), null);//Wilayah Persekutuan
        PaidOrder paidOrder4 = new PaidOrder("PO00003", order5, LocalDateTime.of(2022, 9, 18, 16, 48), "Delivering", customer2.getAddress().getEntry(1), customer2.getPaymentMethodList().getEntry(1), null);//Penang
                
        
        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
        customerList.add(customer3);
        
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(restaurant1);
        restaurantList.add(restaurant2);
        restaurantList.add(restaurant3);
        
        ArrayList<Rider> riderList = new ArrayList<>();
        riderList.add(rider1);
        riderList.add(rider2);
        riderList.add(rider3);
        riderList.add(emergencyRider);
        
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        itemList.add(item4);
        itemList.add(item5);
        itemList.add(item6);
        itemList.add(item7);
        itemList.add(item8);
        itemList.add(item9);
        itemList.add(item10);
        
        ArrayList<Order> cartOrders = new ArrayList<>();
        cartOrders.add(order2);
        cartOrders.add(order4);
        
        ArrayList<PaidOrder> paidOrders = new ArrayList<>();
        paidOrders.add(paidOrder1);
        paidOrders.add(paidOrder2);
        paidOrders.add(paidOrder3);
        paidOrders.add(paidOrder4);
        
        int choice, menuChoice, invalid = 0;
        Scanner scanner = new Scanner(System.in);
        User currentLogin;

        do {
            choice = mainMenu();

            if (choice == 1) {
                currentLogin = login(customerList, restaurantList, riderList);

                if (currentLogin instanceof Customer ) {
                Customer customer=(Customer)currentLogin;
                    do {
                        do {
                            System.out.println("===============");
                            System.out.println(" CUSTOMER MENU ");
                            System.out.println("===============");
                            System.out.println("1. Manage Personal Details");
                            System.out.println("2. Manage Finance");
                            System.out.println("3. Manage Cart");
                            System.out.println("4. Manage Activities");
                            System.out.println("5. Logout");
                            System.out.print("Please enter your choice: ");
                            menuChoice = scanner.nextInt();

                            if (menuChoice < 1 || menuChoice > 5) {
                                System.out.println("Invalid choice entered. Please reenter. ");
                                invalid = 1;
                            }
                        } while (invalid == 1);

                        if (menuChoice == 1) {
                            ManagePersonalDetails.managePersonalDetails(customer, customerList);
                        } else if (menuChoice == 2) {
                            ManageFinance.manageFinance(customer);
                        } else if (menuChoice == 3) {
                            ManageCart.manageCartMenu(customer, restaurantList, itemList, cartOrders, paidOrders);
                        } else if (menuChoice == 4) {
                            ManageActivities.manageActivitiesMenu(customer, paidOrders);
                        }
                    } while (menuChoice != 5);
                } else if (currentLogin instanceof Restaurant ) {
                Restaurant restaurant=(Restaurant)currentLogin;
                    do {
                        do {
                            System.out.println("=================");
                            System.out.println(" RESTAURANT MENU ");
                            System.out.println("=================");
                            System.out.println("1. Manage Menu");
                            System.out.println("2. Manage Orders");
                            System.out.println("3. Logout");
                            System.out.print("Please enter your choice: ");
                            menuChoice = scanner.nextInt();

                            if (menuChoice < 1 || menuChoice > 3) {
                                System.out.println("Invalid choice entered. Please reenter. ");
                                invalid = 1;
                            }
                        } while (invalid == 1);

                        if (menuChoice == 1) {
                            MenuManagement.menuInterface();
                        } else if (menuChoice == 2) {
                            OrderManagement.restaurantOrderManagement();
                        } 
                    } while (menuChoice != 3);
                } else if (currentLogin instanceof Rider ) {
                Rider rider=(Rider) currentLogin;
                RiderModule.riderOrderMenu(riderList, rider, paidOrders);
                }
            } else if (choice == 2) {
                registration(customerList, restaurantList, riderList);
            }
        } while (choice != 3);

    }

    public static int mainMenu() {
        Scanner s = new Scanner(System.in);
        System.out.println("\n\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_   Welcome To Food Ordering & Delivery System    _-_-_-_-_-_-_-_-_");
        System.out.println("\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 1 ] Log in                            .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 2 ] Registration                      .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 3 ] Exit Program                      .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-");
        System.out.print("\n\t\t\t\tEnter your selection: ");
        return s.nextInt();
    }

    public static User login(ArrayList<Customer> customerList, ArrayList<Restaurant> restaurantList, ArrayList<Rider> riderList) {
        boolean userFound = false;
        Scanner s = new Scanner(System.in);

        System.out.println("\n\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-_-_-_-_-_         User Log In         _-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("\t\t\t\t=====================================================================================");
        System.out.print("\t\t\t\tUser ID:\t: ");
        String userId = s.nextLine();
        System.out.print("\t\t\t\tPassword\t: ");
        String password = s.nextLine();

        if (userId.charAt(0) == 'C') {
            Iterator<Customer> custItr = customerList.getIterator();

            while (custItr.hasNext()) {
                Customer customer = custItr.next();

                if (customer.getUserId().equals(userId)) {
                    userFound = true;
                    
                    if (customer.getPassword().equals(password)) {
                        return customer;
                    } else {
                        System.out.println("Incorrect password entered! ");
                        break;
                    }
                } 
            }
        } else if (userId.charAt(0) == 'R') {
            if (userId.substring(0, 3).equals("RES")) {
                Iterator<Restaurant> restItr = restaurantList.getIterator();

                while (restItr.hasNext()) {
                    Restaurant restaurant = restItr.next();

                    if (restaurant.getUserId().equals(userId)) {
                        userFound = true;
                        
                        if (restaurant.getPassword().equals(password)) {
                            return restaurant;
                        } else {
                            System.out.println("Incorrect password entered! ");
                            break;
                        }
                    }
                }
            } else {
                Iterator<Rider> riderItr = riderList.getIterator();

                while (riderItr.hasNext()) {
                    Rider rider = riderItr.next();

                    if (rider.getUserId().equals(userId)) {
                        userFound = true;
                        
                        if (rider.getPassword().equals(password)) {
                            return rider;
                        } else {
                            System.out.println("Incorrect password entered! ");
                            break;
                        }
                    }
                }
            }
        } else {
            System.out.println("Invalid User ID entered! ");
        }

        if (userFound == false) {
            System.out.println("User does not exist! ");
        }
        
        return null;
    }

    public static void registration(ArrayList<Customer> customerList, ArrayList<Restaurant> restaurantList, ArrayList<Rider> riderList) {
        int accType;
        // regular expressions for validation
        String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        String contactRegex = "^(\\+?6?01)[02-46-9][-][0-9]{7}$|^(\\+?6?01)[1][-][0-9]{8}$";
        String emailRegex = "^(.+)@(.+)$";
        String postcodeRegex = "^[0-9]{5}$";
        String icRegex = "^[0-9]{5}-[0-9]{2}-[0-9]{4}$";
        String vehiclePlateRegex = "^[A-Z]{3}[0-9]{4}$";

        Scanner s = new Scanner(System.in);

        do {
            System.out.println("\n\t\t\t\t=====================================================================================");
            System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-_-_-_-_-_         Registration        _-_-_-_-_-_-_-_-_-_-_-_-_-_");
            System.out.println("\t\t\t\t=====================================================================================");
            System.out.println("1. Customer");
            System.out.println("2. Restaurant");
            System.out.println("3. Rider");
            System.out.println("4. Return to Main Menu");
            System.out.print("Please select the type of account you would like to register for: ");
            accType = s.nextInt();
        } while (accType < 1 || accType > 4);

        // input name and validation
        System.out.print("\t\t\t\tName\t\t: ");
        String name = s.nextLine();
        String password = "";
        while (true) {
            System.out.print("\t\t\t\tPassword\t: ");
            password = s.nextLine();
            if (password.matches(passwordRegex) == false) {
                System.out.println("\t\t\t\tPassword must have at least one numeric, lowercase, uppercase character and a special symbol with length between 8-20.");
            } else {
                break;
            }
        }
        // input contact number and validation
        String contactNo = "";
        while (true) {
            System.out.print("\t\t\t\tContact No.\t: ");
            contactNo = s.nextLine();
            if (contactNo.matches(contactRegex) == false) {
                System.out.println("\t\t\t\tInvalid contact number format.");
            } else {
                break;
            }
        }

        if (accType == 1) {
            // input email and validation
            while (true) {
                System.out.print("\t\t\t\tEmail Address\t: ");
                String email = s.nextLine();
                if (email.matches(emailRegex) == false) {
                    System.out.println("\t\t\t\tInvalid email address format.");
                } else {
                    for (int i = 1; i <= customerList.getNumberOfEntries(); i++) {
                        // check if email exist
                        if (customerList.getEntry(i).getEmail().contains(email)) {
                            System.out.println("\t\t\t\tEmail exists.");
                        } else {
                            // customer can choose to add or not adding the address during registration
                            System.out.print("\n\t\t\t\tDo you want to add address? [Y/N]: ");
                            String selection = s.nextLine();
                            OUTER:
                            while (true) {
                                switch (selection) {
                                    case "Y":
                                        // input address with postcode validation
                                        System.out.print("\n\t\t\t\tAddress Line\t: ");
                                        String addressLine = s.nextLine();
                                        String postcode;
                                        while (true) {
                                            System.out.print("\t\t\t\tPostcode\t: ");
                                            postcode = s.nextLine();
                                            if (postcode.matches(postcodeRegex) == false) {
                                                System.out.println("\t\t\t\tInvalid postcode format.");
                                            } else {
                                                break;
                                            }
                                        }
                                        System.out.print("\t\t\t\tDistrict\t: ");
                                        String district = s.nextLine();
                                        System.out.print("\t\t\t\tState\t\t: ");
                                        String state = s.nextLine();
                                        ArrayList<Address> addressList = new ArrayList<>();
                                        addressList.add(new Address(addressLine, postcode, district, state));
                                        // add customer object (with address) to customer list
                                        if (customerList.add(new Customer(name, password, contactNo, addressList, email))) {
                                            System.out.println("\t\t\t\tRegister successfully!");
                                        }
                                        break OUTER;
                                    case "N":
                                        // add customer object (without address) to customer list
                                        if (customerList.add(new Customer(name, password, contactNo, email))) {
                                            System.out.println("\t\t\t\tRegister successfully!");
                                        }
                                        break OUTER;
                                    default:
                                        System.out.println("\t\t\t\tInvalid input. Please select again.");
                                        break;
                                }
                            }
                        }
                        break;
                    }
                }
                break;
            }
        } else if (accType == 2) {
            String addressLine, postcode, district, state;
            LocalTime openingHour, closingHour;

            System.out.println("\t\t\t\tAddress: ");
            // input address with postcode validation
            System.out.print("\n\t\t\t\tAddress Line\t: ");
            addressLine = s.nextLine();
            while (true) {
                System.out.print("\t\t\t\tPostcode\t: ");
                postcode = s.nextLine();
                if (postcode.matches(postcodeRegex) == false) {
                    System.out.println("\t\t\t\tInvalid postcode format.");
                } else {
                    break;
                }
            }
            System.out.print("\t\t\t\tDistrict\t: ");
            district = s.nextLine();
            System.out.print("\t\t\t\tState\t\t: ");
            state = s.nextLine();
            ArrayList<Address> addressList = new ArrayList<>();
            addressList.add(new Address(addressLine, postcode, district, state));

            while (true) {
                int hour, min;

                System.out.println("\t\t\t\tOpening Hour: ");
                System.out.print("\t\t\t\tHour\t: ");
                hour = s.nextInt();
                System.out.print("\t\t\t\tMinute\t: ");
                min = s.nextInt();
                if (hour < 0 || hour > 23 || min < 0 || min > 59) {
                    System.out.println("Invalid opening hour entered. Please reenter. ");
                } else {
                    openingHour = LocalTime.of(hour, min);
                    break;
                }
            }

            while (true) {
                int hour, min;

                System.out.println("\t\t\t\tClosing Hour: ");
                System.out.print("\t\t\t\tHour\t: ");
                hour = s.nextInt();
                System.out.print("\t\t\t\tMinute\t: ");
                min = s.nextInt();
                if (hour < 0 || hour > 23 || min < 0 || min > 59) {
                    System.out.println("Invalid closing hour entered. Please reenter. ");
                } else {
                    closingHour = LocalTime.of(hour, min);
                    break;
                }
            }

            if (restaurantList.add(new Restaurant(name, password, contactNo, addressList, openingHour, closingHour))) {
                System.out.println("\t\t\t\tRegister successfully!");
            }
        } else if (accType == 3) {
            String addressLine, postcode, district, state, icNo, vehiclePlate, emergencyName, emergencyPhone;
            LocalDate dateOfBirth;

            System.out.println("\t\t\t\tAddress: ");
            // input address with postcode validation
            System.out.print("\n\t\t\t\tAddress Line\t: ");
            addressLine = s.nextLine();
            while (true) {
                System.out.print("\t\t\t\tPostcode\t: ");
                postcode = s.nextLine();
                if (postcode.matches(postcodeRegex) == false) {
                    System.out.println("\t\t\t\tInvalid postcode format.");
                } else {
                    break;
                }
            }
            System.out.print("\t\t\t\tDistrict\t: ");
            district = s.nextLine();
            System.out.print("\t\t\t\tState\t\t: ");
            state = s.nextLine();
            ArrayList<Address> addressList = new ArrayList<>();
            addressList.add(new Address(addressLine, postcode, district, state));

            // input IC number and validation
            while (true) {
                System.out.print("\t\t\t\tIC No. \t\t: ");
                icNo = s.nextLine();
                if (icNo.matches(icRegex) == false) {
                    System.out.println("\t\t\tInvalid IC Number entered. ");
                } else {
                    break;
                }
            }

            // input vehicle plate number and validation
            while (true) {
                System.out.print("\t\t\t\tVehicle Plate No. \t\t: ");
                vehiclePlate = s.nextLine();
                if (vehiclePlate.matches(vehiclePlateRegex) == false) {
                    System.out.println("\t\t\tInvalid Vehicle Plate Number entered. ");
                } else {
                    break;
                }
            }

            System.out.print("\t\t\t\tEmergency Rider Name \t\t: ");
            emergencyName = s.nextLine();

            while (true) {
                System.out.print("\t\t\t\tEmergency Rider Contact No.\t: ");
                emergencyPhone = s.nextLine();
                if (emergencyPhone.matches(contactRegex) == false) {
                    System.out.println("\t\t\t\tInvalid contact number format.");
                } else {
                    break;
                }
            }

            while (true) {
                int day, month, year;

                System.out.print("\t\t\t\tDate of Birth\t: ");
                System.out.println("\t\t\t\tDay: ");
                day = s.nextInt();
                System.out.println("\t\t\t\tMonth: ");
                month = s.nextInt();
                System.out.println("\t\t\t\tYear: ");
                year = s.nextInt();
                if (String.valueOf(year).length() != 4 || month < 1 || month > 12 || day < 1 || day > 31) {
                    System.out.println("Invalid date of birth entered. Please reenter. ");
                } else {
                    dateOfBirth = LocalDate.of(year, month, day);
                    break;
                }
            }

            if (riderList.add(new Rider(password, name, contactNo, addressList, icNo, vehiclePlate, emergencyName, emergencyPhone, dateOfBirth, LocalDate.now(),null))) {
                System.out.println("\t\t\t\tRegister successfully!");
            }
        }
    }
}
