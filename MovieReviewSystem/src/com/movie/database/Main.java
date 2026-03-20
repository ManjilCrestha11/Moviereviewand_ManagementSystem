package com.movie.database;

import com.movie.view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Just launch the login view directly
        LoginView login = new LoginView();
        login.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}