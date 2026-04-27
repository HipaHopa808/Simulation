package actions.initActions;

import actions.turnActions.ActionPathFinder;
import entities.*;
import map.Coordinates;
import map.Map;
import simulation.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class ActionGenerateMap {

    public static Map generateNewMap() {
        Map map = new Map(Parameters.MAP_ROWS.getParameter(), Parameters.MAP_COLS.getParameter());
        addTerrain(map);
        addGrass(map);
        addCreatures(map);
        return map;
    }

    public static void addGrass(Map map) {
        for (int i = 0; i < Parameters.GRASS_QUANTITY.getParameter(); i++) {
            Grass grass = new Grass();
            map.put(getRandomCoordinates(map), grass);
        }
    }

    public static void addTerrain(Map map) {
        for (int i = 0; i < Parameters.OBSTACLES_QUANTITY.getParameter(); i++) {
            Random randomObstacle = new Random();
            boolean isRock = randomObstacle.nextBoolean();
            Entity obstacle;
            if (isRock) {
                obstacle = new Rock();
            } else {
                obstacle = new Tree();
            }
            map.put(getRandomCoordinates(map), obstacle);
        }

    }

    public static void addCreatures(Map map) {
        addHerbivores(map);
        addPredators(map);
    }

    public static void addHerbivores(Map map) {
        for (int i = 0; i < Parameters.HERBIVORE_QUANTITY.getParameter(); i++) {
            Herbivore herbivore = new Herbivore(Parameters.HERBIVORE_SPEED.getParameter(), Parameters.HERBIVORE_HEALTH.getParameter());
            map.put(getRandomCoordinates(map), herbivore);
        }

    }

    public static void addPredators(Map map) {
        for (int i = 0; i < Parameters.PREDATOR_QUANTITY.getParameter(); i++) {
            Predator predator = new Predator(Parameters.PREDATOR_SPEED.getParameter(), Parameters.PREDATOR_HEALTH.getParameter(), Parameters.PREDATOR_ATTACK.getParameter());
            map.put(getRandomCoordinates(map), predator);
        }

    }


    private static Coordinates getRandomCoordinates(Map map) {

        Random random = new Random();
        int row = random.nextInt(0, map.getRows());
        int col = random.nextInt(0, map.getCols());
        Coordinates coordinates = new Coordinates(row, col);
        if (map.get(coordinates) == null) {
            return coordinates;
        } else {
            List<Coordinates> freeCoordinates = ActionPathFinder.getNearestFreeCoordinates(map, coordinates);
            while (freeCoordinates == null) {
                freeCoordinates = ActionPathFinder.getNearestFreeCoordinates(map, new Coordinates(coordinates.getRow() + 1, coordinates.getCol() + 1));
            }
            coordinates = freeCoordinates.get(0);

        }
        return coordinates;
    }
}
