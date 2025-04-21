package DAO;

import models.User;
import utils.DatabaseUtil;
import utils.PasswordUtil;

import java.sql.*;

public class UserDAO {

    public User findUserByUsername(String username) throws  SQLException{
        String sql = "SELECT id, username, password_hash FROM users WHERE username = ?";
        User user = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                // Đọc dữ liệu từ ResultSet và tạo đối tượng User
                int id = resultSet.getInt("id");
                String foundUsername = resultSet.getString("username");
                String passwordHash = resultSet.getString("password_hash");
                user = new User(id, foundUsername, passwordHash); // Tạo đối tượng User
            }
        }
        return user;
    }

    public boolean addUser(String username, String passwordHash) throws SQLException{
        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)){

            statement.setString(1, username);
            passwordHash = PasswordUtil.hashPassword(passwordHash);
            statement.setString(2, passwordHash);

            int rowsAffected = statement.executeUpdate(); // Thực thi câu lệnh INSERT, UPDATE, DELETE
            System.out.println("User added: " + username + ", Rows affected: " + rowsAffected);
            return rowsAffected != 0;
        }
    }
}

