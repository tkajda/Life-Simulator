package agh.ics.oop.WorldClasses;

import agh.ics.oop.Interfaces.IPositionChangeObserver;

public class BorderlessMap extends Map implements IPositionChangeObserver {


    public BorderlessMap(int MapHeight, int MapWidth, double JungleRatio, int StartEnergy, int PlantEnergy, int MoveEnergy) {
        super(MapHeight, MapWidth, JungleRatio, StartEnergy, PlantEnergy, MoveEnergy);
    }

    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    public Vector2d calculateNewPosition(Vector2d oldPosition, Vector2d newPosition,Animal animal) {
        if (!oldPosition.equals(newPosition)) {
            if (animals.get(oldPosition) != null) {
                if(super.canMoveTo(newPosition)) {
                    super.positionChanged(oldPosition,newPosition,animal);
                    return newPosition;
                }
                if (newPosition.x > super.mapWidth - 1 && newPosition.y == oldPosition.y) {
                    newPosition = new Vector2d(0, newPosition.y);
                } else if (newPosition.x < 0 && newPosition.y == oldPosition.y) {
                    newPosition = new Vector2d(mapWidth - 1, newPosition.y);
                } else if (newPosition.y > mapHeight - 1 && newPosition.x == oldPosition.x) {
                    newPosition = new Vector2d(newPosition.x, 0);
                } else if (newPosition.y < 0 && newPosition.x == oldPosition.x) {
                    newPosition = new Vector2d(newPosition.x, mapHeight - 1);
                } else if (newPosition.follows(oldPosition)) {
                    newPosition = new Vector2d(0, 0);
                } else if (newPosition.precedes(oldPosition)) {
                    newPosition = new Vector2d(mapWidth - 1, mapHeight - 1);
                } else if (newPosition.x < oldPosition.x && newPosition.y > oldPosition.y) {
                    newPosition = new Vector2d(mapWidth - 1, 0);
                } else if (newPosition.x > oldPosition.x && newPosition.y < oldPosition.y) {
                    newPosition = new Vector2d(0, mapHeight - 1);
            }}
        }
        return newPosition;
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal){
        posChangedForNAniamls++;
        if(!newPosition.equals(oldPosition) && !super.canMoveTo(newPosition)){
                Vector2d calcNewPos = calculateNewPosition(oldPosition,newPosition, animal);
                super.removeAnimal(oldPosition, animal);
                super.insertToAnimals(animal, calcNewPos);

        }
        if(posChangedForNAniamls==currentlyLivingAnimals) {
            System.out.println(animals);
            super.simulateDay();
        }
    }
}
