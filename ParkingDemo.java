import java.util.Scanner;

public class ParkingDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of floors: ");
        int floors = sc.nextInt();
        System.out.print("Enter lanes per floor: ");
        int lanes = sc.nextInt();
        System.out.print("Enter slots per lane: ");
        int slots = sc.nextInt();

        ParkingSystem ps = new ParkingSystem(floors, lanes, slots);

        while (true) {
            System.out.println("\n--- Parking Menu (Phase 3) ---");
            System.out.println("1. Car Arrives (enqueue)");
            System.out.println("2. Allocate one car from queue");
            System.out.println("3. Allocate all waiting cars");
            System.out.println("4. Car Exit");
            System.out.println("5. View waiting queue");
            System.out.println("6. Display parking layout");
            System.out.println("7. Locate Car");
            System.out.println("8. Parking Stats");
            System.out.println("9. Exit Program");
            System.out.print("Choose: ");
            int ch = -1;
            try {
                ch = sc.nextInt();
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Invalid input.");
                continue;
            }

            switch (ch) {
                case 1:
                    System.out.print("Enter Car Number: ");
                    String carNum = sc.next();
                    ps.arrive(new Car(carNum));
                    break;
                case 2:
                    ps.allocateOneFromQueue();
                    break;
                case 3:
                    ps.allocateAllFromQueue();
                    break;
                case 4:
                    System.out.print("Enter Car Number to Exit: ");
                    String carExit = sc.next();
                    ps.exit(carExit);
                    break;
                case 5:
                    ps.viewWaitingQueue();
                    break;
                case 6:
                    ps.displayLayout();
                    break;
                case 7:
                    System.out.print("Enter Car Number to Locate: ");
                    String cnum = sc.next();
                    ps.locateCar(cnum);
                    break;
                case 8:
                    ps.stats();
                    break;
                case 9:
                    System.out.println("Exiting.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid.");
            }
        }
    }
}
