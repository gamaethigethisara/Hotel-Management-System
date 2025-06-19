package view;

import controller.PackageController;
import model.Package;
import util.DBConnect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ManagePackagesView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField txtId, txtName, txtDesc, txtDuration, txtPrice;
    private JComboBox<String> comboAvailability;
    private JTable table;
    private DefaultTableModel model;
    private PackageController controller;

    public ManagePackagesView() {
        try {
            Connection con = DBConnect.getConnection();
            controller = new PackageController(con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
            return;
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1440, 768);
        setTitle("Manage Packages");
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // Header panel
        JPanel header = new JPanel();
        header.setBounds(0, 0, 1440, 60);
        header.setLayout(null);
        header.setBackground(SystemColor.activeCaption);
        contentPane.add(header);

        JLabel lblTitle = new JLabel("Manage Packages");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setBounds(20, 15, 250, 30);
        header.add(lblTitle);

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
        
        JButton btnDashboard = new JButton("Dashboard");
        btnDashboard.setBounds(252, 19, 110, 25);
        btnDashboard.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            dispose(); // ✅ Close current window
        });
        header.add(btnDashboard);

        // Input fields
        JLabel lblId = new JLabel("ID");
        lblId.setBounds(20, 80, 100, 20);
        contentPane.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(130, 80, 230, 25);
        contentPane.add(txtId);

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(20, 115, 100, 20);
        contentPane.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(130, 115, 230, 25);
        contentPane.add(txtName);

        JLabel lblDesc = new JLabel("Description");
        lblDesc.setBounds(20, 150, 100, 20);
        contentPane.add(lblDesc);

        txtDesc = new JTextField();
        txtDesc.setBounds(130, 150, 230, 25);
        contentPane.add(txtDesc);

        JLabel lblDuration = new JLabel("Duration (Days)");
        lblDuration.setBounds(20, 185, 100, 20);
        contentPane.add(lblDuration);

        txtDuration = new JTextField();
        txtDuration.setBounds(130, 185, 230, 25);
        contentPane.add(txtDuration);

        JLabel lblPrice = new JLabel("Price");
        lblPrice.setBounds(20, 220, 100, 20);
        contentPane.add(lblPrice);

        txtPrice = new JTextField();
        txtPrice.setBounds(130, 220, 230, 25);
        contentPane.add(txtPrice);

        JLabel lblAvail = new JLabel("Availability");
        lblAvail.setBounds(20, 255, 100, 20);
        contentPane.add(lblAvail);

        comboAvailability = new JComboBox<>(new String[]{"Available", "Unavailable"});
        comboAvailability.setBounds(130, 255, 230, 25);
        contentPane.add(comboAvailability);

        // Buttons
        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(130, 290, 100, 30);
        btnAdd.addActionListener(e -> addPackage());
        contentPane.add(btnAdd);

        JButton btnEdit = new JButton("Edit");
        btnEdit.setBounds(240, 290, 100, 30);
        btnEdit.addActionListener(e -> loadSelectedRow());
        contentPane.add(btnEdit);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(130, 330, 100, 30);
        btnSave.addActionListener(e -> updatePackage());
        contentPane.add(btnSave);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(240, 330, 100, 30);
        btnDelete.addActionListener(e -> deletePackage());
        contentPane.add(btnDelete);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Name", "Description", "Duration", "Price", "Availability"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(400, 80, 1000, 500);
        contentPane.add(scrollPane);

        loadPackages();
    }

    private void addPackage() {
        try {
            Package pkg = getPackageFromForm();
            if (controller.addPackage(pkg)) {
                model.addRow(new Object[]{pkg.getId(), pkg.getName(), pkg.getDescription(), pkg.getDuration(), pkg.getPrice(), pkg.getAvailability()});
                JOptionPane.showMessageDialog(this, "Package added!");
                clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding: " + e.getMessage());
        }
    }

    private void deletePackage() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        String id = table.getValueAt(row, 0).toString();
        if (controller.deletePackage(id)) {
            model.removeRow(row);
            JOptionPane.showMessageDialog(this, "Deleted!");
        }
    }

    private void loadSelectedRow() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        txtId.setText(table.getValueAt(row, 0).toString());
        txtName.setText(table.getValueAt(row, 1).toString());
        txtDesc.setText(table.getValueAt(row, 2).toString());
        txtDuration.setText(table.getValueAt(row, 3).toString());
        txtPrice.setText(table.getValueAt(row, 4).toString());
        comboAvailability.setSelectedItem(table.getValueAt(row, 5).toString());
    }

    private void updatePackage() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        try {
            Package pkg = getPackageFromForm();
            if (controller.updatePackage(pkg)) {
                model.setValueAt(pkg.getId(), row, 0);
                model.setValueAt(pkg.getName(), row, 1);
                model.setValueAt(pkg.getDescription(), row, 2);
                model.setValueAt(pkg.getDuration(), row, 3);
                model.setValueAt(pkg.getPrice(), row, 4);
                model.setValueAt(pkg.getAvailability(), row, 5);
                JOptionPane.showMessageDialog(this, "Updated!");
                clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating: " + e.getMessage());
        }
    }

    private void loadPackages() {
        model.setRowCount(0);
        List<Package> list = controller.getAllPackages();
        if (list != null) {
            for (Package pkg : list) {
                model.addRow(new Object[]{
                        pkg.getId(), pkg.getName(), pkg.getDescription(),
                        pkg.getDuration(), pkg.getPrice(), pkg.getAvailability()
                });
            }
        }
    }

    private Package getPackageFromForm() throws Exception {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String desc = txtDesc.getText().trim();
        String duration = txtDuration.getText().trim();
        double price = Double.parseDouble(txtPrice.getText().trim());
        String avail = comboAvailability.getSelectedItem().toString();

        if (id.isEmpty() || name.isEmpty() || desc.isEmpty() || duration.isEmpty()) {
            throw new Exception("All fields are required.");
        }

        return new Package(id, name, desc, duration, price, avail);
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtDesc.setText("");
        txtDuration.setText("");
        txtPrice.setText("");
        comboAvailability.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new ManagePackagesView().setVisible(true));
    }
}
