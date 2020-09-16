import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


class Player {

    private final byte WIDTH = 15;
    private final byte HEIGHT = 80;
    private short y = (Main.HEIGHT - HEIGHT) / 2;
    private byte speed = 6;
    private Rectangle object;


    // Отображение объекта
    public void display(short x) {
        object = new Rectangle(x, y, WIDTH, HEIGHT);
        object.setFill(Color.WHITE);
        Main.group.getChildren().add(object);
    }


    // Движение объекта
    public void move(byte value) {
        if (value < 0) speed = (byte) -speed;
        object.setY(object.getY() + speed);
        y = (short) object.getY();
    }


    public short getY() {
        return y;
    }


    public byte getSpeed() {
        return speed;
    }

    public Rectangle getObject() {
        return object;
    }

}