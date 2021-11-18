package agh.ics.oop;

public class Grass {
    private Vector2d v;

    public Grass(Vector2d grassPosition) {
        v.x = grassPosition.x;
        v.y = grassPosition.y;
    }
    public Vector2d getPosition(){
        return v;
    }
    public String toString() {
        return "*";
    }



}
