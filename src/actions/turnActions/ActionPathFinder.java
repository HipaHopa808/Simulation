package actions.turnActions;

import entities.Obstacle;
import map.Coordinates;
import map.Map;
import entities.Entity;

import java.sql.SQLOutput;
import java.util.*;


public class ActionPathFinder {


    public static Deque<Coordinates> findFoodPath(Map map, Entity creature, Class<?> food) {

        Deque<Coordinates> uncheckedCoordinates = new LinkedList();
        Set<Coordinates> checkedCoordinates = new HashSet<>();
        HashMap<Coordinates, Coordinates> path = new HashMap<>();
        Deque<Coordinates> foodPath = new LinkedList<>();
        Coordinates currentCoordinates = map.get(creature);
        Coordinates foodCoordinates = null;
        boolean isFoodFound = false;

        checkedCoordinates.add(currentCoordinates);
        List<Coordinates> nearestCoordinates = getNearestFreeCoordinates(map, currentCoordinates);
        List<Coordinates> nextCoordinates = getNextCoordinates(checkedCoordinates, nearestCoordinates);
        for (Coordinates nextCoordinate : nextCoordinates) {
            path.put(nextCoordinate, currentCoordinates);
        }

        uncheckedCoordinates.addAll(nextCoordinates);


        while (!isFoodFound && !uncheckedCoordinates.isEmpty()) {
            currentCoordinates = uncheckedCoordinates.poll();

            if (currentCoordinates != null) {
                checkedCoordinates.add(currentCoordinates);
                if (map.get(currentCoordinates) != null && food.isInstance(map.get(currentCoordinates))) {
                    isFoodFound = true;
                    foodCoordinates = currentCoordinates;
                    System.out.printf("\tЕда %s найдена!  Координаты [%s, %s]\n", food.toString(), currentCoordinates.getRow(), currentCoordinates.getCol());
                } else {
                    nearestCoordinates = getNearestFreeCoordinates(map, currentCoordinates);
                    nextCoordinates = getNextCoordinates(checkedCoordinates, nearestCoordinates);
                    for (Coordinates nextCoordinate : nextCoordinates) {
                        path.put(nextCoordinate, currentCoordinates);
                    }

                    uncheckedCoordinates.addAll(nextCoordinates);

                }
            } else {
                System.out.printf("Путь к еде не найден! Завершаем поиск для - %s", creature.toString());
                break;
            }

        }

        if (isFoodFound) {
            foodPath.add(foodCoordinates);
            Coordinates coordinates = foodCoordinates;
            while (path.get(coordinates) != null) {
                coordinates = path.get(coordinates);
                foodPath.addFirst(coordinates);
            }
        }


        return foodPath;
    }


    public static List<Coordinates> getNearestFreeCoordinates(Map map, Coordinates currentCoordinates) {
        List<Coordinates> nearCoordinates = new LinkedList<>();
        List<Coordinates> nonValidateCoordinates = new ArrayList<>();
        nonValidateCoordinates.add(getUpCoordinates(currentCoordinates));
        nonValidateCoordinates.add(getLeftCoordinates(currentCoordinates));
        nonValidateCoordinates.add(getDownCoordinates(currentCoordinates));
        nonValidateCoordinates.add(getRightCoordinates(currentCoordinates));
        for (Coordinates coordinates : nonValidateCoordinates) {
            try {
                map.validateCoordinates(coordinates);
                if (map.get(coordinates) == null || !(map.get(coordinates) instanceof Obstacle)) {
                    nearCoordinates.add(coordinates);
                }
            } catch (IndexOutOfBoundsException ex) {

            }
        }

        return nearCoordinates;
    }

    private static List<Coordinates> getNextCoordinates(Set<Coordinates> checkedCoordinates, List<Coordinates> nearestCoordinates) {
        List<Coordinates> nextCoordinates = new LinkedList<>();
        for (Coordinates coordinates : nearestCoordinates) {
            if (!checkedCoordinates.contains(coordinates)) {
                nextCoordinates.add(coordinates);
            }
        }

        return nextCoordinates;

    }

    private static Coordinates getUpCoordinates(Coordinates currentCoordinates) {
        return new Coordinates(currentCoordinates.getRow() + 1, currentCoordinates.getCol());
    }

    private static Coordinates getDownCoordinates(Coordinates currentCoordinates) {
        return new Coordinates(currentCoordinates.getRow() - 1, currentCoordinates.getCol());
    }

    private static Coordinates getLeftCoordinates(Coordinates currentCoordinates) {
        return new Coordinates(currentCoordinates.getRow(), currentCoordinates.getCol() - 1);
    }

    private static Coordinates getRightCoordinates(Coordinates currentCoordinates) {
        return new Coordinates(currentCoordinates.getRow(), currentCoordinates.getCol() + 1);
    }


}
