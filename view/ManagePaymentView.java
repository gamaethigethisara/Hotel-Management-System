package view;

import controller.PaymentController;
import model.Payment;
import util.DBConnect;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class ManagePaymentView extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField tfNIC, tfRoomPrice, tfPackagePrice, tfAC, tfTotal, tfDate;
    private JComboBox<String> cbMethod;
    private JTable table;
    private DefaultTableModel model;

    private PaymentController controller;
    private String editingInvoiceNo = null;

    public ManagePaymentView() {
        try {
            Connection con = DBConnect.getConnection();
            controller = new PaymentController(con);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
            return;
        }

        setTitle("Manage Payments");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 768);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        JLabel lblTitle = new JLabel("Manage Payments");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setBounds(10, 10, 300, 30);
        contentPane.add(lblTitle);

        JButton btnDashboard = new JButton("Dashboard");
        btnDashboard.setBounds(240, 15, 120, 30);
        btnDashboard.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            dispose();
        });
        contentPane.add(btnDashboard);

        tfNIC = new JTextField();
        tfRoomPrice = new JTextField();
        tfPackagePrice = new JTextField();
        tfAC = new JTextField();
        tfTotal = new JTextField();
        tfDate = new JTextField(LocalDate.now().toString());
        cbMethod = new JComboBox<>(new String[]{"Cash", "Card", "Online"});

        tfRoomPrice.setEditable(false);
        tfPackagePrice.setEditable(false);
        tfAC.setEditable(false);
        tfTotal.setEditable(false);
        tfDate.setEditable(false);

        addField("Customer NIC", tfNIC, 60);
        addField("Room Price", tfRoomPrice, 100);
        addField("Package Price", tfPackagePrice, 140);
        addField("AC Charge", tfAC, 180);
        addField("Total Amount", tfTotal, 220);
        addField("Payment Date", tfDate, 260);
        addCombo("Payment Method", cbMethod, 300);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(410, 60, 100, 25);
        btnSearch.addActionListener(e -> fetchPricesByNIC());
        contentPane.add(btnSearch);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(130, 340, 100, 30);
        btnAdd.addActionListener(e -> insertPayment());
        contentPane.add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(240, 340, 100, 30);
        btnUpdate.addActionListener(e -> updatePayment());
        contentPane.add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(350, 340, 100, 30);
        btnDelete.addActionListener(e -> deletePayment());
        contentPane.add(btnDelete);

        JButton btnDownload = new JButton("Download Invoice");
        btnDownload.setBounds(460, 340, 160, 30);
        btnDownload.addActionListener(e -> downloadInvoice());
        contentPane.add(btnDownload);

        model = new DefaultTableModel(new String[]{
                "NIC", "Room Price", "Package Price", "AC Charge", "Total", "Date", "Method", "Invoice No"
        }, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 386, 1408, 336);
        contentPane.add(scrollPane);

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tfNIC.setText(model.getValueAt(row, 0).toString());
                tfRoomPrice.setText(model.getValueAt(row, 1).toString());
                tfPackagePrice.setText(model.getValueAt(row, 2).toString());
                tfAC.setText(model.getValueAt(row, 3).toString());
                tfTotal.setText(model.getValueAt(row, 4).toString());
                tfDate.setText(model.getValueAt(row, 5).toString());
                cbMethod.setSelectedItem(model.getValueAt(row, 6).toString());
                editingInvoiceNo = model.getValueAt(row, 7).toString();
            }
        });

        loadPayments();
    }

    private void addField(String label, JTextField field, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(20, y, 120, 25);
        field.setBounds(150, y, 250, 25);
        contentPane.add(lbl);
        contentPane.add(field);
    }

    private void addCombo(String label, JComboBox<String> combo, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(20, y, 120, 25);
        combo.setBounds(150, y, 250, 25);
        contentPane.add(lbl);
        contentPane.add(combo);
    }

    private void fetchPricesByNIC() {
        controller.fillPricesByNIC(tfNIC, tfRoomPrice, tfPackagePrice, tfAC, tfTotal);
    }

    private Payment getPaymentFromForm() throws Exception {
        String nic = tfNIC.getText().trim();
        double room = Double.parseDouble(tfRoomPrice.getText().trim());
        double pkg = Double.parseDouble(tfPackagePrice.getText().trim());
        double ac = Double.parseDouble(tfAC.getText().trim());
        double total = Double.parseDouble(tfTotal.getText().trim());
        String date = tfDate.getText().trim();
        String method = cbMethod.getSelectedItem().toString();
        String invoiceNo = editingInvoiceNo != null ? editingInvoiceNo : "INV" + System.currentTimeMillis();

        return new Payment(nic, room, pkg, ac, total, Date.valueOf(date), method, invoiceNo);
    }

    private void insertPayment() {
        try {
            Payment p = getPaymentFromForm();
            if (controller.insertPayment(p)) {
                JOptionPane.showMessageDialog(this, "Payment inserted.");
                loadPayments();
                clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Insert error: " + e.getMessage());
        }
    }

    private void updatePayment() {
        if (editingInvoiceNo == null) {
            JOptionPane.showMessageDialog(this, "Select a record to update.");
            return;
        }

        try {
            Payment p = getPaymentFromForm();
            if (controller.updatePayment(p)) {
                JOptionPane.showMessageDialog(this, "Payment updated.");
                loadPayments();
                clearForm();
                editingInvoiceNo = null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update error: " + e.getMessage());
        }
    }

    private void deletePayment() {
        if (editingInvoiceNo == null) {
            JOptionPane.showMessageDialog(this, "Select a record to delete.");
            return;
        }

        if (controller.deletePayment(editingInvoiceNo)) {
            JOptionPane.showMessageDialog(this, "Payment deleted.");
            loadPayments();
            clearForm();
            editingInvoiceNo = null;
        }
    }

    private void downloadInvoice() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to download invoice.");
            return;
        }

        String content = "========= HOTEL INVOICE =========\n"
                + "Invoice No   : " + model.getValueAt(row, 7) + "\n"
                + "NIC          : " + model.getValueAt(row, 0) + "\n"
                + "Room Price   : " + model.getValueAt(row, 1) + "\n"
                + "Package Price: " + model.getValueAt(row, 2) + "\n"
                + "AC Charge    : " + model.getValueAt(row, 3) + "\n"
                + "Total Amount : " + model.getValueAt(row, 4) + "\n"
                + "Payment Date : " + model.getValueAt(row, 5) + "\n"
                + "Payment Type : " + model.getValueAt(row, 6) + "\n"
                + "==================================\n";

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("Invoice_" + model.getValueAt(row, 7) + ".txt"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                java.io.FileWriter writer = new java.io.FileWriter(chooser.getSelectedFile());
                writer.write(content);
                writer.close();
                JOptionPane.showMessageDialog(this, "Invoice downloaded.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Download error: " + e.getMessage());
            }
        }
    }

    private void clearForm() {
        tfNIC.setText(""); tfRoomPrice.setText(""); tfPackagePrice.setText("");
        tfAC.setText(""); tfTotal.setText(""); tfDate.setText(LocalDate.now().toString());
        cbMethod.setSelectedIndex(0); editingInvoiceNo = null;
        table.clearSelection();
    }

    private void loadPayments() {
        model.setRowCount(0);
        List<Payment> list = controller.getAllPayments();
        for (Payment p : list) {
            model.addRow(new Object[]{
                    p.getNic(), p.getRoomPrice(), p.getPackagePrice(), p.getAcCharge(),
                    p.getTotalAmount(), p.getPaymentDate(), p.getPaymentMethod(), p.getInvoiceNo()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManagePaymentView().setVisible(true));
    }
}
