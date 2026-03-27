import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingSystem {
    List<Floor> floors = new ArrayList<>();
    MyQueue<Car> entryQueue = new MyQueue<>();
    HashMap<String, Slot> carSlotMap = new HashMap<>();

    public ParkingSystem(int numFloors, int lanesPerFloor, int slotsPerLane) {
        for (int i = 1; i <= numFloors; i++) {
            floors.add(new Floor("F" + i, lanesPerFloor, slotsPerLane));
        }
    }

    public void arrive(Car c) {
        entryQueue.enqueue(c);
        System.out.println("Car " + c.number + " added to entry queue.");
    }

    // allocate one car using nearest-to-entry rule (floor order, lane order)
    public void allocateOneFromQueue() {
        if (entryQueue.isEmpty()) {
            System.out.println("No cars waiting.");
            return;
        }
        Car c = entryQueue.dequeue();
        for (Floor f : floors) {
            if (!f.isFull()) {
                Slot s = f.assignSlot(c);
                if (s != null) {
                    carSlotMap.put(c.number, s);
                    System.out.println("Car " + c.number + " parked at " + s.slotId);
                    return;
                }
            }
        }
        System.out.println("Parking full! Car " + c.number + " cannot be parked.");
    }

    // allocate all waiting cars (useful to quickly fill)
    public void allocateAllFromQueue() {
        while (!entryQueue.isEmpty()) allocateOneFromQueue();
    }

    // exit car
    public void exit(String carNumber) {
        if (!carSlotMap.containsKey(carNumber)) {
            System.out.println("Car " + carNumber + " not found in parking.");
            return;
        }
        Slot s = carSlotMap.get(carNumber);
        s.free();
        carSlotMap.remove(carNumber);
        // find the lane that owns this slot and return it
        boolean returned = false;
        for (Floor f : floors) {
            for (Lane l : f.lanes) {
                for (Slot slot : l.getSlots()) {
                    if (slot.slotId.equals(s.slotId)) {
                        l.freeSlot(s);
                        returned = true;
                        break;
                    }
                }
                if (returned) break;
            }
            if (returned) break;
        }
        System.out.println("Car " + carNumber + " has exited from " + s.slotId);
    }

    public void displayLayout() {
        System.out.println("\n--- Parking Layout (Driveways shown) ---");
        for (Floor f : floors) {
            System.out.println("Floor " + f.floorId + " (Available: " + f.availableSlots() + "/" + f.totalSlots() + ")");
            for (Lane l : f.lanes) {
                System.out.print("  " + l.laneId + ": ");
                for (Slot s : l.getSlots()) {
                    System.out.print(s.toString() + " ");
                }
                System.out.println();
            }
            System.out.println("  ----- DRIVEWAY -----");
        }
    }

    public void viewWaitingQueue() {
        System.out.println("Waiting Queue (" + entryQueue.size() + "): " + entryQueue.asList());
    }

    public void locateCar(String carNumber) {
        if (!carSlotMap.containsKey(carNumber)) {
            System.out.println("Car " + carNumber + " not parked here.");
            return;
        }
        Slot s = carSlotMap.get(carNumber);
        System.out.println("Car " + carNumber + " is parked at " + s.slotId);
    }

    public void stats() {
        int total = 0;
        int available = 0;
        System.out.println("\n--- Parking Stats ---");
        for (Floor f : floors) {
            int tf = f.totalSlots();
            int av = f.availableSlots();
            System.out.println(f.floorId + " -> Total: " + tf + " Available: " + av + " Occupied: " + (tf - av));
            total += tf;
            available += av;
        }
        System.out.println("Overall -> Total: " + total + " Available: " + available + " Occupied: " + (total - available));
    }

    // For debugging / internal use
    public List<String> parkedCarsList() {
        return new ArrayList<>(carSlotMap.keySet());
    }
}
