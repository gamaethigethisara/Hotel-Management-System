package controller;

import dao.RoomDAO;
import model.Room;

import java.sql.Connection;
import java.util.List;

public class RoomController {

    private final RoomDAO roomDAO;

    public RoomController(Connection con) {
        this.roomDAO = new RoomDAO(con);
    }

    public boolean addRoom(Room room) {
        try {
            return roomDAO.addRoom(room);
        } catch (Exception e) {
            System.err.println("Error adding room: " + e.getMessage());
            return false;
        }
    }

    public boolean updateRoom(Room room, String originalRoomNumber) {
        try {
            return roomDAO.updateRoom(room, originalRoomNumber);
        } catch (Exception e) {
            System.err.println("Error updating room: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteRoom(String roomNumber) {
        try {
            return roomDAO.deleteRoom(roomNumber);
        } catch (Exception e) {
            System.err.println("Error deleting room: " + e.getMessage());
            return false;
        }
    }

    public List<Room> getAllRooms() {
        try {
            return roomDAO.getAllRooms();
        } catch (Exception e) {
            System.err.println("Error fetching rooms: " + e.getMessage());
            return null;
        }
    }
}
