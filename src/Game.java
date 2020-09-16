import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


class Game {

    // Главные объекты
    private AnimationTimer timer;
    private Player player1;
    private Player player2;
    private Bot bot;
    private Ball ball;

    // Переменные для игры
    private byte scorePlayer1;
    private byte scorePlayer2;
    private byte countOfIncreaseSpeed;
    private byte scoreEndGame = 10;
    private byte victorySide = 0;


    // Объекты окружения
    private Text textScorePlayer1 = new Text(270, 60, String.valueOf(scorePlayer1));
    private Text textScorePlayer2 = new Text(340, 60, String.valueOf(scorePlayer2));
    private Text btn1 = new Text(80, 200, "Играть");
    private Text btn2 = new Text(80, 260, "Меню");
    private Text textVictory = new Text(80, 140, "Победа!");
    private Line line = new Line(Main.WIDTH / 2.0, 0, Main.WIDTH / 2.0, Main.HEIGHT);
    private Media media = new Media(getClass().getResource("music/battle.mp3").toExternalForm());
    private MediaPlayer mediaPlayer;


    // Переменны, отвечающие за нажатие клавиш
    private boolean isPressedW;
    private boolean isPressedS;
    private boolean isPressedUp;
    private boolean isPressedDown;


    // Переменные для контроля игры
    private boolean isEndGame;
    private boolean isSinglePlayer;


    // Старт игра
    public void start() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
        init();
    }


    // Стоп игра
    public void stop() {
        Main.group.getChildren().clear();
        timer.stop();
        mediaPlayer.stop();
    }

    // Инициализация
    private void init() {
        initOfVariables();
        createObjects();
        setObjectProperties();
        displayObjects();
    }


    // Инициализируем переменные
    private void initOfVariables() {
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        countOfIncreaseSpeed = 0;
        isEndGame = false;
    }


    // Функция обновления всего игрового процесса
    private void update() {
        moveObjects();
        checkBound();
        refreshScore();
        checkEndGame();
    }


    // Создание объектов
    private void createObjects() {
        player1 = new Player();
        ball = new Ball();
        bot = new Bot();
        player2 = new Player();
        textScorePlayer1.setText(String.valueOf(scorePlayer1));
        textScorePlayer2.setText(String.valueOf(scorePlayer2));
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }


    // Управление игроками
    public void playerControl(KeyEvent key) {
        keyPressed(key);
        keyReleased();
    }


    // Проверка конца игры
    private void checkEndGame() {
        if (scorePlayer1 == scoreEndGame) {
            createEndGame((byte) 0);
        } else if (scorePlayer2 == scoreEndGame) {
            createEndGame((byte) 1);
        }
    }


    // Установить одиночная ли игра
    public void setIsSinglePlayer(boolean isSinglePlayer) {
        this.isSinglePlayer = isSinglePlayer;
    }


    // Вывод объектов на экран
    private void displayObjects() {
        player1.display((short) 20);
        ball.display();
        if (isSinglePlayer) {
            bot.display();
        } else {
            player2.display((short) 605);
        }
        Main.group.getChildren().addAll(textScorePlayer1, textScorePlayer2);
        Main.group.getChildren().add(line);
    }


    // Применить свойства для объектов на экране
    private void setObjectProperties() {
        textScorePlayer1.setFill(Color.WHITE);
        textScorePlayer2.setFill(Color.WHITE);

        textScorePlayer1.setFont(Font.font(60));
        textScorePlayer2.setFont(Font.font(60));

        btn1.setFont(Font.font(50));
        btn2.setFont(Font.font(50));

        btn1.setFill(Color.RED);
        btn2.setFill(Color.WHITE);

        textVictory.setFont(Font.font(50));
        textVictory.setFill(Color.WHITE);

        line.setStrokeWidth(4);
        line.setStroke(Color.WHITE);
        line.getStrokeDashArray().addAll(15.0, 20.6);
    }


    // Движение объектов
    private void moveObjects() {
        movePlayer1();
        if (isSinglePlayer) {
            bot.play(ball, countOfIncreaseSpeed);
        } else {
            movePlayer2();
        }
        ball.move();
    }


    // Движение игрока1
    private void movePlayer1() {
        if (isPressedW && player1.getY() > 0) {
            player1.move((byte) -player1.getSpeed());
        }
        if (isPressedS && player1.getY() < Main.HEIGHT - player1.getObject().getHeight()) {
            player1.move(player1.getSpeed());
        }
    }


    // Движение игрока2
    private void movePlayer2() {
        if (isPressedUp && player2.getY() > 0) {
            player2.move((byte) -player2.getSpeed());
        }
        if (isPressedDown && player2.getY() < Main.HEIGHT - player2.getObject().getHeight()) {
            player2.move(player2.getSpeed());
        }
    }


    // Проверка столкновение мяча
    private void checkBound() {
        checkBoundOfObjects(player1.getObject());
        if (isSinglePlayer) {
            checkBoundOfObjects(bot.getObject());
        } else {
            checkBoundOfObjects(player2.getObject());
        }
    }


    // Столкновение мяча с объектами
    private void checkBoundOfObjects(Rectangle object) {
        if (object.getBoundsInParent().intersects(ball.getObject().getBoundsInParent())) {
            if (object == player1.getObject()) {
                ball.getObject().setX(object.getX() + ball.getObject().getWidth());
            } else {
                ball.getObject().setX(object.getX() - ball.getObject().getWidth());
                bot.setIsMove(false);
            }
            actionAfterBound();
        }
    }


    // Действия после столкновения мяча с платформой
    private void actionAfterBound() {
        ball.setMoveX((byte) (-ball.getMoveX()));
        if (ball.getMoveY() == 0) {
            ball.setMoveY((byte) 4);
        }
        increaseBallSpeed();
    }


    // Обновление счета при забитом мяче
    private void refreshScore() {
        if (ball.getObject().getX() > Main.WIDTH + ball.getObject().getWidth() / 2 + 150 + countOfIncreaseSpeed * 25) {
            victorySide = 1;
            textScorePlayer1.setText(String.valueOf(++scorePlayer1));
        }
        if (ball.getObject().getX() < 0 - ball.getObject().getWidth() / 2 - 150 - countOfIncreaseSpeed * 25) {
            victorySide = 2;
            textScorePlayer2.setText(String.valueOf(++scorePlayer2));
        }
        if (victorySide != 0) nullifyVariables();
    }


    // Создать конец игры
    private void createEndGame(byte selection) {
        isEndGame = true;
        btn1.setX(btn1.getX() + selection * 260);
        btn2.setX(btn2.getX() + selection * 260);
        textVictory.setX(textVictory.getX() + selection * 260);
        textScorePlayer1.setX(selection == 0 ? textScorePlayer1.getX() - 30 : textScorePlayer1.getX());
        showEndGame();
        timer.stop();
    }


    // Показать конец игры
    private void showEndGame() {
        Main.group.getChildren().addAll(btn1, btn2, textVictory);
        Main.group.getChildren().remove(ball.getObject());
    }


    // Увеличиваем скорость мяча
    private void increaseBallSpeed() {
        if (ball.getMoveX() > 0) {
            ball.setMoveX((byte) (ball.getMoveX() + 1));
        } else {
            ball.setMoveX((byte) (ball.getMoveX() - 1));
        }
        countOfIncreaseSpeed++;
    }


    // Приводим переменные к исходным данным при забитом мяче
    private void nullifyVariables() {
        nullifyBall();
        countOfIncreaseSpeed = 0;
        victorySide = 0;
    }


    // Приводим мяч к исходным данным
    private void nullifyBall() {
        ball.setMoveY((byte) 0);
        ball.getObject().setX(300);
        ball.getObject().setY((Main.HEIGHT - ball.getObject().getHeight()) / 2.0);
        if (victorySide == 1) {
            ball.setMoveX((byte) 6);
        } else {
            ball.setMoveX((byte) -6);
        }
    }


    // Меняем цвет меню
    public void changeColor(byte position) {
        btn1.setFill(Color.WHITE);
        btn2.setFill(Color.WHITE);
        if (position == 1) {
            btn1.setFill(Color.RED);
        } else {
            btn2.setFill(Color.RED);
        }
    }


    // вернуть значение isEndGame
    public boolean getIsEndGame() {
        return isEndGame;
    }


    // Клавиша нажата
    private void keyPressed(KeyEvent key) {
        if (key.getCode().getName().equals("W")) {
            isPressedW = true;
        }
        if (key.getCode().getName().equals("S")) {
            isPressedS = true;
        }
        if (key.getCode().getName().equals("Up")) {
            isPressedUp = true;
        }
        if (key.getCode().getName().equals("Down")) {
            isPressedDown = true;
        }
    }


    // Клавиша отжата
    private void keyReleased() {
        Main.scene.setOnKeyReleased(key -> {
            if (key.getCode().getName().equals("W")) {
                isPressedW = false;
            }
            if (key.getCode().getName().equals("S")) {
                isPressedS = false;
            }
            if (key.getCode().getName().equals("Up")) {
                isPressedUp = false;
            }
            if (key.getCode().getName().equals("Down")) {
                isPressedDown = false;
            }
        });
    }
}