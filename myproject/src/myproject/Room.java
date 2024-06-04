package myproject;

public class Room {
    public enum RoomType { GOLD, SILVER, BRONZE }

    private int roomId;
    private RoomType roomType;
    private double price;

    // Constructor
    public Room(int roomId, RoomType roomType) {
        this.roomId = roomId;
        this.roomType = roomType;
        setPrice();
    }

    private void setPrice() {
        switch (roomType) {
            case GOLD:
                price = 10000;
                break;
            case SILVER:
                price = 8000;
                break;
            case BRONZE:
                price = 5000;
                break;
        }
    }

    // Getters
    // You can generate them using your IDE or manually

    // Additional methods if needed
}