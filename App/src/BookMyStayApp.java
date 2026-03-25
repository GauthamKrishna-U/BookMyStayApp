import java.util.*;

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

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public synchronized String allocateRoom(String roomType) {
        int count = inventory.getOrDefault(roomType, 0);
        if (count <= 0) return null;

        String roomId = roomType + "-" + count;
        inventory.put(roomType, count - 1);
        return roomId;
    }

    public synchronized void printInventory() {
        System.out.println("Remaining Inventory: " + inventory);
    }
}

class RoomAllocationService {
    public void allocate(Reservation r, RoomInventory inventory) {
        String roomId = inventory.allocateRoom(r.getRoomType());
        if (roomId != null) {
            System.out.println("Booking confirmed for Guest: " +
                    r.getGuestName() + ", Room ID: " + roomId);
        } else {
            System.out.println("Booking failed for Guest: " +
                    r.getGuestName() + " (No rooms available)");
        }
    }
}

class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(BookingRequestQueue bookingQueue,
                                      RoomInventory inventory,
                                      RoomAllocationService allocationService) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {
        while (true) {
            Reservation r;
            synchronized (bookingQueue) {
                r = bookingQueue.getRequest();
            }

            if (r == null) break;

            allocationService.allocate(r, inventory);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        bookingQueue.addRequest(new Reservation("Aoni", "Single"));
        bookingQueue.addRequest(new Reservation("Kanath", "Double"));
        bookingQueue.addRequest(new Reservation("Kurat", "Suite"));
        bookingQueue.addRequest(new Reservation("Soona", "Single"));

        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
        }

        inventory.printInventory();
    }
}