package controller;

import dao.UserDAO;
import model.User;

import java.sql.Connection;

public class UserController {
    private UserDAO userDAO;

    public UserController(Connection con) {
        userDAO = new UserDAO(con);
    }

    public boolean registerUser(User user) throws Exception {
        return userDAO.insertUser(user);
    }

    // Check if email or NIC already exists
    public boolean isDuplicate(String email, String nic) throws Exception {
        return userDAO.isDuplicate(email, nic);
    }
}
