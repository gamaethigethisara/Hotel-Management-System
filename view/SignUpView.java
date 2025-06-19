package view;

import controller.UserController;
import model.User;
import util.DBConnect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;

public class SignUpView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtName, txtEmail, txtNIC;
    private JPasswordField txtPassword;

    public SignUpView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 842, 537);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(148, 66, 535, 373);
        contentPane.add(panel);

        JLabel lblTitle = new JLabel("SignUp");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 36));
        lblTitle.setBounds(207, 23, 142, 45);
        panel.add(lblTitle);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblName.setBounds(35, 110, 68, 14);
        panel.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(113, 109, 358, 20);
        panel.add(txtName);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblEmail.setBounds(35, 154, 68, 14);
        panel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(113, 153, 358, 20);
        panel.add(txtEmail);

        JLabel lblNIC = new JLabel("NIC");
        lblNIC.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNIC.setBounds(35, 195, 68, 14);
        panel.add(lblNIC);

        txtNIC = new JTextField();
        txtNIC.setBounds(113, 194, 358, 20);
        panel.add(txtNIC);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPassword.setBounds(35, 235, 80, 14);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(113, 234, 358, 20);
        panel.add(txtPassword);

        JButton btnSignUp = new JButton("Sign up");
        btnSignUp.setBounds(403, 301, 89, 23);
        btnSignUp.addActionListener(e -> registerUser());
        panel.add(btnSignUp);

        JButton btnLogin = new JButton("Log in");
        btnLogin.setBounds(229, 301, 89, 23);
        btnLogin.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
        panel.add(btnLogin);

        JButton btnClose = new JButton("Close");
        btnClose.setBounds(45, 301, 89, 23);
        btnClose.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    SignUpView.this,
                    "Do you really want to close the application?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        panel.add(btnClose);

        JLabel lblBackground = new JLabel();
        lblBackground.setIcon(new ImageIcon(getClass().getResource("/images/resort.jpg")));
        lblBackground.setBounds(0, 0, 840, 512);
        contentPane.add(lblBackground);
    }

    private void registerUser() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String nic = txtNIC.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        // Input validations
        if (name.isEmpty() || email.isEmpty() || nic.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (!name.matches("^[A-Za-z\\s]+$")) {
            JOptionPane.showMessageDialog(this, "Name should contain only letters and spaces.");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            return;
        }

        if (!(nic.matches("^[0-9]{9}[Vv]$") || nic.matches("^[0-9]{12}$"))) {
            JOptionPane.showMessageDialog(this, "NIC should be 9 digits with 'V' or 12 digits.");
            return;
        }

        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$")) {
            JOptionPane.showMessageDialog(this,
                    "Password must be at least 6 characters,\ncontain at least one uppercase letter,\none lowercase letter, and one digit.");
            return;
        }

        try {
            Connection con = DBConnect.getConnection();
            UserController controller = new UserController(con);
            

            // Check for duplicate email or NIC before inserting
            if (controller.isDuplicate(email, nic)) {
                JOptionPane.showMessageDialog(this, "Email or NIC already exists.");
                return;
            }

            User user = new User(name, email, nic, password, "Active");

            boolean success = controller.registerUser(user);
            if (success) {
                JOptionPane.showMessageDialog(this, "Registered successfully!");
                new LoginView().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SignUpView frame = new SignUpView();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
