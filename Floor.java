import java.util.ArrayList;
import java.util.List;

public class Floor {
    String floorId;
    List<Lane> lanes = new ArrayList<>();

    public Floor(String floorId, int lanesPerFloor, int slotsPerLane) {
        this.floorId = floorId;
        for (int i = 1; i <= lanesPerFloor; i++) {
            lanes.add(new Lane("L" + i, slotsPerLane, floorId));
        }
    }

    public boolean isFull() {
        for (Lane lane : lanes) {
            if (!lane.isFull()) return false;
        }
        return true;
    }

    public Slot assignSlot(Car c) {
        for (Lane lane : lanes) {
            if (!lane.isFull()) {
                return lane.assignSlot(c);
            }
        }
        return null;
    }

    public Slot assignSlotByType(Car c, Slot.SlotType preferred) {
        for (Lane lane : lanes) {
            if (!lane.isFull()) {
                Slot s = lane.assignSlotByType(c, preferred);
                if (s != null) return s;
            }
        }
        return null;
    }

    public int totalSlots() {
        int sum = 0;
        for (Lane l : lanes) sum += l.totalSlotsCount();
        return sum;
    }

    public int availableSlots() {
        int sum = 0;
        for (Lane l : lanes) sum += l.availableSlotsCount();
        return sum;
    }
}
