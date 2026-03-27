import java.util.List;

public class Lane {
    String laneId;
    DoublyLinkedList<Slot> slots = new DoublyLinkedList<>();
    MyQueue<Slot> freeSlots = new MyQueue<>();

    public Lane(String laneId, int totalSlots, String floorId) {
        this(laneId, totalSlots, floorId, Slot.SlotType.REGULAR);
    }

    public Lane(String laneId, int totalSlots, String floorId, Slot.SlotType defaultType) {
        this.laneId = laneId;
        for (int i = 1; i <= totalSlots; i++) {
            Slot s = new Slot(floorId + "-" + laneId + "-S" + i, defaultType);
            slots.addLast(s);
            freeSlots.enqueue(s);
        }
    }

    public boolean isFull() {
        return freeSlots.isEmpty();
    }

    public Slot assignSlot(Car c) {
        if (isFull()) return null;
        Slot s = freeSlots.dequeue();
        if (s != null) s.park(c);
        return s;
    }

    public Slot assignSlotByType(Car c, Slot.SlotType preferred) {
        if (freeSlots.isEmpty()) return null;
        for (Slot s : freeSlots.asList()) {
            if (s.type == preferred) {
                freeSlots.remove(s);
                s.park(c);
                return s;
            }
        }
        return assignSlot(c);
    }

    public void freeSlot(Slot s) {
        if (s == null) return;
        s.free();
        freeSlots.enqueue(s);
    }

    public List<Slot> getSlots() {
        return slots.toList();
    }

    public int totalSlotsCount() {
        return slots.toList().size();
    }

    public int availableSlotsCount() {
        return freeSlots.size();
    }
}
