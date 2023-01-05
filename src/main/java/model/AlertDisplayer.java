package model;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class AlertDisplayer {
    public void showOneSecondAlert(String title, String message) {
        Image image = new Image(String.valueOf(getClass().getResource("/view/images/Loading.gif")));

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Jumping to a random tile");
        alert.setContentText(message);
        alert.setGraphic(imageView);
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // We'll ignore this exception
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> alert.close());
        new Thread(sleeper).start();

        alert.showAndWait();
    }
}
