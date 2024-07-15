package Entity;

import ADT.Customer.*;
import java.time.LocalDate;
import java.util.Objects;

public class Rider extends User {
    private String icNo;
    private String vehiclePlate;
    private String emergencyName;
    private String emergencyPhone;
    private LocalDate dateOfBirth;
    private LocalDate joinedDate;
    private ArrayList<Shift> shift;
    private static int riderCount = 1;
    
    public Rider(String userId, String password, String name, String contactNo,ArrayList<Address> address, String icNo, String vehiclePlate, String emergencyName, String emergencyPhone, LocalDate birthOfBirth, LocalDate joinedDate, ArrayList<Shift> shift) {
        super(userId, name,password,contactNo, address);
        this.icNo = icNo;
        this.vehiclePlate = vehiclePlate;
        this.emergencyName = emergencyName;
        this.emergencyPhone = emergencyPhone;
        this.dateOfBirth = birthOfBirth;
        this.joinedDate = joinedDate;
        this.shift=shift;
    }
    

    public Rider(String password, String name, String contactNo, ArrayList<Address> address, String icNo, String vehiclePlate, String emergencyName, String emergencyPhone, LocalDate dateOfBirth, LocalDate joinedDate, ArrayList<Shift> shift) {
        super(String.format("R%04d", riderCount++), name,password, contactNo, address);
        this.icNo = icNo;
        this.vehiclePlate = vehiclePlate;
        this.emergencyName = emergencyName;
        this.emergencyPhone = emergencyPhone;
        this.dateOfBirth = dateOfBirth;
        this.joinedDate = joinedDate;
        this.shift = shift;
    }

    public String getIcNo() {
        return icNo;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getEmergencyName() {
        return emergencyName;
    }

    public void setEmergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public ArrayList<Shift> getShift() {
        return shift;
    }

    public void setShift(ArrayList<Shift> shift) {
        this.shift = shift;
    }

    public int hashCode() {
        return icNo.hashCode() + vehiclePlate.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rider rider = (Rider) o;
        return Objects.equals(icNo, rider.icNo) && Objects.equals(vehiclePlate, rider.vehiclePlate) && Objects.equals(emergencyName, rider.emergencyName) && Objects.equals(emergencyPhone, rider.emergencyPhone) && Objects.equals(dateOfBirth, rider.dateOfBirth) && Objects.equals(joinedDate, rider.joinedDate) && Objects.equals(shift, rider.shift);
    }
}
