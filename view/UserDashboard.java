package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class UserDashboard extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1974330013517291778L;
	private JPanel contentPane;

    public UserDashboard() {
        setTitle("User Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        //Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SystemColor.activeCaption);
        headerPanel.setBounds(0, 0, 1440, 60);
        headerPanel.setLayout(null);
        contentPane.add(headerPanel);

        JLabel lblWelcome = new JLabel("Welcome User!");
        lblWelcome.setFont(new Font("Serif", Font.BOLD, 30));
        lblWelcome.setBounds(20, 10, 300, 40);
        headerPanel.add(lblWelcome);

        JButton btnLogout = new JButton("Log out");
        btnLogout.setBounds(1220, 15, 90, 30);
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Do you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginView().setVisible(true);
                dispose();
            }
        });
        headerPanel.add(btnLogout);

        JButton btnClose = new JButton("Close");
        btnClose.setBounds(1320, 15, 90, 30);
        btnClose.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        headerPanel.add(btnClose);

        //Background Image
        JLabel background = new JLabel();
        background.setBounds(0, 0, 1920, 1080);
        background.setIcon(new ImageIcon(getClass().getResource("/images/resort.jpg"))); // ✅ Relative path recommended
        contentPane.add(background);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UserDashboard().setVisible(true);
        });
    }
}
