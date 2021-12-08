package agh.ics.oop;

import com.sun.source.tree.Tree;
import javafx.util.Pair;
import java.util.*;

public class MapBoundary implements IPositionChangeObserver {

    private final SortedSet<Pair<Vector2d, Object>> objectsX = new TreeSet<>(MapBoundary::compareVectorsX);
    private final SortedSet<Pair<Vector2d, Object>> objectsY = new TreeSet<>(MapBoundary::compareVectorsY);

    public void addToMap(Object o, Vector2d position) {
        Pair <Vector2d, Object> para = new Pair<>(position, o);

        objectsX.add(para);
        objectsY.add(para);
        System.out.println("y" + objectsY);
        System.out.println("x" + objectsX);
    }



    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }

    private static int compareObjects(Pair<Vector2d, Object> v1) {
        System.out.println(v1);

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
            return compareObjects(v2);
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
            System.out.println("yyyyyyyyyyyyyyyyyy");
            return compareObjects(v2);
        }
        else {
            return compareVectorsX(v1, v2);
        }
    }


}
