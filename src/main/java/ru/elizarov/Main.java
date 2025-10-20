package ru.elizarov;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<List<Integer>> triangle = List.of(
                List.of(2),
                List.of(3, 4),
                List.of(6, 5, 7),
                List.of(4, 1, 8, 3)
        );

        PathResult result = TriangleMinPath.findMinPathWithTrace(triangle);
        System.out.println(result);

        int sum1 = TriangleMinPath.findMinPath(triangle);
        int sum2 = TriangleMinPath.findMinPathTopDown(triangle);
        int sum3 = TriangleMinPath.findMinPathInPlace(deepCopy(triangle));

        System.out.printf("Bottomup: %d, Topdown: %d, Inplace: %d%n", sum1, sum2, sum3);
        System.out.println("Все согласовано: " + (sum1 == sum2 && sum2 == sum3));
    }

    private static List<List<Integer>> deepCopy(List<List<Integer>> original) {
        return original.stream()
                .map(ArrayList::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}