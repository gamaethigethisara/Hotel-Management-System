package controller;

import dao.PackageDAO;
import model.Package;

import java.sql.Connection;
import java.util.List;

public class PackageController {

	// DAO to interact with the database
    private final PackageDAO packageDAO;
    

    // Constructor initializes the DAO with DB connection
    public PackageController(Connection con) {
        this.packageDAO = new PackageDAO(con);
    }
 // Add a new package to the database
    public boolean addPackage(Package pkg) {
        try {
            return packageDAO.addPackage(pkg);
        } catch (Exception e) {
            System.err.println("Error adding package: " + e.getMessage());
            return false;
        }
    }
    // Update existing package details
    public boolean updatePackage(Package pkg) {
        try {
            return packageDAO.updatePackage(pkg);
        } catch (Exception e) {
            System.err.println("Error updating package: " + e.getMessage());
            return false;
        }
    }
 // Delete a package by its ID
    public boolean deletePackage(String id) {
        try {
            return packageDAO.deletePackage(id);
        } catch (Exception e) {
            System.err.println("Error deleting package: " + e.getMessage());
            return false;
        }
    }
 // Retrieve a list of all packages from the database
    public List<Package> getAllPackages() {
        try {
            return packageDAO.getAllPackages();
        } catch (Exception e) {
            System.err.println("Error fetching packages: " + e.getMessage());
            return null;
        }
    }
}
