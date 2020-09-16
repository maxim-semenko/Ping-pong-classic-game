import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


class Menu {

    private short startY = 170;
    private short startX = 220;

    private Text title = new Text(startX - 80, startY - 100, "Ping-pong");

    private Text btn1 = new Text(startX, startY, "1 игрок");
    private Text btn2 = new Text(startX - 15, startY + 60, "2 игрока");
    private Text btn3 = new Text(startX - 10, startY + 120, "Справка");
    private Text btn4 = new Text(startX + 25, startY + 180, "Автор");
    private Text btn5 = new Text(startX + 20, startY + 240, "Выход");
    private Game game = new Game();
    private Stage stage;


    private byte currentPosition = 1;
    private boolean isMenu = true;

    private AboutControl aboutControl = new AboutControl();
    private About about = new About();


    Menu(Stage stage) {
        this.stage = stage;
    }


    // Старт меню
    public void start() {
        Main.group.getChildren().clear();
        setIsMenu(true);
        control();
        init();
    }


    // Стоп меню
    private void stop() {
        Main.group.getChildren().clear();
        setIsMenu(false);
    }


    // Инициализация
    private void init() {
        currentPosition = 1;
        setObjectProperties();
        Main.group.getChildren().addAll(title, btn1, btn2, btn3, btn4, btn5);
    }


    // Задаем свойства объектам меню
    private void setObjectProperties() {
        changeColor();
        title.setFont(Font.font(80));
        btn1.setFont(Font.font(60));
        btn2.setFont(Font.font(60));
        btn3.setFont(Font.font(60));
        btn4.setFont(Font.font(60));
        btn5.setFont(Font.font(60));
    }


    // Меняем все надписи на белый цвет
    private void changeDefaultColor() {
        title.setFill(Color.WHITE);
        btn1.setFill(Color.WHITE);
        btn2.setFill(Color.WHITE);
        btn3.setFill(Color.WHITE);
        btn4.setFill(Color.WHITE);
        btn5.setFill(Color.WHITE);
    }


    // Меняем цвет надписи в зависимости на какой позиции меню нахоидимся
    private void changeColor() {
        changeDefaultColor();
        controlRangePosition();
        switch (currentPosition) {
            case 1:
                btn1.setFill(Color.RED);
                break;
            case 2:
                btn2.setFill(Color.RED);
                break;
            case 3:
                btn3.setFill(Color.RED);
                break;
            case 4:
                btn4.setFill(Color.RED);
                break;
            case 5:
                btn5.setFill(Color.RED);
                break;
        }
    }


    // Получаем позицию меню
    private byte getPosition() {
        return currentPosition;
    }


    // Устанавливаем находимся ли мы в меню
    private void setIsMenu(boolean isMenu) {
        this.isMenu = isMenu;
    }


    // Получаем в меню или нет
    private boolean getIsMenu() {
        return isMenu;
    }


    // Контроль меню
    private void control() {
        Main.scene.setOnKeyPressed(key -> {
            if (getIsMenu()) {
                positionControl(key);
                selectActionInMenu(key);
            } else {
                selectActionInGame(key);
            }
            if (about.getIsAbout() || aboutControl.getIsAboutControl()) {
                actionInAbout(key);
            }
        });
    }


    // Действия в окне об авторе
    private void actionInAbout(KeyEvent key) {
        if (key.getCode().getName().equals("Esc") && about.getIsAbout()) {
            start();
            about.setIsAbout(false);
        }
        if (key.getCode().getName().equals("Esc") && aboutControl.getIsAboutControl()) {
            start();
            aboutControl.setIsAboutControl(false);
        }
    }


    // Действие в меню
    private void selectActionInMenu(KeyEvent key) {
        if (key.getCode().getName().equals("Enter")) {
            switch (getPosition()) {
                case 1:
                    startWindowGame(true);
                    break;
                case 2:
                    startWindowGame(false);
                    break;
                case 3:
                    startWindowAboutControl();
                    break;
                case 4:
                    startWindowAbout();
                    break;
                case 5:
                    stage.close();
                    break;
            }
            currentPosition = 1;
        }
    }


    // Старт окна игры
    private void startWindowGame(boolean flag) {
        stop();
        game.setIsSinglePlayer(flag);
        game.start();
    }


    // Старт окна справки
    private void startWindowAboutControl() {
        aboutControl.start();
        aboutControl.setIsAboutControl(true);
    }


    // Старт окна об авторе
    private void startWindowAbout() {
        about.start();
        about.setIsAbout(true);
    }


    // Действие в игре
    private void selectActionInGame(KeyEvent key) {
        if (key.getCode().getName().equals("Esc")) {
            game.stop();
            start();
        }
        if (game.getIsEndGame()) {
            positionControl(key);
            game.changeColor(currentPosition);
            if (key.getCode().getName().equals("Enter")) {
                switch (currentPosition) {
                    case 1:
                        restartGame();
                        break;
                    case 2:
                        exitGame();
                        break;
                }
            }
        } else {
            game.playerControl(key);
        }
    }


    // Перезапуск игры
    private void restartGame() {
        Main.group.getChildren().clear();
        game.stop();
        game.start();
    }


    // Выход из игры
    private void exitGame() {
        game.stop();
        start();
    }


    // Двигаемся по пунктам меню
    private void positionControl(KeyEvent key) {
        if (key.getCode().getName().equals("W") || key.getCode().getName().equals("Up")) {
            currentPosition--;
        }
        if (key.getCode().getName().equals("S") || key.getCode().getName().equals("Down")) {
            currentPosition++;
        }
        changeColor();
    }


    // Контроль позиции за выход из диапазона
    private void controlRangePosition() {
        if (currentPosition < 1) {
            currentPosition = 1;
        }
        if (isMenu) {
            if (currentPosition > 5) {
                currentPosition = 5;
            }
        } else if (currentPosition > 2) {
            currentPosition = 2;
        }
    }
}