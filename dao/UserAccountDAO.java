package dao;

import model.UserAccount;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserAccountDAO {
    private final Connection con; // Database connection

    public UserAccountDAO(Connection con) {
        this.con = con;
    }

    // Add a new user to the database
    public boolean addUser(UserAccount user) throws SQLException {
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

    // Update user data by email
    public boolean updateUser(UserAccount user) throws SQLException {
        String sql = "UPDATE users SET name=?, nic=?, password=?, accessibility=? WHERE email=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getNic());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getAccessibility());
            ps.setString(5, user.getEmail());
            return ps.executeUpdate() > 0;
        }
    }

    // Delete user by email
    public boolean deleteUser(String email) throws SQLException {
        String sql = "DELETE FROM users WHERE email=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            return ps.executeUpdate() > 0;
        }
    }

    //  Check if email or NIC already exists (combined check)
    public boolean isDuplicate(String email, String nic) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? OR nic = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, nic);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true if duplicate found
        }
    }

    //  Check only NIC for duplication
    public boolean checkNICExists(String nic) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE nic = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nic);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true if NIC exists
        }
    }

    // Get all users from the database
    public List<UserAccount> getAllUsers() throws SQLException {
        List<UserAccount> list = new ArrayList<>();
        String sql = "SELECT name, email, nic, password, accessibility FROM users";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new UserAccount(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("nic"),
                        rs.getString("password"),
                        rs.getString("accessibility")
                ));
            }
        }
        return list;
    }
}
