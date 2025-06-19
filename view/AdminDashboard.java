package view;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class AdminDashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AdminDashboard frame = new AdminDashboard();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AdminDashboard() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080); 
        setLocationRelativeTo(null); 

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ✅ Header panel
        JPanel panel = new JPanel();
        panel.setBackground(UIManager.getColor("CheckBox.light"));
        panel.setBounds(0, 0, 1443, 60);
        panel.setLayout(null);
        contentPane.add(panel);

        JLabel lblWelcome = new JLabel("Welcome Admin");
        lblWelcome.setFont(new Font("Book Antiqua", Font.BOLD, 26));
        lblWelcome.setBounds(20, 15, 300, 30);
        panel.add(lblWelcome);

        JButton btnLogout = new JButton("Log out");
        btnLogout.setBounds(1231, 19, 80, 30);
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                AdminDashboard.this,
                "Do you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginView().setVisible(true);
                dispose();
            }
        });
        panel.add(btnLogout);

        JButton btnClose = new JButton("Close");
        btnClose.setBounds(1349, 19, 80, 30);
        btnClose.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                AdminDashboard.this,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        panel.add(btnClose);

        // ✅ Buttons
        JButton btnPackages = new JButton("Manage Packages");
        btnPackages.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnPackages.setBounds(287, 71, 250, 88);
        btnPackages.addActionListener(e -> {
            new ManagePackagesView().setVisible(true);
            dispose();
        });
        contentPane.add(btnPackages);

        JButton btnManageRooms = new JButton("Manage Rooms");
        btnManageRooms.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnManageRooms.setBounds(561, 71, 250, 88);
        btnManageRooms.addActionListener(e -> {
            new ManageRoomsView().setVisible(true);
            dispose();
        });
        contentPane.add(btnManageRooms);

        JButton btnManageReservation = new JButton("Manage Reservation");
        btnManageReservation.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnManageReservation.setBounds(837, 71, 300, 88);
        btnManageReservation.addActionListener(e -> {
            new ManageReservationView().setVisible(true);
            dispose();
        });
        contentPane.add(btnManageReservation);

        JButton btnManagePayments = new JButton("Manage Payments");
        btnManagePayments.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnManagePayments.setBounds(1172, 71, 250, 88);
        btnManagePayments.addActionListener(e -> {
            new ManagePaymentView().setVisible(true);
            dispose();
        });
        contentPane.add(btnManagePayments);

        JButton btnUsers = new JButton("Manage Users");
        btnUsers.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnUsers.setBounds(10, 71, 250, 88);
        btnUsers.addActionListener(e -> {
            new ManageUsersView().setVisible(true);
            dispose();
        });
        contentPane.add(btnUsers);
        
                // ✅ Background image
                JLabel background = new JLabel();
                background.setIcon(new ImageIcon(getClass().getResource("/images/resort.jpg"))); 
                // Resize image to 1920x1080 or use a scaled icon
                background.setBounds(0, 0, 1443, 1080);
                contentPane.add(background);
    }
}
