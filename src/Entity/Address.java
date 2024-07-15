package Entity;

public class Address implements Comparable<Address> {

    private String addressLine;
    private String postcode;
    private String district;
    private String state;

    public Address(String addressLine, String postcode, String district, String state) {
        this.addressLine = addressLine;
        this.district = district;
        this.postcode = postcode;
        this.state = state;
    }

    @Override
    public boolean equals(Object anotherAddress) {
        if (anotherAddress instanceof Address) {
            Address address = (Address) anotherAddress;
            return this.addressLine.equals(address.addressLine) && this.postcode.equals(address.postcode) && this.district.equals(address.district) && this.state.equals(address.state);
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Address anotherAddress) {
        return this.addressLine.compareToIgnoreCase(anotherAddress.addressLine);
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getDistrict() {
        return district;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getState() {
        return state;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setPostCode(String postcode) {
        this.postcode = postcode;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return String.format("%s, %s %s, %s. ", addressLine, postcode, district, state);
    }
}
