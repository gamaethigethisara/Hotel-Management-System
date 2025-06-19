package dao;

import model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    private final Connection con;

    public RoomDAO(Connection con) {
        this.con = con;
    }

    public boolean addRoom(Room room) throws SQLException {
        String sql = "INSERT INTO rooms (room_number, bed, price_per_day, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, room.getRoomNumber());
            ps.setInt(2, room.getBed());
            ps.setDouble(3, room.getPricePerDay());
            ps.setString(4, room.getStatus());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateRoom(Room room, String originalRoomNumber) throws SQLException {
        String sql = "UPDATE rooms SET room_number=?, bed=?, price_per_day=?, status=? WHERE room_number=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, room.getRoomNumber());
            ps.setInt(2, room.getBed());
            ps.setDouble(3, room.getPricePerDay());
            ps.setString(4, room.getStatus());
            ps.setString(5, originalRoomNumber);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteRoom(String roomNumber) throws SQLException {
        String sql = "DELETE FROM rooms WHERE room_number=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, roomNumber);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Room> getAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Room room = new Room(
                    rs.getString("room_number"),
                    rs.getInt("bed"),
                    rs.getDouble("price_per_day"),
                    rs.getString("status")
                );
                rooms.add(room);
            }
        }
        return rooms;
    }
}
