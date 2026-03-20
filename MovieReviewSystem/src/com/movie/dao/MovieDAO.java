package com.movie.dao;

import com.movie.database.DatabaseConnection;
import com.movie.model.Movie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    public List<Movie> getAllMovies(String searchKeyword) {
        List<Movie> movieList = new ArrayList<>();
        String query = "SELECT * FROM Movies WHERE title LIKE ? OR genre LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            String pattern = "%" + searchKeyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie(rs.getString("title"), rs.getString("genre"), rs.getInt("release_year"));
                movie.setMovieId(rs.getInt("movie_id")); 
                movieList.add(movie);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return movieList;
    }

    public boolean addMovie(String title, String genre, int year) {
        String query = "INSERT INTO Movies (title, genre, release_year) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, genre);
            stmt.setInt(3, year);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public boolean updateMovie(int id, String title, String genre, int year) {
        String query = "UPDATE Movies SET title = ?, genre = ?, release_year = ? WHERE movie_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, genre);
            stmt.setInt(3, year);
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public boolean deleteMovie(int id) {
        String query = "DELETE FROM Movies WHERE movie_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}