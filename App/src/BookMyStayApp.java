java id="uc10bk"
import java.util.*;

class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        this.active = false;
    }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void allocate(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void release(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }
}

class BookingHistory {
    private Map<String, Reservation> reservations;

    public BookingHistory() {
        reservations = new HashMap<>();
    }

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }
}

class CancellationService {
    private Stack<String> rollbackStack;

    public CancellationService() {
        rollbackStack = new Stack<>();
    }

    public void cancel(String reservationId, BookingHistory history, RoomInventory inventory) {
        Reservation r = history.getReservation(reservationId);

        if (r == null) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        if (!r.isActive()) {
            System.out.println("Cancellation Failed: Already cancelled.");
            return;
        }

        rollbackStack.push(r.getRoomId());
        inventory.release(r.getRoomType());
        r.cancel();

        System.out.println("Cancellation successful for ID: " + reservationId);
    }
}

public class UseCase10BookingCancellation {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancelService = new CancellationService();

        Reservation r1 = new Reservation("R101", "Single", "S1");

        if (inventory.isAvailable("Single")) {
            inventory.allocate("Single");
            history.addReservation(r1);
            System.out.println("Booking confirmed: " + r1.getReservationId());
        }

        cancelService.cancel("R101", history, inventory);

        cancelService.cancel("R101", history, inventory);

        cancelService.cancel("R999", history, inventory);
    }
}
