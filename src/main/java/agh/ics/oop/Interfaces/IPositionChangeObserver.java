package agh.ics.oop.Interfaces;

import agh.ics.oop.WorldClasses.Animal;
import agh.ics.oop.WorldClasses.Vector2d;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);
}
