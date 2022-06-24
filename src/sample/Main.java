package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    final int SCENE_W = 400;
    final int SCENE_H = 300;
    final int HEIGHT = 40;
    final int WIDTH = 70;

    @Override
    public void start(Stage stage) {

        Pane rootPane = new Pane();
        Scene scene = new Scene(rootPane, 400, 300, Color.ALICEBLUE);
        scene.setFill(Color.BLACK);

        ImageView iv = new ImageView(new Image("http://adilkhan.pythonanywhere.com/media/images/dvd_video.png"));
        iv.setFitWidth(WIDTH);
        iv.setFitHeight(HEIGHT);
        iv.setStyle("-fx-background-color: black;");

        StackPane stackPane = new StackPane(iv);
        stackPane.setMaxWidth(WIDTH);
        stackPane.setMaxHeight(HEIGHT);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.relocate((int) ((Math.random() * (SCENE_W - WIDTH * 2)) + WIDTH), (int) ((Math.random() * (SCENE_H - HEIGHT * 2)) + HEIGHT));

        rootPane.getChildren().add(stackPane);

        stage.setTitle("Bouncing DVD Screensaver");
        stage.setScene(scene);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20),
                new EventHandler<ActionEvent>() {

                    double dx = 1; //Step on x or velocity
                    double dy = 1; //Step on y

                    @Override
                    public void handle(ActionEvent e) {
                        //move the ball
                        stackPane.setLayoutX(stackPane.getLayoutX() + dx);
                        stackPane.setLayoutY(stackPane.getLayoutY() + dy);

                        Bounds bounds = rootPane.getBoundsInLocal();

                        //If the ball reaches the left or right border make the step negative
                        if (stackPane.getLayoutX() <= (bounds.getMinX()) ||
                                stackPane.getLayoutX() >= (bounds.getMaxX() - stackPane.getWidth())) {
                            new ChangeColor(iv);
                            dx = -dx;

                        }

                        //If the ball reaches the bottom or top border make the step negative
                        if ((stackPane.getLayoutY() >= (bounds.getMaxY() - stackPane.getHeight())) ||
                                (stackPane.getLayoutY() <= (bounds.getMinY()))) {
                            new ChangeColor(iv);
                            dy = -dy;

                        }
                    }
                }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

class ChangeColor {
    ChangeColor(ImageView iv) {
        Lighting lighting = new Lighting(new Light.Distant(45, 90, Color.color(Math.random(), Math.random(), Math.random())));
        ColorAdjust bright = new ColorAdjust(0, 1, 1, 1);
        lighting.setContentInput(bright);
        lighting.setSurfaceScale(0.0);
        iv.setEffect(lighting);
    }
}