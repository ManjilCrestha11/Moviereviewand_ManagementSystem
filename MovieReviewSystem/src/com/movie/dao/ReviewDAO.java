package com.movie.dao;

import com.movie.database.DatabaseConnection;
import java.sql.*;

public class ReviewDAO {

    /**
     * Fetches all reviews for a specific movie title.
     * Uses a JOIN to connect the movie title to its specific reviews.
     */
    public String getReviewsForMovie(String movieTitle) {
        StringBuilder reviews = new StringBuilder();
        String query = "SELECT r.user_name, r.rating, r.comment FROM reviews r " +
                       "JOIN Movies m ON r.movie_id = m.movie_id " +
                       "WHERE m.title = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, movieTitle);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reviews.append("USER: ").append(rs.getString("user_name")).append("\n")
                       .append("RATING: ").append(rs.getInt("rating")).append("/5\n")
                       .append("COMMENT: ").append(rs.getString("comment"))
                       .append("\n------------------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews.length() > 0 ? reviews.toString() : "No reviews found for this movie.";
    }

    /**
     * Saves a new review into the database.
     */
    public boolean addReview(int movieId, String userName, int rating, String comment) {
        String query = "INSERT INTO reviews (movie_id, user_name, rating, comment) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, movieId);
            stmt.setString(2, userName);
            stmt.setInt(3, rating);
            stmt.setString(4, comment);
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // Success if at least one row was added
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}