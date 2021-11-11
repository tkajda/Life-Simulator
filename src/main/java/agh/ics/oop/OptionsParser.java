package agh.ics.oop;


import java.util.ArrayList;


public class OptionsParser {

    public static MoveDirection[] parse(String[] args) {
        int len = 0;
        ArrayList<String> tab = new ArrayList<String>();
        for(String arg: args) {
            if (arg.equals("r") || arg.equals("b") || arg.equals("f") || arg.equals("l")) {
                tab.add(arg);
                len++;
            }}

        MoveDirection[] directions = new MoveDirection[len];
        int i = 0;

        for(String arg: tab) {
            directions[i] = (switch(arg) {
                case "f" -> MoveDirection.FORWARD;
                case "b" -> MoveDirection.BACKWARD;
                case "r" -> MoveDirection.RIGHT;
                case "l" -> MoveDirection.LEFT;
                default -> null;
            });
            i++;
        }
        return directions;

    }




}
