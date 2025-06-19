package controller;

import dao.UserAccountDAO;
import model.UserAccount;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class UserAccountController {

    private final UserAccountDAO userDAO;

    public UserAccountController(Connection con) {
        this.userDAO = new UserAccountDAO(con);
    }

    // Add user with duplicate check
    public boolean addUser(UserAccount user) {
        try {
            // Check if email or NIC already exists
            if (userDAO.isDuplicate(user.getEmail(), user.getNic())) {
                throw new Exception("Email or NIC already exists.");
            }
            return userDAO.addUser(user);
        } catch (Exception e) {
            System.err.println("Error adding user: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Add Error: " + e.getMessage());
            return false;
        }
    }

    // Update user
    public boolean updateUser(UserAccount user) {
        try {
            return userDAO.updateUser(user);
        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    // Delete user by email
    public boolean deleteUser(String email) {
        try {
            return userDAO.deleteUser(email);
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    // Retrieve all users
    public List<UserAccount> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (Exception e) {
            System.err.println("Error loading users: " + e.getMessage());
            return null;
        }
    }

    //  New method to check for duplicate email or NIC
    public boolean isDuplicate(String email, String nic) {
        try {
            return userDAO.isDuplicate(email, nic);
        } catch (Exception e) {
            System.err.println("Error checking duplicates: " + e.getMessage());
            return true; // safer to block if any error occurs
        }
    }
}
