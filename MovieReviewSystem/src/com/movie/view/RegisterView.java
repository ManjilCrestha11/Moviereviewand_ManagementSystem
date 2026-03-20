package com.movie.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.movie.dao.UserDAO;

public class RegisterView extends Application {

    @Override
    public void start(Stage primaryStage) {
        // --- 1. UI Components ---
        Label titleLabel = new Label("JOIN US");
        titleLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #D4AF37;");

        TextField userField = new TextField();
        userField.setPromptText("Choose Username");
        userField.setMaxWidth(250);
        userField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white; -fx-prompt-text-fill: #777; -fx-border-color: #444;");

        // Password Logic with "Show Password"
        PasswordField passField = new PasswordField();
        passField.setPromptText("Create Password");
        passField.setMaxWidth(250);
        passField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white; -fx-prompt-text-fill: #777; -fx-border-color: #444;");

        TextField visiblePassField = new TextField();
        visiblePassField.setManaged(false);
        visiblePassField.setVisible(false);
        visiblePassField.setMaxWidth(250);
        visiblePassField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white; -fx-border-color: #444;");
        visiblePassField.textProperty().bindBidirectional(passField.textProperty());

        CheckBox showPassword = new CheckBox("Show Password");
        showPassword.setStyle("-fx-text-fill: #D4AF37; -fx-font-size: 12px;");
        showPassword.setOnAction(e -> {
            if (showPassword.isSelected()) {
                visiblePassField.setManaged(true);
                visiblePassField.setVisible(true);
                passField.setManaged(false);
                passField.setVisible(false);
            } else {
                visiblePassField.setManaged(false);
                visiblePassField.setVisible(false);
                passField.setManaged(true);
                passField.setVisible(true);
            }
        });

        PasswordField confirmPassField = new PasswordField();
        confirmPassField.setPromptText("Confirm Password");
        confirmPassField.setMaxWidth(250);
        confirmPassField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white; -fx-prompt-text-fill: #777; -fx-border-color: #444;");

        Button registerBtn = new Button("CREATE ACCOUNT");
        registerBtn.setStyle("-fx-background-color: #D4AF37; -fx-text-fill: black; -fx-font-weight: bold; -fx-cursor: hand;");
        registerBtn.setMinWidth(250);

        Button backBtn = new Button("Back to Login");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4A90E2; -fx-underline: true; -fx-cursor: hand;");

        Label statusLabel = new Label("");

        // --- 2. Button Actions ---
        backBtn.setOnAction(e -> {
            new LoginView().start(new Stage());
            primaryStage.close();
        });

        registerBtn.setOnAction(e -> {
            String user = userField.getText();
            String pass = passField.getText();
            String confirm = confirmPassField.getText();

            if (pass.equals(confirm) && !pass.isEmpty()) {
                if (new UserDAO().registerUser(user, pass)) {
                    statusLabel.setText("Account Created Successfully!");
                    statusLabel.setStyle("-fx-text-fill: #2ECC71;");
                } else {
                    statusLabel.setText("Username already taken.");
                    statusLabel.setStyle("-fx-text-fill: #E74C3C;");
                }
            } else {
                statusLabel.setText("Passwords do not match!");
                statusLabel.setStyle("-fx-text-fill: #E74C3C;");
            }
        });

        // --- 3. Layout ---
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        
        // StackPane used to swap the password field and visible text field
        StackPane passStack = new StackPane(passField, visiblePassField);
        passStack.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(titleLabel, userField, passStack, showPassword, confirmPassField, registerBtn, backBtn, statusLabel);
        layout.setStyle("-fx-background-color: #121212; -fx-padding: 50; -fx-border-color: #D4AF37; -fx-border-width: 2;");

        // --- 4. Scene & Stage ---
        Scene scene = new Scene(layout, 575, 600);
        primaryStage.setTitle("Movie Review Registration");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}