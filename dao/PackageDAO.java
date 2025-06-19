package dao;

import model.Package;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageDAO {
	 // Database connection object
    private final Connection con;
    
    // Constructor initializes connection
    public PackageDAO(Connection con) {
        this.con = con;
    }
 // Insert a new package into the database
    public boolean addPackage(Package pkg) throws SQLException {
        String sql = "INSERT INTO manage_packages (id, package_name, description, duration, price, availability) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pkg.getId());
            ps.setString(2, pkg.getName());
            ps.setString(3, pkg.getDescription());
            ps.setString(4, pkg.getDuration());
            ps.setDouble(5, pkg.getPrice());
            ps.setString(6, pkg.getAvailability());
            return ps.executeUpdate() > 0;
        }
    }
    // Update existing package in the database
    public boolean updatePackage(Package pkg) throws SQLException {
        String sql = "UPDATE manage_packages SET package_name=?, description=?, duration=?, price=?, availability=? WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pkg.getName());
            ps.setString(2, pkg.getDescription());
            ps.setString(3, pkg.getDuration());
            ps.setDouble(4, pkg.getPrice());
            ps.setString(5, pkg.getAvailability());
            ps.setString(6, pkg.getId());
            return ps.executeUpdate() > 0;
        }
    }
 // Delete a package by its ID
    public boolean deletePackage(String id) throws SQLException {
        String sql = "DELETE FROM manage_packages WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id); // Set ID to delete
            
            return ps.executeUpdate() > 0; 
        }
    }
    // Fetch all packages from the database
    public List<Package> getAllPackages() throws SQLException {
        List<Package> list = new ArrayList<>(); // List to store packages
        String sql = "SELECT * FROM manage_packages";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
            	 // Create a Package object from each row
                Package pkg = new Package(
                    rs.getString("id"),
                    rs.getString("package_name"),
                    rs.getString("description"),
                    rs.getString("duration"),
                    rs.getDouble("price"),
                    rs.getString("availability")
                );
                list.add(pkg); // Add to list
            }
        }
        return list;  // Return the list of packages
    }
}
