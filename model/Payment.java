package model;

import java.sql.Date;

public class Payment {
    private String nic;
    private double roomPrice;
    private double packagePrice;
    private double acCharge;
    private double totalAmount;
    private Date paymentDate;
    private String paymentMethod;
    private String invoiceNo;

    public Payment(String nic, double roomPrice, double packagePrice, double acCharge, double totalAmount, Date paymentDate, String paymentMethod, String invoiceNo) {
        this.nic = nic;
        this.roomPrice = roomPrice;
        this.packagePrice = packagePrice;
        this.acCharge = acCharge;
        this.totalAmount = totalAmount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.invoiceNo = invoiceNo;
    }

    // Getters
    public String getNic() { return nic; }
    public double getRoomPrice() { return roomPrice; }
    public double getPackagePrice() { return packagePrice; }
    public double getAcCharge() { return acCharge; }
    public double getTotalAmount() { return totalAmount; }
    public Date getPaymentDate() { return paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getInvoiceNo() { return invoiceNo; }

    // Setters
    public void setNic(String nic) { this.nic = nic; }
    public void setRoomPrice(double roomPrice) { this.roomPrice = roomPrice; }
    public void setPackagePrice(double packagePrice) { this.packagePrice = packagePrice; }
    public void setAcCharge(double acCharge) { this.acCharge = acCharge; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }
}
