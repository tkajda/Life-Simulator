package agh.ics.oop;



public class Grass {

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

}
