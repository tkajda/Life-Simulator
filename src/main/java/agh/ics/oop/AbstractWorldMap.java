package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap {


    protected List<Animal> animals = new ArrayList<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);
    List<Grass> grassFields = new ArrayList<>();



    protected Border getDrawingBorders() {

        int inf = Integer.MAX_VALUE;
        Vector2d bottomLeft = new Vector2d(inf,inf);
        Vector2d topRight = new Vector2d(-inf,-inf);

        for(Grass grass: grassFields) {
            topRight.upperRight(grass.getPosition());
            bottomLeft.lowerLeft(grass.getPosition());
        }
        Border border =new Border(bottomLeft, topRight);

        for (Animal a : animals) {
            border = border.expanses(a.getPos());
        }

        return new Border(
                border.getBL(),
                border.getTR()
        );
    }
//    Vector2d v = new Vector2d(3,8);
//    Vector2d u = new Vector2d(-1,-6);


    @Override
    public String toString() {
        Border borders = getDrawingBorders();
        return visualizer.draw(borders.getBL(), borders.getTR());
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
    public boolean canMoveTo(Vector2d position) {
        for (Animal i: animals) {
            if (i.getPos().equals(position)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal i : animals) {
            if (i.getPos().equals(position)) {
                return true;
            }
        }
        for (Grass j: grassFields) {
            if(j.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {


        if (isOccupied(position)) {
            for (Animal j: animals) {
                if(j.getPos().equals(position)){
                    return j;
                }
            }
            for (Grass i : grassFields) {
                if (i.getPosition().equals(position)) {
                    return i;
                }
            }

        }

        return null;
    }

}
