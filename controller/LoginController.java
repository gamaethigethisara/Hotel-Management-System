package controller;

import model.UserLoginModel;
import view.AdminDashboard;
import view.UserDashboard;

import javax.swing.*;
import java.sql.*;

public class LoginController {

    public static void login(UserLoginModel user) {
        String email = user.getEmail();
        String password = user.getPassword();

        //  Validate empty fields and STOP further execution
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return; // 🛑 Important: stop further code from running
        }

        //  Admin credentials check
        if (email.equals("admin") && password.equals("admin")) {
            new AdminDashboard().setVisible(true);
            return; // 🛑 Avoid continuing to DB check
        }

        try {
            Connection con = util.DBConnect.getConnection();

            //  SQL query to check for an active user with matching credentials
            String query = "SELECT * FROM users WHERE email=? AND password=? AND accessibility='Active'";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                //  If user is found, load Admin Dashboard 
                new UserDashboard().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "DB Error: " + ex.getMessage());
        }
    }
}
