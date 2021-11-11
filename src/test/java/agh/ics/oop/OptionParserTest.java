package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class OptionParserTest {


    @Test
    void inputTest() {
        String[] input1 = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] input2 = {"f", "f", "f", "x", "x", "x", "b", "b", "b", "r", "x", "x", "x", "r", "r", "l", "l", "l", "x", "x", "x"};
        String[] input3 = {};

        MoveDirection[] output1 = {MoveDirection.BACKWARD, MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.RIGHT};
        MoveDirection[] output2 = {MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.FORWARD,
                MoveDirection.BACKWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD,
                MoveDirection.RIGHT, MoveDirection.RIGHT, MoveDirection.RIGHT,
                MoveDirection.LEFT, MoveDirection.LEFT, MoveDirection.LEFT};
        MoveDirection[] output3 = {};

        assertArrayEquals(output1, OptionsParser.parse(input1));
        assertArrayEquals(output2, OptionsParser.parse(input2));
        assertArrayEquals(output3, OptionsParser.parse(input3));
    }
}