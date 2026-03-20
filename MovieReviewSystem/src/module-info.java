/**
 * 
 */
/**
 * 
 */
module MovieReviewSystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; 

    opens com.movie.view to javafx.graphics, javafx.fxml;
    opens com.movie.model to javafx.base;
}
