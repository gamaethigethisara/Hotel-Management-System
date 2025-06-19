package service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;

public class InvoiceService {

    public static void downloadInvoice(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row to download invoice.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();

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

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(chooser.getSelectedFile())) {
                writer.write(content);
                JOptionPane.showMessageDialog(null, "Invoice downloaded successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error downloading invoice: " + ex.getMessage());
            }
        }
    }
}
