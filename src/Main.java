import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    static final short WIDTH = 640;
    static final short HEIGHT = 480;

    static Group group;
    static Scene scene;


    @Override
    public void start(Stage stage) {

        group = new Group();
        scene = new Scene(group, WIDTH, HEIGHT, Color.BLACK);
        Menu menu = new Menu(stage);


        scene.setCursor(Cursor.NONE);
        setProperties(stage);
        stage.show();


        // Запуск меню
        menu.start();
    }


    private void setProperties(Stage stage) {
        stage.setResizable(false);
        stage.setTitle("Ping-pong");
        stage.setScene(scene);
        setIcon(stage);
    }


    private void setIcon(Stage stage) {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}

