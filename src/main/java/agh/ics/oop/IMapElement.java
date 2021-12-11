package agh.ics.oop;

public interface IMapElement {

    Vector2d getPosition();
    void addObserver(IPositionChangeObserver observer);
    String getName();
    String toString();
    String imageAddress();
}
