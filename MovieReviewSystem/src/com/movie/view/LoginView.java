package com.movie.view;

import com.movie.database.DatabaseConnection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class LoginView {

    public void start(Stage stage) {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #121212;");

        Label titleLabel = new Label("MOVIE SYSTEM LOGIN");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #D4AF37; -fx-font-weight: bold;");

        TextField userField = new TextField();
        userField.setPromptText("Username");
        
        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");

        Button loginBtn = new Button("LOGIN");
        loginBtn.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: black; -fx-font-weight: bold; -fx-min-width: 100px;");

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: #E74C3C;");

        loginBtn.setOnAction(e -> {
            String username = userField.getText();
            String password = passField.getText();

            String query = "SELECT role FROM users WHERE username = ? AND password = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    new DashboardView().show(); 
                    stage.close(); 
                } else {
                    statusLabel.setText("Invalid credentials.");
                }
            } catch (SQLException ex) {
                statusLabel.setText("Database Error.");
            }
        });

        layout.getChildren().addAll(titleLabel, userField, passField, loginBtn, statusLabel);
        stage.setScene(new Scene(layout, 400, 450)); // Proper window size
        stage.show();
    }
}