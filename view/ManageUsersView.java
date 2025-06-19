
package view;

import controller.UserAccountController;
import model.UserAccount;
import util.DBConnect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ManageUsersView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nameField, emailField, nicField;
    private JPasswordField passwordField;
    private JComboBox<String> comboAccess;
    private JTable table;
    private DefaultTableModel model;
    private UserAccountController controller;

    public ManageUsersView() {
        try {
            Connection con = DBConnect.getConnection();
            controller = new UserAccountController(con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
            return;
        }

        setTitle("Manage Users");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 768);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // Header
        JPanel header = new JPanel();
        header.setBackground(SystemColor.activeCaption);
        header.setLayout(null);
        header.setBounds(0, 0, 1440, 60);
        contentPane.add(header);

        JLabel lblTitle = new JLabel("Manage Users");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setBounds(20, 15, 200, 30);
        header.add(lblTitle);

        JButton btnDashboard = new JButton("Dashboard");
        btnDashboard.setBounds(194, 22, 110, 25);
        btnDashboard.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            dispose();
        });
        header.add(btnDashboard);

        JButton btnLogout = new JButton("Log out");
        btnLogout.setBounds(1220, 15, 90, 30);
        btnLogout.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
        header.add(btnLogout);

        JButton btnClose = new JButton("Close");
        btnClose.setBounds(1320, 15, 90, 30);
        btnClose.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Exit app?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                System.exit(0);
        });
        header.add(btnClose);

        // Form fields
        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(20, 80, 100, 25);
        contentPane.add(lblName);

        nameField = new JTextField();
        nameField.setBounds(120, 80, 230, 25);
        contentPane.add(nameField);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 115, 100, 25);
        contentPane.add(lblEmail);

        emailField = new JTextField();
        emailField.setBounds(120, 115, 230, 25);
        contentPane.add(emailField);

        JLabel lblNIC = new JLabel("NIC:");
        lblNIC.setBounds(20, 150, 100, 25);
        contentPane.add(lblNIC);

        nicField = new JTextField();
        nicField.setBounds(120, 150, 230, 25);
        contentPane.add(nicField);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(20, 185, 100, 25);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 185, 230, 25);
        contentPane.add(passwordField);

        JLabel lblAccess = new JLabel("Accessibility:");
        lblAccess.setBounds(20, 220, 100, 25);
        contentPane.add(lblAccess);

        comboAccess = new JComboBox<>(new String[]{"Active", "Inactive"});
        comboAccess.setBounds(120, 220, 230, 25);
        contentPane.add(comboAccess);

        // Buttons
        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(130, 260, 100, 30);
        btnAdd.addActionListener(e -> addUser());
        contentPane.add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(240, 260, 110, 30);
        btnUpdate.addActionListener(e -> updateUser());
        contentPane.add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(130, 300, 100, 30);
        btnDelete.addActionListener(e -> deleteUser());
        contentPane.add(btnDelete);

        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(240, 300, 110, 30);
        btnClear.addActionListener(e -> clearForm());
        contentPane.add(btnClear);

        // Table
        model = new DefaultTableModel(new String[]{"Name", "Email", "NIC", "Password", "Accessibility"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(400, 80, 1000, 500);
        contentPane.add(scrollPane);

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                nameField.setText(model.getValueAt(row, 0).toString());
                emailField.setText(model.getValueAt(row, 1).toString());
                nicField.setText(model.getValueAt(row, 2).toString());
                passwordField.setText(model.getValueAt(row, 3).toString());
                comboAccess.setSelectedItem(model.getValueAt(row, 4).toString());
            }
        });

        loadUsers();
    }

    // Add user to the database
    private void addUser() {
        try {
            UserAccount user = getFormData();
            if (controller.isDuplicate(user.getEmail(), user.getNic())) {
                JOptionPane.showMessageDialog(this, "Email or NIC already exists.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (controller.addUser(user)) {
                model.addRow(new Object[]{
                        user.getName(), user.getEmail(), user.getNic(), user.getPassword(), user.getAccessibility()
                });
                JOptionPane.showMessageDialog(this, "User added!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add user.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Add Error: " + e.getMessage());
        }
    }

    // Update selected user data
    private void updateUser() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to update.");
            return;
        }

        try {
            UserAccount user = getFormData();
            String originalEmail = table.getValueAt(row, 1).toString();

            if (!user.getEmail().equals(originalEmail) && controller.isDuplicate(user.getEmail(), user.getNic())) {
                JOptionPane.showMessageDialog(this, "Email or NIC already exists for another user.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (controller.updateUser(user)) {
                model.setValueAt(user.getName(), row, 0);
                model.setValueAt(user.getEmail(), row, 1);
                model.setValueAt(user.getNic(), row, 2);
                model.setValueAt(user.getPassword(), row, 3);
                model.setValueAt(user.getAccessibility(), row, 4);
                JOptionPane.showMessageDialog(this, "User updated!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update Error: " + e.getMessage());
        }
    }

    // Delete selected user
    private void deleteUser() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String email = table.getValueAt(row, 1).toString();
        if (controller.deleteUser(email)) {
            model.removeRow(row);
            JOptionPane.showMessageDialog(this, "User deleted.");
            clearForm();
        }
    }

    // Load users from DB into table
    private void loadUsers() {
        model.setRowCount(0);
        List<UserAccount> list = controller.getAllUsers();
        if (list != null) {
            for (UserAccount user : list) {
                model.addRow(new Object[]{
                        user.getName(), user.getEmail(), user.getNic(), user.getPassword(), user.getAccessibility()
                });
            }
        }
    }

    // Get data from form with validation
    private UserAccount getFormData() throws Exception {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String nic = nicField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String access = comboAccess.getSelectedItem().toString();

        if (name.isEmpty() || email.isEmpty() || nic.isEmpty() || password.isEmpty()) {
            throw new Exception("All fields must be filled.");
        }

        if (!nic.matches("^\\d{9}[vVxX]$") && !nic.matches("^\\d{12}$")) {
            throw new Exception("Invalid NIC. Use 9 digits + V/X or 12 digits.");
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new Exception("Invalid email format.");
        }

        if (password.length() < 6) {
            throw new Exception("Password must be at least 6 characters long.");
        }

        return new UserAccount(name, email, nic, password, access);
    }
    
    // Clear all input fields
    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        nicField.setText("");
        passwordField.setText("");
        comboAccess.setSelectedIndex(0);
        table.clearSelection();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new ManageUsersView().setVisible(true));
    }
}
