package model;

import java.sql.Date;

public class Reservation {
    private String name;
    private String nic;
    private String email;
    private String phone;
    private String gender;
    private String address;
    private Date checkInDate;
    private Date checkOutDate;
    private String roomType;
    private String roomNumber;
    private String packageName;

    public Reservation(String name, String nic, String email, String phone, String gender,
                       String address, Date checkInDate, Date checkOutDate,
                       String roomType, String roomNumber, String packageName) {
        this.name = name;
        this.nic = nic;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.packageName = packageName;
    }

    // Getters
    public String getName() { return name; }
    public String getNic() { return nic; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getGender() { return gender; }
    public String getAddress() { return address; }
    public Date getCheckInDate() { return checkInDate; }
    public Date getCheckOutDate() { return checkOutDate; }
    public String getRoomType() { return roomType; }
    public String getRoomNumber() { return roomNumber; }
    public String getPackageName() { return packageName; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setNic(String nic) { this.nic = nic; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setGender(String gender) { this.gender = gender; }
    public void setAddress(String address) { this.address = address; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
}
