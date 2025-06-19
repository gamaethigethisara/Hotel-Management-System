package view;

import controller.ReservationController;
import model.Reservation;
import util.DBConnect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class ManageReservationView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JTextField tfName, tfNIC, tfEmail, tfPhone, tfAddress, tfCheckin, tfCheckout;
    private JComboBox<String> cbGender, cbRoomType, cbPackage, cbRoomNo;
    private ReservationController controller;
    private String selectedNIC = null;

    public ManageReservationView() {
        try {
            Connection con = DBConnect.getConnection();
            controller = new ReservationController(con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
            return;
        }

        setTitle("Manage Reservation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 768);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("Manage Reservation");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setBounds(10, 10, 300, 30);
        contentPane.add(lblTitle);

        JButton btnDashboard = new JButton("Dashboard");
        btnDashboard.setBounds(246, 10, 110, 30);
        btnDashboard.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            dispose();
        });
        contentPane.add(btnDashboard);

        JButton btnLogout = new JButton("Log out");
        btnLogout.setBounds(1220, 15, 90, 30);
        btnLogout.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
        contentPane.add(btnLogout);

        JButton btnClose = new JButton("Close");
        btnClose.setBounds(1320, 15, 90, 30);
        btnClose.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Exit app?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                System.exit(0);
        });
        contentPane.add(btnClose);

        tfName = new JTextField(); tfNIC = new JTextField(); tfEmail = new JTextField();
        tfPhone = new JTextField(); tfAddress = new JTextField();
        tfCheckin = new JTextField(); tfCheckout = new JTextField();
        cbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        cbRoomType = new JComboBox<>(new String[]{"AC", "Non AC"});
        cbRoomNo = new JComboBox<>();
        cbPackage = new JComboBox<>();

        addLabelAndField("Name", tfName, 60);
        addLabelAndField("NIC", tfNIC, 90);
        addLabelAndField("Email", tfEmail, 120);
        addLabelAndField("Phone", tfPhone, 150);
        addLabelAndCombo("Gender", cbGender, 180);
        addLabelAndField("Address", tfAddress, 210);
        addLabelAndField("Check-in Date", tfCheckin, 240);
        addLabelAndField("Check-out Date", tfCheckout, 270);
        addLabelAndCombo("Room Type", cbRoomType, 300);
        addLabelAndCombo("Room No", cbRoomNo, 330);
        addLabelAndCombo("Package", cbPackage, 360);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(150, 400, 100, 30);
        btnAdd.addActionListener(e -> addReservation());
        contentPane.add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(260, 400, 100, 30);
        btnUpdate.addActionListener(e -> updateReservation());
        contentPane.add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(37, 400, 100, 30);
        btnDelete.addActionListener(e -> deleteReservation());
        contentPane.add(btnDelete);

        model = new DefaultTableModel(new String[]{
                "Name", "NIC", "Email", "Phone", "Gender", "Address",
                "Check-in", "Check-out", "Room Type", "Room No", "Package"
        }, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(450, 60, 950, 600);
        contentPane.add(scrollPane);

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tfName.setText(model.getValueAt(row, 0).toString());
                tfNIC.setText(model.getValueAt(row, 1).toString());
                tfEmail.setText(model.getValueAt(row, 2).toString());
                tfPhone.setText(model.getValueAt(row, 3).toString());
                cbGender.setSelectedItem(model.getValueAt(row, 4).toString());
                tfAddress.setText(model.getValueAt(row, 5).toString());
                tfCheckin.setText(model.getValueAt(row, 6).toString());
                tfCheckout.setText(model.getValueAt(row, 7).toString());
                cbRoomType.setSelectedItem(model.getValueAt(row, 8).toString());
                cbRoomNo.setSelectedItem(model.getValueAt(row, 9).toString());
                cbPackage.setSelectedItem(model.getValueAt(row, 10).toString());
                selectedNIC = model.getValueAt(row, 1).toString();
            }
        });

        loadRooms();
        loadPackages();
        loadReservations();
    }

    private void addLabelAndField(String text, JTextField field, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(20, y, 120, 25);
        field.setBounds(150, y, 250, 25);
        contentPane.add(lbl); contentPane.add(field);
    }

    private void addLabelAndCombo(String text, JComboBox<String> combo, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(20, y, 120, 25);
        combo.setBounds(150, y, 250, 25);
        contentPane.add(lbl); contentPane.add(combo);
    }

    private void addReservation() {
        try {
            Reservation r = getFormReservation();
            if (controller.addReservation(r)) {
                JOptionPane.showMessageDialog(this, "Reservation added successfully!");
                loadReservations();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add reservation.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Add Error: " + e.getMessage());
        }
    }

    private void updateReservation() {
        if (selectedNIC == null) {
            JOptionPane.showMessageDialog(this, "Please select a reservation from the table.");
            return;
        }

        try {
            Reservation r = getFormReservation();
            if (controller.updateReservation(r, selectedNIC)) {
                JOptionPane.showMessageDialog(this, "Reservation updated!");
                loadReservations();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update Error: " + e.getMessage());
        }
    }

    private void deleteReservation() {
        if (selectedNIC == null) {
            JOptionPane.showMessageDialog(this, "Please select a reservation from the table.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this reservation?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.deleteReservation(selectedNIC)) {
                JOptionPane.showMessageDialog(this, "Reservation deleted successfully.");
                loadReservations();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete reservation.");
            }
        }
    }

    private Reservation getFormReservation() throws Exception {
        String name = tfName.getText().trim();
        String nic = tfNIC.getText().trim();
        String email = tfEmail.getText().trim();
        String phone = tfPhone.getText().trim();
        String gender = (String) cbGender.getSelectedItem();
        String address = tfAddress.getText().trim();
        Date checkin = Date.valueOf(tfCheckin.getText().trim());
        Date checkout = Date.valueOf(tfCheckout.getText().trim());
        String roomType = (String) cbRoomType.getSelectedItem();
        String roomNumber = (String) cbRoomNo.getSelectedItem();
        String pkg = (String) cbPackage.getSelectedItem();

        if (name.isEmpty() || nic.isEmpty() || email.isEmpty()) {
            throw new Exception("Please fill all required fields.");
        }

        return new Reservation(name, nic, email, phone, gender, address, checkin, checkout, roomType, roomNumber, pkg);
    }

    private void clearForm() {
        tfName.setText(""); tfNIC.setText(""); tfEmail.setText(""); tfPhone.setText("");
        tfAddress.setText(""); tfCheckin.setText(""); tfCheckout.setText("");
        cbGender.setSelectedIndex(0); cbRoomType.setSelectedIndex(0);
        cbRoomNo.setSelectedIndex(0); cbPackage.setSelectedIndex(0);
        selectedNIC = null;
        table.clearSelection();
    }

    private void loadReservations() {
        model.setRowCount(0);
        List<Reservation> list = controller.getAllReservations();
        if (list != null) {
            for (Reservation r : list) {
                model.addRow(new Object[]{
                        r.getName(), r.getNic(), r.getEmail(), r.getPhone(), r.getGender(), r.getAddress(),
                        r.getCheckInDate(), r.getCheckOutDate(), r.getRoomType(), r.getRoomNumber(), r.getPackageName()
                });
            }
        }
    }

    private void loadRooms() {
        try {
            Connection con = DBConnect.getConnection();
            cbRoomNo.removeAllItems();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT room_number FROM rooms WHERE status='Available'");
            while (rs.next()) {
                cbRoomNo.addItem(rs.getString("room_number"));
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Room Load Error: " + e.getMessage());
        }
    }

    private void loadPackages() {
        try {
            Connection con = DBConnect.getConnection();
            cbPackage.removeAllItems();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT package_name FROM manage_packages");
            while (rs.next()) {
                cbPackage.addItem(rs.getString("package_name"));
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Package Load Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new ManageReservationView().setVisible(true));
    }
}

