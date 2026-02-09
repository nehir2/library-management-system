import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Book extends Material implements Borrowable, Reservable {

    private String author;

    private boolean isBorrowed;
    private LocalDate borrowedDate;
    private static final int LOAN_DAYS = 14;

    private boolean reserved;
    private String reservationHolder;

    public Book(String isbn, String title, String author, int publicationYear) {
        super(isbn, title, publicationYear);
        this.author = author;
        this.isBorrowed = false;
        this.reserved = false;
        this.reservationHolder = null;
        this.borrowedDate = null;
    }

    public String getAuthor() { return author; }

    @Override
    public String toString() {
        String status;
        if (reserved) {
            status = "Reserved by " + reservationHolder;
        } else if (isBorrowed) {
            status = "Borrowed";
        } else {
            status = "Available";
        }
        return String.format("Book | ID: %s | Title: %s | Author: %s | Year: %d | Status: %s",
                getId(), getTitle(), author, getPublicationYear(), status);
    }

    @Override
    public String toFileFormat() {
        return "BOOK," + getId() + "," + getTitle() + "," + author + "," + getPublicationYear() + "," + isBorrowed + "," + reserved + "," + reservationHolder;
    }

    @Override
    public void borrow() {
        if (isAvailableToBorrow()) {
            this.isBorrowed = true;
            this.borrowedDate = LocalDate.now();
            if(this.reserved) {
                cancelReservation();
            }
        }
    }

    @Override
    public void returnItem() {
        this.isBorrowed = false;
        this.borrowedDate = null;
    }

    @Override
    public boolean isAvailableToBorrow() {
        return !isBorrowed && !reserved;
    }

    @Override
    public int getRemainingDays() {
        if (!isBorrowed || borrowedDate == null) return 0;
        long passed = ChronoUnit.DAYS.between(borrowedDate, LocalDate.now());
        long remaining = LOAN_DAYS - passed;
        return (int)Math.max(0, remaining);
    }

    @Override
    public void reserve(String holderName) {
        this.reserved = true;
        this.reservationHolder = holderName;
    }

    @Override
    public void cancelReservation() {
        this.reserved = false;
        this.reservationHolder = null;
    }

    @Override
    public boolean isReserved() {
        return reserved;
    }

    @Override
    public String getReservationHolder() {
        return reservationHolder;
    }

    public void setIsBorrowed(boolean val) { this.isBorrowed = val; }
}