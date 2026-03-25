import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int nights;
    private double totalCost;

    public Reservation(String reservationId, String guestName, String roomType, int nights, double totalCost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
        this.totalCost = totalCost;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    public double getTotalCost() {
        return totalCost;
    }
}

class BookingHistory {
    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

class BookingReportService {

    public void printAllBookings(List<Reservation> reservations) {
        System.out.println("Booking History:");
        for (Reservation r : reservations) {
            System.out.println("ID: " + r.getReservationId() +
                    ", Guest: " + r.getGuestName() +
                    ", Room: " + r.getRoomType() +
                    ", Nights: " + r.getNights() +
                    ", Cost: " + r.getTotalCost());
        }
    }

    public double calculateTotalRevenue(List<Reservation> reservations) {
        double total = 0.0;
        for (Reservation r : reservations) {
            total += r.getTotalCost();
        }
        return total;
    }
}

public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        history.addReservation(new Reservation("R001", "John", "Single", 2, 2000.0));
        history.addReservation(new Reservation("R002", "Alice", "Double", 3, 4500.0));
        history.addReservation(new Reservation("R003", "Bob", "Suite", 1, 3000.0));

        reportService.printAllBookings(history.getAllReservations());

        double revenue = reportService.calculateTotalRevenue(history.getAllReservations());

        System.out.println("Total Revenue: " + revenue);
    }
}