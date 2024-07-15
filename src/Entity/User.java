package Entity;

/**
 *
 * @author xuanyi
 */
import ADT.Customer.*;

public class User {

    private String userId;
    private String password;
    private String name;
    private String contactNo;
    private ArrayList<Address> address;

    public User() {

    }
    
    public User(String name) {
        this.name = name;
    }

    public User(String userId, String name, String password, String contactNo) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.contactNo = contactNo;
    }

    //constructor with complete information
    public User(String userId, String name, String password, String contactNo, ArrayList<Address> address) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.contactNo = contactNo;
        this.address = address;

    }

    //ask nid constructor without address and contactNo? I think we juz standardise all must hv address and contactNo if not dk where to send to & who to call
    //getter
    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public ArrayList<Address> getAddress() {
        return address;
    }

    //setter 
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setAddress(ArrayList<Address> address) {
        this.address = address;
    }

    //methods
    //check does the name entered consist of only alphabet
    public boolean checkNameAlpha() {
        for (int i = 0; i < name.length(); i++) {
            //If have space in between the words then ignore the space
            if (name.charAt(i) == ' ') {
                i++;
            }

            //error message
            if (!Character.isLetter(name.charAt(i))) {
                System.out.println("The Name entered MUST contain ONLY alphabet!\n");
                return false;
            }
        }
        return true;

    }

    //change name value to lower case then convert the first alphabet of each word to upper case
    public boolean formatName() {

        boolean isAlpha = checkNameAlpha();

        //change all letter to lower case
        name = name.toLowerCase();

        // stores each character to a char array to change first letter of each word to upper case
        char[] charArray = name.toCharArray();
        boolean foundSpace = true;

        //if all the name entered is alphabet then only change first letter of each word to upper case
        if (isAlpha == true) {
            for (int i = 0; i < charArray.length; i++) {
                // if the array element is a letter then only check if space is present before the letter otherwise it is a space
                if (Character.isLetter(charArray[i])) {
                    if (foundSpace) {
                        //if there's space before the letter then change the letter into uppercase
                        charArray[i] = Character.toUpperCase(charArray[i]);
                        foundSpace = false;
                    }
                } else {
                    // if it is a space then return true
                    foundSpace = true;
                }
            }
        } else {
            return false;
        }
        // convert the char array back to string
        name = String.valueOf(charArray);
        return true;
    }

}
