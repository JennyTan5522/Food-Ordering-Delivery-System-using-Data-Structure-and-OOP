package Client;

import java.util.Scanner;
import java.util.Iterator;
import java.time.LocalTime;

import ADT.Restaurant.*;
import ADT.Customer.ArrayList;
import Entity.*;
/**
 *
 * @author xuanyi
 */
public class OrderManagement {
    
    public static void restaurantOrderManagement(){
        
        
        ListInterface<Item> menuItem = new SinglyLinkedList<>();

        //hard code for testing
        Restaurant restaurant = new Restaurant(LocalTime.of(8, 0), LocalTime.of(9, 0));

        Item item1 = new Item("A0001", "j", "j", "Food", 2, restaurant);
        Item item2 = new Item("A0002", "kfc", "k", "Food", 2, restaurant);
        Item item3 = new Item("A0003", "l", "l", "Food", 2, restaurant);
        Item item4 = new Item("A0004", "m", "m", "Beverage",2, restaurant);
        Item item5 = new Item("A0005", "n", "n", "Beverage", 2, restaurant);
        Item item6 = new Item("A0006", "o", "o", "Beverage", 2, restaurant);

        menuItem.add(item1);
        menuItem.add(item2);
        menuItem.add(item3);
        menuItem.add(item4);
        menuItem.add(item5);
        menuItem.add(item6);
        
        OrderItem orderItem1 = new OrderItem(item1,1);
        OrderItem orderItem2 = new OrderItem(item2,1);
        OrderItem orderItem3 = new OrderItem(item3,1);
        OrderItem orderItem4 = new OrderItem(item4,1);
        OrderItem orderItem5 = new OrderItem(item5,1);
        OrderItem orderItem6 = new OrderItem(item6,1);
        
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);
        orderItems.add(orderItem3);
        orderItems.add(orderItem4);
        orderItems.add(orderItem5);
        orderItems.add(orderItem6);
        
        
        Order order = new Order(orderItems);
        
        PaidOrder restaurantOrder1 = new PaidOrder(order, null, null);
        PaidOrder restaurantOrder2 = new PaidOrder(order, null, null);
        PaidOrder restaurantOrder3 = new PaidOrder(order, null, null);
        PaidOrder restaurantOrder4 = new PaidOrder(order, null, null);
        PaidOrder restaurantOrder5 = new PaidOrder(order, null, null);
        PaidOrder restaurantOrder6 = new PaidOrder(order, null, null);
        
        QueueInterface<PaidOrder> restaurantOrder = new CircularLinkedQueue<>();

        restaurantOrder.enqueue(restaurantOrder1);
        restaurantOrder.enqueue(restaurantOrder2);
        restaurantOrder.enqueue(restaurantOrder3);
        restaurantOrder.enqueue(restaurantOrder4);
        restaurantOrder.enqueue(restaurantOrder5);
        restaurantOrder.enqueue(restaurantOrder6);
        
        PaidOrder completedOrder1 = new PaidOrder("PO0001");
        PaidOrder completedOrder2 = new PaidOrder("PO0002");

        
        completedOrder1 = restaurantOrder.dequeue();
        completedOrder2 = restaurantOrder.dequeue();
        
        QueueInterface<PaidOrder> completedList = new CircularLinkedQueue<>();
        
        completedList.enqueue(completedOrder1);
        completedList.enqueue(completedOrder2);
        
        updateOrderStatus(restaurantOrder);
        viewOrders(restaurantOrder);
        
        

    }
    
    public static void restaurantInterface(QueueInterface<PaidOrder> restaurantOrder){
        
        Scanner scanner = new Scanner(System.in);
        
        int orderManagement;
        
        if(!restaurantOrder.isEmpty()){
            System.out.println("Currently, the first order is: ");
            System.out.println(restaurantOrder.getFront().getOrder().toString());
        }

        
        do{
            
            System.out.println("\nRestaurant Order Management");
            System.out.println("===========================");
            System.out.println("1. Update Order ");
            System.out.println("2. Cancel Order");
            System.out.println("3. View Order");
            System.out.print("Please enter your choice (0 to Exit): ");
            orderManagement = scanner.nextInt();

            while (orderManagement < 0 || orderManagement > 3) {
                System.out.println("\nInvalid choice entered. Please re-enter. \n");
                System.out.print("Please enter your choice (0 to Exit): ");
                orderManagement = scanner.nextInt();
            }
            
            if(orderManagement == 1){
                
                if(restaurantOrder.isEmpty()){
                    System.out.println("There are no orders currently.");
                }else{
                    restaurantOrder = updateOrderStatus(restaurantOrder);

                }
            }else if(orderManagement == 2){
                
                if (restaurantOrder.isEmpty()) {
                    System.out.println("There are no orders currently.");
                } else {
                    restaurantOrder = cancelOrder(restaurantOrder);
                }
                
            }else if(orderManagement == 3){
                viewOrders(restaurantOrder);
            }
                
            
        }while(orderManagement!=0);
        

        
        
        
    }
    
    public static PaidOrder searchOrderId(QueueInterface<PaidOrder> activeOrderQueue) {

        Scanner scanner = new Scanner(System.in);

        String orderId;
        PaidOrder matchedOrder;
        PaidOrder checkId;

        Iterator<PaidOrder> checkExistIterator;
        QueueInterface<String> orderList = new CircularLinkedQueue<>();

        checkExistIterator = activeOrderQueue.getIterator();

        while (checkExistIterator.hasNext()) {

            checkId = checkExistIterator.next();
            orderList.enqueue(checkId.getOrderId());

        }

        do {
            
            System.out.print("\nPlease enter the order ID to be searched (-1 to Exit): ");
            orderId = scanner.nextLine();

            if (orderList.contains(orderId)) {
                
                Iterator<PaidOrder> getMatchedOrder;

                getMatchedOrder = activeOrderQueue.getIterator();

                while (getMatchedOrder.hasNext()) {

                    matchedOrder = getMatchedOrder.next();
                    if(matchedOrder.getOrderId().equals(orderId)){

                        System.out.println("\nThe following order with the Order ID entered was FOUND !");
                        System.out.printf("\nDetails of ORDER ID: %s\n",orderId);
                        System.out.println("===========================");
                        System.out.println(matchedOrder.toString());
                        return matchedOrder;
                    }
                    
                }

            } else {
                System.out.println("\nNo item in the Restaurant Menu matches the ID entered.");
                orderId="0";
            }

        } while (orderId == "0");

        return null;
    }
        
    
    public static QueueInterface<PaidOrder> displayAllActiveOrders(QueueInterface<PaidOrder> allOrders){
        
        Iterator<PaidOrder> allOrderIterator = allOrders.getIterator();
        QueueInterface<PaidOrder> activeOrderQueue = new CircularLinkedQueue<>();
        
        int countOrder=1;
        
        System.out.println("\nRestaurant Orders");
        System.out.println("=================");        
        System.out.println("\nActive Orders");
        System.out.println("-------------");

        //display all active orders
        while (allOrderIterator.hasNext()) {

            PaidOrder activeOrder = allOrderIterator.next();
            
            if (activeOrder.getOrderStatus().equals("Order Accepted")) {

                System.out.println(countOrder + ".\n" +  activeOrder.toString());
                countOrder++;
                activeOrderQueue.enqueue(activeOrder);
            }

        }
        
        
        System.out.println(countOrder - 1 + " Active Order(s) currently.");

        return activeOrderQueue;
        
        
    }    
    
    public static QueueInterface<PaidOrder> updateOrderStatus(QueueInterface<PaidOrder> order){
        
        Scanner scanner = new Scanner(System.in);
        
        char updateOrder, confUpdate;
        PaidOrder orderFound, activeOrderUpdated;
        
        QueueInterface<PaidOrder> activeOrderQueue;

        //display all active orders and return active orders
        activeOrderQueue = displayAllActiveOrders(order);

        System.out.print("\nDo you want to update the order status of the orders in the active order (Y/N)? ");
        updateOrder = scanner.next().charAt(0);

        // validate user input
        while (Character.toUpperCase(updateOrder) != 'Y' && Character.toUpperCase(updateOrder) != 'N') {
            System.out.println("\nInvalid character entered. Please re-enter. ");
            System.out.print("\nDo you want to update the order status of the orders in the active order (Y/N)? ");
            updateOrder = scanner.next().charAt(0);
        }        
        
        do {
            
            if (Character.toUpperCase(updateOrder) == 'Y') {
                orderFound = searchOrderId(activeOrderQueue);

                if (orderFound != null) {

                    System.out.print("\nIs this the the order that you want to update (Y/N)? ");
                    confUpdate = scanner.next().charAt(0);

                    // validate user input
                    while (Character.toUpperCase(confUpdate) != 'Y' && Character.toUpperCase(confUpdate) != 'N') {
                        System.out.println("\nInvalid character entered. Please re-enter. ");
                        System.out.print("\nIs this the the order that you want to update (Y/N)? ");
                        confUpdate = scanner.next().charAt(0);
                    }

                    if (Character.toUpperCase(confUpdate) == 'Y') {
                        

                        Iterator<PaidOrder> checkStatusIterator;

                        checkStatusIterator = order.getIterator();

                        while (checkStatusIterator.hasNext()) {

                            activeOrderUpdated = checkStatusIterator.next();

                            if (activeOrderUpdated.getOrderId().equals(orderFound.getOrderId())) {

                                activeOrderUpdated.setOrderStatus("In the kitchen");
                                System.out.println("\nUpdated Details of the Order");
                                System.out.println("============================");

                                System.out.println(activeOrderUpdated.toString());

                                //display the next order of this order
                                activeOrderUpdated = checkStatusIterator.next();
                                System.out.println("The next order will be: ");
                                System.out.println(activeOrderUpdated.toString());

                            }

                        }

                    } else {
                        System.out.println("The status of the order was not updated ! ");
                    }

                }
            }

            
            System.out.print("\nDo you want to update the order status of other orders in the active order (Y/N)? ");
            updateOrder = scanner.next().charAt(0);

            // validate user input
            while (Character.toUpperCase(updateOrder) != 'Y' && Character.toUpperCase(updateOrder) != 'N') {
                System.out.println("\nInvalid character entered. Please re-enter. ");
                System.out.print("\nDo you want to update the order status of other orders in the active order (Y/N)? ");
                updateOrder = scanner.next().charAt(0);
            }

        } while (Character.toUpperCase(updateOrder) == 'Y');
        
        displayAllActiveOrders(order);
        
        return order;
    }
    
    public static QueueInterface<PaidOrder> cancelOrder(QueueInterface<PaidOrder> order) {

        Scanner scanner = new Scanner(System.in);

        char cancelOrder, confCancel;
        PaidOrder orderFound, activeOrderUpdated;

        QueueInterface<PaidOrder> activeOrderQueue;

        //display all active orders and return active orders
        activeOrderQueue = displayAllActiveOrders(order);

        System.out.print("\nDo you want to cancel an order (Y/N)? ");
        cancelOrder = scanner.next().charAt(0);

        // validate user input
        while (Character.toUpperCase(cancelOrder) != 'Y' && Character.toUpperCase(cancelOrder) != 'N') {
            System.out.println("\nInvalid character entered. Please re-enter. ");
            System.out.print("\nDo you want to cancel an order (Y/N)? ");
            cancelOrder = scanner.next().charAt(0);
        }

        do {

            if (Character.toUpperCase(cancelOrder) == 'Y') {
                orderFound = searchOrderId(activeOrderQueue);

                if (orderFound != null) {

                    System.out.print("\nIs this the the order that you want to cancel (Y/N)? ");
                    confCancel = scanner.next().charAt(0);

                    // validate user input
                    while (Character.toUpperCase(confCancel) != 'Y' && Character.toUpperCase(confCancel) != 'N') {
                        System.out.println("\nInvalid character entered. Please re-enter. ");
                        System.out.print("\nIs this the the order that you want to cancel (Y/N)? ");
                        confCancel = scanner.next().charAt(0);
                    }

                    if (Character.toUpperCase(confCancel) == 'Y') {
                        
                        if(order.getNumberOfEntries() == 1){
                            order.getFront().setOrderStatus("Cancelled by restaurant");
                            System.out.println("\nUpdated Details of the Order");
                            System.out.println("============================");
                            order.clear();

                        }

                        Iterator<PaidOrder> checkStatusIterator;

                        checkStatusIterator = order.getIterator();

                        while (checkStatusIterator.hasNext()) {

                            activeOrderUpdated = checkStatusIterator.next();

                            if (activeOrderUpdated.getOrderId().equals(orderFound.getOrderId())) {

                                activeOrderUpdated.setOrderStatus("Cancelled by restaurant");
                                System.out.println("\nUpdated Details of the Order");
                                System.out.println("============================");

                                System.out.println(activeOrderUpdated.toString());

                            }

                        }

                    } else {
                        System.out.println("The order was not cancelled ! ");
                    }

                }
            }

            System.out.print("\nDo you want to cancel other orders (Y/N)? ");
            cancelOrder = scanner.next().charAt(0);

            // validate user input
            while (Character.toUpperCase(cancelOrder) != 'Y' && Character.toUpperCase(cancelOrder) != 'N') {
                System.out.println("\nInvalid character entered. Please re-enter. ");
                System.out.print("\nDo you want to cancel other orders (Y/N)? ");
                cancelOrder = scanner.next().charAt(0);
            }

        } while (Character.toUpperCase(cancelOrder) == 'Y');

        displayAllActiveOrders(order);

        return order;
        
    }
    
    public static void viewFoodOrders(QueueInterface<PaidOrder> order){
        

        Iterator<PaidOrder> allOrderIterator = order.getIterator();

        QueueInterface<PaidOrder> activeOrderQueue = new CircularLinkedQueue<>();

        int countOrder = 1;

        System.out.println("\nRestaurant Orders");
        System.out.println("=================");
        System.out.println("\nFood Orders");
        System.out.println("-----------");

        //display all active orders
        while (allOrderIterator.hasNext()) {

            PaidOrder activeOrder = allOrderIterator.next();
            
            //check through all items in each orderId
                
            if (activeOrder.getOrder().getOrderItems().getIterator().next().getItem().getCategory() == "Food") {

                System.out.println(countOrder + ".\n" + activeOrder.getOrder().getOrderItems().getIterator().next().getItem().toString());
                countOrder++;
            }

        }

        System.out.println(countOrder - 1 + " Food Order(s) currently.");
        
    }
    
    public static void viewBeverageOrder(QueueInterface<PaidOrder> order) {

        Iterator<PaidOrder> allOrderIterator = order.getIterator();

        QueueInterface<PaidOrder> activeOrderQueue = new CircularLinkedQueue<>();

        int countOrder = 1;

        System.out.println("\nRestaurant Orders");
        System.out.println("=================");
        System.out.println("\nBeverage Orders");
        System.out.println("--------------");

        //display all active orders
        while (allOrderIterator.hasNext()) {

            PaidOrder activeOrder = allOrderIterator.next();

            //check through all items in each orderId
            if (activeOrder.getOrder().getOrderItems().getIterator().next().getItem().getCategory() == "Beverage") {

                System.out.println(countOrder + ".\n" + activeOrder.getOrder().getOrderItems().getIterator().next().getItem().toString());
                countOrder++;
            }

        }

        System.out.println(countOrder - 1 + " Beverage Order(s) currently.");

    }
    
    
    //view Selction for active orders
    public static void viewSelection(QueueInterface<PaidOrder> order){
        
        Scanner scanner = new Scanner(System.in);
        
        int viewOrder, viewMenu=0, viewOption;
        char viewSpecific = 'N';
        
        PaidOrder orderFound;
        
        do{
            
            System.out.println("\nView Active Order");
            System.out.println("=================");
            System.out.println("1. View Specific Order");
            System.out.println("2. View All Orders");
            System.out.print("Please enter your choice (0 to Exit): ");
            viewOrder = scanner.nextInt();

            while (viewOrder < 0 || viewOrder > 2) {
                System.out.println("\nInvalid choice entered. Please re-enter. \n");
                System.out.print("Please enter your choice (0 to Exit): ");
                viewOrder = scanner.nextInt();
            }

            if (viewOrder == 1) {

                do {

                    orderFound = searchOrderId(order);
                    if (orderFound != null) {

                        System.out.print("\nDo you want to view other Active Order details of a specific item (Y/N)? ");
                        viewSpecific = scanner.next().charAt(0);

                        // validate user input
                        while (Character.toUpperCase(viewSpecific) != 'Y' && Character.toUpperCase(viewSpecific) != 'N') {
                            System.out.println("\nInvalid character entered. Please re-enter. ");
                            System.out.print("\nDo you want to view other Active Order details of a specific item (Y/N)? ");
                            viewSpecific = scanner.next().charAt(0);
                        }
                        
                        

                    }

                } while (Character.toUpperCase(viewSpecific) == 'Y');

            }else{
                do{
                    System.out.println("\nView Selection");
                    System.out.println("================");
                    System.out.println("1. Active Food Orders");
                    System.out.println("2. Active Beverage Orders");
                    System.out.println("3. All Active Orders");

                    System.out.print("Please enter your choice (0 to Exit): ");
                    viewOption = scanner.nextInt();

                    while (viewOption < 0 || viewOption > 3) {
                        System.out.println("\nInvalid choice entered. Please re-enter. \n");
                        System.out.print("Please enter your choice (0 to Exit): ");
                        viewOption = scanner.nextInt();
                    }
                    
                    if(viewOption == 1){
                        
                        viewFoodOrders(order);
                        
                    }else if(viewOption == 2){
                        viewBeverageOrder(order);
                    }else{
                        displayAllActiveOrders(order);
                    }
                    
                }while(viewOption != 0);
            }
            
        }while(viewOrder !=0);

    }
    
    //create another viewselection for order history
    
    public static void viewOrders(QueueInterface<PaidOrder> restaurantOrder){
                
        Scanner scanner = new Scanner(System.in);
        
        //variables
        int viewOption;
        
        System.out.println("View Order Option");
        System.out.println("=================");        
        System.out.println("1. Active Order");
        System.out.println("2. Order History");
        System.out.println("3. Order Summary");
        System.out.print("Please enter your choice: ");
        viewOption = scanner.nextInt();
        
        while(viewOption<1 || viewOption>3){
            System.out.println("\nInvalid choice entered. Please re-enter. \n");
            System.out.print("Please enter your choice: ");
            viewOption = scanner.nextInt();
        }
        
        if(viewOption == 1){
            
            viewSelection(restaurantOrder);
            
        }else if(viewOption == 2){
            //call view order history method
            
        }else{
            //call order summary method
        }
        
        
        
    }
    
}
