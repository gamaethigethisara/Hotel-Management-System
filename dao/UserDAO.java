package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    private final Connection con;

    public UserDAO(Connection con) {
        this.con = con;
    }

    // Insert new user into the database
    public boolean insertUser(User user) throws Exception {
        String sql = "INSERT INTO users (name, email, nic, password, accessibility) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getNic());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getAccessibility());
            return ps.executeUpdate() > 0;
        }
    }

    // Check if user email or NIC already exists
    public boolean isDuplicate(String email, String nic) throws Exception {
        String sql = "SELECT * FROM users WHERE email = ? OR nic = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, nic);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // True if a duplicate exists
        }
    }
}
