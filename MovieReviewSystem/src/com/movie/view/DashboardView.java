package com.movie.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.movie.model.Movie;
import com.movie.dao.MovieDAO;
import com.movie.dao.ReviewDAO;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardView {
    private TableView<Movie> movieTable = new TableView<>();
    private TextArea reviewDisplayArea = new TextArea(); 
    private MovieDAO movieDAO = new MovieDAO();
    private ReviewDAO reviewDAO = new ReviewDAO();
    
    // Set this to 'true' to see Admin tools, 'false' for regular users
    private boolean isAdmin = true; 

    public void show() {
        Stage stage = new Stage();
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(25));
        mainLayout.setStyle("-fx-background-color: #121212;");

        // --- 1. Header & Search ---
        Label headerLabel = new Label("MOVIE REVIEW");
        headerLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #D4AF37;");

        // --- 2. Admin CRUD Bar (Hidden from regular users) ---
        TextField titleIn = new TextField(); titleIn.setPromptText("Title");
        TextField genreIn = new TextField(); genreIn.setPromptText("Genre");
        TextField yearIn = new TextField(); yearIn.setPromptText("Year");
        
        Button addBtn = new Button("ADD MOVIE");
        Button updateBtn = new Button("UPDATE");
        Button deleteBtn = new Button("DELETE");
        
        addBtn.setStyle("-fx-background-color: #2ECC71; -fx-text-fill: white; -fx-font-weight: bold;");
        updateBtn.setStyle("-fx-background-color: #F39C12; -fx-text-fill: white;");
        deleteBtn.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white;");

        HBox adminBox = new HBox(10, titleIn, genreIn, yearIn, addBtn, updateBtn, deleteBtn);
        adminBox.setAlignment(Pos.CENTER_LEFT);
        adminBox.setVisible(isAdmin); // This is the magic line!
        adminBox.setManaged(isAdmin);

        // --- 3. Movie Table ---
        TableColumn<Movie, String> tCol = new TableColumn<>("Title");
        tCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Movie, String> gCol = new TableColumn<>("Genre");
        gCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        movieTable.getColumns().addAll(tCol, gCol);
        movieTable.setPrefHeight(200);

        // --- 4. User Review Section (For Everyone) ---
        Label reviewLabel = new Label("WRITE A REVIEW");
        reviewLabel.setStyle("-fx-text-fill: #D4AF37; -fx-font-weight: bold;");
        
        TextField reviewerName = new TextField(); reviewerName.setPromptText("Your Name");
        ComboBox<Integer> ratingBox = new ComboBox<>();
        ratingBox.getItems().addAll(1, 2, 3, 4, 5); ratingBox.setPromptText("Rating");
        
        TextArea commentIn = new TextArea(); commentIn.setPromptText("Write your thoughts...");
        commentIn.setPrefHeight(80);
        
        Button submitReviewBtn = new Button("SUBMIT REVIEW");
        submitReviewBtn.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-weight: bold;");

        VBox reviewInputBox = new VBox(10, reviewLabel, new HBox(10, reviewerName, ratingBox), commentIn, submitReviewBtn);
        reviewInputBox.setPadding(new Insets(15));
        reviewInputBox.setStyle("-fx-background-color: #1A1A1A; -fx-border-color: #333;");

        // --- 5. Logic ---
        
        // Load reviews and fill admin fields on click
        movieTable.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                reviewDisplayArea.setText(reviewDAO.getReviewsForMovie(newVal.getTitle()));
                if(isAdmin) {
                    titleIn.setText(newVal.getTitle());
                    genreIn.setText(newVal.getGenre());
                    yearIn.setText(String.valueOf(newVal.getReleaseYear()));
                }
            }
        });

        // Submit Review Logic (User Action)
        submitReviewBtn.setOnAction(e -> {
            Movie selected = movieTable.getSelectionModel().getSelectedItem();
            if (selected != null && !reviewerName.getText().isEmpty() && ratingBox.getValue() != null) {
                reviewDAO.addReview(selected.getMovieId(), reviewerName.getText(), ratingBox.getValue(), commentIn.getText());
                reviewDisplayArea.setText(reviewDAO.getReviewsForMovie(selected.getTitle()));
                commentIn.clear();
            }
        });

        // Admin CRUD Actions
        addBtn.setOnAction(e -> {
            movieDAO.addMovie(titleIn.getText(), genreIn.getText(), Integer.parseInt(yearIn.getText()));
            refreshTable();
        });

        deleteBtn.setOnAction(e -> {
            Movie selected = movieTable.getSelectionModel().getSelectedItem();
            if(selected != null) {
                movieDAO.deleteMovie(selected.getMovieId());
                refreshTable();
            }
        });

        // --- 6. Final Layout ---
        mainLayout.getChildren().addAll(
            headerLabel, 
            new Label("Admin Controls:"), adminBox, 
            movieTable, 
            new Label("Recent Reviews:"), reviewDisplayArea, 
            reviewInputBox
        );

        stage.setScene(new Scene(mainLayout, 1000, 900));
        stage.setTitle("Movie Review System - Dashboard");
        stage.show();
        refreshTable();
    }

    private void refreshTable() {
        movieTable.setItems(FXCollections.observableArrayList(movieDAO.getAllMovies("")));
    }
}