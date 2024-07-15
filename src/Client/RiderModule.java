package Client;

import ADT.Rider.*;
import ADT.Customer.ArrayList;
import Entity.*;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RiderModule {
    static Scanner scanner = new Scanner(System.in);

    //===================================================Validation Part===================================================
    //This method is used for user input choice reg validation (For example, menu only can be chosen from 1-7, if userInputPosition=8, will return false)
    public static boolean noValidation(int fromPosition, int toPosition, String userInputPosition) {
        String regCheck = "^([" + fromPosition + "-" + toPosition + "])$";
        return true ? userInputPosition.matches(regCheck) : true;
    }

    //Date validation
    public static boolean dateValidation(String shiftDate) {
        //2022-mm-dd
        return shiftDate.matches("([2][0][2][2])-([0][1-9]|[1][0-2])-([0][1-9]|[12][0-9]|3[01])");
    }

    //Rider login part
    public static Rider riderLogin(ArrayList<Rider> riderArrayList) {
        String riderID;
        String password;
        boolean found=false;
        System.out.println("\n\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-_-      Welcome To Rider Login Module        _-_-_-_-_-_-_-_-_-_-");
        System.out.println("\t\t\t\t=====================================================================================");
        System.out.print("\t\t\t\t                            Enter your Rider ID        >> ");
        riderID=scanner.nextLine();
        System.out.print("\t\t\t\t                            Enter your Rider Password  >> ");
        password=scanner.nextLine();

        //Validate ID and password
        for(int i=1;i<=riderArrayList.getNumberOfEntries();i++){
            if(riderArrayList.getEntry(i).getUserId().equals(riderID)){
                if(riderArrayList.getEntry(i).getPassword().equals(password)){
                    System.out.print("\n\t\t\t\t                             = [ Successfully login ! ] =\n\n");
                    found=true;
                    return riderArrayList.getEntry(i);
                }
            }
        }
        if(found){
            System.out.print("\n\t\t\t\t                             = [ Unsuccessfully login, please try again ! ] =");
        }
        return null;//Return null if not found
    }

    //Rider menu part
    public static void riderOrderMenu(ArrayList<Rider> riderArrayList, Rider rider, ArrayList<PaidOrder> paidOrderArrayList) {
        riderHeader("YOUR ORDER , WE DELIVER !");
        String menuChoice;//rider menu choice
        int riderChoice;
        StackInterface<PaidOrder> paidOrderHistory = new ArrayStack<PaidOrder>();
        QueueInterface<PaidOrder> riderOrderQueue = new DoubleLinkedQueue<PaidOrder>();
        do {
            System.out.println("\n\t\t\t\t=====================================================================================");
            System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-_-   Welcome To Rider Manage Order Module    _-_-_-_-_-_-_-_-_-_-");
            System.out.println("\t\t\t\t=====================================================================================");
            System.out.println("\t\t\t\t                                    SHIFT                     ");
            System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 1 ] View available shifts             .'.'.''.'..'.'.'.'.'");
            System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 2 ] Book upcoming shifts              .'.'.''.'..'.'.'.'.'");
            System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 3 ] View booked shift                 .'.'.''.'..'.'.'.'.'");
            System.out.println("\t\t\t\t-------------------------------------------------------------------------------------");
            System.out.println("\t\t\t\t                                   DELIVERY                                       ");
            System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 4 ] View pending order                .'.'.''.'..'.'.'.'.'");
            System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 5 ] Search pending order              .'.'.''.'..'.'.'.'.'");
            System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 6 ] Cancel pending order              .'.'.''.'..'.'.'.'.'");
            System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 7 ] Update delivery order status      .'.'.''.'..'.'.'.'.'");
            System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 8 ] View delivery order history       .'.'.''.'..'.'.'.'.'");
            System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 9 ] Back to HomePage                  .'.'.''.'..'.'.'.'.'");
            System.out.println("\t\t\t\t-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-");
            riderPendingOrder(riderArrayList, paidOrderArrayList, riderOrderQueue, rider);
        
            do {
                System.out.printf("\t\t\t\t\t Rider (%s) , please select your choice >> ",rider.getName());
                menuChoice = scanner.nextLine().trim();
            } while (!noValidation(1, 9, menuChoice));//Match only 1 to 7;
            riderChoice = Integer.parseInt(menuChoice.trim());
            switch (riderChoice) {
                case 1:
                    riderHeader("WEEKLY AVAILABLE SHIFT");
                    ShiftModule.thisWeekAvailableShift(riderArrayList, rider);
                    break;
                case 2:
                    riderHeader("ADD NEW SHIFT");
                    ShiftModule.addNewShift(riderArrayList, rider);
                    break;
                case 3:
                    riderHeader("VIEW BOOKED SHIFT");
                    ShiftModule.viewBookedShift(rider);
                    break;
                case 4:
                    riderHeader("VIEW PENDING ORDER");
                    viewPendingOrder(riderOrderQueue, rider);
                    break;
                case 5:
                    riderHeader("SEARCH PENDING ORDER");
                    searchPendingOrder(paidOrderArrayList, riderOrderQueue, rider);
                    break;
                case 6:
                    riderHeader("CANCEL PENDING ORDER");
                    cancelPendingOrder(paidOrderArrayList, riderOrderQueue, rider, riderArrayList);
                    break;
                case 7:
                    riderHeader("UPDATE ORDER STATUS");
                    updateOrderStatus(paidOrderArrayList, riderOrderQueue, rider, paidOrderHistory);
                    break;
                case 8:
                    riderHeader("DELIVERY ORDER HISTORY");
                    deliveryOrderHistory(paidOrderArrayList, paidOrderHistory, rider);
                    break;
            }
            System.out.println("\n\n\n\n\n");
        } while (riderChoice != 9);
    }

  
    //After customer place an order the system will dispatch the current order to rider based on their shift time
    public static void riderPendingOrder(ArrayList<Rider> riderArrayList, ArrayList<PaidOrder> paidOrderArrayList, QueueInterface<PaidOrder> riderOrderQueue, Rider rider) {
        //Based on the shift time(date & time & district), system will assign order to rider
        LocalDate todayDate = LocalDateTime.now().toLocalDate();
        ArrayList<Rider> riderTodayShift_Penang = ShiftModule.riderTodayShift_Penang(riderArrayList);
        ArrayList<Rider> riderTodayShift_KL = ShiftModule.riderTodayShift_KL(riderArrayList);
        //1.Validate today date
        //2.Validate whether rider's district same with the customer's state
        //3.Validate whether the current PaidOrder have rider or not(If current PaidOrder rider=null,means no rider yet)
        //4.Validate current PaidOrder's status is delivering or not
        //5.Validate the PaidOrder time is within the rider shift time
        //6.If all conditions meet,assign this Order delivery to this rider

        //Pulau Pinang slot
        for (int i = 1; i <= riderTodayShift_Penang.getNumberOfEntries(); i++) {
            for (int x = 1; x <= paidOrderArrayList.getNumberOfEntries(); x++) {
                //Validate state
                if (paidOrderArrayList.getEntry(x).getAddress().getState().equals("Penang")) {
                    if (paidOrderArrayList.getEntry(x).getOrderDateTime().toLocalDate().equals(todayDate)) {
                        // Validate whether the current PaidOrder have rider or not
                        if (paidOrderArrayList.getEntry(x).getRider() == null && paidOrderArrayList.getEntry(x).getOrderStatus().toUpperCase().trim().equals("DELIVERING")) {
                            //5.Validate the PaidOrder time and rider shift time
                            ArrayList<Shift> riderShift = riderTodayShift_Penang.getEntry(i).getShift();
                            for (int y = 1; y <= riderShift.getNumberOfEntries(); y++) {
                                //If rider shift time is within the PaidOrder time , then set this rider to this delivery
                                if (paidOrderArrayList.getEntry(x).getOrderDateTime().isAfter(riderShift.getEntry(y).getBookedShiftTime()[0]) && paidOrderArrayList.getEntry(x).getOrderDateTime().isBefore(riderShift.getEntry(y).getBookedShiftTime()[1])) {
                                    paidOrderArrayList.getEntry(x).setRider(riderTodayShift_Penang.getEntry(i));
                                    //Add to current PaidOrder to the queue
                                    riderOrderQueue.enqueue(paidOrderArrayList.getEntry(x));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }


        //KL rider shift slot
        for (int i = 1; i <= riderTodayShift_KL.getNumberOfEntries(); i++) {
            for (int x = 1; x <= paidOrderArrayList.getNumberOfEntries(); x++) {
                //Validate state
                if (paidOrderArrayList.getEntry(x).getAddress().getState().equals("Wilayah Persekutuan")) {
                    if (paidOrderArrayList.getEntry(x).getOrderDateTime().toLocalDate().equals(todayDate)) {
                        // Validate whether the current PaidOrder have rider or not
                        if (paidOrderArrayList.getEntry(x).getRider() == null && paidOrderArrayList.getEntry(x).getOrderStatus().toUpperCase().trim().equals("DELIVERING")) {
                            //5.Validate the PaidOrder time and rider shift time
                            ArrayList<Shift> riderShift = riderTodayShift_KL.getEntry(i).getShift();
                            for (int y = 1; y <= riderShift.getNumberOfEntries(); y++) {
                                //If rider shift time is within the PaidOrder time , then set this rider to this delivery
                                if (paidOrderArrayList.getEntry(x).getOrderDateTime().isAfter(riderShift.getEntry(y).getBookedShiftTime()[0]) && paidOrderArrayList.getEntry(x).getOrderDateTime().isBefore(riderShift.getEntry(y).getBookedShiftTime()[1])) {
                                    paidOrderArrayList.getEntry(x).setRider(riderTodayShift_KL.getEntry(i));
                                    //Add to current PaidOrder to the queue
                                    riderOrderQueue.enqueue(paidOrderArrayList.getEntry(x));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    //======================= Choice 4 : View Current Rider Pending Order =======================
    //Let the rider view their current order in the queue
    public static void viewPendingOrder(QueueInterface<PaidOrder> riderOrderQueue, Rider rider) {
        ListIterator<PaidOrder> queueIterator;
        String riderChoice;
        do {
            System.out.println("\n\t\t\t\t=====================================================================================");
            System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-_-  Welcome To Rider Pending Order Module    _-_-_-_-_-_-_-_-_-_-");
            System.out.println("\t\t\t\t=====================================================================================");
            System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'      [ 1 ] View CURRENT pending order       .'.'.''.'..'.'.'.'.'");
            System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'      [ 2 ] View LATEST pending order        .'.'.''.'..'.'.'.'.'");
            System.out.println("\t\t\t\t-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-");
            System.out.print("\t\t\t\t\t\t\t\t   Please select your choice >> ");
            riderChoice = scanner.nextLine();
        } while (!noValidation(1, 2, riderChoice));
        switch (Integer.parseInt(riderChoice)) {
            case 1:
                queueIterator = riderOrderQueue.getListIterator(1);
                int count = 1;
                boolean found = false;
                System.out.println("\n=========================================================================================================================================================================================================================================");
                System.out.println("                                                 Rider CURRENT Pending Order   ");
                System.out.println("=========================================================================================================================================================================================================================================");
                System.out.println("NO  ORDER ID  ORDER PAID TIME                               RESTAURANT ADDRESS                             CUSTOMER NAME         CUSTOMER PHONE NO                  DELIVERY ADDRESS");
                System.out.println("=========================================================================================================================================================================================================================================");
                if (!riderOrderQueue.isEmpty()) {
                    while (queueIterator.hasNext()) {
                        PaidOrder paidOrder = queueIterator.next();
                        if (paidOrder.getRider().equals(rider)) {
                            System.out.printf("%02d %8s   %15s        %55s     %20s       %18s      %s \n", count, paidOrder.getOrderId(), paidOrder.getOrderDateTime(),paidOrder.getOrder().getOrderItems().getEntry(1).getItem().getRestaurant().getAddress().getEntry(1).getAddressLine(), paidOrder.getOrder().getCustomer().getName(), paidOrder.getOrder().getCustomer().getContactNo(), paidOrder.getAddress().getAddressLine());
                            count++;
                            found = true;
                        }

                    }
                    System.out.println("=========================================================================================================================================================================================================================================");
                }
                if (found == false) {
                    System.out.println("\t\t\t\t\n                                      == [ No Current Pending Order Found ! ] ==");
                } else {
                    System.out.printf("\t\t\t\t\n                                       == [ Total %02d Pending Order Found ! ] ==", count - 1);
                }
                break;
            case 2:
                queueIterator = riderOrderQueue.getListIterator(riderOrderQueue.size());
                count = 1;
                found = false;
                System.out.println("\n=========================================================================================================================================================================================================================================");
                System.out.println("                                                 Rider LATEST Pending Order   ");
                System.out.println("=========================================================================================================================================================================================================================================");
                System.out.println("NO  ORDER ID  ORDER PAID TIME                               RESTAURANT ADDRESS                             CUSTOMER NAME         CUSTOMER PHONE NO                  DELIVERY ADDRESS");
                System.out.println("=========================================================================================================================================================================================================================================");
                if (!riderOrderQueue.isEmpty()) {
                    while (queueIterator.hasPrevious()) {
                        PaidOrder paidOrder = queueIterator.previous();
                        if (paidOrder.getRider().equals(rider)) {
                            System.out.printf("%02d %8s   %15s        %55s     %20s       %18s      %s \n", count, paidOrder.getOrderId(), paidOrder.getOrderDateTime(),paidOrder.getOrder().getOrderItems().getEntry(1).getItem().getRestaurant().getAddress().getEntry(1).getAddressLine(), paidOrder.getOrder().getCustomer().getName(), paidOrder.getOrder().getCustomer().getContactNo(), paidOrder.getAddress().getAddressLine());
                            count++;
                            found = true;
                        }

                    }
                System.out.println("=========================================================================================================================================================================================================================================");
                }
                if (found == false) {
                    System.out.println("\t\t\t\t\t\n                                      == [ No Current Pending Order Found ! ] ==");
                } else {
                    System.out.printf("\t\t\t\t\t\n                                       == [ Total %02d Pending Order Found ! ] ==", count - 1);
                }

                break;
        }
    }

    public static PaidOrder getPaidOrder(ArrayList<PaidOrder> paidOrderArrayList, String searchOrderID) {
        PaidOrder searchPaidOrder = null;
        for (int i = 1; i <= paidOrderArrayList.getNumberOfEntries(); i++) {
            if (paidOrderArrayList.getEntry(i).getOrderId().trim().equals(searchOrderID.trim())) {
                searchPaidOrder = paidOrderArrayList.getEntry(i);
            }
        }
        return searchPaidOrder;
    }

    //======================= Choice 5 : Search Current Rider Pending Order =======================
    public static void searchPendingOrder(ArrayList<PaidOrder> paidOrderArrayList, QueueInterface<PaidOrder> riderOrderQueue, Rider rider) {
        int found = 0;
        System.out.println("\n\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-_-  Welcome To Rider Search Pending Order    _-_-_-_-_-_-_-_-_-_-");
        System.out.println("\t\t\t\t=====================================================================================");
        System.out.print("\n\t\t\t\t Please enter Order ID to search >> ");
        String orderID = scanner.nextLine();
        System.out.println("\t\t\t\t -------------------------------------------------------------------------------------------");
        PaidOrder searchPaidOrder = getPaidOrder(paidOrderArrayList, orderID);
        if (!riderOrderQueue.isEmpty()) {
            if (riderOrderQueue.contains(searchPaidOrder)) {
                Iterator<PaidOrder> queueIterator = riderOrderQueue.getListIterator(1);
                while (queueIterator.hasNext()) {
                    PaidOrder paidOrder = queueIterator.next();
                    if (paidOrder.getRider().getUserId().equals(rider.getUserId())) {
                        if (paidOrder.getOrderId().equals(orderID)) {
                            System.out.println("\t\t\t\t ORDER ");
                            System.out.println("\t\t\t\t\t    Order ID           >> " + paidOrder.getOrderId());
                            System.out.println("\t\t\t\t\t    Order Date Time    >> " + paidOrder.getOrderDateTime());
                            System.out.println("\t\t\t\t\t    Order Status       >> " + paidOrder.getOrderStatus());
                            System.out.println("\t\t\t\t\t    Restaurant address >> " + paidOrder.getOrder().getOrderItems().getEntry(1).getItem().getRestaurant().getAddress());
                            System.out.println("\t\t\t\t\t\t------------------------------------------------------");
                            System.out.println("\t\t\t\t CUSTOMER ");
                            System.out.println("\t\t\t\t\t    Customer Name      >> " + paidOrder.getOrder().getCustomer().getName());
                            System.out.println("\t\t\t\t\t    Customer Phone No  >> " + paidOrder.getOrder().getCustomer().getContactNo());
                            System.out.println("\t\t\t\t\t    Delivery address   >> " + paidOrder.getAddress().getAddressLine());
                            found = 1;
                        }
                    }
                }
            }
        } else
            found = 0;
        if (found == 0)
            System.out.println("\t\t\t\t\n                                      == [ Current Order ID Not Found ! ] ==");
    }

    //======================= Choice 6 : Cancel Current Rider Pending Order =======================
    public static void cancelPendingOrder(ArrayList<PaidOrder> paidOrderArrayList, QueueInterface<PaidOrder> riderOrderQueue, Rider rider, ArrayList<Rider> riderArrayList) {
        Iterator<PaidOrder> queueIterator = riderOrderQueue.getListIterator(1);
        int count = 1;
        boolean found = false;
        System.out.println("\n=========================================================================================================================================================================================================================================");
        System.out.println("                                                 Rider CANCEL Pending Order   ");
        System.out.println("=========================================================================================================================================================================================================================================");
        System.out.println("NO  ORDER ID  ORDER PAID TIME                               RESTAURANT ADDRESS                             CUSTOMER NAME         CUSTOMER PHONE NO                  DELIVERY ADDRESS");
        System.out.println("=========================================================================================================================================================================================================================================");
        if (!riderOrderQueue.isEmpty()) {
            while (queueIterator.hasNext()) {
                PaidOrder paidOrder = queueIterator.next();
                if (paidOrder.getRider().equals(rider)) {
                    System.out.printf("%02d %8s   %15s        %55s     %20s       %18s      %s \n", count, paidOrder.getOrderId(), paidOrder.getOrderDateTime(),paidOrder.getOrder().getOrderItems().getEntry(1).getItem().getRestaurant().getAddress().getEntry(1).getAddressLine(), paidOrder.getOrder().getCustomer().getName(), paidOrder.getOrder().getCustomer().getContactNo(), paidOrder.getAddress().getAddressLine());
                    count++;
                    found = true;
                }

            }
        System.out.println("=========================================================================================================================================================================================================================================");
        }
        if (found == false) {
            System.out.println("\t\t\t\t\n                                      == [ No Current Pending Order Found ! ] ==");
        }
        System.out.print("\n\t\t\t\t Please enter Order ID to cancel  >> ");
        String searchOrderID = scanner.nextLine().trim();
        PaidOrder searchPaidOrder = getPaidOrder(paidOrderArrayList, searchOrderID);
        int foundRider = 0;
        //Means order found
        // After delivery cancellation will assign the delivery to emergency driver (Using SET implementation to assign new rider)
        if (riderOrderQueue.contains(searchPaidOrder)) {
            if (searchPaidOrder.getRider().equals(rider)) {
                int orderIndex = riderOrderQueue.indexOf(searchPaidOrder);
                for (int i = 1; i <= riderArrayList.getNumberOfEntries(); i++) {
                    if (riderArrayList.getEntry(i).getUserId().equals("R000E"))//Emergency driver
                    {
                        searchPaidOrder.setRider(riderArrayList.getEntry(i)); //Set PaidOrder to new rider
                        riderOrderQueue.set(orderIndex, searchPaidOrder);//Set OrderQueue to current PaidOrder
                        foundRider = 1;
                        System.out.printf("\n\t\t\t\t\t\t\t             == [ %6s has been assign to new rider ! ] ==\n", searchPaidOrder.getOrderId());
                        break;
                    }
                }
            }
        }
        if (foundRider == 0) {
            System.out.println("\t\t\t\t\t\t\n               == [ Unsuccessfully assign to rider ! ] ==");
        }
    }

    //======================= Choice 7 : Update Rider Pending Order =======================
    public static void updateOrderStatus(ArrayList<PaidOrder> paidOrderArrayList, QueueInterface<PaidOrder> riderOrderQueue, Rider rider, StackInterface<PaidOrder> paidOrderHistory) {
        //1. Check PaidOrderArrayList PaidOrder's status
        // Check rider
        if (!riderOrderQueue.isEmpty()) {
            String updateRespond = "";
            Iterator<PaidOrder> queueIterator = riderOrderQueue.getListIterator(1);//Used for iterator
            for (int i = 1; i <= paidOrderArrayList.getNumberOfEntries(); i++) {
                while (queueIterator.hasNext()) {
                    PaidOrder paidOrder = queueIterator.next();
                    if (paidOrder.getRider().getUserId().equals(rider.getUserId())) {
                        System.out.println("\n\t Paid Order ID >> " + paidOrder.getOrderId() + "(" + paidOrder.getOrderDateTime() + ")" + " is found in your current delivery");
                        System.out.println("\t -------------------------------------------------------------------------------------------");
                        String orderStatus = paidOrder.getOrderStatus().toUpperCase().trim();
                        switch (orderStatus) {
                            case "DELIVERING":
                                do {
                                    System.out.print("\t Order ID [" + paidOrder.getOrderId() + "] want to update status to 'DELIVERED' ? ('Y'/'N') >> ");
                                    updateRespond = scanner.nextLine();
                                    if (!updateRespond.toUpperCase().trim().equals("Y") && !updateRespond.toUpperCase().trim().equals("N")) {
                                        System.out.println("\t\t\t\t\t-[ Invalid respond ! ]-");
                                    }
                                } while (!updateRespond.toUpperCase().trim().equals("Y") && !updateRespond.toUpperCase().trim().equals("N"));
                                //If rider respond yes change current order status to 'DELIVERED'
                                if (updateRespond.toUpperCase().trim().equals("Y")) {
                                    paidOrder.setOrderStatus("Delivered");
                                    //Deque the current PaidOrder from queue , then add it the PaidOrder to DeliveryOrderHistory(ArrayStack)
                                    riderOrderQueue.dequeue(i);
                                    paidOrderHistory.push(paidOrder);
                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    //======================= Choice 8 : Delivery Order History =======================
    //Display delivery order history (Array Stack) - Maybe can sort by weekly
    public static void deliveryOrderHistory(ArrayList<PaidOrder> paidOrderArrayList, StackInterface<PaidOrder> riderDeliveryHistory, Rider rider) {
        //The system will check whether the rider have delivery order history or not
        if (riderDeliveryHistory.isEmpty()) {
            System.out.println("\t\t\t\t\n                    == [ No Delivery History Found ! ] ==");
        }
        //If the rider have delivery order history
        else {
            String choice;
            do {
                System.out.println("\n\t\t\t\t=====================================================================================");
                System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-_-  Welcome To Rider Delivery Order History  _-_-_-_-_-_-_-_-_-_-");
                System.out.println("\t\t\t\t=====================================================================================");
                System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'    [ 1 ] View all delivery order history    .'.'.''.'..'.'.'.'.'");
                System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'    [ 2 ] View today delivery order history  .'.'.''.'..'.'.'.'.'");
                System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'    [ 3 ] Search history based on Order ID   .'.'.'.'.'.'.'.'.");
                System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'    [ 4 ] Search history based on Order Date .'.'.'.'.'.'.'.'.");
                System.out.println("\t\t\t\t-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-");
                System.out.print("\t\t\t\t\t\t\t\t   Please select your choice >> ");
                choice = scanner.nextLine();
            } while (!noValidation(1, 5, choice));
            switch (Integer.parseInt(choice)) {
                //View all delivery order history
                case 1:
                System.out.println("=========================================================================================================================================================================================================================================");
                System.out.println("                                                  All Order History ( From Latest )   ");
                System.out.println("=========================================================================================================================================================================================================================================");
                System.out.println("NO  ORDER ID  ORDER PAID TIME                               RESTAURANT ADDRESS                             CUSTOMER NAME         CUSTOMER PHONE NO                  DELIVERY ADDRESS");
                System.out.println("=========================================================================================================================================================================================================================================");
                    int count = 1;
                    for (int i = riderDeliveryHistory.getSize(); i > 0; i--) {
                        if (riderDeliveryHistory.get(i).getRider().getUserId().equals(rider.getUserId())) {
                            if (riderDeliveryHistory.get(i).getRider().equals(rider)) {
                                System.out.printf("%02d %8s   %15s        %55s     %20s       %18s      %s \n", count, riderDeliveryHistory.get(i).getOrderId(), riderDeliveryHistory.get(i).getOrderDateTime(),riderDeliveryHistory.get(i).getOrder().getOrderItems().getEntry(1).getItem().getRestaurant().getAddress().getEntry(1).getAddressLine(), riderDeliveryHistory.get(i).getOrder().getCustomer().getName(), riderDeliveryHistory.get(i).getOrder().getCustomer().getContactNo(),riderDeliveryHistory.get(i).getAddress().getAddressLine());
                                count++;
                            }
                        }
                    }
                System.out.println("=========================================================================================================================================================================================================================================");
                    break;
                //View today's delivery order history
                case 2:  
                System.out.println("=========================================================================================================================================================================================================================================");
                System.out.println("                                                 Today's Order History ( From Latest )   ");
                System.out.println("=========================================================================================================================================================================================================================================");
                System.out.println("NO  ORDER ID  ORDER PAID TIME                               RESTAURANT ADDRESS                             CUSTOMER NAME         CUSTOMER PHONE NO                  DELIVERY ADDRESS");
                System.out.println("=========================================================================================================================================================================================================================================");
                    count = 1;
                    for (int i = riderDeliveryHistory.getSize(); i > 0; i--) {
                        if (riderDeliveryHistory.get(i).getOrderDateTime().toLocalDate().equals(LocalDateTime.now().toLocalDate()) && riderDeliveryHistory.get(i).getRider().getUserId().equals(rider.getUserId())) {
                            if (riderDeliveryHistory.get(i).getRider().equals(rider)) {
                                      System.out.printf("%02d %8s   %15s        %55s     %20s       %18s      %s \n", count, riderDeliveryHistory.get(i).getOrderId(), riderDeliveryHistory.get(i).getOrderDateTime(),riderDeliveryHistory.get(i).getOrder().getOrderItems().getEntry(1).getItem().getRestaurant().getAddress().getEntry(1).getAddressLine(), riderDeliveryHistory.get(i).getOrder().getCustomer().getName(), riderDeliveryHistory.get(i).getOrder().getCustomer().getContactNo(),riderDeliveryHistory.get(i).getAddress().getAddressLine());
                                count++;
                            }
                        }
                    }
                System.out.println("=========================================================================================================================================================================================================================================");
                    break;
                case 3:
                    String orderID;
                    System.out.print("\n\t\t\t\t Please enter Order ID to search >> ");
                    orderID = scanner.nextLine();
                    PaidOrder paidOrder = getPaidOrder(paidOrderArrayList, orderID);
                    if(paidOrder!=null) {
                        int indexOrderID = riderDeliveryHistory.indexOf(paidOrder);
                        //If indexOrderID returns is not -1 means current orderID delivery history found
                        if (indexOrderID != -1) {
                            PaidOrder riderOrderHistory = riderDeliveryHistory.get(indexOrderID);
                            //If found rider ID is same as the current rider ID
                            if (riderOrderHistory.getRider().equals(rider)) {
                                System.out.println("\n\t\t\t\t -[ " + riderOrderHistory.getOrderId() + " is found ! ]- ");
                                System.out.println("\n\t\t\t\t ORDER ");
                                System.out.println("\t\t\t\t\t    Order ID           >> " + riderOrderHistory.getOrderId());
                                System.out.println("\t\t\t\t\t    Order Date Time    >> " + riderOrderHistory.getOrderDateTime());
                                System.out.println("\t\t\t\t\t    Order Status       >> " + riderOrderHistory.getOrderStatus());
                                System.out.println("\t\t\t\t\t    Restaurant address >> "+ riderOrderHistory.getOrder().getOrderItems().getEntry(1).getItem().getRestaurant().getAddress());
                                System.out.println("\t\t\t\t\t\t------------------------------------------------------");
                                System.out.println("\t\t\t\t CUSTOMER ");
                                System.out.println("\t\t\t\t\t    Customer Name      >>"+ riderOrderHistory.getOrder().getCustomer().getName());
                                System.out.println("\t\t\t\t\t    Customer Phone No  >> "+ riderOrderHistory.getOrder().getCustomer().getContactNo());
                                System.out.println("\t\t\t\t\t    Delivery address   >> "+ riderOrderHistory.getAddress().getAddressLine());
                            }
                        }
                    }
                    break;
                case 4:
                    String orderDate;
                    do {
                        System.out.print("\n\t\t\t\t Please enter delivery order date you want to search  (yyyy-mm-dd) >> ");
                        orderDate = scanner.nextLine();
                    } while (!dateValidation(orderDate));
                    int year = Integer.parseInt(orderDate.substring(0, 4));
                    int month = Integer.parseInt(orderDate.substring(5, 7));
                    int day = Integer.parseInt(orderDate.substring(8, 10));
                    LocalDate searchDate = LocalDate.of(year, month, day);
                    for (int i = riderDeliveryHistory.getSize(); i > 0; i--) {
                        if (riderDeliveryHistory.get(i).getOrderDateTime().toLocalDate().equals(searchDate) && riderDeliveryHistory.get(i).getRider().getUserId().equals(rider.getUserId())) {
                            System.out.println(riderDeliveryHistory.get(i).getOrderId());
                                System.out.println("\n\t\t\t\t ORDER ");
                                System.out.println("\t\t\t\t\t    Order ID           >> " + riderDeliveryHistory.get(i).getOrderId());
                                System.out.println("\t\t\t\t\t    Order Date Time    >> " + riderDeliveryHistory.get(i).getOrderDateTime());
                                System.out.println("\t\t\t\t\t    Order Status       >> " + riderDeliveryHistory.get(i).getOrderStatus());
                                System.out.println("\t\t\t\t\t    Restaurant address >> "+ riderDeliveryHistory.get(i).getOrder().getOrderItems().getEntry(1).getItem().getRestaurant().getAddress());
                                System.out.println("\t\t\t\t\t\t------------------------------------------------------");
                                System.out.println("\t\t\t\t CUSTOMER ");
                                System.out.println("\t\t\t\t\t    Customer Name      >>"+ riderDeliveryHistory.get(i).getOrder().getCustomer().getName());
                                System.out.println("\t\t\t\t\t    Customer Phone No  >> "+ riderDeliveryHistory.get(i).getOrder().getCustomer().getContactNo());
                                System.out.println("\t\t\t\t\t    Delivery address   >> "+ riderDeliveryHistory.get(i).getAddress().getAddressLine());
                        }
                    }
                    break;
            }
        }
    }

    public static void riderHeader(String headerTitle) {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        System.out.println("\n                            #%%%%%%%                                            " + formattedDate);
        System.out.println("                           %%%%%,,,,                           ");
        System.out.println("                           .%%%%,///                       ==   WELCOME TO ");
        System.out.println("         #%%%%%%%%%%%/     *&&&&&.                            ,------. ,--.   ,--.               ");
        System.out.println("       *&&&&&&&&&&&&&&&  &&&&&&&&&&                           |  .--. '`--' ,-|  | ,---. ,--.--. ");
        System.out.println("       *&&&&&&&&&&&&&&& &&&&&&&&&&&&.       *###              |  '--'.',--.' .-. || .-. :|  .--' ");
        System.out.println("       *&&&&&&&&&&&&&&&(%%%%%%&    *%&&&&####.##              |  |\\  \\ |  |\\ `-' |\\   --.|  |");
        System.out.println("       *&&&&&&&&&&&&&&& %%%%%%%%%%%%%.   /###                 `--' '--'`--' `---'  `----'`--' ");
        System.out.println("             %%%%%%%%%%%%####     #%&&% *####%%%%%%%%(                                    MODULE ==");
        System.out.println("           %%%%%%%%%%%%%%%%###############%%%%%%%, @@@        ----------------------------------------");
        System.out.println("           %%%%%%%%%%%%%%%%#############. %%%@     ,@@                 " + headerTitle);
        System.out.println("              %@@@@@@@@                     .@@@@@@@@         ----------------------------------------          ");
    }


}