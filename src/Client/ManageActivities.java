package Client;

import ADT.Cart_Activities.*;
import ADT.Customer.ArrayList;
import Entity.*;

import java.util.Scanner;
import java.util.Iterator;

public class ManageActivities {

    public static void manageActivitiesMenu(Customer customer, ArrayList<PaidOrder> paidOrders) {
        int menuChoice, invalid, numCancelledOrders = 0, position = 1;
        Scanner scanner = new Scanner(System.in);
        StackInterface<PaidOrder> cancelledOrders = new LinkedStack<>();

        for (int i = 1; i <= paidOrders.getNumberOfEntries(); i++) {
            if (paidOrders.getEntry(i).getOrder().getCustomer().equals(customer) && paidOrders.getEntry(i).getOrderStatus().equals("Cancelled by Restaurant")) {
                cancelledOrders.push(paidOrders.getEntry(i));
                numCancelledOrders++;
            }
        }

        if (!cancelledOrders.isEmpty()) {
            System.out.printf("You have %d orders cancelled by the restaurant(s). ", numCancelledOrders);

            while (!cancelledOrders.isEmpty()) {
                PaidOrder cancelledOrder = cancelledOrders.pop();
                System.out.println("Cancelled Order #" + position);
                System.out.println("Order Details: ");
                System.out.println("===============");
                System.out.println(cancelledOrder.toString());
                cancelledOrder.setOrderStatus("Cancelled");
                position++;
            }
        }

        do {
            invalid = 0;

            System.out.println("========================");
            System.out.println(" MANAGE ACTIVITIES MENU ");
            System.out.println("========================");
            System.out.println("1. View Active Orders");
            System.out.println("2. Cancel Order");
            System.out.println("3. View Order History");
            System.out.println("4. Clear Order History");
            System.out.println("5. Return to Main Menu");
            System.out.println("Please enter your choice: ");
            menuChoice = scanner.nextInt();

            if (menuChoice < 1 || menuChoice > 5) {
                System.out.println("Invalid choice entered. Please reenter. ");
                invalid = 1;
            } else {
                if (menuChoice == 1) {
                    viewActiveOrders(customer, paidOrders);
                } else if (menuChoice == 2) {
                    cancelOrder(customer, paidOrders);
                } else if (menuChoice == 3) {
                    viewOrderHistory(customer, paidOrders);
                } else if (menuChoice == 4) {
                    clearOrderHistory(customer, paidOrders);
                }
            }
        } while (invalid == 1);
    }

    public static void viewActiveOrders(Customer customer, ArrayList<PaidOrder> paidOrders) {
        int choice, invalid, orderNo = 1;
        Scanner scanner = new Scanner(System.in);
        String orderId;
        PaidOrder paidOrder;
        QueueInterface<PaidOrder> activeOrders = new ArrayQueueDynamicFront<>();
        Iterator<PaidOrder> iterator;

        for (int i = 1; i <= paidOrders.getNumberOfEntries(); i++) {
            if (paidOrders.getEntry(i).getOrder().getCustomer().equals(customer)) {
                if (!paidOrders.getEntry(i).getOrderStatus().equals("Delivered") && !paidOrders.getEntry(i).getOrderStatus().equals("Cancelled")) {
                    activeOrders.enqueue(paidOrders.getEntry(i));
                }
            }
        }

        if (activeOrders.isEmpty()) {
            System.out.println("You do not have any active orders! ");
        } else {
            do {
                do {
                    invalid = 0;

                    System.out.println("====================");
                    System.out.println(" VIEW ACTIVE ORDERS ");
                    System.out.println("====================");
                    System.out.println("1. View specific order");
                    System.out.println("2. View Summary of all orders");
                    System.out.println("3. Return to Main Menu");
                    System.out.println("Please enter your choice: ");
                    choice = scanner.nextInt();

                    if (choice < 1 || choice > 3) {
                        System.out.println("Invalid choice entered. Please reenter. ");
                        invalid = 1;
                    }
                } while (invalid == 1);

                if (choice == 1) {
                    System.out.println("Please enter the order ID: ");
                    orderId = scanner.next();

                    if (activeOrders.contains(new PaidOrder(orderId))) {
                        PaidOrder tempOrder = new PaidOrder(orderId);

                        paidOrder = activeOrders.element(activeOrders.positionOf(tempOrder));

                        System.out.println("Order Details: ");
                        System.out.println("===============");
                        System.out.println(paidOrder.toString());
                    } else {
                        System.out.println("Sorry, order not found! ");
                    }
                } else if (choice == 2) {
                    iterator = activeOrders.getIterator();

                    while (iterator.hasNext()) {
                        PaidOrder nextOrder = iterator.next();

                        System.out.printf("Order #%02d: \n", orderNo);
                        System.out.println("===========");
                        System.out.println("Order ID: " + nextOrder.getOrderId());
                        System.out.println("Order Status: " + nextOrder.getOrderStatus());
                        System.out.println("Restaurant: " + nextOrder.getOrder().getOrderItems().getEntry(0).getItem().getRestaurant().getName());
                        System.out.println("Number of items: " + nextOrder.getOrder().getOrderItems().getNumberOfEntries());
                        System.out.printf("Grand Total (RM): %.2f \n", nextOrder.calGrandTotal());
                        System.out.println("");
                    }
                }
            } while (choice != 3);
        }
    }

    public static void cancelOrder(Customer customer, ArrayList<PaidOrder> paidOrders) {
        int invalid;
        char confirm, cont;
        Scanner scanner = new Scanner(System.in);
        String orderId;
        PaidOrder paidOrder;
        QueueInterface<PaidOrder> activeOrders = new ArrayQueueDynamicFront<>();

        for (int i = 1; i <= paidOrders.getNumberOfEntries(); i++) {
            if (paidOrders.getEntry(i).getOrder().getCustomer().equals(customer)) {
                if (!paidOrders.getEntry(i).getOrderStatus().equals("Delivered") && !paidOrders.getEntry(i).getOrderStatus().equals("Cancelled")) {
                    activeOrders.enqueue(paidOrders.getEntry(i));
                }
            }
        }

        if (activeOrders.isEmpty()) {
            System.out.println("You do not have any active orders! ");
        } else {
            do {
                System.out.println("Please enter the order ID: ");
                orderId = scanner.next();

                PaidOrder tempOrder = new PaidOrder(orderId);

                if (activeOrders.contains(tempOrder)) {
                    paidOrder = activeOrders.element(activeOrders.positionOf(tempOrder));

                    if (paidOrder.getOrderStatus().equals("Order Accepted")) {
                        System.out.println("Order is already being processed, unable to cancel order. ");
                    } else {
                        System.out.println("Order Details: ");
                        System.out.println("===============");
                        System.out.println(paidOrder.toString());

                        do {
                            invalid = 0;

                            System.out.println("Are you sure you want to cancel this order? [Y/N] ");
                            confirm = scanner.next().charAt(0);

                            if (Character.toUpperCase(confirm) != 'Y' && Character.toUpperCase(confirm) != 'N') {
                                System.out.println("Invalid character entered. Please reenter. ");
                                invalid = 1;
                            }
                        } while (invalid == 1);

                        if (Character.toUpperCase(confirm) == 'Y') {
                            paidOrders.getEntry(paidOrders.getPositionOf(paidOrder)).setOrderStatus("Cancelled");
                            activeOrders.dequeue(activeOrders.positionOf(paidOrder));

                            System.out.println("Order cancelled successfully! ");
                        }
                    }
                } else {
                    System.out.println("Sorry, order not found! ");
                }

                do {
                    invalid = 0;

                    System.out.println("Do you want to continue cancelling orders? [Y/N] ");
                    cont = scanner.next().charAt(0);

                    if (Character.toUpperCase(cont) != 'Y' && Character.toUpperCase(cont) != 'N') {
                        System.out.println("Invalid character entered. Please reenter. ");
                        invalid = 1;
                    }
                } while (invalid == 1);
            } while (Character.toUpperCase(cont) == 'Y');
        }
    }

    public static void viewOrderHistory(Customer customer, ArrayList<PaidOrder> paidOrders) {
        int choice, invalid, orderNo = 1;
        Scanner scanner = new Scanner(System.in);
        String orderId;
        PaidOrder paidOrder;
        StackInterface<PaidOrder> orderHistory = new LinkedStack<>();
        Iterator<PaidOrder> iterator;

        for (int i = 1; i <= paidOrders.getNumberOfEntries(); i++) {
            if (paidOrders.getEntry(i).getOrder().getCustomer().equals(customer) && paidOrders.getEntry(i).getOrderStatus().equals("Delivered")) {
                orderHistory.push(paidOrders.getEntry(i));
            }
        }

        if (orderHistory.isEmpty()) {
            System.out.println("You do not have any order history! ");
        } else {
            do {
                do {
                    invalid = 0;

                    System.out.println("====================");
                    System.out.println(" VIEW ORDER HISTORY ");
                    System.out.println("====================");
                    System.out.println("1. View specific order");
                    System.out.println("2. View Summary of all orders");
                    System.out.println("3. Return to Main Menu");
                    System.out.println("Please enter your choice: ");
                    choice = scanner.nextInt();

                    if (choice < 1 || choice > 3) {
                        System.out.println("Invalid choice entered. Please reenter. ");
                        invalid = 1;
                    }
                } while (invalid == 1);

                if (choice == 1) {
                    System.out.println("Please enter the order ID: ");
                    orderId = scanner.next();

                    PaidOrder tempOrder = new PaidOrder(orderId);

                    if (orderHistory.contains(tempOrder)) {
                        paidOrder = orderHistory.peek(orderHistory.search(tempOrder));

                        System.out.println("Order Details: ");
                        System.out.println("===============");
                        System.out.println(paidOrder.toString());
                    } else {
                        System.out.println("Sorry, order not found! ");
                    }
                } else if (choice == 2) {
                    iterator = orderHistory.getIterator();

                    while (iterator.hasNext()) {
                        PaidOrder nextOrder = iterator.next();

                        System.out.printf("Order #%02d: \n", orderNo);
                        System.out.println("===========");
                        System.out.println("Order ID: " + nextOrder.getOrderId());
                        System.out.println("Restaurant: " + nextOrder.getOrder().getOrderItems().getEntry(0).getItem().getRestaurant().getName());
                        System.out.println("Number of items: " + nextOrder.getOrder().getOrderItems().getNumberOfEntries());
                        System.out.printf("Grand Total (RM): %.2f \n", nextOrder.calGrandTotal());
                        System.out.println("");
                    }
                }
            } while (choice != 3);
        }
    }

    public static void clearOrderHistory(Customer customer, ArrayList<PaidOrder> paidOrders) {
        int invalid, choice;
        char confirm;
        Scanner scanner = new Scanner(System.in);
        String orderId;
        PaidOrder paidOrder, removedOrder;
        StackInterface<PaidOrder> orderHistory = new LinkedStack<>();
        Iterator<PaidOrder> iterator;

        for (int i = 1; i <= paidOrders.getNumberOfEntries(); i++) {
            if (paidOrders.getEntry(i).getOrder().getCustomer().equals(customer) && paidOrders.getEntry(i).getOrderStatus().equals("Delivered")) {
                orderHistory.push(paidOrders.getEntry(i));
            }
        }

        if (orderHistory.isEmpty()) {
            System.out.println("You do not have any order history! ");
        } else {
            do {
                invalid = 0;

                System.out.println("=====================");
                System.out.println(" CLEAR ORDER HISTORY ");
                System.out.println("=====================");
                System.out.println("1. Delete specific order");
                System.out.println("2. Clear entire order history");
                System.out.println("3. Return to Main Menu");
                System.out.println("Please enter your choice: ");
                choice = scanner.nextInt();

                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid choice entered. Please reenter. ");
                    invalid = 1;
                }
            } while (invalid == 1);

            if (choice == 1) {
                System.out.println("Please enter the order ID: ");
                orderId = scanner.next();

                PaidOrder tempOrder = new PaidOrder(orderId);

                if (orderHistory.contains(tempOrder)) {
                    paidOrder = orderHistory.peek(orderHistory.search(tempOrder));

                    System.out.println("Order Details: ");
                    System.out.println("===============");
                    System.out.println(paidOrder.toString());

                    do {
                        invalid = 0;

                        System.out.println("Are you sure you want to delete this order from your order history? [Y/N] ");
                        confirm = scanner.next().charAt(0);

                        if (Character.toUpperCase(confirm) != 'Y' && Character.toUpperCase(confirm) != 'N') {
                            System.out.println("Invalid character entered. Please reenter. ");
                            invalid = 1;
                        }
                    } while (invalid == 1);

                    if (Character.toUpperCase(confirm) == 'Y') {
                        paidOrders.remove(paidOrders.getPositionOf(paidOrder));
                        removedOrder = orderHistory.pop(orderHistory.search(paidOrder));

                        System.out.printf("Order %s deleted successfully! \n", removedOrder.getOrderId());
                    }
                } else {
                    System.out.println("Sorry, order not found! ");
                }
            } else if (choice == 2) {
                iterator = orderHistory.getIterator();

                while (iterator.hasNext()) {
                    paidOrders.remove(iterator.next());
                }

                orderHistory.clear();

                System.out.println("Order history cleared successfully! ");
            }
        }
    }
}
