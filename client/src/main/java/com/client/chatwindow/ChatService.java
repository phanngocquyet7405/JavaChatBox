package com.client.chatwindow;

import com.client.login.databaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatService {
    public int getUserIdByUsername (String username){
        String query = "select account_id from user_account where username = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public void saveMessage(int senderId, int receiverId, String messages) {
        String query = "INSERT INTO messages_user (sender_id, received_id, messages) VALUES (?, ?, ?)";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setString(3, messages);
            stmt.executeUpdate();
            System.out.println("Message saved successfully!");
        } catch (SQLException e) {
            System.out.println("Error occurred while executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<String> loadChatHistory(int currentUserId, int otherUserId) {
        List<String> chatHistory = new ArrayList<>();
        String query = "SELECT sender_id, messages FROM messages_user WHERE " +
                "(sender_id = ? AND received_id = ?) OR (sender_id = ? AND received_id = ?) ORDER BY timestamp";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, currentUserId);
            stmt.setInt(2, otherUserId);
            stmt.setInt(3, otherUserId);
            stmt.setInt(4, currentUserId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String sender = rs.getInt("sender_id") == currentUserId ? "Me" : "User";
                chatHistory.add(sender + ": " + rs.getString("messages"));
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }
        return chatHistory;
    }
}
