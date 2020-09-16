import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Ball {

    private Rectangle object;
    private byte sign = (byte) (Math.random() * 2);
    private byte moveX;
    private byte moveY = 0;
    private final byte size = 16;


    // Отображение объекта
    public void display() {
        object = new Rectangle(300, (Main.HEIGHT - size) / 2.0, size, size);
        object.setFill(Color.WHITE);
        initMoveX();
        Main.group.getChildren().add(object);
    }

    public void initMoveX() {
        if (sign > 0) {
            moveX = 6;
        } else {
            moveX = -6;
        }
    }


    // Движение объекта
    public void move() {
        object.setX(object.getX() + moveX);
        object.setY(object.getY() + moveY);
        checkOutOfGameField();
    }


    // Проверка на выход за пределы поля
    private void checkOutOfGameField() {
        if (object.getY() < 0) {
            moveY = (byte) -moveY;
        }
        if (object.getY() > Main.HEIGHT - size) {
            moveY = (byte) -moveY;
        }
    }


    public void setMoveX(byte moveX) {
        this.moveX = moveX;
    }


    public void setMoveY(byte moveY) {
        this.moveY = moveY;
    }


    public byte getMoveX() {
        return moveX;
    }


    public byte getMoveY() {
        return moveY;
    }

    public Rectangle getObject() {
        return object;
    }

}

