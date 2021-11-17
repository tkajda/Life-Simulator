package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;


class RectangularMap implements IWorldMap {
    private final Vector2d topRight;
    private final Vector2d bottomLeft;
    List<Animal> animals = new ArrayList<>();
    MapVisualizer visualizer = new MapVisualizer(this);

    public RectangularMap(int width, int height) {
        if (width >= 1 && height >= 1) {
            this.bottomLeft = new Vector2d(0, 0);
            this.topRight = new Vector2d(width-1, height-1);
        } else {
            throw new IllegalArgumentException();
        }
    }

//    public Vector2d getTopRight() {
//        return topRight;
//    }
//
//    public Animal getAnimal(int i) {
//        return animals.get(i);
//    }


    public String toString() {
        return visualizer.draw(bottomLeft, topRight);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return bottomLeft.precedes(position) && position.precedes(topRight) &&
                !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPos())) {
            return false;
        }
        animals.add(animal);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal i : animals) {
            if (i.getPos().equals(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (isOccupied(position)) {
            for (Animal animal : animals) {
                if (animal.getPos().equals(position)) {
                    return animal;
                }
            }
        }
        return null;
    }
}
