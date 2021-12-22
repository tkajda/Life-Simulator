package agh.ics.oop.Interfaces;

import agh.ics.oop.WorldClasses.Vector2d;

public interface IMapElement {

    Vector2d getPosition();
    void addObserver(IPositionChangeObserver observer);
    String getName();
    String toString();
    String imageAddress();
}
