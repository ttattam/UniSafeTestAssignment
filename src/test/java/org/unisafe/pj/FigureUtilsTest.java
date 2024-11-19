package org.unisafe.pj;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FigureUtilsTest {

    @Test
    public void testCloseFigures() {
        // Создаем фигуру для проверки
        List<List<Integer>> figure = new ArrayList<>();
        figure.add(Arrays.asList(1, 1));
        figure.add(Arrays.asList(2, 2));
        figure.add(Arrays.asList(3, 3));

        // Помещаем эту фигуру в список фигур
        List<List<List<Integer>>> figures = new ArrayList<>();
        figures.add(figure);

        // Вызываем функцию, чтобы замкнуть фигуры
        FigureUtils.closeFigures(figures);

        // Проверяем, что первая точка добавлена в конец
        assertEquals(figure.get(0), figure.get(figure.size() - 1), "Фигура не замкнута корректно");

        // Если тест пройден, выводим сообщение на экран
        System.out.println("testCloseFigures прошел успешно");
    }

    @Test
    public void testSortFiguresBySize() {
        List<List<Integer>> smallFigure = new ArrayList<>();
        smallFigure.add(Arrays.asList(1, 1));
        smallFigure.add(Arrays.asList(2, 2));
        smallFigure.add(Arrays.asList(3, 3));

        List<List<Integer>> largeFigure = new ArrayList<>();
        largeFigure.add(Arrays.asList(1, 1));
        largeFigure.add(Arrays.asList(10, 10));
        largeFigure.add(Arrays.asList(20, 20));

        List<List<List<Integer>>> figures = new ArrayList<>();
        figures.add(largeFigure);
        figures.add(smallFigure);

        // Сортируем фигуры по размеру
        FigureUtils.sortFiguresBySize(figures);

        // Проверяем, что маленькая фигура теперь на первом месте
        assertEquals(figures.get(0), smallFigure, "Фигуры не отсортированы по размеру корректно");

        // Если тест пройден, выводим сообщение на экран
        System.out.println("testSortFiguresBySize прошел успешно");
    }
}
