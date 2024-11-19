package org.unisafe.pj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FigureUtils {
    // Добавляет первую точку каждой фигуры в конец, чтобы гарантировать её замкнутость
    public static void closeFigures(List<List<List<Integer>>> figures) {
        for (List<List<Integer>> figure : figures) {
            List<Integer> firstPoint = new ArrayList<>(figure.get(0));
            figure.add(firstPoint);
        }
    }

    // Сортировка фигур по размеру в порядке возрастания
    public static void sortFiguresBySize(List<List<List<Integer>>> figures) {
        figures.sort(Comparator.comparingDouble(FigureUtils::calculateFigureSize));
    }

    // Убедиться, что все фигуры ориентированы по часовой стрелке
    public static void orientFiguresClockwise(List<List<List<Integer>>> figures) {
        for (List<List<Integer>> figure : figures) {
            if (!isClockwise(figure)) {
                Collections.reverse(figure); // Перевернуть фигуру, чтобы сделать её по часовой стрелке
            }
        }
    }

    // Настройка начальных точек фигур для минимизации расстояния между резами
    public static void adjustStartPoints(List<List<List<Integer>>> figures) {
        for (int i = 1; i < figures.size(); i++) {
            List<List<Integer>> prevFigure = figures.get(i - 1);
            List<List<Integer>> currentFigure = figures.get(i);

            List<Integer> lastPointPrev = prevFigure.get(prevFigure.size() - 1);
            int closestIndex = findClosestPoint(lastPointPrev, currentFigure);

            // Повернуть текущую фигуру так, чтобы она начиналась с ближайшей точки к концу предыдущей фигуры
            Collections.rotate(currentFigure, -closestIndex);
        }
    }

    // Печать координат всех фигур
    public static void printFigures(List<List<List<Integer>>> figures) {
        for (List<List<Integer>> figure : figures) {
            System.out.println(figure);
        }
    }

    // Удаление пустых фигур из списка
    public static void removeEmptyLists(List<List<List<Integer>>> figures) {
        figures.removeIf(List::isEmpty);
    }

    // Удаление фигур, которые не являются замкнутыми (где последняя точка не совпадает с первой)
    public static void removeNotCycledFigures(List<List<List<Integer>>> figures) {
        figures.removeIf(figure -> {
            int lastX = figure.get(figure.size() - 1).get(0);
            int lastY = figure.get(figure.size() - 1).get(1);
            int firstX = figure.get(0).get(0);
            int firstY = figure.get(0).get(1);
            return firstX != lastX || firstY != lastY;
        });
    }

    // Проверка, ориентирована ли фигура по часовой стрелке
    private static boolean isClockwise(List<List<Integer>> figure) {
        double sum = 0;
        for (int i = 0; i < figure.size() - 1; i++) {
            int x1 = figure.get(i).get(0);
            int y1 = figure.get(i).get(1);
            int x2 = figure.get(i + 1).get(0);
            int y2 = figure.get(i + 1).get(1);
            sum += (x2 - x1) * (y2 + y1); // Вычисление суммы для определения ориентации
        }
        return sum > 0;
    }

    // Вычисление размера фигуры путем суммирования расстояний между последовательными точками
    private static double calculateFigureSize(List<List<Integer>> figure) {
        double size = 0;
        for (int i = 0; i < figure.size() - 1; i++) {
            int x1 = figure.get(i).get(0);
            int y1 = figure.get(i).get(1);
            int x2 = figure.get(i + 1).get(0);
            int y2 = figure.get(i + 1).get(1);
            size += Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)); // Евклидово расстояние между точками
        }
        return size;
    }

    // Найти индекс точки в фигуре, которая находится ближе всего к заданной точке
    private static int findClosestPoint(List<Integer> point, List<List<Integer>> figure) {
        int minIndex = 0;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < figure.size(); i++) {
            List<Integer> currentPoint = figure.get(i);
            double distance = Math.sqrt(Math.pow(point.get(0) - currentPoint.get(0), 2) +
                    Math.pow(point.get(1) - currentPoint.get(1), 2)); // Вычисление расстояния до текущей точки
            if (distance < minDistance) {
                minDistance = distance;
                minIndex = i; // Обновить индекс, если найдена более близкая точка
            }
        }
        return minIndex;
    }
}
