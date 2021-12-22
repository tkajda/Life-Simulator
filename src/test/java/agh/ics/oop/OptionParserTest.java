package agh.ics.oop;

import agh.ics.oop.Console.OptionsParser;
import agh.ics.oop.Enums.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OptionParserTest {


    @Test
    void inputTest() {
        String[] input1 = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] input2 = {"f", "f", "f", "b", "b", "b", "r", "r", "r", "l", "l", "l"};
        String[] input3 = {};
        MoveDirection[] output11 = new MoveDirection[]{MoveDirection.BACKWARD, MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.RIGHT};
        MoveDirection[] output22 = {MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD,
                MoveDirection.BACKWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD,
                MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.RIGHT,
                MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.LEFT};
        MoveDirection[] output33 = {};

        List<MoveDirection> output1 = new ArrayList<>(Arrays.asList(output11));
        List<MoveDirection> output2 = new ArrayList<>(Arrays.asList(output22));
        List<MoveDirection> output3 = new ArrayList<>(Arrays.asList(output33));

        assertThrows(IllegalArgumentException.class, () -> OptionsParser.parse(input1));
        assertEquals(output2, OptionsParser.parse(input2));
        assertEquals(output3, OptionsParser.parse(input3));
    }
}