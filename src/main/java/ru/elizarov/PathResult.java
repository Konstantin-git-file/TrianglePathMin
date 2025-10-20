package ru.elizarov;

import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class PathResult {
    int minSum;
    List<Integer> path;

    @Override
    public String toString() {
        return "Минимальный путь: %s = %d".formatted(
                path.stream().map(String::valueOf).collect(Collectors.joining(" → ")),
                minSum
        );
    }
}