import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Magazine extends Material implements Borrowable {

    private boolean isBorrowed;
    private LocalDate borrowedDate;
    private static final int LOAN_DAYS = 7;

    public Magazine(String id, String title, int publicationYear) {
        super(id, title, publicationYear);
        this.isBorrowed = false;
    }

    @Override
    public String toString() {
        String status = isBorrowed ? "Borrowed" : "Available";
        return String.format("Mag. | ID: %s | Title: %s | Year: %d | Status: %s",
                getId(), getTitle(), getPublicationYear(), status);
    }

    @Override
    public String toFileFormat() {
        return "MAG," + getId() + "," + getTitle() + "," + getPublicationYear() + "," + isBorrowed;
    }

    @Override
    public void borrow() {
        this.isBorrowed = true;
        this.borrowedDate = LocalDate.now();
    }

    @Override
    public void returnItem() {
        this.isBorrowed = false;
        this.borrowedDate = null;
    }

    @Override
    public boolean isAvailableToBorrow() {
        return !isBorrowed;
    }

    @Override
    public int getRemainingDays() {
        if (!isBorrowed || borrowedDate == null) return 0;
        long passed = ChronoUnit.DAYS.between(borrowedDate, LocalDate.now());
        long remaining = LOAN_DAYS - passed;
        return (int)Math.max(0, remaining);
    }

    public void setIsBorrowed(boolean val) {
        this.isBorrowed = val;
    }
}