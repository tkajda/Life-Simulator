package agh.ics.oop.WorldClasses;

import agh.ics.oop.Interfaces.IPositionChangeObserver;

public class BorderlessMap extends Map implements IPositionChangeObserver {


    public BorderlessMap(int MapHeight, int MapWidth, double JungleRatio, int StartEnergy, int PlantEnergy, int MoveEnergy,boolean isMagic) {
        super(MapHeight, MapWidth, JungleRatio, StartEnergy, PlantEnergy, MoveEnergy, isMagic);
    }

    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    public Vector2d calculateNewPosition(Vector2d oldPosition, Vector2d newPosition) {
        Vector2d newPos = newPosition;
        if (!oldPosition.equals(newPosition)) {
            if (animals.get(oldPosition) != null) {
                if(super.canMoveTo(newPosition)) {
                    return newPos;
                }
                //corners

                if (newPosition.precedes(new Vector2d(0,0))) {
                    newPos = new Vector2d(mapWidth-1,mapHeight-1);
                }
                else if (newPosition.follows(new Vector2d(mapWidth-1,mapHeight-1))){
                    newPos = new Vector2d(0,0);
                }
                else if (newPosition.x>mapWidth-1 && newPosition.y<0) {
                    newPos = new Vector2d(0,mapHeight-1);
                }
                else if (newPosition.x<0 && newPosition.y>mapHeight-1) {
                    newPos = new Vector2d(mapWidth-1,0);
                }
                //left side
                else if (newPosition.x<0) {
                    newPos = new Vector2d(mapWidth-1,newPosition.y);
                }
                //right side
                else if (newPosition.x>mapWidth-1) {
                    newPos = new Vector2d(0, newPosition.y);
                }
                //top side
                else if (newPosition.y>mapHeight-1) {
                    newPos = new Vector2d(newPosition.x,0);
                }
                //bottom side
                else {
                    newPos = new Vector2d(newPosition.x,mapHeight-1);
                }

            }
        }
        return newPos;
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal){
        posChangedForNAniamls++;
        if(!newPosition.equals(oldPosition)){
                Vector2d calcNewPos = calculateNewPosition(oldPosition,newPosition);

                removeAnimal(oldPosition, animal);
                insertToAnimals(animal, calcNewPos);

                animal.setPosition(calcNewPos);
        }
        if(posChangedForNAniamls==currentlyLivingAnimals) {
            super.simulateDay();
        }
    }
}
