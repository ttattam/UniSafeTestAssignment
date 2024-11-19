package org.unisafe.pj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EpsFileReader {
    public static List<List<List<Integer>>> getFiguresFromFile(String filePath) {
        List<List<List<Integer>>> listOfFigures = new ArrayList<>();
        List<String> blocks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean reachedEndData = false;
            boolean reachedBeginData = false;
            boolean blockStarted = false;

            System.out.println("Чтение файла: " + filePath);

            // Чтение EPS файла построчно
            while ((line = reader.readLine()) != null) {
                if (!reachedBeginData) {
                    if (line.trim().startsWith("%%EndPageSetup")) {
                        reachedBeginData = true;
                        System.out.println("Найден %%EndPageSetup - начало релевантных данных.");
                    }
                } else if (!reachedEndData) {
                    if (line.startsWith("%ADO")) {
                        reachedEndData = true;
                        System.out.println("Найден %ADO - конец релевантных данных.");
                    } else {
                        // Начать новую фигуру при обнаружении "mo" или "m"
                        if (line.contains("mo") && Character.isDigit(line.charAt(0))) {
                            listOfFigures.add(new ArrayList<>());
                            blockStarted = true;
                            blocks.add(line);
                        } else if (line.contains("m") && Character.isDigit(line.charAt(0))) {
                            listOfFigures.add(new ArrayList<>());
                            blockStarted = true;
                            blocks.add(line);
                        } else if (line.trim().equals("cp") || line.trim().equals("@c") || line.trim().equals("@")) {
                            blockStarted = false;
                        } else if (blockStarted) {
                            blocks.add(line);
                        }
                    }
                } else {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка чтения EPS файла: " + e.getMessage());
            return new ArrayList<>();
        }

        System.out.println("Обработка блоков данных для извлечения фигур...");

        int currentFigureIndex = -1;
        for (String block : blocks) {
            String[] lineParts = block.split(" ");

            if (Objects.equals(lineParts[lineParts.length - 1], "mo") || Objects.equals(lineParts[lineParts.length - 1], "m")) {
                List<Integer> listN = new ArrayList<>();
                currentFigureIndex++;
                addNumericalValues(currentFigureIndex, listOfFigures, lineParts, listN);
            } else if (Objects.equals(lineParts[lineParts.length - 1], "li")) {
                List<Integer> listN = new ArrayList<>();
                addNumericalValues(currentFigureIndex, listOfFigures, lineParts, listN);
            } else if (Objects.equals(lineParts[lineParts.length - 1], "cv") || Objects.equals(lineParts[lineParts.length - 1], "C")) {
                List<Integer> listN = new ArrayList<>();
                addNumericalValues(currentFigureIndex, listOfFigures, lineParts, listN);
            }
        }

        System.out.println("Фигуры обработаны. Количество фигур: " + listOfFigures.size());
        return listOfFigures;
    }

    // Вспомогательный метод для разбора числовых значений и добавления их в список фигур
    private static void addNumericalValues(int currentFigureIndex, List<List<List<Integer>>> listOfFigures, String[] lineParts, List<Integer> listN) {
        for (int j = 0; j < lineParts.length - 1; j++) {
            if (lineParts[j].startsWith(".")) {
                lineParts[j] = "0" + lineParts[j];
            }
            double calk;
            if (j % 2 != 0) {
                calk = (Double.parseDouble(lineParts[j]) + 1.5) * UniSafeTestAssignment.kMulX;
            } else {
                calk = (Double.parseDouble(lineParts[j]) + 1.5) * UniSafeTestAssignment.kMulY;
            }
            int thisInt = (int) Math.round(calk);
            listN.add(thisInt);
        }
        listOfFigures.get(currentFigureIndex).add(listN);
        System.out.println("Добавлены координаты к фигуре: " + listN);
    }
}
