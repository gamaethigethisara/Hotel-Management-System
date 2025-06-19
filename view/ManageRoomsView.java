package view;

import controller.RoomController;
import model.Room;
import util.DBConnect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ManageRoomsView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField txtRoomNumber, txtPrice;
    private JComboBox<String> comboBed, comboStatus;
    private JTable table;
    private DefaultTableModel model;
    private RoomController controller;
    private String selectedOriginalRoom = null;

    public ManageRoomsView() {
        try {
            Connection con = DBConnect.getConnection();
            controller = new RoomController(con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
            return;
        }

        setTitle("Manage Rooms");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 768);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        // Header
        JPanel header = new JPanel();
        header.setLayout(null);
        header.setBackground(SystemColor.activeCaption);
        header.setBounds(0, 0, 1440, 60);
        contentPane.add(header);

        JLabel lblTitle = new JLabel("Manage Rooms");
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
            dispose(); //  Go to Dashboard
        });
        header.add(btnDashboard);

        // Form inputs
        JLabel lblRoom = new JLabel("Room Number");
        lblRoom.setBounds(20, 80, 120, 20);
        contentPane.add(lblRoom);

        txtRoomNumber = new JTextField();
        txtRoomNumber.setBounds(140, 80, 200, 25);
        contentPane.add(txtRoomNumber);

        JLabel lblBed = new JLabel("Bed");
        lblBed.setBounds(20, 115, 120, 20);
        contentPane.add(lblBed);

        comboBed = new JComboBox<>(new String[]{"1", "2", "3"});
        comboBed.setBounds(140, 115, 200, 25);
        contentPane.add(comboBed);

        JLabel lblPrice = new JLabel("Price per day");
        lblPrice.setBounds(20, 150, 120, 20);
        contentPane.add(lblPrice);

        txtPrice = new JTextField();
        txtPrice.setBounds(140, 150, 200, 25);
        contentPane.add(txtPrice);

        JLabel lblStatus = new JLabel("Status");
        lblStatus.setBounds(20, 185, 120, 20);
        contentPane.add(lblStatus);

        comboStatus = new JComboBox<>(new String[]{"Available", "Booked", "Maintenance"});
        comboStatus.setBounds(140, 185, 200, 25);
        contentPane.add(comboStatus);

        // Buttons
        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(140, 225, 90, 30);
        btnAdd.addActionListener(e -> addRoom());
        contentPane.add(btnAdd);

        JButton btnSave = new JButton("Save");
        btnSave.setBounds(250, 225, 90, 30);
        btnSave.addActionListener(e -> updateRoom());
        contentPane.add(btnSave);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(140, 265, 90, 30);
        btnDelete.addActionListener(e -> deleteRoom());
        contentPane.add(btnDelete);

        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(250, 265, 90, 30);
        btnClear.addActionListener(e -> clearForm());
        contentPane.add(btnClear);

        // Table
        model = new DefaultTableModel(new String[]{"Room#", "Bed", "Price", "Status"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(400, 80, 1000, 500);
        contentPane.add(scrollPane);

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                selectedOriginalRoom = table.getValueAt(row, 0).toString();
                txtRoomNumber.setText(selectedOriginalRoom);
                comboBed.setSelectedItem(table.getValueAt(row, 1).toString());
                txtPrice.setText(table.getValueAt(row, 2).toString());
                comboStatus.setSelectedItem(table.getValueAt(row, 3).toString());
            }
        });

        loadRooms();
    }

    private void addRoom() {
        try {
            Room room = getRoomFromForm();
            if (controller.addRoom(room)) {
                model.addRow(new Object[]{room.getRoomNumber(), room.getBed(), room.getPricePerDay(), room.getStatus()});
                JOptionPane.showMessageDialog(this, "Room added!");
                clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding: " + e.getMessage());
        }
    }

    private void updateRoom() {
        int row = table.getSelectedRow();
        if (row == -1 || selectedOriginalRoom == null) {
            JOptionPane.showMessageDialog(this, "Select a room first.");
            return;
        }
        try {
            Room room = getRoomFromForm();
            if (controller.updateRoom(room, selectedOriginalRoom)) {
                model.setValueAt(room.getRoomNumber(), row, 0);
                model.setValueAt(room.getBed(), row, 1);
                model.setValueAt(room.getPricePerDay(), row, 2);
                model.setValueAt(room.getStatus(), row, 3);
                JOptionPane.showMessageDialog(this, "Room updated!");
                clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update Error: " + e.getMessage());
        }
    }

    private void deleteRoom() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String roomNumber = table.getValueAt(row, 0).toString();
        if (controller.deleteRoom(roomNumber)) {
            model.removeRow(row);
            JOptionPane.showMessageDialog(this, "Room deleted.");
            clearForm();
        }
    }

    private void loadRooms() {
        model.setRowCount(0);
        List<Room> list = controller.getAllRooms();
        if (list != null) {
            for (Room room : list) {
                model.addRow(new Object[]{
                        room.getRoomNumber(),
                        room.getBed(),
                        room.getPricePerDay(),
                        room.getStatus()
                });
            }
        }
    }

    private Room getRoomFromForm() throws Exception {
        String room = txtRoomNumber.getText().trim();
        int bed = Integer.parseInt(comboBed.getSelectedItem().toString());
        double price = Double.parseDouble(txtPrice.getText().trim());
        String status = comboStatus.getSelectedItem().toString();

        if (room.isEmpty()) throw new Exception("Room number is required.");
        return new Room(room, bed, price, status);
    }

    private void clearForm() {
        txtRoomNumber.setText("");
        txtPrice.setText("");
        comboBed.setSelectedIndex(0);
        comboStatus.setSelectedIndex(0);
        selectedOriginalRoom = null;
        table.clearSelection();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new ManageRoomsView().setVisible(true));
    }
}
