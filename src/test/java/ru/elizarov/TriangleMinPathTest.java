package ru.elizarov;

import org.junit.jupiter.api.Test;
import ru.elizarov.exceptions.InvalidTriangleException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TriangleMinPathTest {

    @Test
    public void testBasicTriangle() {
        List<List<Integer>> triangle = Arrays.asList(
                Arrays.asList(2),
                Arrays.asList(3, 4),
                Arrays.asList(6, 5, 7),
                Arrays.asList(4, 1, 8, 3)
        );
        assertEquals(11, TriangleMinPath.findMinPath(triangle));
    }

    @Test
    public void testWithNegativeNumbers() {
        List<List<Integer>> triangle = Arrays.asList(
                Arrays.asList(-1),
                Arrays.asList(2, 3),
                Arrays.asList(1, -1, -3),
                Arrays.asList(4, 2, 1, 3)
        );
        assertEquals(0, TriangleMinPath.findMinPath(triangle));
    }

    @Test
    public void testSingleRow() {
        List<List<Integer>> triangle = Arrays.asList(Arrays.asList(5));
        assertEquals(5, TriangleMinPath.findMinPath(triangle));
    }

    @Test
    public void testEmptyTriangle() {
        List<List<Integer>> triangle = new ArrayList<>();
        assertThrows(InvalidTriangleException.class, () -> {
            TriangleMinPath.findMinPath(triangle);
        });
    }

    @Test
    public void testNullTriangle() {
        assertThrows(InvalidTriangleException.class, () -> {
            TriangleMinPath.findMinPath(null);
        });
    }

    @Test
    public void testInvalidRowSize() {
        List<List<Integer>> triangle = Arrays.asList(
                Arrays.asList(1),
                Arrays.asList(2, 3, 4) // должно быть 2 элемента
        );
        assertThrows(InvalidTriangleException.class, () -> {
            TriangleMinPath.findMinPath(triangle);
        });
    }
}