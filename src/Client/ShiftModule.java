/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import ADT.Customer.ArrayList;
import static Client.RiderModule.scanner;
import Entity.Rider;
import Entity.Shift;
import Entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author User
 */
public class ShiftModule {
    
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
    
    //===================================================Validation Rider Shift Time===================================================
    //Returns the ONLY riders with TODAY shift time
    public static ArrayList<Rider> riderTodayShift_Penang(ArrayList<Rider> riderArrayList) {
        int foundRider = 0;
        ArrayList<Rider> riderTodayShift = new ArrayList<Rider>();
        for (int i = 1; i <= riderArrayList.getNumberOfEntries(); i++) {
            if (riderArrayList.getEntry(i) instanceof User) {
                User user = riderArrayList.getEntry(i);
                if (user.getAddress().getEntry(1).getState().equals("Penang")) {
                    if (riderArrayList.getEntry(i).getShift() != null) {
                        ArrayList<Shift> riderShift = riderArrayList.getEntry(i).getShift();
                        for (int y = 1; y <= riderShift.getNumberOfEntries(); y++) {
                            if (riderShift.getEntry(y).getBookedShiftTime()[0].toLocalDate().equals(LocalDate.now())) {
                                foundRider = 1;
                            }
                        }
                        if (foundRider == 1)
                            riderTodayShift.add(riderArrayList.getEntry(i));
                    }
                }
            }
            foundRider = 0;
        }
        return riderTodayShift;
    }

    public static ArrayList<Rider> riderTodayShift_KL(ArrayList<Rider> riderArrayList) {
        int foundRider = 0;
        ArrayList<Rider> riderTodayShift = new ArrayList<Rider>();
        for (int i = 1; i <= riderArrayList.getNumberOfEntries(); i++) {
            if (riderArrayList.getEntry(i) instanceof User) {
                User user = riderArrayList.getEntry(i);
                if (user.getAddress().getEntry(1).getState().equals("Wilayah Persekutuan")) {
                    if (riderArrayList.getEntry(i).getShift() != null) {
                        ArrayList<Shift> riderShift = riderArrayList.getEntry(i).getShift();
                        for (int y = 1; y <= riderShift.getNumberOfEntries(); y++) {
                            if (riderShift.getEntry(y).getBookedShiftTime()[0].toLocalDate().equals(LocalDate.now())) {
                                foundRider = 1;
                            }
                        }
                        if (foundRider == 1)
                            riderTodayShift.add(riderArrayList.getEntry(i));
                    }
                }
            }
            foundRider = 0;
        }
        return riderTodayShift;
    }

    
    
    public static LocalDateTime[] thisWeekShiftDate() {
        LocalDateTime[] thisWeekShiftDate = new LocalDateTime[8];
        Calendar cal = Calendar.getInstance();
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            cal.set(Calendar.DAY_OF_WEEK, i);
            //Getting the timezone
            TimeZone tz = cal.getTimeZone();
            thisWeekShiftDate[i] = LocalDateTime.ofInstant(cal.toInstant(), tz.toZoneId());
        }
        return thisWeekShiftDate;
    }

    //======================= Choice 1 : View Available Shifts =======================
    public static void thisWeekAvailableShift(ArrayList<Rider> riderArrayList, Rider rider) {
        LocalDateTime[] thisWeekShiftDate = thisWeekShiftDate();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        System.out.println("===================================================================================================================");
        System.out.printf("                %25s   [ Time Table of day %10s - %10s ]\n", rider.getAddress().getEntry(1).getState().toUpperCase(), dtf.format(thisWeekShiftDate[1]), dtf.format(thisWeekShiftDate[7]));
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        System.out.println("      Day\\Time           | 9.00AM-12.00PM | 12.00PM-15.00PM | 15.00PM-18.00PM | 18.00PM-21.00PM | 21.00PM-00.00AM |");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        int found = 0;
        int totalShift = 0;
        int count9 = 0, count12 = 0, count15 = 0, count18 = 0, count21 = 0;
        ArrayList<Rider> riderSameDistrict = new ArrayList<Rider>();
        for (int i = 1; i <= riderArrayList.getNumberOfEntries(); i++) {
            //System.out.println(riderArrayList.get(i).getUserId());
            if (riderArrayList.getEntry(i) instanceof User) {
                User user = riderArrayList.getEntry(i);
                if (user.getAddress().getEntry(1).getState().equals(rider.getAddress().getEntry(1).getState())) {
                    riderSameDistrict.add(riderArrayList.getEntry(i));
                }
            }
        }
        //1. Loop 7 days (Mon-Sun)
        for (int y = 1; y <= 7; y++) {
            System.out.printf(String.format("%10s %-10s", thisWeekShiftDate[y].toLocalDate(), thisWeekShiftDate[y].getDayOfWeek()));
            found = 0;
            count9 = 0;
            count12 = 0;
            count15 = 0;
            count18 = 0;
            count21 = 0;
            //2. Loop rider array list (Only display shift time that rider currently lived at)
            for (int i = 1; i <= riderSameDistrict.getNumberOfEntries(); i++) {
                ArrayList<Shift> shiftArrayList = riderSameDistrict.getEntry(i).getShift();
                //3. Loop shift array list
                if (shiftArrayList != null) {
                    for (int x = 1; x <= shiftArrayList.getNumberOfEntries(); x++) {
                        //Compare shift date
                        if (shiftArrayList.getEntry(x).getBookedShiftTime()[0].toLocalDate().compareTo(thisWeekShiftDate[y].toLocalDate()) == 0) {
                            found = 1;
                            //If booked shift HOUR same with the week shift HOUR
                            if (shiftArrayList.getEntry(x).getBookedShiftTime()[0].getHour() == 9) {
                                count9++;
                            } else if (shiftArrayList.getEntry(x).getBookedShiftTime()[0].getHour() == 12) {
                                count12++;
                            } else if (shiftArrayList.getEntry(x).getBookedShiftTime()[0].getHour() == 15) {
                                count15++;
                            } else if (shiftArrayList.getEntry(x).getBookedShiftTime()[0].getHour() == 18) {
                                count18++;
                            } else if (shiftArrayList.getEntry(x).getBookedShiftTime()[0].getHour() == 21) {
                                count21++;
                            }
                        }
                    }
                }
            }
            if (found == 0)
                System.out.printf(String.format("%15s  %15s  %15s  %15s  %15s\n", "0", "0", "0", "0", "0"));
            else
                System.out.printf(String.format("%15d  %15d  %15d  %15d  %15d\n", count9, count12, count15, count18, count21));
            totalShift += count9 + count12 + count15 + count18 + count21;
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------");
        System.out.println("Total Shifts >> " + totalShift);
    }

    //======================= Choice 2 : Add New Shifts =======================
    public static void addNewShift(ArrayList<Rider> riderArrayList, Rider rider) {
        //Display available shift
        //thisWeekAvailableShift(riderArrayList);
        String shiftDate;
        String shiftTime;
        //===== 1. Select shift date =======
        do {
            System.out.print("\nPlease select the available DAY shift slot (yyyy-mm-dd) ('XXX'-Back to Menu) >> ");
            shiftDate = scanner.nextLine();
            if (shiftDate.trim().toUpperCase().equals("XXX"))
                return;
        } while (!dateValidation(shiftDate));
        int year = Integer.parseInt(shiftDate.substring(0, 4));
        int month = Integer.parseInt(shiftDate.substring(5, 7));
        int day = Integer.parseInt(shiftDate.substring(8, 10));

        //===== 2. Select shift time =====
        System.out.println("\n\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-_-   Welcome To Rider Shift Time Module      _-_-_-_-_-_-_-_-_-_-");
        System.out.println("\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t                                    SHIFT                     ");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'      [ 1 ] 09.00 PM - 12.00 PM              .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'      [ 2 ] 12.00 PM - 15.00 PM              .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'      [ 3 ] 15.00 PM - 18.00 PM              .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'      [ 4 ] 18.00 PM - 21.00 PM              .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'      [ 5 ] 21.00 PM - 00.00 AM              .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-");
        do {
            System.out.print("\t\t\t\t Please select available shift time >> ");
            shiftTime = scanner.nextLine();
        } while (!noValidation(1, 5, shiftTime));
        int hour = 0;
        int endShiftTime = 0;
        Integer chosenShiftTime = Integer.parseInt(shiftTime);
        switch (chosenShiftTime) {
            case 1:
                hour = 9;
                endShiftTime = 12;
                break;
            case 2:
                hour = 12;
                endShiftTime = 15;
                break;
            case 3:
                hour = 15;
                endShiftTime = 18;
                break;
            case 4:
                hour = 18;
                endShiftTime = 21;
                break;
            case 5:
                hour = 21;
                endShiftTime = 00;
                break;
        }
        LocalDateTime[] shiftDateTimeChosen = new LocalDateTime[2];
        shiftDateTimeChosen[0] = LocalDateTime.of(year, month, day, hour, 0);//Shift start time
        shiftDateTimeChosen[1] = LocalDateTime.of(year, month, day, endShiftTime, 0);//Shift end time
        LocalDateTime[] thisWeekShiftDate = thisWeekShiftDate();
        LocalDate now = LocalDate.now();
        //Check book shift date is within thisWeekShiftDate and make sure the date booking is before 1 day
        boolean foundDate = false;
        for (int y = 1; y <= 7; y++) {
            if (thisWeekShiftDate[y].toLocalDate().compareTo(shiftDateTimeChosen[0].toLocalDate()) == 0 && shiftDateTimeChosen[0].toLocalDate().isAfter(now)) {
                foundDate = true;
                break;
            }
        }
        //If date found add shift
        if (foundDate == true) {
            System.out.println("\n\t\t\t\t                  = [ Successfully added to slot ]=");
            rider.getShift().add(new Shift(shiftDateTimeChosen));
        } else {
            System.out.println("\n\t\t\t\t    =[ Unsuccessfully add to slot , book shift must be made BEFORE 1 DAY ! ]=");
        }
        thisWeekAvailableShift(riderArrayList, rider);
    }

    //======================= Choice 3 : View Booked Shifts =======================
    //Let rider to view weekly booked shift
    public static void viewBookedShift(Rider rider) {
        ArrayList<Shift> riderCurrentShift = rider.getShift();
        LocalDateTime[] thisWeekShiftDate = thisWeekShiftDate();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        System.out.printf("\n\n\t\t\t%20s , Shift Week  >> %10s - %10s\n", rider.getName(), dtf.format(thisWeekShiftDate[1]), dtf.format(thisWeekShiftDate[7]));
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        System.out.println("      Day\\Time           | 9.00AM-12.00PM | 12.00PM-15.00PM | 15.00PM-18.00PM | 18.00PM-21.00PM | 21.00PM-00.00AM |");
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        int found = 0;
        int totalWeekShift = 0;
        int count9 = 0, count12 = 0, count15 = 0, count18 = 0, count21 = 0;
        for (int y = 1; y <= 7; y++) {
            found = 0;
            System.out.printf(String.format("%10s %-10s", thisWeekShiftDate[y].toLocalDate(), thisWeekShiftDate[y].getDayOfWeek()));
            for (int x = 1; x <= riderCurrentShift.getNumberOfEntries(); x++) {
                //Compare shift date
                //System.out.println(thisWeekShiftDate[y]);
                if (riderCurrentShift.getEntry(x).getBookedShiftTime()[0].toLocalDate().compareTo(thisWeekShiftDate[y].toLocalDate()) == 0) {
                    found = 1;
                    //If booked shift HOUR same with the week shift HOUR
                    if (riderCurrentShift.getEntry(x).getBookedShiftTime()[0].getHour() == 9) {
                        //tick+=String.format("%15s  ", "X");
                        count9++;
                    } else if (riderCurrentShift.getEntry(x).getBookedShiftTime()[0].getHour() == 12) {
                        count12++;
                    } else if (riderCurrentShift.getEntry(x).getBookedShiftTime()[0].getHour() == 15) {
                        count15++;
                    } else if (riderCurrentShift.getEntry(x).getBookedShiftTime()[0].getHour() == 18) {
                        count18++;
                    }
                }
            }

            totalWeekShift = count9 + count12 + count15 + count18 + count21;
            if (found == 0)
                System.out.printf(String.format("%15s  %15s  %15s  %15s  %15s\n", "0", "0", "0", "0", "0"));
            else
                System.out.printf(String.format("%15d  %15d  %15d  %15d  %15d\n", count9, count12, count15, count18, count21));
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------");

        System.out.println("Total booked shift in this week >> " + totalWeekShift);
    }

}
