package dao;

import model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private final Connection con;

    public PaymentDAO(Connection con) {
        this.con = con;
    }

    public boolean insertPayment(Payment p) throws SQLException {
        String sql = "INSERT INTO payments (nic, room_price, package_price, ac_charge, total_amount, payment_date, payment_method, invoice_no) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNic());
            ps.setDouble(2, p.getRoomPrice());
            ps.setDouble(3, p.getPackagePrice());
            ps.setDouble(4, p.getAcCharge());
            ps.setDouble(5, p.getTotalAmount());
            ps.setDate(6, p.getPaymentDate());
            ps.setString(7, p.getPaymentMethod());
            ps.setString(8, p.getInvoiceNo());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updatePayment(Payment p) throws SQLException {
        String sql = "UPDATE payments SET nic=?, room_price=?, package_price=?, ac_charge=?, total_amount=?, payment_date=?, payment_method=? WHERE invoice_no=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNic());
            ps.setDouble(2, p.getRoomPrice());
            ps.setDouble(3, p.getPackagePrice());
            ps.setDouble(4, p.getAcCharge());
            ps.setDouble(5, p.getTotalAmount());
            ps.setDate(6, p.getPaymentDate());
            ps.setString(7, p.getPaymentMethod());
            ps.setString(8, p.getInvoiceNo());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deletePayment(String invoiceNo) throws SQLException {
        String sql = "DELETE FROM payments WHERE invoice_no = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, invoiceNo);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Payment> getAllPayments() throws SQLException {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Payment p = new Payment(
                        rs.getString("nic"),
                        rs.getDouble("room_price"),
                        rs.getDouble("package_price"),
                        rs.getDouble("ac_charge"),
                        rs.getDouble("total_amount"),
                        rs.getDate("payment_date"),
                        rs.getString("payment_method"),
                        rs.getString("invoice_no")
                );
                list.add(p);
            }
        }
        return list;
    }
}
