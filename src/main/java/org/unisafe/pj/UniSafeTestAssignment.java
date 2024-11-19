package org.unisafe.pj;

import java.util.List;

public class UniSafeTestAssignment {

    public static final double kMulX = 7.0843;
    public static final double kMulY = 7.0855;

    public static void main(String[] args) {
        String filePath = "/Users/tomtam/Documents/blank/folder/uniSafeTest/camera_block.eps";

        System.out.println("Начало чтения фигур из EPS файла...");

        // Чтение фигур из EPS файла
        List<List<List<Integer>>> listOfFigures = EpsFileReader.getFiguresFromFile(filePath);

        // Проверка, считались ли фигуры
        if (listOfFigures == null || listOfFigures.isEmpty()) {
            System.out.println("Ошибка: Фигуры не считаны. Список фигур пуст или произошла ошибка при чтении.");
            return;
        } else {
            System.out.println("Фигуры успешно считаны. Количество фигур: " + listOfFigures.size());
        }

        // Удаление пустых фигур перед дальнейшей обработкой
        listOfFigures.removeIf(List::isEmpty);

        // Замыкание каждой фигуры путем добавления начальной точки в конец
        FigureUtils.closeFigures(listOfFigures);

        // Сортировка фигур по размеру, от самой маленькой к самой большой
        FigureUtils.sortFiguresBySize(listOfFigures);

        // Убедиться, что все фигуры ориентированы по часовой стрелке
        FigureUtils.orientFiguresClockwise(listOfFigures);

        // Настройка начальных точек фигур для обеспечения непрерывности между резами
        FigureUtils.adjustStartPoints(listOfFigures);

        // Печать итогового списка фигур
        System.out.println("Итоговый список фигур после всех преобразований:");
        FigureUtils.printFigures(listOfFigures);
    }
}
