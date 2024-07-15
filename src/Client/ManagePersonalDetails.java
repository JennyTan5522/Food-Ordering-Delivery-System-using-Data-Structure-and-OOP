package Client;

import ADT.Customer.*;
import Entity.*;

import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagePersonalDetails {

    public static void managePersonalDetails(Customer currentLogin, ArrayList<Customer> customerList) {
        // regular expressions for validation
        String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        String contactRegex = "^(\\+?6?01)[02-46-9][-][0-9]{7}$|^(\\+?6?01)[1][-][0-9]{8}$";
        String emailRegex = "^(.+)@(.+)$";
        String postcodeRegex = "^[0-9]{5}$";
        
        LOOP:
        while (true) {
            int selection1 = CustomerPersonalDetailsModule();
            switch (selection1) {
                case 1:
                    // view account
                    viewAccount(currentLogin);
                    break;
                case 2:
                    // update password
                    updatePassword(passwordRegex, currentLogin, customerList);
                    break;
                case 3:
                    // update contact number
                    updateContactNo(contactRegex, currentLogin, customerList);
                    break;
                case 4:
                    // update email address
                    updateEmail(emailRegex, currentLogin, customerList);
                    break;
                case 5:
                    // view address
                    viewAddress(currentLogin);
                    break;
                case 6:
                    // add address
                    addAddress(postcodeRegex, currentLogin, customerList);
                    break;
                case 7:
                    // update address
                    updateAddress(postcodeRegex, currentLogin);
                    break;
                case 8:
                    // remove address
                    removeAddress(currentLogin);
                    break;
                case 9:
                    // clear address
                    clearAddresses(currentLogin);
                    break;
                case 0:
                    // Log out
                    break LOOP;
                default:
                    System.out.println("Invalid input. Please select again.");
                    break;
            }
        }
    }

    public static int CustomerPersonalDetailsModule() {
        Scanner s = new Scanner(System.in);
        System.out.println("\n\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t _-_-_-_-_-_-_   Welcome To Customer Manage Personal Details Module    _-_-_-_-_-_-_");
        System.out.println("\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t                                  MY PROFILE                   ");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 1 ] View profile                      .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 2 ] Update password                   .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 3 ] Update contact number             .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 4 ] Update email address              .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t-------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t                                  MY ADDRESS                                      ");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 5 ] View address(es)                  .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 6 ] Add address                       .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 7 ] Update address                    .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 8 ] Remove address                    .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 9 ] Clear address(es)                 .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 0 ] Back to Module Menu               .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-");
        System.out.print("\n\t\t\t\tEnter your selection: ");
        return s.nextInt();
    }

    public static void viewAccount(Customer currentLogin) {
        System.out.println("\n\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-_-_-_-_-_         My Account          _-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("\t\t\t\t=====================================================================================");
        System.out.print("\t\t\t\t" + currentLogin);
    }

    public static void updatePassword(String passwordRegex, Customer currentLogin, ArrayList<Customer> customerList) {
        Scanner s = new Scanner(System.in);
        OUTER:
        while (true) {
            System.out.print("\n\t\t\t\tDo you want to reset password? [Y/N]\t: ");
            String selection = s.nextLine();
            switch (selection) {
                case "Y":
                    // input old password and password validation
                    System.out.print("\t\t\t\tOld password: ");
                    String oldPassword = s.nextLine();
                    if (currentLogin.getPassword().equals(oldPassword)) {
                        while (true) {
                            // input new password and password validation
                            System.out.print("\t\t\t\tNew password: ");
                            String newPassword = s.nextLine();
                            if (isValid(newPassword, passwordRegex) == false) {
                                System.out.println("\t\t\t\tPassword must have at least one numeric, lowercase, uppercase character and a special symbol with length between 8-20.");
                            } else {
                                // replace password
                                currentLogin.setPassword(newPassword);
                                System.out.println("\t\t\t\tPassword has been changed successfully!");
                                break;
                            }
                        }
                    } else {
                        System.out.println("\t\t\t\tIncorrect password.");
                    }
                    break;
                case "N":
                    break OUTER;
                default:
                    System.out.println("\t\t\t\tInvalid input. Please select again.");
                    break;
            }
        }
    }

    public static void updateContactNo(String contactRegex, Customer currentLogin, ArrayList<Customer> customerList) {
        Scanner s = new Scanner(System.in);
        OUTER:
        while (true) {
            System.out.print("\n\t\t\t\tDo you want to update contact number? [Y/N]\t: ");
            String selection = s.nextLine();
            switch (selection) {
                case "Y":
                    // input newContactNo and contactNo validation
                    System.out.print("\t\t\t\tNew contact number: ");
                    String newContactNo = s.nextLine();
                    if (isValid(newContactNo, contactRegex) == false) {
                        System.out.println("\t\t\t\tInvalid contact number format.");
                    } else {
                        // replace contact number
                        currentLogin.setContactNo(newContactNo);
                        System.out.println("\t\t\t\tContact number has been updated successfully!");
                        break;
                    }
                    break;
                case "N":
                    break OUTER;
                default:
                    System.out.println("\t\t\t\tInvalid input. Please select again.");
                    break;
            }
        }
    }

    public static void updateEmail(String emailRegex, Customer currentLogin, ArrayList<Customer> customerList) {
        Scanner s = new Scanner(System.in);
        OUTER:
        while (true) {
            System.out.print("\n\t\t\t\tDo you want to update email address? [Y/N]\t: ");
            String selection = s.nextLine();
            switch (selection) {
                case "Y":
                    // input newEmail and email validation
                    System.out.print("\t\t\t\tEnter new email: ");
                    String newEmail = s.nextLine();
                    if (isValid(newEmail, emailRegex) == false) {
                        System.out.println("\t\t\t\tInvalid email address format.");
                    } else {
                        // replace email
                        currentLogin.setEmail(newEmail);
                        System.out.println("\t\t\t\tEmail has been updated successfully!");
                        break;
                    }
                    break;
                case "N":
                    break OUTER;
                default:
                    System.out.println("\t\t\t\tInvalid input. Please select again.");
                    break;
            }
        }
    }

// my address
    public static void viewAddress(Customer currentLogin) {
        Iterator<Address> addressIterator = currentLogin.getAddress().getIterator();
        System.out.println("\n\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-_-_-_-_-_         My Address          _-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("\t\t\t\t=====================================================================================");
        int position = 1;
        // check if addressList is empty
        if (currentLogin.getAddress().isEmpty()) {
            System.out.println("\t\t\t\tThere is no saved address.");
        } else {
            while (addressIterator.hasNext()) {
                // display all addresses
                System.out.println("\t\t\t\t" + position + ". " + addressIterator.next());
                position++;
            }
        }
    }

    public static void addAddress(String postcodeRegex, Customer currentLogin, ArrayList<Customer> customerList) {
        Scanner s = new Scanner(System.in);
        OUTER:
        while (true) {
            System.out.print("\n\t\t\t\tDo you want to add new address? [Y/N]\t: ");
            String selection = s.nextLine();
            switch (selection) {
                case "Y":
                    // input address with postcode validation
                    System.out.print("\t\t\t\tAddress Line\t: ");
                    String addressLine = s.nextLine();
                    String postcode;
                    while (true) {
                        System.out.print("\t\t\t\tPostcode\t: ");
                        postcode = s.nextLine();
                        if (isValid(postcode, postcodeRegex) == false) {
                            System.out.println("\t\t\t\tInvalid postcode format.");
                        } else {
                            break;
                        }
                    }
                    System.out.print("\t\t\t\tDistrict\t: ");
                    String district = s.nextLine();
                    System.out.print("\t\t\t\tState\t\t: ");
                    String state = s.nextLine();
                    // add address into addressList
                    if (currentLogin.getAddress().add(new Address(addressLine, postcode, district, state))) {
                        System.out.println("\t\t\t\tAddress has been added successfully!");
                    }
                    break;
                case "N":
                    break OUTER;
                default:
                    System.out.println("\t\t\t\tInvalid input. Please select again.");
                    break;
            }
        }
    }

    public static void updateAddress(String postcodeRegex, Customer currentLogin) {
        Scanner s = new Scanner(System.in);
        // check if addressList is empty
        if (currentLogin.getAddress().isEmpty()) {
            System.out.println("\t\t\t\tThere is no saved address.");
        } else {
            OUTER:
            while (true) {
                // display all addresses for customer to choose which address to edit
                viewAddress(currentLogin);
                System.out.print("\n\t\t\t\tDo you want to update address? [Y/N]\t: ");
                String selection = s.nextLine();
                switch (selection) {
                    case "Y":
                        System.out.print("\t\t\t\tAddress to edit: ");
                        int position = s.nextInt();
                        s.nextLine();
                        // check if input position is between 1 and numberOfEntries
                        if (position < 1 || position > currentLogin.getAddress().getNumberOfEntries()) {
                            System.out.println("\t\t\t\tInvalid input. Please select between 1 to " + currentLogin.getAddress().getNumberOfEntries());
                            break;
                        } else {
                            System.out.print("\t\t\t\tAddress Line\t: ");
                            String addressLine = s.nextLine();
                            String postcode;
                            while (true) {
                                System.out.print("\t\t\t\tPostcode\t: ");
                                postcode = s.nextLine();
                                if (isValid(postcode, postcodeRegex) == false) {
                                    System.out.println("\t\t\t\tInvalid postcode format.");
                                } else {
                                    break;
                                }
                            }
                            System.out.print("\t\t\t\tDistrict\t: ");
                            String district = s.nextLine();
                            System.out.print("\t\t\t\tState\t\t: ");
                            String state = s.nextLine();
                            // replace address at specific position
                            if (currentLogin.getAddress().replace(position, new Address(addressLine, postcode, district, state))) {
                                System.out.println("\t\t\t\tAddress has been edited successfully!");
                            }
                        }
                        break;
                    case "N":
                        break OUTER;
                    default:
                        System.out.println("\t\t\t\tInvalid input. Please select again.");
                        break;
                }
            }
        }
    }

    public static void removeAddress(Customer currentLogin) {
        Scanner s = new Scanner(System.in);
        // check if addressList is empty
        if (currentLogin.getAddress().isEmpty()) {
            System.out.println("\n\t\t\t\tThere is no saved address.");
        } else {
            OUTER:
            while (true) {
                // display all addresses for customer to choose which address to remove
                viewAddress(currentLogin);
                System.out.print("\n\t\t\t\tDo you want to remove address? [Y/N]\t: ");
                String selection = s.nextLine();
                switch (selection) {
                    case "Y":
                        System.out.print("\n\t\t\t\tAddress to remove: ");
                        int position = s.nextInt();
                        s.nextLine();
                        // check if input position is between 1 and numberOfEntries
                        if (position < 1 || position > currentLogin.getAddress().getNumberOfEntries()) {
                            System.out.println("\t\t\t\tInvalid input. Please select between 1 to " + currentLogin.getAddress().getNumberOfEntries());
                            break;
                        } else {
                            // remove address at specific position
                            System.out.println("\t\t\t\t" + currentLogin.getAddress().remove(position) + " has been removed successfully!");
                            break;
                        }
                    case "N":
                        break OUTER;
                    default:
                        System.out.println("\t\t\t\tInvalid input. Please select again.");
                        break;
                }
            }
        }

    }

    public static void clearAddresses(Customer currentLogin) {
        Scanner s = new Scanner(System.in);
        // check if addressList is empty
        if (currentLogin.getAddress().isEmpty()) {
            System.out.println("\n\t\t\t\tThere is no saved address.");
        } else {
            OUTER:
            while (true) {
                // display all addresses 
                viewAddress(currentLogin);
                System.out.print("\n\t\t\t\tDo you want to remove all address(es)? [Y/N]: ");
                String selection = s.nextLine();
                switch (selection) {
                    case "Y":
                        // clear all addresses in addressList
                        currentLogin.getAddress().clear();
                        System.out.println("\t\t\t\tAll address(es) has been removed successfully!");
                        break OUTER;
                    case "N":
                        break OUTER;
                    default:
                        System.out.println("\t\t\t\tInvalid input. Please select again.");
                        break;
                }
            }
        }
    }
    
    public static boolean isValid(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
