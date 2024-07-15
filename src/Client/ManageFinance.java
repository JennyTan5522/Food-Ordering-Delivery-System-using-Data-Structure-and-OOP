package Client;

import ADT.Customer.*;
import Entity.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageFinance {

    public static void manageFinance(Customer currentLogin) {
        // regular expressions for validation
        String cardNoRegex = "^[0-9]{16}$";
        String cvvRegex = "^[0-9]{3}$";
        
        LOOP:
        while (true) {
            int selection2 = customerFinanceModule();
            Iterator<PaymentMethod> paymentMethodIterator = currentLogin.getPaymentMethodList().getIterator();
            // copy all entries in paymentMethodList
            ArrayList<PaymentMethod> copiedCreditCard = (ArrayList<PaymentMethod>) currentLogin.getPaymentMethodList().copy();
            Iterator<PaymentMethod> creditCardIterator = copiedCreditCard.getIterator();
            while (creditCardIterator.hasNext()) {
                PaymentMethod paymentMethod = creditCardIterator.next();
                if (paymentMethod instanceof Ewallet) {
                    // remove the entry which is ewallet
                    copiedCreditCard.remove(paymentMethod);
                }
            }
            switch (selection2) {
                case 1:
                    // view payment method
                    viewPaymentMethod(paymentMethodIterator);
                    break;
                case 2:
                    // add credit card
                    addCreditCard(cardNoRegex, cvvRegex, currentLogin);
                    break;
                case 3:
                    // update credit card.
                    updateCreditCard(cardNoRegex, cvvRegex, currentLogin, copiedCreditCard);
                    break;
                case 4:
                    // remove credit card
                    removeCreditCard(currentLogin, copiedCreditCard);
                    break;
                case 5:
                    // clear credit card
                    clearCreditCard(currentLogin, copiedCreditCard);
                    break;
                case 6:
                    // view balance
                    viewBalance(currentLogin);
                    break;
                case 7:
                    // top up
                    topUpEwallet(currentLogin);
                    break;
                case 0:
                    break LOOP;
                default:
                    System.out.println("\t\t\t\tInvalid input. Please select again.");
                    break;
            }
        }
    }

    public static int customerFinanceModule() {
        Scanner s = new Scanner(System.in);
        System.out.println("\n\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-   Welcome To Customer Manage Finance Module   _-_-_-_-_-_-_-_-_-");
        System.out.println("\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t                                PAYMENT METHOD                 ");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 1 ] View payment method(s)            .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t-------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t                                MY CREDIT CARD                                     ");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 2 ] Add credit card                   .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 3 ] Update credit card                .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 4 ] Remove credit card                .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 5 ] Clear credit card(s)              .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t-------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t                                 MY E-WALLET                                     ");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 6 ] View balance                      .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 7 ] Top Up                            .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t.'.'.''.'..'.'.'.'.'     [ 0 ] Back to HomePage                  .'.'.''.'..'.'.'.'.'");
        System.out.println("\t\t\t\t-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-");
        System.out.print("\n\t\t\t\tEnter your selection: ");
        return s.nextInt();
    }

    public static void viewPaymentMethod(Iterator<PaymentMethod> paymentMethodIterator) {
        System.out.println("\n\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-_-_-_-_-_      My Payment Method      _-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("\t\t\t\t=====================================================================================");
        int position = 1;
        // display all payment methods
        while (paymentMethodIterator.hasNext()) {
            System.out.println("\t\t\t\t" + position + ". " + paymentMethodIterator.next());
            position++;
        }
    }

// my credit card
    public static void addCreditCard(String cardNoRegex, String cvvRegex, Customer currentLogin) {
        Scanner s = new Scanner(System.in);
        OUTER:
        while (true) {
            System.out.print("\n\t\t\t\tDo you want to add new credit card? [Y/N]\t: ");
            String selection = s.nextLine();
            switch (selection) {
                case "Y":
                    // input credit card information and validate the information
                    String cardNo;
                    while (true) {
                        System.out.print("\t\t\t\tCard Number\t\t: ");
                        cardNo = s.nextLine();
                        if (isValid(cardNo, cardNoRegex) == false) {
                            System.out.println("\t\t\t\tInvalid credit card format.");
                        } else {
                            break;
                        }
                    }
                    System.out.print("\t\t\t\tExpiry Date[mm/yy]\t: ");
                    String date = s.nextLine();
                    YearMonth expiryDate = YearMonth.parse(date, DateTimeFormatter.ofPattern("MM/yy"));
                    String cvv;
                    while (true) {
                        System.out.print("\t\t\t\tCVV\t\t\t: ");
                        cvv = s.nextLine();
                        if (isValid(cvv, cvvRegex) == false) {
                            System.out.println("\t\t\t\tInvalid cvv format.");
                        } else {
                            break;
                        }
                    }
                    // add credit card to paymentMethodList
                    if (currentLogin.getPaymentMethodList().add(new CreditCard(cardNo, expiryDate, cvv))) {
                        System.out.println("\t\t\t\tCredit card has been added successfully!");
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

    public static void updateCreditCard(String cardNoRegex, String cvvRegex, Customer currentLogin, ArrayList<PaymentMethod> copiedCreditCard) {
        Scanner s = new Scanner(System.in);
        // check if credit Cart List is empty
        if (copiedCreditCard.isEmpty()) {
            System.out.println("\n\t\t\t\tThere is no saved credit card.");
        } else {
            OUTER:
            while (true) {
                // display all credit cards for customer to choose which credit card to edit
                displayCreditCard(copiedCreditCard);
                System.out.print("\n\t\t\t\tDo you want to edit credit card? [Y/N]\t: ");
                String selection = s.nextLine();
                switch (selection) {
                    case "Y":
                        System.out.print("\t\t\t\tCredit card to edit\t: ");
                        int position = s.nextInt();
                        s.nextLine();
                        // check if input position is between 1 and numberOfEntries
                        if (position < 1 || position > copiedCreditCard.getNumberOfEntries()) {
                            System.out.println("\t\t\t\tInvalid input. Please select between 1 to " + copiedCreditCard.getNumberOfEntries());
                            break;
                        } else {
                            // input credit card information and validate the information
                            String cardNo;
                            while (true) {
                                System.out.print("\t\t\t\tCard Number\t\t: ");
                                cardNo = s.nextLine();
                                if (isValid(cardNo, cardNoRegex) == false) {
                                    System.out.println("\t\t\t\tInvalid credit card format.");
                                } else {
                                    break;
                                }
                            }
                            System.out.print("\t\t\t\tExpiry Date[mm/yy]\t: ");
                            String date = s.nextLine();
                            YearMonth expiryDate = YearMonth.parse(date, DateTimeFormatter.ofPattern("MM/yy"));
                            String cvv;
                            while (true) {
                                System.out.print("\t\t\t\tCVV\t\t\t: ");
                                cvv = s.nextLine();
                                if (isValid(cvv, cvvRegex) == false) {
                                    System.out.println("\t\t\t\tInvalid cvv format.");
                                } else {
                                    break;
                                }
                            }
                            // replace credit card at specific position
                            if (currentLogin.getPaymentMethodList().replace(position + 1, new CreditCard(cardNo, expiryDate, cvv))) {
                                copiedCreditCard.replace(position, new CreditCard(cardNo, expiryDate, cvv));
                                System.out.println("\t\t\t\tCredit card has been edited successfully!");
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

    public static void removeCreditCard(Customer currentLogin, ArrayList<PaymentMethod> copiedCreditCard) {
        Scanner s = new Scanner(System.in);
        // check if credit card list is empty
        if (copiedCreditCard.isEmpty()) {
            System.out.println("\n\t\t\t\tThere is no saved cresit card.");
        } else {
            OUTER:
            while (true) {
                // display all credit cards for customer to choose which credit card to remove
                displayCreditCard(copiedCreditCard);
                System.out.print("\n\t\t\t\tDo you want to remove credit card? [Y/N]\t: ");
                String selection = s.nextLine();
                switch (selection) {
                    case "Y":
                        System.out.print("\t\t\t\tCredit card to remove: ");
                        int position = s.nextInt();
                        s.nextLine();
                        // check if input position is between 1 and numberOfEntries
                        if (position < 1 || position > copiedCreditCard.getNumberOfEntries()) {
                            System.out.println("\t\t\t\tInvalid input. Please select between 1 to " + copiedCreditCard.getNumberOfEntries());
                            break;
                        } else {
                            // remove the cresit card at specific position
                            System.out.println("\t\t\t\t" + currentLogin.getPaymentMethodList().remove(position + 1) + " has been removed successfully!");
                            copiedCreditCard.remove(position);
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

    public static void clearCreditCard(Customer currentLogin, ArrayList<PaymentMethod> copiedCreditCard) {
        Scanner s = new Scanner(System.in);
        if (!copiedCreditCard.isEmpty()) {
            // display all credit cards
            displayCreditCard(copiedCreditCard);
            System.out.print("\n\t\t\t\tDo you want to remove all credit card(s)? [Y/N]: ");
            String selection = s.nextLine();
            OUTER:
            while (true) {
                switch (selection) {
                    case "Y":
                        // remove paymentMethods in paymentMethodList from position 2 as position 1 stored ewallet
                        currentLogin.getPaymentMethodList().removeFrom(2);
                        System.out.println("\t\t\t\tAll credit card(s) has been removed successfully");
                        break OUTER;
                    case "N":
                        break OUTER;
                    default:
                        System.out.println("\t\t\t\tInvalid input. Please select again.");
                        break;
                }
            }
        } else {
            System.out.println("\n\t\t\t\tThere is no saved credit card.");
        }
    }

// my e-wallet
    public static void viewBalance(Customer currentLogin) {
        System.out.println("\n\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-_-_-_-_-_         My E-wallet         _-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("\t\t\t\t=====================================================================================");
        for (int i = 1; i <= currentLogin.getPaymentMethodList().getNumberOfEntries(); i++) {
            if (currentLogin.getPaymentMethodList().getEntry(i) instanceof Ewallet) {
                Ewallet ewallet = (Ewallet) currentLogin.getPaymentMethodList().getEntry(i);
                // display the balance if the entry is instance of e-wallet
                System.out.println("\t\t\t\t" + ewallet);
            }
        }
    }

    public static void topUpEwallet(Customer currentLogin) {
        Scanner s = new Scanner(System.in);
        System.out.print("\t\t\t\tTop Up Amount\t: ");
        double amount = s.nextDouble();
        for (int i = 1; i <= currentLogin.getPaymentMethodList().getNumberOfEntries(); i++) {
            if (currentLogin.getPaymentMethodList().getEntry(i) instanceof Ewallet) {
                Ewallet ewallet = (Ewallet) currentLogin.getPaymentMethodList().getEntry(i);
                // top up e-wallet with the amount
                ewallet.topUpBalance(amount);
                System.out.println("\t\t\t\tE-wallet has been top up successfully!");
                System.out.println("\t\t\t\t" + ewallet);
            }
        }
    }

    public static void displayCreditCard(ArrayList<PaymentMethod> copiedCreditCard) {
        System.out.println("\n\t\t\t\t=====================================================================================");
        System.out.println("\t\t\t\t _-_-_-_-_-_-_-_-_-_-_-_-_-_       My Credit Card        _-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("\t\t\t\t=====================================================================================");
        for (int i = 1; i <= copiedCreditCard.getNumberOfEntries(); i++) {
            System.out.println("\t\t\t\t" + i + ". " + copiedCreditCard.getEntry(i));
        }
    }

    public static boolean isValid(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
