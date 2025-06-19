package controller;

import dao.ReservationDAO;
import model.Reservation;

import java.sql.Connection;
import java.util.List;

public class ReservationController {

    private final ReservationDAO reservationDAO;

    public ReservationController(Connection con) {
        this.reservationDAO = new ReservationDAO(con);
    }

    // Add a new reservation
    public boolean addReservation(Reservation reservation) {
        try {
            return reservationDAO.addReservation(reservation);
        } catch (Exception e) {
            System.err.println("Error adding reservation: " + e.getMessage());
            return false;
        }
    }

    // Update an existing reservation
    public boolean updateReservation(Reservation reservation, String originalNIC) {
        try {
            return reservationDAO.updateReservation(reservation, originalNIC);
        } catch (Exception e) {
            System.err.println("Error updating reservation: " + e.getMessage());
            return false;
        }
    }

    // Delete a reservation by NIC
    public boolean deleteReservation(String nic) {
        try {
            return reservationDAO.deleteReservation(nic);
        } catch (Exception e) {
            System.err.println("Error deleting reservation: " + e.getMessage());
            return false;
        }
    }

    // Fetch all reservations
    public List<Reservation> getAllReservations() {
        try {
            return reservationDAO.getAllReservations();
        } catch (Exception e) {
            System.err.println("Error loading reservations: " + e.getMessage());
            return null;
        }
    }
}
