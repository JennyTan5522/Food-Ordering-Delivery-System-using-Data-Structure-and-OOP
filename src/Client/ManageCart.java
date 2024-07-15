package Client;

import ADT.Customer.ArrayList;
import ADT.Cart_Activities.*;
import Entity.*;

import java.util.Scanner;
import java.util.ListIterator;
import java.time.YearMonth;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class ManageCart {

    public static void manageCartMenu(Customer customer, ArrayList<Restaurant> restaurants, ArrayList<Item> items, ArrayList<Order> cartOrders, ArrayList<PaidOrder> paidOrders) {
        int menuChoice, invalid;
        Scanner scanner = new Scanner(System.in);

        do {
            invalid = 0;

            System.out.println("==================");
            System.out.println(" MANAGE CART MENU ");
            System.out.println("==================");
            System.out.println("1. Add Order to Cart");
            System.out.println("2. Edit Cart Order");
            System.out.println("3. View Cart Orders");
            System.out.println("4. Delete Cart Order");
            System.out.println("5. Pay Cart Order");
            System.out.println("6. Return to Main Menu");
            System.out.print("Please enter your choice: ");
            menuChoice = scanner.nextInt();

            if (menuChoice < 1 || menuChoice > 6) {
                System.out.println("Invalid choice entered. Please reenter. ");
                invalid = 1;
            } else {
                if (menuChoice == 1) {
                    addCartOrder(customer, restaurants, items, cartOrders);
                } else if (menuChoice == 2) {
                    editCartOrder(customer, cartOrders);
                } else if (menuChoice == 3) {
                    viewCartOrder(customer, cartOrders);
                } else if (menuChoice == 4) {
                    deleteCartOrder(customer, cartOrders);
                } else if (menuChoice == 5) {
                    payCartOrder(customer, cartOrders, paidOrders);
                }
            }
        } while (invalid == 1 || menuChoice != 6);
    }

    public static void addCartOrder(Customer customer, ArrayList<Restaurant> restaurants, ArrayList<Item> items, ArrayList<Order> cartOrders) {
        int restaurantChoice = 0, itemChoice = 0, invalid, qty = 0, qtyChoice = 0;
        char cont = ' ', confirm = ' ';
        boolean foundOrder = false, foundItem = false;
        Scanner scanner = new Scanner(System.in);
        Restaurant restaurantSelected;
        Order order = null, tempOrder;
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        ListInterface<Item> itemList = new DoublyLinkedList<>();
        ListInterface<Order> orders = new DoublyLinkedList<>();

        for (int i = 1; i <= cartOrders.getNumberOfEntries(); i++) {
            if (cartOrders.getEntry(i).getCustomer().equals(customer)) {
                orders.add(cartOrders.getEntry(i));
            }
        }

        do {
            invalid = 0;
            itemList.clear();
            orderItems.clear();

            do {
                invalid = 0;

                System.out.println("=============");
                System.out.println(" RESTAURANTS ");
                System.out.println("=============");
                for (int i = 1; i <= restaurants.getNumberOfEntries(); i++) {
                    System.out.println(i + ". " + restaurants.getEntry(i).getName());
                }
                System.out.print("Please select a restaurant (-1 to exit): ");
                restaurantChoice = scanner.nextInt();

                if (restaurantChoice != -1 && (restaurantChoice <= 0 || restaurantChoice > restaurants.getNumberOfEntries())) {
                    System.out.println("Invalid choice entered. Please reenter. \n");
                    invalid = 1;
                }
            } while (invalid == 1);

            if (restaurantChoice != -1) {
                restaurantSelected = restaurants.getEntry(restaurantChoice);

                ArrayList<OrderItem> tempList = new ArrayList<>();
                Item tempItem = new Item(restaurantSelected);
                OrderItem tempOrderItem = new OrderItem(tempItem);
                tempList.add(tempOrderItem);

                tempOrder = new Order(tempList);
                if (orders.contains(tempOrder)) {
                    foundOrder = true;
                    int position = orders.positionOf(tempOrder);

                    if (position == 1) {
                        order = orders.getFirst();
                    } else if (position == orders.size()) {
                        order = orders.getLast();
                    } else {
                        order = orders.get(orders.positionOf(tempOrder));
                    }
                    System.out.println("Order found in cart! ");
                    System.out.println(order.toString());
                }

                for (int i = 1; i <= items.getNumberOfEntries(); i++) {
                    if (items.getEntry(i).getRestaurant().equals(restaurantSelected)) {
                        itemList.add(items.getEntry(i));
                    }
                }

                do {
                    invalid = 0;

                    do {
                        invalid = 0;

                        System.out.println("==========");
                        System.out.println(" PRODUCTS");
                        System.out.println("==========");
                        for (int i = 1; i <= itemList.size(); i++) {
                            System.out.println(i + ". " + itemList.get(i).toString());
                        }
                        System.out.print("Please select an item (-1 to exit): ");
                        itemChoice = scanner.nextInt();

                        if (itemChoice != -1 && (itemChoice <= 0 || itemChoice > itemList.size())) {
                            System.out.println("Invalid choice entered. Please reenter. \n");
                            invalid = 1;
                        }
                    } while (invalid == 1);

                    if (itemChoice != -1) {
                        if (foundOrder == true) {
                            if (order.getOrderItems().contains(new OrderItem(itemList.get(itemChoice)))) {
                                System.out.println("Item found in cart! ");
                                System.out.println(order.getOrderItems().getEntry(order.getOrderItems().getPositionOf(new OrderItem(itemList.get(itemChoice)))).toString());
                                foundItem = true;
                            }
                        }

                        do {
                            invalid = 0;

                            System.out.print("Please enter the quantity you would like to order (-1 to exit): ");
                            qty = scanner.nextInt();

                            if (qty != -1 && qty <= 0) {
                                System.out.println("Invalid quantity entered. Please reenter. \n");
                                invalid = 1;
                            }
                        } while (invalid == 1);

                        if (qty != -1) {
                            if (foundItem == false) {
                                OrderItem orderItem = new OrderItem(itemList.get(itemChoice), qty);

                                if (foundOrder == true) {
                                    order.getOrderItems().add(orderItem);
                                } else {
                                    orderItems.add(orderItem);
                                }

                                System.out.println("Item added to order successfully! ");
                            } else {
                                do {
                                    invalid = 0;

                                    System.out.println("1 - Edit quantity, 2 - Add on quantity");
                                    System.out.print("Would you like to edit the quantity of the item or add on to the current quantity? ");
                                    qtyChoice = scanner.nextInt();
                                    if (qtyChoice != 1 && qtyChoice != 2) {
                                        System.out.println("Invalid choice entered. Please reenter. \n");
                                        invalid = 1;
                                    }
                                } while (invalid == 1);

                                if (qtyChoice == 1) {
                                    order.getOrderItems().getEntry(order.getOrderItems().getPositionOf(new OrderItem(itemList.get(itemChoice)))).setQuantity(qty);
                                    System.out.println("Quantity edited successfully! ");
                                } else {
                                    int oriQty = order.getOrderItems().getEntry(order.getOrderItems().getPositionOf(new OrderItem(itemList.get(itemChoice)))).getQuantity();
                                    int newQty = oriQty + qty;
                                    order.getOrderItems().getEntry(order.getOrderItems().getPositionOf(new OrderItem(itemList.get(itemChoice)))).setQuantity(newQty);
                                    System.out.println("Quantity added successfully! ");
                                }
                            }
                        }
                    }

                    do {
                        invalid = 0;

                        System.out.print("Would you like to add another item to your order? (Y = yes) ");
                        cont = scanner.next().charAt(0);
                        if (Character.toUpperCase(cont) != 'Y' && Character.toUpperCase(cont) != 'N') {
                            System.out.println("Invalid choice entered. Please reenter. \n");
                            invalid = 1;
                        }
                    } while (invalid == 1);
                } while (Character.toUpperCase(cont) == 'Y');

                if (orderItems.getNumberOfEntries() != 0) {
                    System.out.println("Items in your order: ");
                    System.out.println("=====================");
                    for (int i = 1; i <= orderItems.getNumberOfEntries(); i++) {
                        System.out.println(i + ". " + orderItems.getEntry(i).toString());
                    }

                    do {
                        invalid = 0;

                        System.out.print("Confirm add order to cart? (Y = yes) ");
                        confirm = scanner.next().charAt(0);
                        if (Character.toUpperCase(confirm) != 'Y' && Character.toUpperCase(confirm) != 'N') {
                            System.out.println("Invalid choice entered. Please reenter. \n");
                            invalid = 1;
                        }
                    } while (invalid == 1);

                    if (Character.toUpperCase(confirm) == 'Y') {
                        Order newOrder = new Order(orderItems, customer);
                        cartOrders.add(newOrder);
                        orders.add(new Order(orderItems, customer));
                        System.out.println("Order added to cart successfully! ");
                    }
                }

                do {
                    invalid = 0;

                    System.out.print("Would you like to add another order? (Y = yes) ");
                    cont = scanner.next().charAt(0);
                    if (Character.toUpperCase(cont) != 'Y' && Character.toUpperCase(cont) != 'N') {
                        System.out.println("Invalid choice entered. Please reenter. \n");
                        invalid = 1;
                    }
                } while (invalid == 1);
            }
        } while (Character.toUpperCase(cont) == 'Y');
    }

    public static void editCartOrder(Customer customer, ArrayList<Order> cartOrders) {
        int invalid, choice, qty, orderChoice;
        char confirm, cont = ' ';
        boolean foundOrder = false, foundItem = false;
        Scanner scanner = new Scanner(System.in);
        String restaurantName, itemID;
        Order order = null;
        OrderItem orderItem = null;
        ListInterface<Order> orders = new DoublyLinkedList<>();

        for (int i = 1; i <= cartOrders.getNumberOfEntries(); i++) {
            if (cartOrders.getEntry(i).getCustomer().equals(customer)) {
                orders.add(cartOrders.getEntry(i));
            }
        }

        if (orders.isEmpty()) {
            System.out.println("You do not have any orders in your cart! ");
        } else {
            do {
                invalid = 0;

                do {
                    invalid = 0;

                    System.out.println("Orders in your cart: ");
                    System.out.println("=====================");
                    for (int i = 1; i <= orders.size(); i++) {
                        System.out.println(i + orders.get(i).getOrderItems().getEntry(0).getItem().getRestaurant().getName());
                    }
                    System.out.print("Please select an order: ");
                    orderChoice = scanner.nextInt();

                    if (orderChoice < 1 || orderChoice > orders.size()) {
                        invalid = 1;
                        System.out.println("Invalid choice entered. Please reenter. ");
                    }

                    System.out.println("Please enter the restaurant of the order you would like to edit: ");
                    restaurantName = scanner.next();
                } while (invalid == 1);

                if (orderChoice == 1) {
                    order = orders.getFirst();
                } else if (orderChoice == orders.size()) {
                    order = orders.getLast();
                } else {
                    order = orders.get(orderChoice);
                }

                do {
                    System.out.println(order.toString());

                    do {
                        invalid = 0;

                        System.out.println("Please enter the item ID of the item you would like to edit: ");
                        itemID = scanner.next();

                        if (itemID.equals("") == false) {
                            Item item = new Item(itemID);
                            OrderItem tempOrderItem = new OrderItem(item);

                            if (order.getOrderItems().contains(tempOrderItem) == false) {
                                System.out.println("Item not found! Please reenter. ");
                                invalid = 1;
                            } else {
                                orderItem = order.getOrderItems().getEntry(order.getOrderItems().getPositionOf(tempOrderItem));
                                foundItem = true;
                            }
                        }
                    } while (invalid == 1);

                    if (foundItem == true) {
                        do {
                            invalid = 0;

                            System.out.println("===========");
                            System.out.println(" EDIT ITEM ");
                            System.out.println("===========");
                            System.out.println("1. Remove item");
                            System.out.println("2. Edit item quantity");
                            System.out.println("3. Return to previous menu");
                            System.out.println("Please enter your choice: ");
                            choice = scanner.nextInt();

                            if (choice < 1 || choice > 3) {
                                System.out.println("Invalid choice entered. Please reenter. ");
                                invalid = 1;
                            }
                        } while (invalid == 1);

                        if (choice == 1) {
                            System.out.println(orderItem.toString());

                            do {
                                invalid = 0;

                                System.out.println("Are you sure you want to remove this item? (Y = yes) ");
                                confirm = scanner.next().charAt(0);
                                if (Character.toUpperCase(confirm) != 'Y' && Character.toUpperCase(confirm) != 'N') {
                                    System.out.println("Invalid choice entered. Please reenter. \n");
                                    invalid = 1;
                                }
                            } while (invalid == 1);

                            if (Character.toUpperCase(confirm) == 'Y') {
                                order.getOrderItems().remove(order.getOrderItems().getPositionOf(orderItem));
                                System.out.println("Item removed successfully! ");
                            }
                        } else if (choice == 2) {
                            do {
                                invalid = 0;

                                System.out.println("Please enter the quantity you would like to order (-1 to exit): ");
                                qty = scanner.nextInt();

                                if (qty != -1 && qty <= 0) {
                                    System.out.println("Invalid quantity entered. Please reenter. \n");
                                    invalid = 1;
                                }
                            } while (invalid == 1);

                            do {
                                invalid = 0;

                                System.out.println("Are you sure you want to edit the quantity? (Y = yes) ");
                                confirm = scanner.next().charAt(0);
                                if (Character.toUpperCase(confirm) != 'Y' && Character.toUpperCase(confirm) != 'N') {
                                    System.out.println("Invalid choice entered. Please reenter. \n");
                                    invalid = 1;
                                }
                            } while (invalid == 1);

                            if (Character.toUpperCase(confirm) == 'Y') {
                                orderItem.setQuantity(qty);
                                System.out.println("Quantity edited successfully! ");
                            }
                        }

                        if (choice != 3) {
                            do {
                                invalid = 0;

                                System.out.println("Do you want to continue editing this order? (Y = yes) ");
                                cont = scanner.next().charAt(0);
                                if (Character.toUpperCase(cont) != 'Y' && Character.toUpperCase(cont) != 'N') {
                                    System.out.println("Invalid choice entered. Please reenter. \n");
                                    invalid = 1;
                                }
                            } while (invalid == 1);
                        }
                    }
                } while (Character.toUpperCase(cont) == 'Y');

                do {
                    invalid = 0;

                    System.out.println("Would you like to edit another order? (Y = yes) ");
                    cont = scanner.next().charAt(0);
                    if (Character.toUpperCase(cont) != 'Y' && Character.toUpperCase(cont) != 'N') {
                        System.out.println("Invalid choice entered. Please reenter. \n");
                        invalid = 1;
                    }
                } while (invalid == 1);
            } while (Character.toUpperCase(cont) == 'Y');
        }
    }

    public static void viewCartOrder(Customer customer, ArrayList<Order> cartOrders) {
        int viewChoice, sortChoice = 1, sortOrder = 0, invalid, orderNo = 1, viewNextPrev;
        char sort;
        boolean foundOrder = false;
        Scanner scanner = new Scanner(System.in);
        String restaurantName;
        Order order = null;
        ListInterface<Order> orders = new DoublyLinkedList<>();
        DoublyLinkedList<Order> sortedOrders = new DoublyLinkedList<>();
        ListIterator<Order> iterator;

        for (int i = 1; i <= cartOrders.getNumberOfEntries(); i++) {
            if (cartOrders.getEntry(i).getCustomer().equals(customer)) {
                orders.add(cartOrders.getEntry(i));
            }
        }

        if (orders.isEmpty()) {
            System.out.println("You do not have any orders in your cart! ");
        } else {
            do {
                invalid = 0;

                System.out.println("=================");
                System.out.println(" VIEW CART ORDER ");
                System.out.println("=================");
                System.out.println("1. View specific order");
                System.out.println("2. View summary of all orders");
                System.out.println("3. Return to previous menu");
                System.out.println("Please enter your choice: ");
                viewChoice = scanner.nextInt();

                if (viewChoice < 1 || viewChoice > 3) {
                    System.out.println("Invalid choice entered. Please reenter. ");
                    invalid = 1;
                }
            } while (invalid == 1);

            if (viewChoice == 1) {
                do {
                    invalid = 0;

                    System.out.println("Please enter the restaurant of the order you would like to view: ");
                    restaurantName = scanner.next();

                    if (restaurantName.equals("") == false) {
                        Restaurant tempRestaurant = new Restaurant(restaurantName);
                        Item tempItem = new Item(tempRestaurant);
                        OrderItem tempOrderItem = new OrderItem(tempItem);
                        ArrayList<OrderItem> tempOrderItemList = new ArrayList<>();
                        tempOrderItemList.add(tempOrderItem);
                        Order tempOrder = new Order(tempOrderItemList);

                        if (orders.contains(tempOrder) == false) {
                            System.out.println("Restaurant not found! Please reenter. ");
                            invalid = 1;
                        } else {
                            foundOrder = true;
                            int position = orders.positionOf(tempOrder);

                            if (position == 1) {
                                order = orders.getFirst();
                            } else if (position == orders.size()) {
                                order = orders.getLast();
                            } else {
                                order = orders.get(position);
                            }
                        }
                    }
                } while (invalid == 1);

                if (foundOrder == true) {
                    do {
                        System.out.println("Order Details: ");
                        System.out.println("===============");
                        System.out.println(order.toString());

                        do {
                            invalid = 0;

                            System.out.println("0 - Exit, 1 - View Next Order, 2 - View Previous Order");
                            System.out.println("Do you want to continue viewing orders? ");
                            viewNextPrev = scanner.nextInt();

                            if (viewNextPrev < 0 || viewNextPrev > 2) {
                                System.out.println("Invalid choice entered. Please reenter. ");
                                invalid = 1;
                            }
                        } while (invalid == 1);

                        if (viewNextPrev != 0) {
                            iterator = orders.getListIterator(orders.positionOf(order));

                            if (viewNextPrev == 1) {
                                if (iterator.hasNext()) {
                                    order = iterator.next();
                                }
                            } else {
                                if (iterator.hasPrevious()) {
                                    order = iterator.previous();
                                }
                            }
                        }
                    } while (viewNextPrev != 0);
                } else {
                    System.out.println("Sorry, order not found! ");
                }
            } else if (viewChoice == 2) {
                do {
                    invalid = 0;

                    System.out.println("Would you like to apply any sorting? (Y = yes) ");
                    sort = scanner.next().charAt(0);

                    if (Character.toUpperCase(sort) != 'Y' && Character.toUpperCase(sort) != 'N') {
                        System.out.println("Invalid character entered. Please reenter. ");
                        invalid = 1;
                    }
                } while (invalid == 1);

                if (Character.toUpperCase(sort) == 'Y') {
                    do {
                        invalid = 0;

                        System.out.println("Sort By: ");
                        System.out.println("=========");
                        System.out.println("1. Date added");
                        System.out.println("2. Alphabetical order");
                        System.out.println("3. Order subtotal");
                        System.out.println("Please enter your choice: ");
                        sortChoice = scanner.nextInt();

                        if (sortChoice < 1 || sortChoice > 3) {
                            System.out.println("Invalid choice entered. Please reenter. ");
                            invalid = 1;
                        }
                    } while (invalid == 1);

                    do {
                        invalid = 0;

                        System.out.println("0 - Ascending, 1 - Descending");
                        System.out.println("Please select the sort order: ");
                        sortOrder = scanner.nextInt();

                        if (sortOrder < 0 || sortOrder > 1) {
                            System.out.println("Invalid choice entered. Please reenter. ");
                            invalid = 1;
                        }
                    } while (invalid == 1);
                }

                if (sortChoice != 1) {
                    orders.copy(sortedOrders);

                    if (sortChoice == 2) {
                        sortedOrders.sort(sortedOrders.get(1).getAlphabeticalComparator());
                    } else {
                        sortedOrders.sort(sortedOrders.get(1).getSubtotalComparator());
                    }

                    if (sortOrder == 0) {
                        iterator = sortedOrders.getListIterator();
                    } else {
                        iterator = sortedOrders.getListIterator(sortedOrders.size());
                    }
                } else {
                    if (sortOrder == 0) {
                        iterator = orders.getListIterator();
                    } else {
                        iterator = orders.getListIterator(orders.size());
                    }
                }

                if (sortOrder == 0) {
                    while (iterator.hasNext()) {
                        Order nextOrder = iterator.next();

                        System.out.printf("Order #%02d: \n", orderNo);
                        System.out.println("===========");
                        System.out.println("Restaurant: " + nextOrder.getOrderItems().getEntry(1).getItem().getRestaurant().getName());
                        System.out.println("Number of items: " + nextOrder.getOrderItems().getNumberOfEntries());
                        System.out.printf("Subtotal (RM): %.2f \n", nextOrder.calGrandTotal());
                        System.out.println("");
                    }
                } else {
                    while (iterator.hasPrevious()) {
                        Order previousOrder = iterator.previous();

                        System.out.printf("Order #%02d: \n", orderNo);
                        System.out.println("===========");
                        System.out.println("Restaurant: " + previousOrder.getOrderItems().getEntry(1).getItem().getRestaurant().getName());
                        System.out.println("Number of items: " + previousOrder.getOrderItems().getNumberOfEntries());
                        System.out.printf("Subtotal (RM): %.2f \n", previousOrder.calGrandTotal());
                        System.out.println("");
                    }
                }
            }
        }
    }

    public static void deleteCartOrder(Customer customer, ArrayList<Order> cartOrders) {
        int choice, invalid;
        char confirm;
        boolean foundOrder = false;
        Scanner scanner = new Scanner(System.in);
        String restaurantName;
        Order order = null;
        ListInterface<Order> orders = new DoublyLinkedList<>();

        for (int i = 1; i <= cartOrders.getNumberOfEntries(); i++) {
            if (cartOrders.getEntry(i).getCustomer().equals(customer)) {
                orders.add(cartOrders.getEntry(i));
            }
        }

        if (orders.isEmpty()) {
            System.out.println("You do not have any orders in your cart! ");
        } else {
            do {
                do {
                    invalid = 0;

                    System.out.println("===================");
                    System.out.println(" REMOVE CART ORDER ");
                    System.out.println("===================");
                    System.out.println("1. Remove specific order");
                    System.out.println("2. Clear cart");
                    System.out.println("3. Return to previous menu");
                    System.out.println("Please enter your choice: ");
                    choice = scanner.nextInt();

                    if (choice < 1 || choice > 3) {
                        System.out.println("Invalid choice entered. Please reenter. ");
                        invalid = 1;
                    }
                } while (invalid == 1);

                if (choice == 1) {
                    do {
                        invalid = 0;

                        System.out.println("Please enter the restaurant of the order you would like to delete: ");
                        restaurantName = scanner.next();

                        if (restaurantName.equals("") == false) {
                            Restaurant tempRestaurant = new Restaurant(restaurantName);
                            Item tempItem = new Item(tempRestaurant);
                            OrderItem tempOrderItem = new OrderItem(tempItem);
                            ArrayList<OrderItem> tempOrderItemList = new ArrayList<>();
                            tempOrderItemList.add(tempOrderItem);
                            Order tempOrder = new Order(tempOrderItemList);

                            if (orders.contains(tempOrder) == false) {
                                System.out.println("Restaurant not found! Please reenter. ");
                                invalid = 1;
                            } else {
                                foundOrder = true;
                                int position = orders.positionOf(tempOrder);

                                if (position == 1) {
                                    order = orders.getFirst();
                                } else if (position == orders.size()) {
                                    order = orders.getLast();
                                } else {
                                    order = orders.get(position);
                                }
                            }
                        }
                    } while (invalid == 1);

                    if (foundOrder == true) {
                        System.out.println("Order Details: ");
                        System.out.println("===============");
                        System.out.println(order.toString());

                        do {
                            invalid = 0;

                            System.out.println("Are you sure you want to delete this order? (Y = yes) ");
                            confirm = scanner.next().charAt(0);

                            if (Character.toUpperCase(confirm) != 'Y' && Character.toUpperCase(confirm) != 'N') {
                                System.out.println("Invalid character entered. Please reenter. ");
                                invalid = 1;
                            }
                        } while (invalid == 1);

                        if (Character.toUpperCase(confirm) == 'Y') {
                            int position = orders.positionOf(order);

                            if (position == 1) {
                                order = orders.removeFirst();
                            } else if (position == orders.size()) {
                                order = orders.removeLast();
                            } else {
                                order = orders.remove(position);
                            }
                            cartOrders.remove(cartOrders.getPositionOf(order));

                            System.out.printf("Order of restaurant %s deleted successfully! ", order.getOrderItems().getEntry(0).getItem().getRestaurant().getName());
                        }
                    } else {
                        System.out.println("Sorry, order not found! ");
                    }
                } else if (choice == 2) {
                    do {
                        invalid = 0;

                        System.out.println("Are you sure you want to clear your cart? (Y = yes) ");
                        confirm = scanner.next().charAt(0);

                        if (Character.toUpperCase(confirm) != 'Y' && Character.toUpperCase(confirm) != 'N') {
                            System.out.println("Invalid character entered. Please reenter. ");
                            invalid = 1;
                        }
                    } while (invalid == 1);

                    if (Character.toUpperCase(confirm) == 'Y') {
                        for (int i = 1; i <= orders.size(); i++) {
                            for (int j = 1; j <= cartOrders.getNumberOfEntries(); j++) {
                                if (orders.get(i).equals(cartOrders.getEntry(j))) {
                                    cartOrders.remove(j);
                                    break;
                                }
                            }
                        }

                        orders.clear();

                        System.out.println("Cart cleared successfully! ");
                    }
                }
            } while (choice != 3);
        }
    }

    public static void payCartOrder(Customer customer, ArrayList<Order> cartOrders, ArrayList<PaidOrder> paidOrders) {
        int invalid, addChoice, paymentChoice;
        char confirm, cont;
        boolean foundOrder = false, orderValid = true;
        Scanner scanner = new Scanner(System.in);
        String restaurantName;
        Address address;
        PaymentMethod paymentMethod;
        Order order = null;
        PaidOrder paidOrder;
        ListInterface<Order> orders = new DoublyLinkedList<>();

        for (int i = 1; i <= cartOrders.getNumberOfEntries(); i++) {
            if (cartOrders.getEntry(i).getCustomer().equals(customer)) {
                orders.add(cartOrders.getEntry(i));
            }
        }

        if (orders.isEmpty()) {
            System.out.println("You do not have any orders in your cart! ");
        } else {
            do {
                do {
                    invalid = 0;

                    System.out.println("Please enter the restaurant of the order you would like to pay for: ");
                    restaurantName = scanner.next();

                    if (restaurantName.equals("") == false) {
                        Restaurant tempRestaurant = new Restaurant(restaurantName);
                        Item tempItem = new Item(tempRestaurant);
                        OrderItem tempOrderItem = new OrderItem(tempItem);
                        ArrayList<OrderItem> tempOrderItemList = new ArrayList<>();
                        tempOrderItemList.add(tempOrderItem);
                        Order tempOrder = new Order(tempOrderItemList);

                        if (orders.contains(tempOrder) == false) {
                            System.out.println("Restaurant not found! Please reenter. ");
                            invalid = 1;
                        } else {
                            foundOrder = true;
                            int position = orders.positionOf(tempOrder);

                            if (position == 1) {
                                order = orders.getFirst();
                            } else if (position == orders.size()) {
                                order = orders.getLast();
                            } else {
                                order = orders.get(position);
                            }
                        }
                    }
                } while (invalid == 1);

                if (foundOrder == true) {
                    Restaurant orderRestaurant = order.getOrderItems().getEntry(0).getItem().getRestaurant();
                    Address restaurantAddress = orderRestaurant.getAddress().getEntry(0);

                    if (LocalTime.now().isBefore(orderRestaurant.getOpeningHour()) || LocalTime.now().isAfter(orderRestaurant.getClosingHour())) {
                        System.out.println("Sorry, the restaurant is closed. Please try again later. ");
                        orderValid = false;
                    } else {
                        for (int i = 1; i <= order.getOrderItems().getNumberOfEntries(); i++) {
                            if (order.getOrderItems().getEntry(i).getItem().getAvailability() == 'N') {
                                System.out.println("Sorry, " + order.getOrderItems().getEntry(i).getItem().getName()
                                        + " is unavailable at the moment. Please try again later or edit your cart before proceeding. ");
                                orderValid = false;
                            }
                        }
                    }

                    if (orderValid == true) {
                        System.out.println(order.toString());

                        do {
                            invalid = 0;

                            System.out.println("Addresses: ");
                            for (int i = 1; i <= customer.getAddress().getNumberOfEntries(); i++) {
                                System.out.println((i + 1) + ". " + customer.getAddress().getEntry(i));
                            }
                            System.out.println(customer.getAddress().getNumberOfEntries() + ". Enter new address");

                            do {
                                System.out.println("Please select a delivery address: ");
                                addChoice = scanner.nextInt();

                                if (addChoice < 1 || addChoice > customer.getAddress().getNumberOfEntries()) {
                                    System.out.println("Invalid choice entered. Please reenter. ");
                                    invalid = 1;
                                }
                            } while (invalid == 1);

                            if (addChoice == customer.getAddress().getNumberOfEntries()) {
                                String addressLine, district, postcode, state;

                                System.out.println("New address: ");
                                do {
                                    invalid = 0;

                                    System.out.println("Please enter the address line: ");
                                    addressLine = scanner.nextLine();

                                    if (addressLine.equals("")) {
                                        System.out.println("Invalid address line entered. Please reenter. ");
                                        invalid = 1;
                                    }
                                } while (invalid == 1);

                                do {
                                    invalid = 0;

                                    System.out.println("Please enter the district: ");
                                    district = scanner.nextLine();

                                    if (district.equals("")) {
                                        System.out.println("Invalid district entered. Please reenter. ");
                                        invalid = 1;
                                    }
                                } while (invalid == 1);

                                do {
                                    invalid = 0;

                                    System.out.println("Please enter the postcode: ");
                                    postcode = scanner.nextLine();

                                    if (postcode.equals("") || postcode.length() != 5) {
                                        System.out.println("Invalid postcode entered. Please reenter. ");
                                        invalid = 1;
                                    }
                                } while (invalid == 1);

                                do {
                                    invalid = 0;

                                    System.out.println("Please enter the state: ");
                                    state = scanner.nextLine();

                                    if (postcode.equals("")) {
                                        System.out.println("Invalid state entered. Please reenter. ");
                                        invalid = 1;
                                    }
                                } while (invalid == 1);

                                address = new Address(addressLine, district, postcode, state);
                            } else {
                                address = customer.getAddress().getEntry(addChoice);
                            }

                            System.out.println("Selected Address: ");
                            System.out.println(address.toString());

                            do {
                                invalid = 0;

                                System.out.println("Confirm delivery address? (Y = yes) ");
                                confirm = scanner.next().charAt(0);

                                if (Character.toUpperCase(confirm) != 'Y' && Character.toUpperCase(confirm) != 'N') {
                                    System.out.println("Invalid character entered. Please reenter. ");
                                    invalid = 1;
                                }
                            } while (invalid == 1);

                            if (Character.toUpperCase(confirm) == 'Y') {
                                if (addChoice == customer.getAddress().getNumberOfEntries()) {
                                    customer.getAddress().add(address);
                                }

                                if (!address.getState().equals(restaurantAddress.getState())) {
                                    System.out.println("Invalid address selected. Please select an address that is in the same state as the restaurant. ");
                                    invalid = 1;
                                }
                            } else {
                                invalid = 1;
                            }
                        } while (invalid == 1);

                        do {
                            System.out.println("Payment Methods: ");
                            for (int i = 1; i <= customer.getPaymentMethodList().getNumberOfEntries(); i++) {
                                System.out.println((i + 1) + ". " + customer.getPaymentMethodList().getEntry(i));
                            }
                            System.out.println(customer.getPaymentMethodList().getNumberOfEntries() + ". Enter new payment method");

                            do {
                                System.out.println("Please select a payment method: ");
                                paymentChoice = scanner.nextInt();

                                if (paymentChoice < 1 || paymentChoice > customer.getPaymentMethodList().getNumberOfEntries()) {
                                    System.out.println("Invalid choice entered. Please reenter. ");
                                    invalid = 1;
                                }
                            } while (invalid == 1);

                            if (paymentChoice == customer.getPaymentMethodList().getNumberOfEntries()) {
                                String creditCardNo, cvv;
                                int expiryMonth, expiryYear;
                                YearMonth expiryDate = null;

                                System.out.println("New Payment Method: ");
                                System.out.println("Payment Type: Credit Card");

                                do {
                                    invalid = 0;

                                    System.out.println("Please enter your credit card number: ");
                                    creditCardNo = scanner.next();

                                    if (creditCardNo.length() != 16 && creditCardNo.matches("[0-9]+") == false) {
                                        System.out.println("Invalid credit card number entered. Please reenter. ");
                                        invalid = 1;
                                    }
                                } while (invalid == 1);

                                do {
                                    System.out.println("Please enter your cvv: ");
                                    cvv = scanner.next();

                                    if (cvv.length() != 3 && cvv.matches("[0-9]+") == false) {
                                        System.out.println("Invalid cvv entered. Please reenter. ");
                                        invalid = 1;
                                    }
                                } while (invalid == 1);

                                do {
                                    invalid = 0;

                                    System.out.println("Please enter your credit card expiry date: ");
                                    System.out.println("Year: ");
                                    expiryYear = scanner.nextInt();
                                    System.out.println("Month: ");
                                    expiryMonth = scanner.nextInt();

                                    if (String.valueOf(expiryYear).length() != 4 || expiryMonth < 1 || expiryMonth > 12) {
                                        System.out.println("Invalid expiry date entered. Please reenter. ");
                                        invalid = 1;
                                    } else {
                                        expiryDate = YearMonth.of(expiryYear, expiryMonth);
                                    }
                                } while (invalid == 1);

                                paymentMethod = new CreditCard(creditCardNo, expiryDate, cvv);
                            } else {
                                paymentMethod = customer.getPaymentMethodList().getEntry(paymentChoice);
                            }

                            System.out.println("Selected Payment Method: ");
                            System.out.println(paymentMethod.toString());

                            do {
                                invalid = 0;

                                System.out.println("Confirm payment method? (Y = yes) ");
                                confirm = scanner.next().charAt(0);

                                if (Character.toUpperCase(confirm) != 'Y' && Character.toUpperCase(confirm) != 'N') {
                                    System.out.println("Invalid character entered. Please reenter. ");
                                    invalid = 1;
                                }
                            } while (invalid == 1);

                            if (Character.toUpperCase(confirm) == 'Y') {
                                if (paymentChoice == customer.getPaymentMethodList().getNumberOfEntries()) {
                                    customer.getPaymentMethodList().add(paymentMethod);
                                }

                                if (paymentMethod.getMethodType().equals("Credit Card")) {
                                    CreditCard creditCard = (CreditCard) paymentMethod;

                                    if (YearMonth.now().isAfter(creditCard.getExpiryDate())) {
                                        System.out.println("Credit card is expired. Please select another payment method. ");
                                        invalid = 1;
                                    }
                                }
                            } else {
                                invalid = 1;
                            }
                        } while (invalid == 1);

                        paidOrder = new PaidOrder(order, address, paymentMethod);
                        System.out.println("Order Details: ");
                        System.out.println("===============");
                        System.out.println(order.toString());
                        System.out.println("Grand Total (RM): " + paidOrder.calGrandTotal());
                        System.out.println("Delivery Details: ");
                        System.out.println("==================");
                        System.out.println("Restaurant Address (From): ");
                        System.out.println(restaurantAddress.toString());
                        System.out.println("Customer Address (To): ");
                        System.out.println(address.toString());
                        System.out.println("Payment Details: ");
                        System.out.println(paymentMethod.toString());

                        do {
                            invalid = 0;

                            System.out.println("Confirm place order? (Y = yes) ");
                            confirm = scanner.next().charAt(0);

                            if (Character.toUpperCase(confirm) != 'Y' && Character.toUpperCase(confirm) != 'N') {
                                System.out.println("Invalid character entered. Please reenter. ");
                                invalid = 1;
                            }
                        } while (invalid == 1);

                        if (Character.toUpperCase(confirm) == 'Y') {
                            if (paymentMethod.getMethodType().equals("Ewallet")) {
                                Ewallet ewallet = (Ewallet) paymentMethod;

                                ewallet.deductBalance(paidOrder.calGrandTotal());
                            }

                            paidOrder.setOrderId(paidOrders.getNumberOfEntries());
                            paidOrder.setOrderDateTime(LocalDateTime.now());
                            paidOrder.setOrderStatus("Order Accepted");

                            paidOrders.add(paidOrder);

                            System.out.println("Order Details: ");
                            System.out.println("===============");
                            System.out.println(paidOrder.toString());
                        }
                    }
                } else {
                    System.out.println("Sorry, order not found! ");
                }

                do {
                    invalid = 0;

                    System.out.println("Do you want to pay for another order? (Y = yes) ");
                    cont = scanner.next().charAt(0);

                    if (Character.toUpperCase(cont) != 'Y' && Character.toUpperCase(cont) != 'N') {
                        System.out.println("Invalid character entered. Please reenter. ");
                        invalid = 1;
                    }
                } while (invalid == 1);
            } while (Character.toUpperCase(cont) == 'Y');
        }
    }
}
