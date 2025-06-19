package model;

public class Room {
    private String roomNumber;
    private int bed;
    private double pricePerDay;
    private String status;

    public Room(String roomNumber, int bed, double pricePerDay, String status) {
        this.roomNumber = roomNumber;
        this.bed = bed;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public int getBed() {
        return bed;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public String getStatus() {
        return status;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
