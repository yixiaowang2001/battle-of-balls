import edu.macalester.graphics.*;

/**
 * A map of type 3
 */
public class Map3 implements GameMap {
    private GraphicsGroup group;
    private Brick part1, part2, part3, part4, part5, part6, part7, part8, part9, part10, part11, part12, part13, part14,
        part15, part16, part17, part18, part19, part20, part21, part22, part23, part24, part25;

    /**
     * Creates a type 3 map
     */
    public Map3() {
        group = new GraphicsGroup();

        part1 = new Brick(0, 0, 1140, 20);
        group.add(part1);

        part2 = new Brick(0, 20, 20, 840);
        group.add(part2);

        part3 = new Brick(20, 840, 1120, 20);
        group.add(part3);

        part4 = new Brick(1120, 20, 20, 820);
        group.add(part4);

        part5 = new Brick(140, 20, 20, 140);
        group.add(part5);

        part6 = new Brick(140, 280, 20, 440);
        group.add(part6);

        part7 = new Brick(280, 20, 20, 140);
        group.add(part7);

        part8 = new Brick(980, 560, 140, 20);
        group.add(part8);

        part9 = new Brick(560, 20, 20, 120);
        group.add(part9);

        part10 = new Brick(420, 140, 160, 20);
        group.add(part10);

        part11 = new Brick(700, 140, 160, 20);
        group.add(part11);

        part12 = new Brick(700, 160, 20, 120);
        group.add(part12);

        part13 = new Brick(980, 140, 20, 160);
        group.add(part13);

        part14 = new Brick(280, 280, 700, 20);
        group.add(part14);

        part15 = new Brick(560, 300, 20, 140);
        group.add(part15);

        part16 = new Brick(580, 420, 280, 20);
        group.add(part16);

        part17 = new Brick(280, 420, 160, 20);
        group.add(part17);

        part18 = new Brick(280, 440, 20, 120);
        group.add(part18);

        part19 = new Brick(160, 560, 700, 20);
        group.add(part19);

        part20 = new Brick(280, 700, 20, 140);
        group.add(part20);

        part21 = new Brick(420, 580, 20, 140);
        group.add(part21);

        part22 = new Brick(440, 700, 280, 20);
        group.add(part22);

        part23 = new Brick(840, 700, 20, 140);
        group.add(part23);

        part24 = new Brick(860, 700, 140, 20);
        group.add(part24);

        part25 = new Brick(980, 420, 20, 140);
        group.add(part25);
    }

    @Override
    public GraphicsObject getGraphics() {
        return group;
    }

    @Override
    public String toString() {
        return "Map3 [group=" + group + ", part1=" + part1 + ", part10=" + part10 + ", part11=" + part11 + ", part12="
            + part12 + ", part13=" + part13 + ", part14=" + part14 + ", part15=" + part15 + ", part16=" + part16
            + ", part17=" + part17 + ", part18=" + part18 + ", part19=" + part19 + ", part2=" + part2 + ", part20="
            + part20 + ", part21=" + part21 + ", part22=" + part22 + ", part23=" + part23 + ", part24=" + part24
            + ", part25=" + part25 + ", part3=" + part3 + ", part4=" + part4 + ", part5=" + part5 + ", part6=" + part6
            + ", part7=" + part7 + ", part8=" + part8 + ", part9=" + part9 + "]";
    }
}
