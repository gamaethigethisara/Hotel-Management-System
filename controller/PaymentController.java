package controller;

import model.Payment;
//import util.DBConnect;

import javax.swing.*;
import java.sql.*;
import java.util.List;

public class PaymentController {

	// Database connection
    private final Connection con;

 // Constructor to initialize the controller with a database connection
    public PaymentController(Connection con) {
        this.con = con;
    }

    //Inserts a new payment record into the database
    public boolean insertPayment(Payment p) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO payments (nic, room_price, package_price, ac_charge, total_amount, payment_date, payment_method, invoice_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, p.getNic());
            ps.setDouble(2, p.getRoomPrice());
            ps.setDouble(3, p.getPackagePrice());
            ps.setDouble(4, p.getAcCharge());
            ps.setDouble(5, p.getTotalAmount());
            ps.setDate(6, p.getPaymentDate());
            ps.setString(7, p.getPaymentMethod());
            ps.setString(8, p.getInvoiceNo());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Insert Error: " + e.getMessage());
            return false;
        }
    }

    //Updates an existing payment based on invoice number
    public boolean updatePayment(Payment p) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE payments SET nic=?, room_price=?, package_price=?, ac_charge=?, total_amount=?, payment_date=?, payment_method=? WHERE invoice_no=?"
            );
            ps.setString(1, p.getNic());
            ps.setDouble(2, p.getRoomPrice());
            ps.setDouble(3, p.getPackagePrice());
            ps.setDouble(4, p.getAcCharge());
            ps.setDouble(5, p.getTotalAmount());
            ps.setDate(6, p.getPaymentDate());
            ps.setString(7, p.getPaymentMethod());
            ps.setString(8, p.getInvoiceNo());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Update Error: " + e.getMessage());
            return false;
        }
    }

    //Deletes a payment record from the database using invoice number
    public boolean deletePayment(String invoiceNo) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM payments WHERE invoice_no=?");
            ps.setString(1, invoiceNo);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Delete Error: " + e.getMessage());
            return false;
        }
    }

    //Retrieves all payment records from the database
    public List<Payment> getAllPayments() {
        List<Payment> list = new java.util.ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM payments");
            while (rs.next()) {
                list.add(new Payment(
                    rs.getString("nic"),
                    rs.getDouble("room_price"),
                    rs.getDouble("package_price"),
                    rs.getDouble("ac_charge"),
                    rs.getDouble("total_amount"),
                    rs.getDate("payment_date"),
                    rs.getString("payment_method"),
                    rs.getString("invoice_no")
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Load Error: " + e.getMessage());
        }
        return list;
    }

    // ✅ Method to fill prices by NIC (for use from ManagePaymentView)
    public void fillPricesByNIC(JTextField tfNIC, JTextField tfRoomPrice, JTextField tfPackagePrice, JTextField tfAC, JTextField tfTotal) {
        String nic = tfNIC.getText().trim();
        if (nic.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter NIC to fetch data.");
            return;
        }

        try {
            PreparedStatement ps = con.prepareStatement("SELECT room_number, package_name, room_type FROM reservations WHERE nic=?");
            ps.setString(1, nic);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String roomNo = rs.getString("room_number");
                String packageName = rs.getString("package_name");
                String roomType = rs.getString("room_type");

                double roomPrice = 0, pkgPrice = 0, ac = 0;

                PreparedStatement psRoom = con.prepareStatement("SELECT price_per_day FROM rooms WHERE room_number=?");
                psRoom.setString(1, roomNo);
                ResultSet rsRoom = psRoom.executeQuery();
                if (rsRoom.next()) roomPrice = rsRoom.getDouble("price_per_day");

                PreparedStatement psPkg = con.prepareStatement("SELECT price FROM manage_packages WHERE package_name=?");
                psPkg.setString(1, packageName);
                ResultSet rsPkg = psPkg.executeQuery();
                if (rsPkg.next()) pkgPrice = rsPkg.getDouble("price");

                if ("AC".equalsIgnoreCase(roomType)) ac = 2000;

                //Calculate total
                double total = roomPrice + pkgPrice + ac;
                tfRoomPrice.setText(String.valueOf(roomPrice));
                tfPackagePrice.setText(String.valueOf(pkgPrice));
                tfAC.setText(String.valueOf(ac));
                tfTotal.setText(String.valueOf(total));
            } else {
                JOptionPane.showMessageDialog(null, "No reservation found for given NIC.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DB Error: " + e.getMessage());
        }
    }
}
