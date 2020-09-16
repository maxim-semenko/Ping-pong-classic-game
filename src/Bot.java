import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


class Bot {

    private final byte WIDTH = 15;
    private final byte HEIGHT = 80;
    private byte speed = 6;
    private boolean isMove;
    private Rectangle object;


    // Отображение объекта
    public void display() {
        object = new Rectangle(605, (Main.HEIGHT - HEIGHT) / 2.0, WIDTH, HEIGHT);
        object.setFill(Color.WHITE);
        Main.group.getChildren().add(object);
    }


    // Движение объекта
    private void move(byte value) {
        if (value < 0) speed = (byte) -speed;
        object.setY(object.getY() + speed);
    }


    // Запуск объекта
    public void play(Ball ball, byte countOfIncreaseSpeed) {
        if (ball.getMoveX() < 0 && ball.getObject().getX() < 175 - countOfIncreaseSpeed * 2) {
            setIsMove(true);
        }
        checkCoordinates(ball);
    }


    // Проверка координат
    private void checkCoordinates(Ball ball) {
        if (isMove) {
            if (ball.getObject().getY() - object.getHeight() / 2 > object.getY()) {
                move((byte) getSpeed());
            } else {
                move((byte) -getSpeed());
            }
        }
    }


    public void setIsMove(boolean isMove) {
        this.isMove = isMove;
    }


    public Rectangle getObject() {
        return object;
    }


    private byte getSpeed() {
        return speed;
    }

}