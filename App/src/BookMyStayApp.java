import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay App
 *
 * Demonstrates booking requests being queued fairly (FIFO).
 * @version 5.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        // Display application header
        System.out.println("Booking Request Queue\n");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add requests to the queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queued booking requests in FIFO order
        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.getNextRequest();
            System.out.println("Processing booking for Guest: "
                    + request.getGuestName()
                    + ", Room Type: "
                    + request.getRoomType());
        }
    }
}

/**
 * CLASS Reservation
 *
 * Represents a booking request by a guest
 * @version 5.0
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * CLASS BookingRequestQueue
 *
 * Manages booking requests using FIFO queue
 * @version 5.0
 */
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add a booking request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    // Retrieve and remove the next request (FIFO)
    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    // Check if there are pending requests
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}