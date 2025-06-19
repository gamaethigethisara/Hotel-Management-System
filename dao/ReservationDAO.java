package dao;

import model.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private final Connection con;

    public ReservationDAO(Connection con) {
        this.con = con;
    }

    // Add reservation
    public boolean addReservation(Reservation r) throws SQLException {
        String sql = "INSERT INTO reservations (name, nic, email, phone, gender, address, checkin_date, checkout_date, room_type, room_number, package_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getNic());
            ps.setString(3, r.getEmail());
            ps.setString(4, r.getPhone());
            ps.setString(5, r.getGender());
            ps.setString(6, r.getAddress());
            ps.setDate(7, r.getCheckInDate());
            ps.setDate(8, r.getCheckOutDate());
            ps.setString(9, r.getRoomType());
            ps.setString(10, r.getRoomNumber());
            ps.setString(11, r.getPackageName());
            return ps.executeUpdate() > 0;
        }
    }

    // Update reservation
    public boolean updateReservation(Reservation r, String originalNIC) throws SQLException {
        String sql = "UPDATE reservations SET name=?, nic=?, email=?, phone=?, gender=?, address=?, checkin_date=?, checkout_date=?, room_type=?, room_number=?, package_name=? WHERE nic=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getName());
            ps.setString(2, r.getNic());
            ps.setString(3, r.getEmail());
            ps.setString(4, r.getPhone());
            ps.setString(5, r.getGender());
            ps.setString(6, r.getAddress());
            ps.setDate(7, r.getCheckInDate());
            ps.setDate(8, r.getCheckOutDate());
            ps.setString(9, r.getRoomType());
            ps.setString(10, r.getRoomNumber());
            ps.setString(11, r.getPackageName());
            ps.setString(12, originalNIC);
            return ps.executeUpdate() > 0;
        }
    }

    // Delete reservation by NIC
    public boolean deleteReservation(String nic) throws SQLException {
        String sql = "DELETE FROM reservations WHERE nic=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nic);
            return ps.executeUpdate() > 0;
        }
    }

    // Get all reservations
    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservations";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Reservation r = new Reservation(
                        rs.getString("name"),
                        rs.getString("nic"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getDate("checkin_date"),
                        rs.getDate("checkout_date"),
                        rs.getString("room_type"),
                        rs.getString("room_number"),
                        rs.getString("package_name")
                );
                list.add(r);
            }
        }
        return list;
    }
}
