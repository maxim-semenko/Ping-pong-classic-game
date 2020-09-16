import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

class AboutControl {

    private String text1 = "Управление 1-го игрока:" +
            "\n W - движение вверх" +
            "\n S - движение вниз\n" +
            "\n Управление 2-го игрока:" +
            "\n ↑ - движение вверх" +
            "\n ↓ - движение вниз";

    private String text2 = "Правила игры: управляйте платформой и отбивайте мяч." +
            "\n                         Выиграет тот, кто первый забьет 10 голов.";

    private Text textContent1 = new Text(180, 130, text1);
    private Text textContent2 = new Text(55, 430, text2);
    private boolean isAboutControl;


    public void start() {
        Main.group.getChildren().clear();
        setProperties();
        Main.group.getChildren().add(textContent1);
        Main.group.getChildren().add(textContent2);
    }


    private void setProperties() {
        textContent1.setFill(Color.WHITE);
        textContent1.setFont(Font.font(25));
        textContent2.setFill(Color.WHITE);
        textContent2.setFont(Font.font(20));
    }


    public void setIsAboutControl(boolean isAboutControl) {
        this.isAboutControl = isAboutControl;
    }


    public boolean getIsAboutControl() {
        return isAboutControl;
    }
}
