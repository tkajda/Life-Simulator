package agh.ics.oop;

import com.sun.source.tree.Tree;
import javafx.util.Pair;
import java.util.*;

public class MapBoundary implements IPositionChangeObserver {

    private final SortedSet<Pair<Vector2d, Object>> objectsX = new TreeSet<>(MapBoundary::compareVectorsX);
    private final SortedSet<Pair<Vector2d, Object>> objectsY = new TreeSet<>(MapBoundary::compareVectorsY);


    public void addToMap( Vector2d position, Object o) {
        Pair <Vector2d, Object> para = new Pair<>(position, o);
        objectsX.add(para);
        objectsY.add(para);
    }


    public Vector2d getBottomLeft(){

        return new Vector2d(objectsX.first().getKey().x,objectsY.first().getKey().y);

    }
    public Vector2d getTopRight(){
        return  new Vector2d(objectsX.last().getKey().x, objectsY.last().getKey().y);
    }




    private static int compareObjects(Pair<Vector2d, Object> v1,Pair<Vector2d, Object> v2) {
        if (v1.getKey().equals(v2.getKey()) && v1.getValue() instanceof Animal && v2.getValue() instanceof Animal ) {
            return 0;}
        if (v1.getValue() instanceof Animal) {
            return 1;
        }

        return -1;
    }


    private static int compareVectorsX(Pair<Vector2d, Object> v1, Pair<Vector2d, Object> v2) {
        if (v1.getKey().x < v2.getKey().x) {
            return -1;
        }
        else if (v1.getKey().x > v2.getKey().x) {
            return 1;
        }
        else if (v1.getKey().y == v2.getKey().y){
            return compareObjects(v2,v1);
        }
        else {
            return compareVectorsY(v1, v2);
        }
    }

    private static int compareVectorsY(Pair<Vector2d, Object> v1, Pair<Vector2d, Object> v2) {
        if (v1.getKey().y < v2.getKey().y) {
            return -1;
        }
        else if (v1.getKey().y > v2.getKey().y) {
            return 1;
        }
        else if (v1.getKey().x== v2.getKey().x){
            return compareObjects(v2,v1);
        }
        else {
            return compareVectorsX(v1, v2);
        }
    }


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

        for(Pair<Vector2d, Object> para: objectsX){

            if (para.getKey().equals(oldPosition) && para.getValue() instanceof Animal) {
                objectsX.remove(para);
                objectsY.remove(para);
                addToMap(newPosition, para.getValue());
                break;
            }
        }
    }
}
