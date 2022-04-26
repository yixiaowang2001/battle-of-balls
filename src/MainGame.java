import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Color;

import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;

public class MainGame {
    public static final int CANVAS_WIDTH = 1000;
    public static final int CANVAS_HEIGHT = 750;
    public double ballSpeed = 7;

    private CanvasWindow canvas;
    private GameMap map;
    private Button start, menu, quit;
    private int score;
    private GraphicsText scoreBoard1, gameOver, caption;
    private Image window;
    private PlayerBall pb;
    private boolean isStart, isBound;
    private double offsetX, offsetY;

    public MainGame() {
        canvas = new CanvasWindow("Test", CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    public void run() {
        menu = new Button("Back to Menu");
        start = new Button("New Game");
        quit = new Button("Quit");
        isStart = false;
        resetGame();

        start.onClick(() -> {
            canvas.removeAll();
            startGame();
            menu.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.95);
            canvas.add(menu);
        });

        quit.onClick(() -> {
            canvas.closeWindow();
        });

        menu.onClick(() -> {
            resetGame();
        });

        inGame();

        canvas.animate(() -> {
            if (isStart) {
                if (pb.getArea() > 200000) {
                    endGame();
                }
            }
        });
    }

    private void createMap() {
        map = new GameMap();
        canvas.add(map.getGraphcs());
        map.getGraphcs().setCenter(0.5 * CANVAS_WIDTH, 0.5 * CANVAS_HEIGHT);
    }

    private void createPB() {
        pb = new PlayerBall(canvas);
    }

    private void resetGame() {

        isBound = false;
        offsetX = 0;
        offsetY = 0;

        canvas.removeAll();
        isStart = false;

        window = new Image("Background.jpg");
        canvas.add(window);

        caption = new GraphicsText("BATTLE OF BALLS");
        caption.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.0675);
        caption.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.4);
        canvas.add(caption);

        canvas.add(start);
        start.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8375);

        canvas.add(quit);
        quit.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8875);

        score = 0;
    }

    private void startGame() {
        createMap();
        createPB();
        isStart = true;
    }

    private void inGame() {

        canvas.onMouseMove(event -> {
            if (isStart) {
                updateBallSpeed();
                System.out.println(ballSpeed);
                double cos = (event.getPosition().getX() - canvas.getCenter().getX())
                        / event.getPosition().distance(canvas.getCenter());
                double sin = (event.getPosition().getY() - canvas.getCenter().getY())
                        / event.getPosition().distance(canvas.getCenter());
                if (event.getPosition() != canvas.getCenter()) {
                    double moveX = -cos * ballSpeed;
                    double moveY = -sin * ballSpeed;
                    isBound = false;
                    if ((offsetX + moveX < -5 * CANVAS_WIDTH + pb.getDiameter() / 2 ||
                            offsetX + moveX > 5 * CANVAS_WIDTH - pb.getDiameter() / 2)) {
                        moveX = 0;
                        isBound = true;
                    }
                    if ((offsetY + moveY <= -5 * CANVAS_HEIGHT + pb.getDiameter() / 2 ||
                            offsetY + moveY >= 5 * CANVAS_HEIGHT - pb.getDiameter() / 2)) {
                        moveY = 0;
                        isBound = true;
                    }
                    offsetX += moveX;
                    offsetY += moveY;

                    pb.collisionCircle(moveX, moveY);
                    map.getGraphcs().moveBy(moveX, moveY);

                    ifHitBound();

                    pb.returnCC().controlNum(offsetX, offsetY);
                }
            }
        });
    }

    /**
     * Ends the game when one of the players reaches the winning point.
     */
    private void endGame() {
        isStart = false;

        gameOver = new GraphicsText("Game Over! Your score is: " + (int)pb.getArea());
        gameOver.setFont(FontStyle.BOLD, CANVAS_WIDTH * 0.05);
        gameOver.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.45);
        canvas.add(gameOver);

        menu.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8375);
        canvas.add(menu);

        quit.setCenter(CANVAS_WIDTH / 2, CANVAS_HEIGHT * 0.8875);
        canvas.add(quit);
    }

    private void ifHitBound() {
        if (isBound) {
            List<Integer> boundSide = boundWhere();
            double moveDisX = 0;
            double moveDisY = 0;
            if (boundSide.get(0) == 1) {
                moveDisX = map.getGraphcs().getX() + pb.getDiameter() / 2 - 500;
                pb.moveCir(-moveDisX, 0);
                map.getGraphcs().moveBy(-moveDisX, 0);
                offsetX -= moveDisX;
            } else if (boundSide.get(0) == 2) {
                moveDisX = -9500 + pb.getDiameter() / 2 - map.getGraphcs().getX();
                pb.moveCir(moveDisX, 0);
                map.getGraphcs().moveBy(moveDisX, 0);
                offsetX += moveDisX;
            }
            if (boundSide.get(1) == 1) {
                moveDisY = map.getGraphcs().getY() + pb.getDiameter() / 2 - 375;
                pb.moveCir(0, -moveDisY);
                map.getGraphcs().moveBy(0, -moveDisY);
                offsetY -= moveDisY;
            } else if (boundSide.get(1) == 2) {
                moveDisY = -7125 + pb.getDiameter() / 2 - map.getGraphcs().getY();
                pb.moveCir(0, moveDisY);
                map.getGraphcs().moveBy(0, moveDisY);
                offsetY += moveDisY;
            }
        }
    }

    /**
     * Be called if the player ball hit the bound. Detect which bound collides the
     * ball. 0 represents no collision, 1 represents the left one or upper one, and
     * 2 represents the right one or bottom one.
     * 
     * @return 
     */
    private List<Integer> boundWhere() {
        List<Integer> retList = new ArrayList<>();
        retList.add(0);
        retList.add(0);
        if (map.getGraphcs().getX() - (-9500) < pb.getDiameter() / 2) {
            retList.set(0, 2);
        } else if (500 - map.getGraphcs().getX() < pb.getDiameter() / 2) {
            retList.set(0, 1);
        }
        if (map.getGraphcs().getY() - (-7125) < pb.getDiameter() / 2) {
            retList.set(1, 2);
        } else if (375 - map.getGraphcs().getY() < pb.getDiameter() / 2) {
            retList.set(1, 1);
        }
        return retList;
    }

    private void updateBallSpeed() {
        ballSpeed = 150 * 1 / pb.getDiameter() + 0.8;
    }

    public static void main(String[] args) {
        MainGame game = new MainGame();
        game.run();
    }
}
