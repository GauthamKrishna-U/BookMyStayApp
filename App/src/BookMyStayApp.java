import java.io.*;
import java.util.*;

class RoomInventory implements Serializable {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void display() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

class PersistenceService {

    public void saveInventory(RoomInventory inventory, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(inventory);
            System.out.println("Inventory saved successfully");
        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    public RoomInventory loadInventory(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (RoomInventory) ois.readObject();
        } catch (Exception e) {
            System.out.println("No valid Inventory data found. Starting fresh.");
            return new RoomInventory();
        }
    }
}

public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {

        System.out.println("System Recovery");

        String filePath = "inventory.dat";

        PersistenceService service = new PersistenceService();

        RoomInventory inventory = service.loadInventory(filePath);

        inventory.display();

        service.saveInventory(inventory, filePath);
    }
}