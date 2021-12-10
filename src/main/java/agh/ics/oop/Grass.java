package agh.ics.oop;



public class Grass implements IMapElement {

    private final Vector2d v;

    public Grass(Vector2d grassPosition) {
        v = grassPosition;
    }
    public Vector2d getPosition() {
        return v;
    }
    public String toString() {
        return "*";
    }

    @Override
    public String imageAddress() {
        return "src/main/resources/grass.png";
    }
    @Override
    public String getName() {
        return "Trawa";
    }

}
