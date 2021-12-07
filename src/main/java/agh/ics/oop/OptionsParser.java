package agh.ics.oop;


import java.util.ArrayList;
import java.util.List;


public class OptionsParser {

    public static List<MoveDirection> parse(String[] args) throws IllegalArgumentException {

        List<MoveDirection> directions = new ArrayList<>();

        for(String arg: args) {
            MoveDirection i = (switch(arg) {
                case "f" -> MoveDirection.FORWARD;
                case "b" -> MoveDirection.BACKWARD;
                case "r" -> MoveDirection.RIGHT;
                case "l" -> MoveDirection.LEFT;
                default -> throw new IllegalArgumentException(arg + " is not legal move specification");
            });
            directions.add(i);
        }
        return directions;
    }
}