import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

class About {

    private String text = "Данная программа сделана студентом\n группы 951008 - Семенько Максимом\n" +
            "    Контакт: maks.semenko@gmail.com\n\n" +
            "Copyright © 2020. Все права защищены\n" +
            "             Esc - вернуться назад";

    private Text textContent = new Text(100, 150, text);
    private boolean isAbout;


    public void start() {
        Main.group.getChildren().clear();
        setProperties();
        Main.group.getChildren().add(textContent);
    }


    private void setProperties() {
        textContent.setFill(Color.WHITE);
        textContent.setFont(Font.font(25));
    }


    public void setIsAbout(boolean isAbout) {
        this.isAbout = isAbout;
    }


    public boolean getIsAbout() {
        return isAbout;
    }
}
