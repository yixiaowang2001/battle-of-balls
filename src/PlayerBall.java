import java.util.Random;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.Point;

public class PlayerBall extends Ball {

    private static final int CIRCLE_RAIDUS = 20;
    private double speed;
    private CanvasWindow canvas;
    private Ellipse ballShape;
    private Boolean flag;
    private double resizeValue;

    public PlayerBall(CanvasWindow canvas) {

        this.canvas = canvas;
        speed = 0;
        create();
    }

    public void create() {
        Color randColor = createRandColor();
        Point startPoint = new Point(canvas.getWidth() * 0.5 - CIRCLE_RAIDUS, canvas.getHeight() * 0.5 - CIRCLE_RAIDUS);
        ballShape = new Ellipse(startPoint.getX(), startPoint.getY(), CIRCLE_RAIDUS * 2, CIRCLE_RAIDUS * 2);
        ballShape.setFillColor(randColor);
        ballShape.setStrokeColor(randColor.darker());
        ballShape.setStrokeWidth(5);
        canvas.add(ballShape);
    }

    public Color createRandColor() {
        Random rand = new Random();
        float[] hsb = Color.RGBtoHSB(rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0, rand.nextInt(255 - 0) + 0,
                null);
        Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        return color;
    }

    private void resizeAIBall() {
        if (flag) {
            ballShape.setSize(ballShape.getWidth() + 10, ballShape.getHeight() + 10);
            flag = false;
        }
    }

    public void collisionCircle(double dx, double dy, CircleControl cc) {
        Iterator<Circle> itrCir = cc.getCircleList().iterator();
        while (itrCir.hasNext()) {
            Circle cir = itrCir.next();
            cir.getGraphics().moveBy(dx, dy);
            if (ballShape.getCenter().distance(cir.getCtr()) <= ballShape.getHeight() / 2 + cir.getRadius()) {
                resizeCir();
                canvas.remove(cir.getGraphics());
                itrCir.remove();
            }
        }
    }

    public void collisionBall(double dx, double dy, AIBallControl ac) {
        Iterator<AIBall> itrBall = ac.getBallList().iterator();
        while (itrBall.hasNext()) {
            AIBall ball = itrBall.next();
            ball.autoMove(0, 0);
            ball.getGraphics().moveBy(dx, dy);
            ball.getGraphicsName().moveBy(dx, dy);
            if (isCollision(ballShape.getCenter(), ball.getCtr(), getDiameter() / 2, ball.getRadius(), 0.85)) {
                if (getDiameter() / 2 > ball.getRadius()) {
                    System.out.println("牛逼！");
                    resizeBall(ball);
                    canvas.remove(ball.getGraphics());
                    canvas.remove(ball.getGraphicsName());
                    itrBall.remove();
                } else {
                    // 被吃
                    System.out.println("废物！");
                }
            }
        }
    }

    private boolean isCollision(Point ctr1, Point ctr2, double r1, double r2, double rate) {
        double dis = ctr1.distance(ctr2);
        PriorityQueue<Double> pq = new PriorityQueue<>();
        pq.add(r1);
        pq.add(r2);
        double r = pq.poll();
        double R = pq.poll();
        if (r / R <= rate) {
            if (dis <= R) {
                return true;
            }
        }
        return false;
    }

    private void resizeCir() {
        ballShape.setSize(Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(Circle.CIRCLE_RAIDUS, 2)),
                Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(Circle.CIRCLE_RAIDUS, 2)));
        ballShape.setCenter(canvas.getWidth() * 0.5, canvas.getHeight() * 0.5);
    }

    private void resizeBall(AIBall aiBall) {
        ballShape.setSize(Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(aiBall.getRadius(), 2)),
                Math.sqrt(Math.pow(ballShape.getHeight(), 2) + Math.pow(aiBall.getRadius(), 2)));
        ballShape.setCenter(canvas.getWidth() * 0.5, canvas.getHeight() * 0.5);
    }

    public void updateSpeed() {
        speed = 100 * 1 / ballShape.getHeight() + 0.8;
    }

    public double getSpeed() {
        return speed;
    }

    // public CircleControl returnCC() {
    // return cc;
    // }

    // public AIBallControl returnAC() {
    // return ac;
    // }

    public double getDiameter() {
        return ballShape.getHeight();
    }

    public double getArea() {
        return Math.PI * Math.pow(ballShape.getHeight(), 2);
    }

    public double getResizeVal() {
        return resizeValue;
    }
}
