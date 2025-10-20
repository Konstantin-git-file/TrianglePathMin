package ru.elizarov;

import lombok.experimental.UtilityClass;
import ru.elizarov.exceptions.InvalidTriangleException;

import java.util.*;
import java.util.stream.IntStream;

/**
 * dP снизу вверх (основной, O(n) памяти)
 * dP сверху вниз (альтернатива, O(n²) памяти)
 * Inplace модификация (изменяет вход, O(1) доп. памяти)
 * восстановление минимального пути
 */
@UtilityClass
public class TriangleMinPath {
    /**
     * @param triangle треугольник, где triangle[i] содержит i+1 элементов
     * @return минимальная сумма пути от вершины до основания
     * @throws InvalidTriangleException если структура треугольника некорректна
     */
    public int findMinPath(List<List<Integer>> triangle) {
        return findMinPathBottomUp(triangle);
    }

    /**
     * dP снизу вверх, использует O(n) памяти
     * @throws InvalidTriangleException если структура треугольника некорректна
     */
    public int findMinPathBottomUp(List<List<Integer>> triangle) {
        validateTriangle(triangle);

        int n = triangle.size();
        int[] dp = triangle.get(n - 1).stream().mapToInt(Integer::intValue).toArray();

        for (int i = n - 2; i >= 0; i--) {
            List<Integer> row = triangle.get(i);
            for (int j = 0; j < row.size(); j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + row.get(j);
            }
        }
        return dp[0];
    }

    /**
     * @throws InvalidTriangleException если структура треугольника некорректна
     */
    public int findMinPathTopDown(List<List<Integer>> triangle) {
        validateTriangle(triangle);

        int n = triangle.size();
        int[][] dp = new int[n][n];
        dp[0][0] = triangle.get(0).get(0);

        for (int i = 1; i < n; i++) {
            List<Integer> row = triangle.get(i);
            for (int j = 0; j <= i; j++) {
                int fromLeft = (j > 0) ? dp[i - 1][j - 1] : Integer.MAX_VALUE;
                int fromAbove = (j < i) ? dp[i - 1][j] : Integer.MAX_VALUE;
                dp[i][j] = Math.min(fromLeft, fromAbove) + row.get(j);
            }
        }

        return Arrays.stream(dp[n - 1], 0, n).min().orElseThrow(
                () -> new InvalidTriangleException("Пустая последняя строка")
        );
    }

    /**
     * время O(n2),память: O(1)
     * @throws InvalidTriangleException если структура треугольника некорректна
     */
    public int findMinPathInPlace(List<List<Integer>> triangle) {
        validateTriangle(triangle);

        for (int i = triangle.size() - 2; i >= 0; i--) {
            List<Integer> current = triangle.get(i);
            List<Integer> below = triangle.get(i + 1);
            for (int j = 0; j < current.size(); j++) {
                current.set(j, current.get(j) + Math.min(below.get(j), below.get(j + 1)));
            }
        }
        return triangle.get(0).get(0);
    }

    /**
     * восстановление минимального пути
     * @throws InvalidTriangleException если структура треугольника некорректна
     */
    public PathResult findMinPathWithTrace(List<List<Integer>> triangle) {
        validateTriangle(triangle);

        int n = triangle.size();
        int[][] dp = new int[n][n];
        int[][] next = new int[n][n];

        IntStream.range(0, n).forEach(j -> dp[n - 1][j] = triangle.get(n - 1).get(j));

        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                int left = dp[i + 1][j];
                int right = dp[i + 1][j + 1];
                if (left <= right) {
                    dp[i][j] = left + triangle.get(i).get(j);
                    next[i][j] = j;
                } else {
                    dp[i][j] = right + triangle.get(i).get(j);
                    next[i][j] = j + 1;
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        int col = 0;
        for (int i = 0; i < n; i++) {
            path.add(triangle.get(i).get(col));
            if (i < n - 1) col = next[i][col];
        }
        return new PathResult(dp[0][0], path);
    }

    /**
     * валидация структуры треугольника
     * Бросает исключение при нарушении условий
     * @throws InvalidTriangleException если треугольник некорректен
     */
    private void validateTriangle(List<List<Integer>> triangle) {
        if (triangle == null || triangle.isEmpty()) {
            throw new InvalidTriangleException("Треугольник не может быть null или пустым");
        }
        for (int i = 0; i < triangle.size(); i++) {
            List<Integer> row = triangle.get(i);
            if (row == null) {
                throw new InvalidTriangleException("Строка " + i + " не может быть null");
            }
            if (row.size() != i + 1) {
                throw new InvalidTriangleException(
                        "Строка " + i + " должна содержать " + (i + 1) + " элемент(ов), но содержит " + row.size()
                );
            }
        }
    }
}