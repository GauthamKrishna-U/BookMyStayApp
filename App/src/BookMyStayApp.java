import java.util.Scanner;

public class BookMyStayApp {

    public static void main(String[] args) {

        /**
         * MAIN CLASS UseCase2RoomInitialization
         *
         * Use Case 2: Basic Room Types & Static Availability
         * @version 2.1
         */


                System.out.println("Hotel Room Initialization\n");

                // Create room objects (Polymorphism)
                Room singleRoom = new SingleRoom();
                Room doubleRoom = new DoubleRoom();
                Room suiteRoom = new SuiteRoom();

                // Static availability (separate from domain)
                int singleAvailable = 5;
                int doubleAvailable = 3;
                int suiteAvailable = 2;

                // Display Single Room
                System.out.println("Single Room:");
                singleRoom.displayRoomDetails();
                System.out.println("Available: " + singleAvailable);
                System.out.println();

                // Display Double Room
                System.out.println("Double Room:");
                doubleRoom.displayRoomDetails();
                System.out.println("Available: " + doubleAvailable);
                System.out.println();

                // Display Suite Room
                System.out.println("Suite Room:");
                suiteRoom.displayRoomDetails();
                System.out.println("Available: " + suiteAvailable);
            }
        }

/**
 * ABSTRACT CLASS Room
 * Represents a generic hotel room
 * @version 2.1
 */
        abstract class Room {

            protected int numberOfBeds;
            protected int squareFeet;
            protected double pricePerNight;

            public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
                this.numberOfBeds = numberOfBeds;
                this.squareFeet = squareFeet;
                this.pricePerNight = pricePerNight;
            }

            public void displayRoomDetails() {
                System.out.println("Beds: " + numberOfBeds);
                System.out.println("Size: " + squareFeet + " sqft");
                System.out.println("Price per night: " + pricePerNight);
            }
        }

/**
 * Single Room Class
 */
        class SingleRoom extends Room {

            public SingleRoom() {
                super(1, 250, 1500.0);
            }
        }

/**
 * Double Room Class
 */
        class DoubleRoom extends Room {

            public DoubleRoom() {
                super(2, 400, 2500.0);
            }
        }

/**
 * Suite Room Class
 */
        class SuiteRoom extends Room {

            public SuiteRoom() {
                super(3, 750, 5000.0);
            }
        }