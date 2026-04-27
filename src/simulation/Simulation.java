package simulation;

import actions.initActions.ActionGenerateMap;
import actions.turnActions.ActionRenderMap;
import entities.*;
import map.Coordinates;
import map.Map;

import java.util.HashSet;
import java.util.Set;

public class Simulation {
    private Map worldMap;
    private boolean isActive;
    private ActionRenderMap worldMapRender = new ActionRenderMap();

    public Simulation() {
        this.worldMap = ActionGenerateMap.generateNewMap();
        System.out.println("Карта сгенерирована!");
    }

    public void nextTurn(){

        System.out.println("Карта отрисована!");
        Set<Coordinates> allCoordinates = new HashSet<>(worldMap.getAllCoordinates());
        System.out.println("Получен список координат");
        boolean hasGrass = false;
        boolean hasHerbivore = false;
        boolean hasPredator = false;
        for(Coordinates coordinates : allCoordinates){
            Entity entity = worldMap.get(coordinates);
            if(entity instanceof Creature){
                if(entity instanceof Herbivore){
                    hasHerbivore = true;
                }
                if(entity instanceof Predator){
                    hasPredator = true;
                }
                Creature creature = (Creature) entity;
                creature.makeMove(worldMap);
                System.out.printf("Существо %s завершило ход.[%s, %s]\n", entity.toString(),worldMap.get(creature).getRow(),worldMap.get(creature).getRow());
            } else if(entity instanceof Grass) {
                hasGrass = true;
            }
        }
        if(!hasGrass){
            ActionGenerateMap.addGrass(worldMap);
        }
        if(!hasHerbivore){
            ActionGenerateMap.addHerbivores(worldMap);
        }
        if(!hasPredator){
            ActionGenerateMap.addPredators(worldMap);
        }

        worldMapRender.renderMap(worldMap);

    }

    public void startSimulation(){
        isActive = true;
        while (isActive){
            nextTurn();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void pauseSimulation(){
        isActive = false;
    }

}
