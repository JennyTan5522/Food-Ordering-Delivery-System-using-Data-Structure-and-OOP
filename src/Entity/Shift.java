package Entity;

import java.time.LocalDateTime;

public class Shift {

    private LocalDateTime[] bookedShiftTime;

    public Shift(LocalDateTime[] bookedShiftTime) {
        this.bookedShiftTime = bookedShiftTime;
    }

    public LocalDateTime[] getBookedShiftTime() {
        return bookedShiftTime;
    }
    public void setBookedShiftTime(LocalDateTime[] bookedShiftTime) {
        this.bookedShiftTime = bookedShiftTime;
    }
}
