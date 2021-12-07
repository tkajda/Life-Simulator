package agh.ics.oop;

import com.sun.source.tree.Tree;
import javafx.util.Pair;
import java.util.*;

public class MapBoundary implements IPositionChangeObserver {

    SortedSet<Pair<Vector2d, Object>> objectsX;
    SortedSet<Pair<Vector2d, Object>> objectsY;

    public void addToMap(Object o, Vector2d position) {
        Pair <Vector2d, Object> para = new Pair<>(position, o);
        System.out.println(para);
        //objectsX.add(para);
//        objectsY.add(para);
//        System.out.println("y" + objectsY);
//        System.out.println("x" + objectsX);
    }



    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }
}
